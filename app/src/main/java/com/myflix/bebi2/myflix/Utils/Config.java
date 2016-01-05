package com.myflix.bebi2.myflix.Utils;

/**
 * Created by bebi2 on 12/19/2015.
 */
public class Config {

    /*
     ---------------- API Urls -------------------
     */

    public static final String API_UPCOMING="http://api.themoviedb.org/3/movie/upcoming";
    public static final String API_TOP_RATED ="http://api.themoviedb.org/3/movie/top_rated";
    public static final String API_MOST_POPULAR="http://api.themoviedb.org/3/movie/popular";
    public static final String API_NOW_PLAYING="http://api.themoviedb.org/3/movie/now_playing";

    public static final String API_TRAILER_BASE_API= "http://api.themoviedb.org/3/movie/";

    public static final String API_GET_MOVIE_LIST = "";
    public static final String API_KEY="[YOUR API KEY]";
    public static final String API_IMAGE_BASE_URL_POSTER="http://image.tmdb.org/t/p/w185";
    public static final String API_IMAGE_BASE_URL_BACKDROP="http://image.tmdb.org/t/p/w342";

    public static final String YOUTUBE_BASE = "https://www.youtube.com/watch?v=";
    public static final String YOUTUBE_THUMBNAIL= "http://img.youtube.com/vi/";


    /*
     ---------------- BUNDLE Strings -------------------
     */

    public static final String BUNDLE_MOVIES ="_movies";
    public static final String BUNDLE_SINGLE_MOVIES ="_single_movie";
    public static final String BUNDLE_SORT_ORDER ="_sort_order";
    public static final String BUNDLE_SAVED_LAYOUT_MANAGER="_recyclerview_state";
    public static final String BUNDLE_TRAILERS="_trailers";
    public static final String BUNDLE_REVIEWS ="_reviews";
    public static final String BUNDLE_MOVIE_OVERVIEW="_movie_overview";
    public static final String BUNDLE_IS_FAVOURITE ="_isFav";
   public static final String BUNDLE_IS_LOADED="_is_loaded";
    public static final String BUNDLE_IS_EMPTY="_is_empty";
    /*
     ---------------- SORT ORDER Strings -------------------
     */
    /*
    ---------------------------Volley TimeOut Request-----------------------
     */
    public static final int TIMEOUT_LENGTH = 9000;


    /*
    ---------------------------TYPE -----------------------
     */
    public static final int REVIEW = 1;
    public static final int TRAILER = 2;

    private static final double TMDB_POSTER_SIZE_RATIO = 185.0 / 277.0;


}
