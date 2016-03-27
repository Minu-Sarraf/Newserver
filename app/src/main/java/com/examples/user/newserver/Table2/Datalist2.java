package com.examples.user.newserver.Table2;

/**
 * Created by User on 3/24/2016.
 */
public class Datalist2 {
    String imageurl;
    String description;


    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Datalist2(String title, String s){
        this.imageurl=s;
        this.description=title;

    }

}
