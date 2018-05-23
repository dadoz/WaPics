package com.application.dave.wapictures.data.cursor;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.application.dave.wapictures.data.TrackDataSource;
import com.application.dave.wapictures.data.model.Profile;

import org.joda.time.DateTime;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class TrackCursorDataSource implements TrackDataSource {
    private final WeakReference<Context> context;

    @Inject
    public TrackCursorDataSource(Context context) {
        this.context = new WeakReference<>(context);
    }

    @Override
    public Observable<List<Profile>> getProfiles(String avatarId) {
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
            profile.setAvatarUrl(photoUri.toString());
            profile.setDate(new DateTime().toString());
        }
        return Observable.just(Arrays.asList(profile));
    }

    @Override
    public void setTracks(List<Profile> stargazers, String paramsKey) {

    }

    @Override
    public boolean hasTracks(String paramsKey) {
        return false;
    }
}
