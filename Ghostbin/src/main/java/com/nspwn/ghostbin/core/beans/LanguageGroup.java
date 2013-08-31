package com.nspwn.ghostbin.core.beans;

import java.util.List;

/**
 * Created by jordanm on 31/08/2013.
 */
public class LanguageGroup {
    private String title;
    private List<Language> languages;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }
}
