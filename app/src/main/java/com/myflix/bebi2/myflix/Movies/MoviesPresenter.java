package com.myflix.bebi2.myflix.Movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.myflix.bebi2.myflix.Network.CustomRequest;
import com.myflix.bebi2.myflix.Network.VolleySingleton;
import com.myflix.bebi2.myflix.Pojo.Movie;
import com.myflix.bebi2.myflix.Utils.CheckJson;
import com.myflix.bebi2.myflix.Utils.Config;
import com.myflix.bebi2.myflix.Utils.Language;
import com.myflix.bebi2.myflix.Utils.MovieFilterList;
import com.myflix.bebi2.myflix.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bebi2 on 12/29/2015.
 */
public class MoviesPresenter implements MoviesContract.UserActionListener {

    public final static String TAG = MoviesPresenter.class.getSimpleName();

    private MoviesContract.View mMoviesView;
    private MovieFilterList mCurrentListFilter = MovieFilterList.NOW_PLAYING;
    private int mResultsPage = 1;
    private boolean mFetching = false;

    public MoviesPresenter(@NonNull MoviesContract.View movieView) {
        this.mMoviesView = movieView;
    }


    @Override
    public void setProgressIndicator(boolean active) {
        mMoviesView.setProgressIndicator(active);
    }

    @Override
    public void openMovieDetails(@NonNull Movie requestedMovie, View view) {
        Bundle movieArgs = new Bundle();
        movieArgs.putParcelable(Config.BUNDLE_SINGLE_MOVIES, requestedMovie);
        mMoviesView.showMovieDetailUi(movieArgs,view);
    }


    @Override
    public void loadMovies(MovieFilterList filterCriteria) {

        if (filterCriteria != mCurrentListFilter) {
            mCurrentListFilter = filterCriteria; //set new filter criteria.
            mMoviesView.clearMovies(); //Clear old movies
            mMoviesView.setProgressIndicator(true); //And show progress indicator.
            mResultsPage = 1; //Reset page count and fetch with new criteria.
            mFetching = false; //Cancel any current fetch
        }

        if (mFetching) {
            return;
        }

        mFetching = true;


        String url = "";

        if (filterCriteria == MovieFilterList.UPCOMING) {

            url += Config.API_UPCOMING;
            url += "?api_key=" + Config.API_KEY;
            url += "&page=" + mResultsPage;
            url += "&language=" + Language.LANGUAGE_EN.toString();
            getMovies(url);

        } else if (filterCriteria == MovieFilterList.NOW_PLAYING) {

            url += Config.API_NOW_PLAYING;
            url += "?api_key=" + Config.API_KEY;
            url += "&page=" + mResultsPage;
            url += "&language=" + Language.LANGUAGE_EN.toString();
            getMovies(url);

        } else if (filterCriteria == MovieFilterList.HIGHEST_RATED) {
            url += Config.API_TOP_RATED;
            url += "?api_key=" + Config.API_KEY;
            url += "&page=" + mResultsPage;
            url += "&language=" + Language.LANGUAGE_EN.toString();
            getMovies(url);

        } else if (filterCriteria == MovieFilterList.MOST_POPULAR) {
            url += Config.API_MOST_POPULAR;
            url += "?api_key=" + Config.API_KEY;
            url += "&page=" + mResultsPage;
            url += "&language=" + Language.LANGUAGE_EN.toString();
            getMovies(url);
        } else if (filterCriteria == MovieFilterList.FAVORITES) {
            mMoviesView.loadFavoriteMovies();
        }


    }


    private void getMovies(String url) {
        CustomRequest getMoviesRequest = new CustomRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseJson(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            ShowErrorMessage("No Internet Connection");
                        } else {
                            NetworkResponse networkResponse = error.networkResponse;
                            int x = networkResponse.statusCode;
                            if (x == 401) {
                                //Auth Error
                            } else if (x == 500) {
                                ShowErrorMessage("We are having problem with our server :(");
                            }
                        }
                    }
                });

        getMoviesRequest.setRetryPolicy(new DefaultRetryPolicy(
                Config.TIMEOUT_LENGTH,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        getMoviesRequest.setTag(TAG);
        VolleySingleton.getsInstance().addToRequestQueue(getMoviesRequest);
    }

    private void parseJson(JSONObject response) {
        try {
            ArrayList<Movie> mTempMovieList = new ArrayList<>();
            if (CheckJson.contains(response, "results")) {
                JSONArray movies = response.getJSONArray("results");
                for (int i = 0; i < movies.length(); i++) {
                    JSONObject _movie = movies.getJSONObject(i);
                    Movie movie = new Movie();

                    if (CheckJson.contains(_movie, "id")) {
                        movie.setId(_movie.getInt("id"));
                    }
                    if (CheckJson.contains(_movie, "title")) {
                        movie.setTitle(_movie.getString("title"));
                    }
                    if (CheckJson.contains(_movie, "backdrop_path")) {
                        movie.setBackdrop_url(Config.API_IMAGE_BASE_URL_BACKDROP + _movie.getString("backdrop_path"));
                    }
                    if (CheckJson.contains(_movie, "vote_average")) {
                        movie.setVote_average((float) _movie.getDouble("vote_average"));
                    }
                    if (CheckJson.contains(_movie, "poster_path")) {
                        movie.setPosterImage(Config.API_IMAGE_BASE_URL_POSTER + _movie.getString("poster_path"));
                    }
                    if (CheckJson.contains(_movie, "overview")) {
                        movie.setOverview(_movie.getString("overview"));
                    }
                    if (CheckJson.contains(_movie, "release_date")) {
                        try {
                            movie.setRelease_date(Utils.getDateFromString(_movie.getString("release_date")));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    if (movie.getTitle() != null && !movie.getTitle().isEmpty()) {
                        mTempMovieList.add(movie);
                    }


                }
                mMoviesView.setMovies(mTempMovieList);
                mMoviesView.setProgressIndicator(false);
                mResultsPage++;
                mFetching = false;
            }
        } catch (JSONException e) {
            Log.d(TAG, "Parsing Error");
            ShowErrorMessage("An Error occured during JSON Parsing");
        }
    }


    private void ShowErrorMessage(String errorMessage) {
        mMoviesView.showMessage(errorMessage, Snackbar.LENGTH_SHORT);
    }

    @Override
    public int getResultPageNumber() {
        return mResultsPage;
    }

    @Override
    public void setLastKnownResultPage(int pageNumber) {
        this.mResultsPage = pageNumber;
        this.mFetching = false;
    }

    @Override
    public MovieFilterList getCurrentMovieFilter() {

        return mCurrentListFilter;
    }

    @Override
    public void setLastKnownFilter(MovieFilterList filter) {
        this.mCurrentListFilter = filter;
        this.mFetching = false;
    }


}
