package com.nspwn.ghostbin.core.beans;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by jordanm on 31/08/2013.
 */
public class Language {
    @SerializedName("Name")
    private String name;
    @SerializedName("Title")
    private String title;
    @SerializedName("Name")
    private List<String> names;

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getNames() {
        return names;
    }

    @Override
    public String toString() {
        return String.format("name: %s; title: %s; names: %s;", this.getName(), this.getTitle(), StringUtils.join(this.getNames(), ","));
    }
}
