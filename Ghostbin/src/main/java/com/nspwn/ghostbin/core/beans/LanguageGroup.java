package com.nspwn.ghostbin.core.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jordanm on 31/08/2013.
 */
public class LanguageGroup {
    @SerializedName("Title")
    private String title;
    @SerializedName("Languages")
    private List<Language> languages;

    public String getTitle() {
        return title;
    }

    public List<Language> getLanguages() {
        return languages;
    }
}
