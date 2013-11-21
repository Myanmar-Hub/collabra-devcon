package net.myanmarhub.collabra.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.appspot.myanhub_collabra.collabra.model.Conversation;
import com.appspot.myanhub_collabra.collabra.model.User;
import com.google.api.client.util.DateTime;

/**
 * Tin Htoo Aung (Myanmar Hub) on 21/11/13.
 */
public class ConversationDAO extends BaseDAO<Conversation> {

    public static final String TABLE = "Conversation";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_SENDER_ID = "senderId";
    public static final String FIELD_MESSAGE = "message";
    public static final String FIELD_SEND_AT = "sendAt";

    protected ConversationDAO(Context context) {
        super(context, TABLE);
    }

    @Override
    public Conversation parse(Cursor cursor) {
        Conversation conversation = new Conversation();
        conversation.setId(cursor.getLong(cursor.getColumnIndex(FIELD_ID)));
        conversation.setMessage(cursor.getString(cursor.getColumnIndex(FIELD_MESSAGE)));
        User user = new User();
        user.setId(cursor.getLong(cursor.getColumnIndex(FIELD_SENDER_ID)));
        conversation.setSender(user);
        conversation.setSendAt(new DateTime(cursor.getLong(cursor.getColumnIndex(FIELD_SEND_AT))));
        return conversation;
    }

    @Override
    public ContentValues toContentValues(Conversation o) {
        ContentValues values = new ContentValues();
        values.put(FIELD_ID, o.getId());
        values.put(FIELD_MESSAGE, o.getMessage());
        values.put(FIELD_SENDER_ID, o.getSender().getId());
        values.put(FIELD_SEND_AT, o.getSendAt().getValue());
        return values;
    }

    @Override
    public Conversation fromContentValues(ContentValues values) {
        Conversation conversation = new Conversation();
        conversation.setId(values.getAsLong(FIELD_ID));
        conversation.setMessage(values.getAsString(FIELD_MESSAGE));
        User user = new User();
        user.setId(values.getAsLong(FIELD_SENDER_ID));
        conversation.setSender(user);
        conversation.setSendAt(new DateTime(values.getAsLong(FIELD_SEND_AT)));
        return conversation;
    }
}
