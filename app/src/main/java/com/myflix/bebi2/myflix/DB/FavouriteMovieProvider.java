package com.myflix.bebi2.myflix.DB;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by bebi2 on 12/29/2015.
 */
@ContentProvider(authority = FavouriteMovieProvider.AUTHORITY, database = MovieDB.class)
public class FavouriteMovieProvider {
    public static final String AUTHORITY =
            "com.myflix.bebi2.myflix.DB.FavouriteMovieProvider";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String FAVOURITE_MOVIES = "favourite_movies";

    }

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }


    @TableEndpoint(table = MovieDB.FAVOURITE_MOVIES)
    public static class Movies {
        @ContentUri(
                path = Path.FAVOURITE_MOVIES,
                type = "vnd.android.cursor.dir/favourite_movies",
                defaultSort = FavouriteMoviesColumn.RATING + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.FAVOURITE_MOVIES);

        @InexactContentUri(
                name = "MOVIE_ID",
                path = Path.FAVOURITE_MOVIES + "/#",
                type = "vnd.android.cursor.item/favourite_movies",
                whereColumn = FavouriteMoviesColumn.ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return buildUri(Path.FAVOURITE_MOVIES, String.valueOf(id));
        }
    }



}
