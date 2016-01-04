package com.myflix.bebi2.myflix.Utils;

import java.util.List;

/**
 * Created by bebi2 on 12/29/2015.
 */
public class Genre {

    public static final String GENRE_ACTION = "Action"; // 28
    public static final String GENRE_ADVENTURE = "Adventure"; //12
    public static final String GENRE_ANIMATION = "Animation"; //16
    public static final String GENRE_COMEDY = "Comedy"; //35
    public static final String GENRE_CRIME = "Crime"; //80
    public static final String GENRE_DOCUMENTARY = "Crime"; //99
    public static final String GENRE_DRAMA = "Drama"; //18
    public static final String GENRE_FAMILY = "Family"; //10751
    public static final String GENRE_FANTASY = "Fantasy"; //14
    public static final String GENRE_FOREIGN = "Foreign"; //10769
    public static final String GENRE_HISTORY = "History"; //36
    public static final String GENRE_HORROR = "Horror"; //27
    public static final String GENRE_MUSIC = "Music"; //10402
    public static final String GENRE_MYSTERY = "Mystery"; //9648
    public static final String GENRE_ROMANCE = "Romance"; //10749
    public static final String GENRE_SCIENCE_FICTION = "Science Fiction"; //878
    public static final String GENRE_TV_MOVIE = "TV Movie"; //10770
    public static final String GENRE_THRILLER = "Thriller"; //53
    public static final String GENRE_WAR = "War"; //10752
    public static final String GENRE_WESTERN = "Western"; //37

    /**
     * Searches for the Genre name base on the id, this is an utility version
     * to prevent calls to /genres/list
     *
     * @param id
     * @return
     */
    public static String getGenreById(int id) {
        switch (id) {
            case 28:
                return GENRE_ACTION;
            case 12:
                return GENRE_ADVENTURE;
            case 16:
                return GENRE_ANIMATION;
            case 35:
                return GENRE_COMEDY;
            case 80:
                return GENRE_CRIME;
            case 99:
                return GENRE_DOCUMENTARY;
            case 18:
                return GENRE_DRAMA;
            case 10751:
                return GENRE_FAMILY;
            case 14:
                return GENRE_FANTASY;
            case 10769:
                return GENRE_FOREIGN;
            case 36:
                return GENRE_HISTORY;
            case 27:
                return GENRE_HORROR;
            case 10402:
                return GENRE_MUSIC;
            case 9648:
                return GENRE_MYSTERY;
            case 10749:
                return GENRE_ROMANCE;
            case 878:
                return GENRE_SCIENCE_FICTION;
            case 10770:
                return GENRE_TV_MOVIE;
            case 53:
                return GENRE_THRILLER;
            case 10752:
                return GENRE_WAR;
            case 37:
                return GENRE_WESTERN;
            default:
                return "";
        }
    }

    /**
     * Returns a comma separated version of genre names using the @param id property.
     *
     * @param genres
     * @return A comma separated representation of genres names.
     */
    public static String toCsv(List<Integer> genres) {
        if (genres == null) {
            return "";
        } else {
            StringBuilder builder = new StringBuilder();
            int size = genres.size();
            for (int i = 0; i < size; i++) {
                builder.append(getGenreById(genres.get(i)));
                if (i + 1 != size) {
                    builder.append(", ");
                }
            }

            return builder.toString();
        }
    }
}
