package com.examples.user.newserver.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.examples.user.newserver.Debug;

/**
 * Created by User on 2/5/2016.
 */
public class Dbase extends SQLiteOpenHelper {
    private static final String LOGCAT = null;
    public static String Tablename = "Urlinfo";
    public static String Tablename2 = "Topinfo";
    public static String ID = "_id";
    public static final String colm = "text";
  //  public static final String col2="date";
    public static  String colm3="image";
    public static  String url="url";
    public static  String description="description";

    static final String CREATE_DB_TABLE = ("CREATE TABLE " + Tablename + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, text TEXT,image TEXT)");
    static final String CREATE_DB_TABLE2 = ("CREATE TABLE " + Tablename2 + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, description TEXT,url TEXT)");


    public Dbase(Context ctx) {
        super(ctx, "New Project.db", null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query;
        //query = "CREATE TABLE "+Tablename +"( _id INTEGER PRIMARY KEY AUTOINCREMENT, colm TEXT)";
        db.execSQL(CREATE_DB_TABLE);
        db.execSQL(CREATE_DB_TABLE2);

        Debug.v("table created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query,query2;
        query = "DROP TABLE IF EXISTS" + Tablename;
        query2 = "DROP TABLE IF EXISTS" + Tablename2;
        db.execSQL(query);
        db.execSQL(query2);
        onCreate(db);
    }

}
