package com.examples.user.newserver.DataHandler;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 3/24/2016.
 */
public class Jsonmodel2 {
    @SerializedName("imageurl")
    private String imageurl;
    @SerializedName("description")
    private String description;
    public Jsonmodel2(String url, String text) {
        this.imageurl = url;
        this.description =text;

    }

    public String getDescription() {
        return description;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }
}
