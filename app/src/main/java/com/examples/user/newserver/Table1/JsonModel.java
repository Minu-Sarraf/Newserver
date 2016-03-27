package com.examples.user.newserver.Table1;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 3/21/2016.
 */
public class JsonModel {


    /**
     * url : 21 March, 2016
     * text : Swiss Franc
     */
@SerializedName("url")
    private String url;
    @SerializedName("text")
    private String text;
    public JsonModel(String url, String text) {
        this.url = url;
        this.text =text;

    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}