package com.application.dave.wapictures.data.cursor;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.application.dave.wapictures.data.TrackDataSource;
import com.application.dave.wapictures.data.model.Profile;

import org.joda.time.DateTime;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class TrackCursorDataSource implements TrackDataSource {
    private final WeakReference<Context> context;
    private Object photoUriContentProvider;

    @Inject
    public TrackCursorDataSource(Context context) {
        this.context = new WeakReference<>(context);
    }

    @Override
    public Observable<List<Profile>> getProfiles(String avatarId) {
        List list = new ArrayList<>();
        getPhotoBitmapByContentProvider(list);
        return Observable.just(list);
    }

    @Override
    public void setTracks(List<Profile> stargazers, String paramsKey) {

    }

    @Override
    public boolean hasTracks(String paramsKey) {
        return false;
    }

    /**
     * TODO move to utils
     * @return
     */
    public Bitmap getPhotoBitmapByContentProviderOld() {
        ContentResolver cr = context.get().getContentResolver();

        Cursor contactCursor = cr.query(
                ContactsContract.RawContacts.CONTENT_URI,
                new String[] { ContactsContract.RawContacts._ID, ContactsContract.RawContacts.CONTACT_ID } ,
                ContactsContract.RawContacts.ACCOUNT_TYPE + "= ?",
                new String[] { "com.whatsapp" }, null);
        Profile profile = new Profile();
        if (contactCursor  != null && contactCursor .moveToFirst()) {
            String whatsappContactId = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID));
            Uri photoId = ContentUris.withAppendedId(ContactsContract.RawContacts.CONTENT_URI, Long.parseLong(whatsappContactId));
            Uri photoUri = Uri.withAppendedPath(photoId, ContactsContract.RawContacts.DisplayPhoto.CONTENT_DIRECTORY);
            AssetFileDescriptor afd = null;
            try {
                afd = cr.openAssetFileDescriptor(photoUri, "r");
                FileDescriptor fileDescriptor = afd.getFileDescriptor();
                return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    afd.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * TODO move to utils
     * @param context
     * @param photoUri
     * @return
     */
    public Bitmap getBitmapFromContentUri(Context context, Uri photoUri) {
        Log.e(getClass().getName(), "------> photo uri: " + photoUri.toString());
        ContentResolver cr = context.getContentResolver();
        try {
            InputStream is = cr.openInputStream(photoUri);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * TODO move to utils
     * @param context
     * @param bitmap
     * @return
     */
    public Uri getImageUri(Context context, Bitmap bitmap) {
        Log.e(getClass().getName(), bitmap == null ? "------> bitmap null" : ":)");
        if (bitmap == null)
            return new Uri.Builder().appendPath("").build();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        Log.e(getClass().getName(), "------> path uri: " + path);
        return Uri.parse(path);
    }

    /**
     *
     * @param list
     */
    public void getPhotoBitmapByContentProvider(List list) {
        //This class provides applications access to the content model.
        ContentResolver cr = context.get().getContentResolver();

        //RowContacts for filter Account Types
        Cursor contactCursor = cr.query(
                ContactsContract.RawContacts.CONTENT_URI,
                new String[]{ContactsContract.RawContacts._ID,
                        ContactsContract.RawContacts.CONTACT_ID},
                ContactsContract.RawContacts.ACCOUNT_TYPE + "= ?",
                new String[]{"com.whatsapp"},
                null);

        //ArrayList for Store Whatsapp Contact
        ArrayList<String> myWhatsappContacts = new ArrayList<>();

        if (contactCursor != null) {
            if (contactCursor.getCount() > 0) {
                if (contactCursor.moveToFirst()) {
                    do {
                        //whatsappContactId for get Number,Name,Id ect... from  ContactsContract.CommonDataKinds.Phone
                        String whatsappContactId = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID));

                        if (whatsappContactId != null) {
                            //Get Data from ContactsContract.CommonDataKinds.Phone of Specific CONTACT_ID
                            Cursor whatsAppContactCursor = cr.query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    new String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                                            ContactsContract.CommonDataKinds.Phone.NUMBER,
                                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                    new String[]{whatsappContactId}, null);

                            if (whatsAppContactCursor != null && list.size() == 0) {
                                whatsAppContactCursor.moveToFirst();
                                String id = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                                String name = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                String number = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                Uri photoUri = ContentUris.withAppendedId(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, Long.parseLong(id));

                                whatsAppContactCursor.close();

                                //Add Number to ArrayList
                                myWhatsappContacts.add(number);

                                String avatarUrl = getImageUri(context.get(), getBitmapFromContentUri(context.get(), photoUri)).toString();
                                list.add(buildProfile(id, name, number, avatarUrl));
                            }
                        }
                    } while (contactCursor.moveToNext());

                    //cursor close
                    contactCursor.close();
                }
            }
        }

        Log.e(getClass().getName(), " WhatsApp contact size :  " + myWhatsappContacts.size());
    }

    private Profile buildProfile(String id, String name, String number, String photoUri) {
        //todo move to builder
        Profile profile = new Profile();
        profile.setId(id);
        profile.setName(name);
        profile.setDate(new DateTime().toString());
        profile.setAvatarUrl(photoUri);
        return profile;
    }

}
