package com.myflix.bebi2.myflix.Pojo;

/**
 * Created by bebi2 on 12/21/2015.
 */
public class Help {
    String name;
    String url;

    public Help(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
