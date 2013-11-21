package net.myanmarhub.collabra.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.appspot.myanhub_collabra.collabra.model.User;
import com.google.api.client.util.DateTime;

/**
 * Tin Htoo Aung (Myanmar Hub) on 21/11/13.
 */
public class UserDAO extends BaseDAO<User> {

    public static final String TABLE = "User";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_LAST_LOGIN_TIME = "lastLoginTime";

    public UserDAO(Context context) {
        super(context, TABLE);
    }

    @Override
    public User parse(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getLong(cursor.getColumnIndex(FIELD_ID)));
        user.setUsername(cursor.getString(cursor.getColumnIndex(FIELD_USERNAME)));
        user.setLastLoginTime(new DateTime(
                cursor.getLong(cursor.getColumnIndex(FIELD_LAST_LOGIN_TIME))
        ));
        return user;
    }

    @Override
    public ContentValues toContentValues(User o) {
        ContentValues values = new ContentValues();
        values.put(FIELD_ID, o.getId());
        values.put(FIELD_LAST_LOGIN_TIME, o.getLastLoginTime().getValue());
        values.put(FIELD_USERNAME, o.getUsername());
        return values;
    }

    @Override
    public User fromContentValues(ContentValues values) {
        User user = new User();
        user.setId(values.getAsLong(FIELD_ID));
        user.setUsername(values.getAsString(FIELD_USERNAME));
        user.setLastLoginTime(new DateTime(
                values.getAsLong(FIELD_LAST_LOGIN_TIME))
        );
        return user;
    }
}
