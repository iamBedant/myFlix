package com.myflix.bebi2.myflix.Utils;

/**
 * Created by bebi2 on 12/31/2015.
 */
public enum Language {

    LANGUAGE_EN("en");

    private final String value;

    private Language(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
