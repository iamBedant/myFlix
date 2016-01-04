package com.myflix.bebi2.myflix.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bebi2 on 12/29/2015.
 */
public class Movie implements Parcelable {

    public Integer id;
    public Boolean adult;
    public String title;
    public String overview;
    public float popularity;
    public float vote_average;
    public String posterImage;
    public String backdrop_url;
    public Date release_date;
    public Integer vote_count;
    public Boolean video;
    public List<Integer> genre_ids;
    public String original_title;

    public Movie() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getBackdrop_url() {
        return backdrop_url;
    }

    public void setBackdrop_url(String backdrop_url) {
        this.backdrop_url = backdrop_url;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    protected Movie(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        byte adultVal = in.readByte();
        adult = adultVal == 0x02 ? null : adultVal != 0x00;
        title = in.readString();
        overview = in.readString();
        popularity = in.readFloat();
        vote_average = in.readFloat();
        posterImage = in.readString();
        backdrop_url = in.readString();
        long tmpRelease_date = in.readLong();
        release_date = tmpRelease_date != -1 ? new Date(tmpRelease_date) : null;
        vote_count = in.readByte() == 0x00 ? null : in.readInt();
        byte videoVal = in.readByte();
        video = videoVal == 0x02 ? null : videoVal != 0x00;
        if (in.readByte() == 0x01) {
            genre_ids = new ArrayList<Integer>();
            in.readList(genre_ids, Integer.class.getClassLoader());
        } else {
            genre_ids = null;
        }
        original_title = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        if (adult == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (adult ? 0x01 : 0x00));
        }
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeFloat(popularity);
        dest.writeFloat(vote_average);
        dest.writeString(posterImage);
        dest.writeString(backdrop_url);
        dest.writeLong(release_date != null ? release_date.getTime() : -1L);
        if (vote_count == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(vote_count);
        }
        if (video == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (video ? 0x01 : 0x00));
        }
        if (genre_ids == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(genre_ids);
        }
        dest.writeString(original_title);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}