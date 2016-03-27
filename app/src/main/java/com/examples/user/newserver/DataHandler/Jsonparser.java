package com.examples.user.newserver.DataHandler;


import android.util.Log;

import com.examples.user.newserver.Table1.JsonModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by User on 3/21/2016.
 */
public class Jsonparser {
    static Gson gson;
    static ArrayList<JsonModel> al = new ArrayList<>();
    static ArrayList<Jsonmodel2> a2 = new ArrayList<>();
    private static ArrayList<JsonModel> array;
    private static ArrayList<Jsonmodel2> array2;
    // private static JsonModel post;

    public static ArrayList parse1(String response) throws Exception {
        gson = new Gson();
        try {
            Type listType = new TypeToken<ArrayList<JsonModel>>() {
            }.getType();
         //  if( listType.getClass()instanceof JsonModel){

            array = new GsonBuilder().create().fromJson(response, listType);
            //   postList=new StringBuffer();
            for (JsonModel post : array) {

                al.add(post);
            }
            return al;
        } catch (Exception e) {
            Log.e("parse object", "Database is object rather than array");
            return null;
        }
    }
    public static ArrayList parse2(String response) throws Exception {
        gson = new Gson();
        try {
            Type listType = new TypeToken<ArrayList<Jsonmodel2>>() {
            }.getType();
            array2= new GsonBuilder().create().fromJson(response, listType);
            //   postList=new StringBuffer();
            for (Jsonmodel2 post : array2) {

                a2.add(post);
            }
            return a2;
        } catch (Exception e) {
            Log.e("parse object", "Database is object rather than array");
            return null;
        }
    }

}