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

        void setProgressIndicator(boolean active);

        void showMessage(String msg, int duration);

        void setMoviePoster(String moviePoster);

        void setMovieBackdropImage(String backdropImage);

        void setTitle(String title);



        void setTrailers(ArrayList<Videos> trailers);

        void setReviews(ArrayList<Reviews> reviews);

        void setRating(Float rating);

        void setReleaseDate(String releaseDate);

        void setFavoriteButtonSrc(boolean isFavorite);

        void setFavoriteButtonBackgroundColor(int color);

        void setPlayTrailerButtonBackgroundColor(int color);

        void setShareTrailerButtonBackgroundColor(int color);

    }

    interface UserActionListener {

        void showMovieDetails();

        void changeFavoriteButtonSrc(boolean isFavorite);

        void changeFavoriteButtonBackgroundColor(int color);

        void changePlayTrailerButtonBackgroundColor(int color);

        void changeShareTrailerButtonBackgroundColor(int color);

        void loadTrailers();

        void loadFirstTrailer();
    }
}


