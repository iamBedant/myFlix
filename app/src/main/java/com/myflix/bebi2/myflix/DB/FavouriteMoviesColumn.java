package com.myflix.bebi2.myflix.DB;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by bebi2 on 12/29/2015.
 */
public interface FavouriteMoviesColumn {

//    @DataType(DataType.Type.INTEGER) @PrimaryKey
//    @AutoIncrement
//    public static final String _ID =
//            "_id";

    @DataType(DataType.Type.INTEGER) @PrimaryKey
    public static final String ID = "id";

    @DataType(DataType.Type.TEXT) @NotNull
    public static final String TITLE = "title";

    @DataType(DataType.Type.TEXT)
    public static final String POSTER_URL = "poster_url";

    @DataType(DataType.Type.TEXT)
    public static final String BACKDROP_URL = "backdrop_url";

    @DataType(DataType.Type.TEXT)
    public static final String RELEASE_DATE = "release_date";

    @DataType(DataType.Type.TEXT)
    public static final String OVERVIEW = "overview";

    @DataType(DataType.Type.REAL)
    public static final String RATING = "rating";

    @DataType(DataType.Type.INTEGER)
    public static final String USERS = "users";


    //we will use this value to show notification for upcoming movie bookings

    @DataType(DataType.Type.INTEGER)
    public static final String IS_UPCOMING = "is_upcoming";

}
