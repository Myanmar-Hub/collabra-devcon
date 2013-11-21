package net.myanmarhub.collabra.provider;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Tin Htoo Aung (Myanmar Hub) on 20/11/13.
 */
public class CollabraKind {

    public static final String AUTHORITY = "net.myanmarhub.collabra.provider";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static class User {

        public static final String USER = "user";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(CollabraKind.CONTENT_URI, USER);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + USER;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + USER;
    }

    public static class Conversation {

        public static final String CONVERSATION = "conversation";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(CollabraKind.CONTENT_URI, CONVERSATION);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONVERSATION;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONVERSATION;
    }
}
