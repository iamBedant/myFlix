package com.myflix.bebi2.myflix.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by bebi2 on 12/29/2015.
 */
public class Reviews implements Parcelable {

    public String id;
    public String author;
    public String content;
    public String url;
    public Boolean isCut;

    public Reviews() {
    }

    public Boolean getCut() {
        return isCut;
    }

    public void setCut(Boolean cut) {
        isCut = cut;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    protected Reviews(Parcel in) {
        id = in.readString();
        author = in.readString();
        content = in.readString();
        url = in.readString();
        byte isCutVal = in.readByte();
        isCut = isCutVal == 0x02 ? null : isCutVal != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(url);
        if (isCut == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isCut ? 0x01 : 0x00));
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Reviews> CREATOR = new Parcelable.Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel in) {
            return new Reviews(in);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };
}