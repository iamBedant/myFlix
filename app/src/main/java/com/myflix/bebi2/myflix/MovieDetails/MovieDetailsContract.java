package com.myflix.bebi2.myflix.MovieDetails;

import com.myflix.bebi2.myflix.Pojo.Reviews;
import com.myflix.bebi2.myflix.Pojo.Videos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bebi2 on 12/29/2015.
 */
public class MovieDetailsContract {
    interface View {
        void showMessage(String msg, int duration);

        void setMoviePoster(String moviePoster);

        void setTitle(String title);

        void setTrailers(ArrayList<Videos> trailers);

        void setReviews(ArrayList<Reviews> reviews);

        void setRating(Float rating);

        void setReleaseDate(String releaseDate);
    }

    interface UserActionListener {

        void showMovieDetails();
    }
}


