package net.myanmarhub.collabra.backend.api;

public class Config {
    public static final String VERSION = "v1";
    public static final String DESCRIPTION = "API access for "
            + "Collabra!";
    public static final String SCOPE_EMAIL = "https://www.googleapis.com/auth/userinfo.email";
    public static final String CLIENT_WEB = "699213652060-3pmo1b07noahu30l670esplhvct45k03.apps.googleusercontent.com";
    public static final String CLIENT_ANDROID_DEBUG =
            "699213652060-j1sr80drqta51r7sb1ieipd1j9ceqnsf.apps.googleusercontent.com";
    public static final String AUDIENCE_DEBUG = CLIENT_WEB;

    /**
     * Need this value to test from API Explorer *
     */
    public static final String CLIENT_API_EXPLORER = "com.google.api.server.spi" +
            ".Constant.API_EXPLORER_CLIENT_ID";

}


