package com.examples.user.newserver;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.examples.user.newserver.Table1.JsonModel;
import com.examples.user.newserver.DataHandler.Jsonmodel2;
import com.examples.user.newserver.Database.Contentprovider;
import com.examples.user.newserver.Database.Dbase;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by User on 2/11/2016.
 */
public class InsertdatatoDb {
    //static List<Integer> img = new ArrayList<>();

    public static void bottom(ArrayList<JsonModel> data,Context ctx) {
        for(int i=0;i<data.size();i++) {
            ContentValues values = new ContentValues();

            values.put(Dbase.colm, data.get(i).getText());
             values.put(Dbase.colm3, data.get(i).getUrl());
            ContentResolver content = ctx.getContentResolver();
            content.insert(Contentprovider.CONTENT_URI, values);
        }}
    public static void top(ArrayList<Jsonmodel2> data,Context ctx) {
        for(int i=0;i<data.size();i++) {
            ContentValues values = new ContentValues();

            values.put(Dbase.url, data.get(i).getImageurl());
            values.put(Dbase.description, data.get(i).getDescription());
            ContentResolver content = ctx.getContentResolver();
          Uri uri= content.insert(Contentprovider.CONTENT_URI2, values);

        }

    }

}
