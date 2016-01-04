package com.myflix.bebi2.myflix.Movies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.myflix.bebi2.myflix.Base.BaseActivity;
import com.myflix.bebi2.myflix.MovieDetails.MovieDetailsFragment;

import com.myflix.bebi2.myflix.R;
import com.myflix.bebi2.myflix.Utils.EventBus;


import java.util.ArrayList;

public class Movies extends BaseActivity implements MoviesFragmentCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

//        if (savedInstanceState == null) {
//            if (Utils.isTablet(this)) {
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fragment_detail_view, new MovieDetailsFragment())
//                        .commit();
//            }
//        }


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }


    @Override
    public void onMOvieClick(Bundle movieArgs) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_detail_view, MovieDetailsFragment.newInstance(movieArgs), MovieDetailsFragment.TAG)
                .commit();
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
