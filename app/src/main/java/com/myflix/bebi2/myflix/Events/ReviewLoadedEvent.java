package com.myflix.bebi2.myflix.Events;

import com.myflix.bebi2.myflix.Pojo.Reviews;
import com.myflix.bebi2.myflix.Pojo.Videos;

import java.util.ArrayList;

/**
 * Created by bebi2 on 1/3/2016.
 */
public class ReviewLoadedEvent {
    public ArrayList<Reviews> reviews ;

    public ReviewLoadedEvent(ArrayList<Reviews> reviews) {
        this.reviews = reviews;
    }
}
