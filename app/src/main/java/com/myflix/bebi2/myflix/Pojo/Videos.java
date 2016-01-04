package com.myflix.bebi2.myflix.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by bebi2 on 12/29/2015.
 */
public class Videos implements Parcelable {

    String id;
    String key;
    String name;
    String type;
    String site;
    String size;
    String iso_639_1;

    public Videos() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    protected Videos(Parcel in) {
        id = in.readString();
        key = in.readString();
        name = in.readString();
        type = in.readString();
        site = in.readString();
        size = in.readString();
        iso_639_1 = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(site);
        dest.writeString(size);
        dest.writeString(iso_639_1);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Videos> CREATOR = new Parcelable.Creator<Videos>() {
        @Override
        public Videos createFromParcel(Parcel in) {
            return new Videos(in);
        }

        @Override
        public Videos[] newArray(int size) {
            return new Videos[size];
        }
    };
}