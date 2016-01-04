package com.myflix.bebi2.myflix.DB;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by bebi2 on 12/29/2015.
 */

@Database(version = MovieDB.VERSION)
public class MovieDB {
    private MovieDB(){}

    public static final int VERSION = 1;

    @Table(FavouriteMoviesColumn.class) public static final String FAVOURITE_MOVIES = "favourite_movies";

}
