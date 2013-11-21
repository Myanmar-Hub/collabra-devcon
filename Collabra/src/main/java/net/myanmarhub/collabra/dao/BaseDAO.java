package net.myanmarhub.collabra.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Tin Htoo Aung (Myanmar Hub) on 30/10/13.
 */
public abstract class BaseDAO<T> {

    protected static SQLiteDatabase db;
    private SQDBHelper helper;
    private Context mContext;
    private String TABLE;

    protected BaseDAO(Context context, String table) {
        this.TABLE = table;
        this.mContext = context;
        this.helper = new SQDBHelper(mContext);
        if (db == null || db.isOpen() == false) {
            db = helper.getWritableDatabase();
        }
    }

    public Context getContext() {
        return mContext;
    }

    public void close() {
        this.db.close();
    }

    public Long insert(T object) {
        return db.insert(TABLE, null, toContentValues(object));
    }

    public Cursor getAll(String[] projection, String sortOrder) {
        return db.query(TABLE, projection, null, null, null, null, sortOrder);
    }

    public Cursor getById(Long id) {
        return db.query(TABLE, null, "_id = ?", new String[]{String.valueOf(id)}, null, null, null);
    }

    public int update(T object, Long id) {
        return db.update(TABLE, toContentValues(object), "_id = ?", new String[]{String.valueOf(id)});
    }

    public int delete(Long id) {
        return db.delete(TABLE, "_id = ?", new String[]{String.valueOf(id)});
    }

    public int deleteAll() {
        return db.delete(TABLE, null, null);
    }

    public abstract T parse(Cursor cursor);

    public T toObject(Cursor cursor) {
        if (cursor.moveToFirst()) {
            return parse(cursor);
        }
        return null;
    }

    public List toObjects(Cursor cursor) {
        List<T> result = new ArrayList<T>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                result.add(parse(cursor));
            }
        }
        return result;
    }


    public abstract ContentValues toContentValues(T o);

    public abstract T fromContentValues(ContentValues values);
}
