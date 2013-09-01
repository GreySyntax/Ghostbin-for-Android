package com.nspwn.ghostbin.core.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jordanm on 31/08/2013.
 */
public class Language {
    @SerializedName("Name")
    private String name;
    @SerializedName("Title")
    private String title;

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }
}
