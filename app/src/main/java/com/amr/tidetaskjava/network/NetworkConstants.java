package com.amr.tidetaskjava.network;


import com.amr.tidetaskjava.BuildConfig;

public class NetworkConstants {

    public static final boolean QA = BuildConfig.QA;

    /**
     * The base url of the web service.
     */

    //Server of production
    public static final String BASE_URL = "https://maps.googleapis.com";

    //Server of staging
    public static final String BASE_URL_QA = "https://maps.googleapis.com";

    public static String BASE_URL_API = getBaseUrl() + "/";

    public static String getBaseUrl() {
        if (QA) {
            return BASE_URL_QA;
        } else {
            return BASE_URL;
        }
    }
}
