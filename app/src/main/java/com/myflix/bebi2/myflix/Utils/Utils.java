package com.myflix.bebi2.myflix.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.myflix.bebi2.myflix.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bebi2 on 12/20/2015.
 */
public class Utils {
    public enum ToolbarNavIcon {
        UP,
        NONE
    }



    public static int getScreenWidth(@NonNull Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static Date getDateFromString(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date date = formatter.parse(dateString);
        return date;

    }

    public static String getStringFromDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }

    public static boolean isTablet(Context mContext) {
        return (mContext.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static String makeReviewUrl(int id){
     return Config.API_TRAILER_BASE_API+id+"/reviews"+"?api_key=" + Config.API_KEY;
    }
    public static String makeTrailerUrl(int id){
        return Config.API_TRAILER_BASE_API+id+"/videos"+"?api_key=" + Config.API_KEY;
    }

    public static String getThumbnail(String id){
        return Config.YOUTUBE_THUMBNAIL+id+"/0.jpg";
    }
    public static String getVideoUrl(String key){
        return Config.YOUTUBE_BASE+key;
    }

    public static void setupToolbar(final Activity activity, Toolbar toolbar, ToolbarNavIcon icon,
                                    String title) {
        if (icon == ToolbarNavIcon.UP) {
            Drawable upArrow = ContextCompat.getDrawable(activity, R.drawable.arrow_left);
            Utils.tintDrawable(upArrow, ContextCompat.getColor(activity, android.R.color.white));
            toolbar.setNavigationIcon(upArrow);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onBackPressed();
                }
            });
        }
        toolbar.setTitle(title);
    }

    public static void tintDrawable(@Nullable Drawable drawable, int color) {
        if (drawable != null) {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }
}
