package com.examples.user.newserver.Database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.Switch;

import com.examples.user.newserver.Debug;

import static com.examples.user.newserver.Database.Dbase.Tablename;
import static com.examples.user.newserver.Database.Dbase.Tablename2;


/**
 * Created by User on 2/8/2016.
 */
public class Contentprovider extends ContentProvider {
    private Dbase db;
    private SQLiteDatabase database;
    private static final String AUTHORITY = "com.examples.user.newserver.Database.Contentprovider";
    public static final int uricode = 100;
    public static final int TUTORIAL_ID = 110;
    public static final int TUTORIAL_ID2 = 120;
    public static final int uricode2 = 130;


    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + Tablename);
    public static final Uri CONTENT_URI2 = Uri.parse("content://" + AUTHORITY
            + "/" + Tablename2);

    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/mt-tutorial";
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/mt-tutorial";
    private final static UriMatcher sURIMatcher;

    static {
        sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        sURIMatcher.addURI(AUTHORITY, Tablename, uricode);
        sURIMatcher.addURI(AUTHORITY, Tablename + "/#", TUTORIAL_ID);
        sURIMatcher.addURI(AUTHORITY, Tablename2, uricode2);
        sURIMatcher.addURI(AUTHORITY, Tablename2 + "/#", TUTORIAL_ID2);
    }

    @Override
    public boolean onCreate() {
        db = new Dbase(getContext());
        return true;

    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        Cursor c;
        // int uriType = sURIMatcher.match(uri);
        database = db.getReadableDatabase();
        // c = database.query(db.Tablename, projection, selection, selectionArgs, null, null, sortOrder);
        switch (sURIMatcher.match(uri)) {
            case uricode:
                c = database.rawQuery("select _id, text, image from Urlinfo group by text order by _id", null);
                break;
            case uricode2:
                c = database.rawQuery("select _id, description, url from Topinfo group by description order by _id", null);
                break;
            default:
                throw new SQLException("Failed to insert row into " + uri);
        }

        String a = DatabaseUtils.dumpCursorToString(c);
        Debug.v("error" + a);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        database = db.getWritableDatabase();
        switch (sURIMatcher.match(uri)) {
            case uricode:
                database.insert(Tablename, null, values);
                break;
            case uricode2:
                database.insert(Tablename2, null, values);
                break;
            default:
                throw new SQLException("Failed to insert row into " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return null;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        // database.delete(db.Tablename, selection, selectionArgs);
        // String[] whereArgs = new String[] { String.valueOf(selectionArgs) };
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

}
