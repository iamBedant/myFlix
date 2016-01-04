package com.myflix.bebi2.myflix.Events;

import com.myflix.bebi2.myflix.Pojo.Reviews;
import com.myflix.bebi2.myflix.Pojo.Videos;

import java.util.ArrayList;

/**
 * Created by bebi2 on 1/3/2016.
 */
public class TrailerLoadedEvent {
    public ArrayList<Videos> trailers ;

    public TrailerLoadedEvent(ArrayList<Videos> trailers) {
        this.trailers = trailers;
    }
}
