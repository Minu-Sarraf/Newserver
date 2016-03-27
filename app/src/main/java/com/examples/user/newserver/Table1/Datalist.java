package com.examples.user.newserver.Table1;

/**
 * Created by User on 2/8/2016.
 */
public class Datalist {
    String url;
    String text;


    public Datalist(String title, String s){
        this.url=s;
        this.text=title;

    }
    public String getTitle(){
        return text;
    }
    public String getUrl(){
        return url;
    }

}


