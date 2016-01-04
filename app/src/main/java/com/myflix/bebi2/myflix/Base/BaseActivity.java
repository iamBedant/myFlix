package com.myflix.bebi2.myflix.Base;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.myflix.bebi2.myflix.R;

/**
 * Created by bebi2 on 12/29/2015.
 */
public class BaseActivity extends AppCompatActivity {
    protected void showSnackbar(View parent, String msg) {
        Snackbar.make(parent, msg, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Displays a Snackbar with an offline message.
     *
     * @param parent
     * @param listener
     */
    protected void showOfflineSnackbar(View parent, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(parent, "You are offline", Snackbar.LENGTH_SHORT);
        snackbar.setAction("GO ONLINE", listener);
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();

    }

    /**
     * Detects where or not there is a network available.
     *
     * @return networkAvailability
     */
    protected boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    protected boolean weAreInATabletSizeDevice() {
        return (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
