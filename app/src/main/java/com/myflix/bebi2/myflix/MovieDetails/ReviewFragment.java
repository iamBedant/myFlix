package com.myflix.bebi2.myflix.MovieDetails;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.myflix.bebi2.myflix.Events.OverviewLoadedEvent;
import com.myflix.bebi2.myflix.Events.ReviewLoadedEvent;
import com.myflix.bebi2.myflix.Movies.MoviesPresenter;
import com.myflix.bebi2.myflix.Pojo.Reviews;
import com.myflix.bebi2.myflix.Pojo.Videos;
import com.myflix.bebi2.myflix.R;
import com.myflix.bebi2.myflix.Utils.Config;
import com.myflix.bebi2.myflix.Utils.EventBus;
import com.myflix.bebi2.myflix.Utils.Utils;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bebi2 on 1/1/2016.
 */


public class ReviewFragment extends Fragment implements onRTItemClick {

    @Bind(R.id.reviews)
    RecyclerView mRecyclerView;

    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    @Bind(R.id.empty_review)
    TextView empty;

    Context mContext;
    ReviewAdapter mAdapter;
    Boolean isAvailable = false;
    Boolean isEmpty = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating, container, false);
        ButterKnife.bind(this, view);
        if(!isAvailable){
            progressBar.setVisibility(View.VISIBLE);
        }
        if(isEmpty){
            empty.setText(R.string.no_review_available);
            empty.setVisibility(View.VISIBLE);
        }
        SetUpRecyclerView();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mAdapter = new ReviewAdapter(mContext, this);

        if (savedInstanceState != null) {
            isEmpty = savedInstanceState.getBoolean(Config.BUNDLE_IS_EMPTY);
            isAvailable = savedInstanceState.getBoolean(Config.BUNDLE_IS_LOADED);
            ArrayList<Reviews> reviews = savedInstanceState.getParcelableArrayList(Config.BUNDLE_REVIEWS);
            mAdapter.addAll(reviews);
        }
    }

    @Subscribe
    public void ReviewLoadedEvent(ReviewLoadedEvent event) {
        isAvailable = true;
        progressBar.setVisibility(View.GONE);
        if (!(event.reviews.size() > 0)) {
            isEmpty=true;
            empty.setText(R.string.no_review_available);
            empty.setVisibility(View.VISIBLE);
        } else {
            mAdapter.addAll(event.reviews);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(Config.BUNDLE_IS_EMPTY,isEmpty);
        outState.putBoolean(Config.BUNDLE_IS_LOADED,isAvailable);
        outState.putParcelableArrayList(Config.BUNDLE_REVIEWS, (ArrayList<? extends Parcelable>) mAdapter.getAll());
    }


    private void SetUpRecyclerView() {

        GridLayoutManager manager = new GridLayoutManager(mContext, 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onStart() {
        EventBus.getInstance().register(this);
        super.onStart();

    }

    @Override
    public void onStop() {
        EventBus.getInstance().unregister(this);
        super.onStop();

    }

    @Override
    public void onMovieClick(int position) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mAdapter.get(position).getUrl()));
        getActivity().startActivity(browserIntent);

    }
}
