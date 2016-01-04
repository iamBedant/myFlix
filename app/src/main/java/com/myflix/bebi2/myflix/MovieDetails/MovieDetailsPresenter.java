package com.myflix.bebi2.myflix.MovieDetails;

import android.support.design.widget.Snackbar;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.myflix.bebi2.myflix.Network.CustomRequest;
import com.myflix.bebi2.myflix.Network.VolleySingleton;
import com.myflix.bebi2.myflix.Pojo.Movie;
import com.myflix.bebi2.myflix.Pojo.Reviews;
import com.myflix.bebi2.myflix.Pojo.Videos;
import com.myflix.bebi2.myflix.Utils.CheckJson;
import com.myflix.bebi2.myflix.Utils.Config;
import com.myflix.bebi2.myflix.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by bebi2 on 12/29/2015.
 */
public class MovieDetailsPresenter implements MovieDetailsContract.UserActionListener {

    public final static String TAG = MovieDetailsPresenter.class.getSimpleName();

    private MovieDetailsContract.View mMovieDetailsView;
    private Movie mMovie;

    public MovieDetailsPresenter(Movie movie, MovieDetailsContract.View movieDetailsView) {
        this.mMovie = movie;
        this.mMovieDetailsView = movieDetailsView;

    }

    @Override
    public void showMovieDetails() {
        mMovieDetailsView.setMoviePoster(mMovie.getPosterImage());
        mMovieDetailsView.setTitle(mMovie.title);
        mMovieDetailsView.setRating(mMovie.vote_average);
        mMovieDetailsView.setReleaseDate((String) android.text.format.DateFormat.format("yyyy", mMovie.release_date));
        VolleySingleton.getsInstance().cancelPendingRequests(TAG);
        getReviews(mMovie.getId());
        getTrailers(mMovie.getId());
    }

    private void ShowErrorMessage(String errorMessage) {
        mMovieDetailsView.showMessage(errorMessage, Snackbar.LENGTH_SHORT);
    }

    private void getTrailers(Integer id) {


        getResponse(Utils.makeTrailerUrl(id), Config.TRAILER);


    }

    private void getReviews(Integer id) {
        getResponse(Utils.makeReviewUrl(id), Config.REVIEW);

    }

    @Override
    public void changeFavoriteButtonSrc(boolean isFavorite) {

    }

    @Override
    public void changeFavoriteButtonBackgroundColor(int color) {

    }

    @Override
    public void changePlayTrailerButtonBackgroundColor(int color) {

    }

    @Override
    public void changeShareTrailerButtonBackgroundColor(int color) {

    }

    @Override
    public void loadTrailers() {

    }

    @Override
    public void loadFirstTrailer() {

    }


    private void getResponse(String url, final int type) {
        CustomRequest getMoviesRequest = new CustomRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (type == Config.REVIEW) {
                    parseReview(response);
                } else if (type == Config.TRAILER) {
                    parseTrailer(response);
                } else {
                    //TODO:Show error message;
                }
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

    private void parseTrailer(JSONObject response) {
        ArrayList<Videos> trailers = new ArrayList<>();
        try {
            if (CheckJson.contains(response, "results")) {
                JSONArray _trailers = response.getJSONArray("results");


                for (int i = 0; i < _trailers.length(); i++) {
                    JSONObject _trailer = _trailers.getJSONObject(i);
                    Videos trailer = new Videos();

                    if (CheckJson.contains(_trailer, "id")) {
                        trailer.setId(_trailer.getString("id"));
                    }

                    if (CheckJson.contains(_trailer, "iso_639_1")) {
                        trailer.setIso_639_1(_trailer.getString("iso_639_1"));
                    }
                    if (CheckJson.contains(_trailer, "key")) {
                        trailer.setKey(_trailer.getString("key"));
                    }
                    if (CheckJson.contains(_trailer, "name")) {
                        trailer.setName(_trailer.getString("name"));
                    }
                    if (CheckJson.contains(_trailer, "site")) {
                        trailer.setSite(_trailer.getString("site"));
                    }
                    if (CheckJson.contains(_trailer, "size")) {
                        trailer.setSize(_trailer.getString("size"));
                    }
                    if (CheckJson.contains(_trailer, "type")) {
                        trailer.setType(_trailer.getString("type"));
                    }

                    trailers.add(trailer);
                }

                mMovieDetailsView.setTrailers(trailers);


            }
        } catch (JSONException e) {

        }


    }

    private void parseReview(JSONObject response) {

        //TODO: improvement --> autoload on scroll;

        ArrayList<Reviews> reviews = new ArrayList<>();
        try {
            if (CheckJson.contains(response, "results")) {
                JSONArray _trailers = response.getJSONArray("results");
                if (_trailers.length() > 0) {

                    for (int i = 0; i < _trailers.length(); i++) {
                        JSONObject _review = _trailers.getJSONObject(i);
                        Reviews trailer = new Reviews();

                        if (CheckJson.contains(_review, "id")) {
                            trailer.setId(_review.getString("id"));
                        }

                        if (CheckJson.contains(_review, "author")) {
                            trailer.setAuthor(_review.getString("author"));
                        }
                        if (CheckJson.contains(_review, "content")) {
                            String content = _review.getString("content");
                            if (content.length() > 100) {
                                content = content.substring(0, 100) + "...";
                                trailer.isCut = true;
                            } else {
                                trailer.isCut = false;
                            }
                            trailer.setContent(content);
                        }
                        if (CheckJson.contains(_review, "url")) {
                            trailer.setUrl(_review.getString("url"));
                        }

                        reviews.add(trailer);
                    }
                    mMovieDetailsView.setReviews(reviews);

                } else {
                    //TODO: notify no trailers available
                }
            }
        } catch (JSONException e) {

        }
    }


}
