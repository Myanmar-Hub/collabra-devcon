package net.myanmarhub.collabra.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Tin Htoo Aung (Myanmar Hub) on 29/10/13.
 */
public class SQDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "myanhub_collabra.sql";
    public static final Integer DB_VERSION = 1;
    private Context mContext;
    private static SQLiteDatabase db;


    public SQDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            InputStream in = mContext.getAssets().open("myanhub_collabra.sql");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int i;
            try {
                i = in.read();
                while (i != -1) {
                    out.write(i);
                    i = in.read();
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] sqls = out.toString().split("###");
            for (String sql : sqls) {
                sqLiteDatabase.execSQL(sql);
            }
        } catch (IOException e) {
            Log.e("DB", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
    }
}
