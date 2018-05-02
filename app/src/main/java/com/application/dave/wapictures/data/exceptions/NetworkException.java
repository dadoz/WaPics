package com.application.dave.wapictures.data.exceptions;


import com.application.dave.wapictures.R;

public class NetworkException extends RuntimeException {


    private NetworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getErrorString() {
        return R.string.error_network_no_network;
    }

    public static NetworkException networkError(Throwable error) {
        return new NetworkException("Network exception", error);
    }
}