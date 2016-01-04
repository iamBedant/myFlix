package com.myflix.bebi2.myflix.MovieDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.myflix.bebi2.myflix.Base.BaseActivity;
import com.myflix.bebi2.myflix.R;
import com.myflix.bebi2.myflix.Utils.Config;
import com.myflix.bebi2.myflix.Utils.EventBus;

public class MovieDetails extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Intent intent = getIntent();
            if (intent != null) {
                Bundle movieArgs = intent.getExtras().getParcelable(Config.BUNDLE_SINGLE_MOVIES);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_detail_view, MovieDetailsFragment.newInstance(movieArgs), MovieDetailsFragment.TAG)
                        .commit();
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getInstance().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getInstance().unregister(this);
        super.onStop();

    }

}
