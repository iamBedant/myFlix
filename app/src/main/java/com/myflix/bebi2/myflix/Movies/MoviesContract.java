package com.myflix.bebi2.myflix.Movies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.myflix.bebi2.myflix.Pojo.Movie;
import com.myflix.bebi2.myflix.Utils.MovieFilterList;

import java.util.List;

/**
 * Created by bebi2 on 12/29/2015.
 */
public class MoviesContract {

    interface View{
        void setProgressIndicator(boolean active);

        void showMovieDetailUi(Bundle movieArgs, android.view.View view);

        void setMovies(List<Movie> movies);

        void clearMovies();

        void showMessage(String msg, int duration);

        void loadFavoriteMovies();
    }

    interface UserActionListener{
        void setProgressIndicator(boolean active);

        void openMovieDetails(@NonNull Movie requestedMovie,android.view.View view);

        void loadMovies(MovieFilterList filterCriteria);

        int getResultPageNumber();

        void setLastKnownResultPage(int pageNumber);

        MovieFilterList getCurrentMovieFilter();

        void setLastKnownFilter(MovieFilterList filter);
    }
}
