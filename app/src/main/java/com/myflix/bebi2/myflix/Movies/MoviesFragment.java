package com.myflix.bebi2.myflix.Movies;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.myflix.bebi2.myflix.About.About;
import com.myflix.bebi2.myflix.DB.FavouriteMovieProvider;
import com.myflix.bebi2.myflix.DB.FavouriteMoviesColumn;
import com.myflix.bebi2.myflix.Events.FavouriteRemovedEvent;
import com.myflix.bebi2.myflix.Events.ReviewLoadedEvent;
import com.myflix.bebi2.myflix.MovieDetails.MovieDetails;
import com.myflix.bebi2.myflix.Pojo.Movie;
import com.myflix.bebi2.myflix.R;
import com.myflix.bebi2.myflix.Utils.Config;
import com.myflix.bebi2.myflix.Utils.EventBus;
import com.myflix.bebi2.myflix.Utils.MovieFilterList;
import com.myflix.bebi2.myflix.Utils.Utils;
import com.squareup.otto.Subscribe;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFragment extends Fragment implements MoviesContract.View, MovieItemClickListener, Toolbar.OnMenuItemClickListener {

    @Bind(R.id.recyclerview_movies)
    RecyclerView mRecyclerView;

    @Bind(R.id.toolbar)


    Toolbar mToolbar;
    Context mContext;
    RecyclerViewAdapter mAdapter;
    GridLayoutManager manager;
    private MoviesContract.UserActionListener mActionListener;

    public MoviesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mContext = getActivity();
        mAdapter = new RecyclerViewAdapter(mContext, this);
        mActionListener = new MoviesPresenter(this);


        if (savedInstanceState == null) {
            mActionListener.loadMovies(MovieFilterList.NOW_PLAYING);
        } else {
            mActionListener.setLastKnownResultPage(savedInstanceState.getInt(MoviesPresenter.TAG, 1));
            mActionListener.setLastKnownFilter((MovieFilterList) savedInstanceState.getSerializable(Config.BUNDLE_SORT_ORDER));
            ArrayList<Movie> movies = savedInstanceState.getParcelableArrayList(Config.BUNDLE_MOVIES);
            mAdapter.addAll(movies);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, view);


        setUpToolbar();
        SetUpRecyclerView();

        return view;
    }

    private void setUpToolbar() {
        final Activity activity = getActivity();
        String title = getString(R.string.app_name);
        Utils.setupToolbar(activity, mToolbar, Utils.ToolbarNavIcon.NONE, title);
        mToolbar.inflateMenu(R.menu.menu_movies);
        mToolbar.setOnMenuItemClickListener(this);
    }

//    private void setUpToolbar() {
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MoviesPresenter.TAG, mActionListener.getResultPageNumber());
        outState.putSerializable(Config.BUNDLE_SORT_ORDER, mActionListener.getCurrentMovieFilter());
        outState.putParcelableArrayList(Config.BUNDLE_MOVIES, (ArrayList<? extends Parcelable>) mAdapter.getAll());
    }


    @Subscribe
    public void FavouriteMovieDeleted(FavouriteRemovedEvent event) {
        if(mActionListener.getCurrentMovieFilter() == MovieFilterList.FAVORITES){
            mAdapter.removeMovie(event.id);
        }
    }

    private void SetUpRecyclerView() {

        int columnCount;

        if(Utils.isTablet(mContext)){
            columnCount = 3;
        }
        else {
            columnCount = 2;
        }


        manager = new GridLayoutManager(mContext, columnCount, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    int visibleItemCount = recyclerView.getChildCount();
                    int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                    int pastVisibleItem = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisibleItem) >= totalItemCount) {
                        mActionListener.loadMovies(mActionListener.getCurrentMovieFilter());
                    }
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);

    }



    /*------------------- Implementation of MovieContract.View ------------------------*/

    @Override
    public void setProgressIndicator(boolean active) {
        if (getView() == null) {
            return;
        }

        ProgressBar progressBar = (ProgressBar) getView().findViewById(R.id.progress_bar);
        if (active) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void showMovieDetailUi(Bundle movieArgs, View view) {
        if (Utils.isTablet(mContext)) {
            ((MoviesFragmentCallback) getActivity()).onMOvieClick(movieArgs);
        } else {
            Intent intent = new Intent(mContext, MovieDetails.class);
            intent.putExtra(Config.BUNDLE_SINGLE_MOVIES, movieArgs);
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= Build.VERSION_CODES.JELLY_BEAN) {
                Pair<View, String> p1 = Pair.create(view.findViewById(R.id.movie_poster_image_view), "poster");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) mContext, p1);
                mContext.startActivity(intent, options.toBundle());
            } else {
                mContext.startActivity(intent);
            }
        }
    }

    @Override
    public void setMovies(List<Movie> movies) {
        mAdapter.addAll(movies);
    }

    @Override
    public void clearMovies() {
        mAdapter.clear();
    }

    @Override
    public void showMessage(String msg, int duration) {
        if (getView() == null) {
            return;
        }

        Snackbar.make(getView(), msg, duration).show();
    }

    @Override
    public void loadFavoriteMovies() {
        mAdapter.clear();
        setProgressIndicator(true);
        List<Movie> favList = new ArrayList<>();

        Cursor c = getActivity().getContentResolver().query(FavouriteMovieProvider.Movies.CONTENT_URI, null, null, null, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Movie movie = new Movie();

                    movie.setId(c.getInt(c.getColumnIndex(FavouriteMoviesColumn.ID)));
                    movie.setTitle(c.getString(c.getColumnIndex(FavouriteMoviesColumn.TITLE)));
                    movie.setBackdrop_url(c.getString(c.getColumnIndex(FavouriteMoviesColumn.BACKDROP_URL)));
                    movie.setPosterImage(c.getString(c.getColumnIndex(FavouriteMoviesColumn.POSTER_URL)));
                    movie.setVote_average(c.getLong(c.getColumnIndex(FavouriteMoviesColumn.RATING)));

                    Date date = new Date();
                    try {
                        date = Utils.getDateFromString(c.getString(c.getColumnIndex(FavouriteMoviesColumn.RELEASE_DATE)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    movie.setRelease_date(date);
                    movie.setOverview(c.getString(c.getColumnIndex(FavouriteMoviesColumn.OVERVIEW)));


                    favList.add(movie);


                } while (c.moveToNext());
            }

            c.close();
        }

        if (favList.size() > 0) {
            mAdapter.addAll(favList);
            mActionListener.setProgressIndicator(false);
        } else {
            setProgressIndicator(false);
            showMessage("You do not have Favorite movies yet", 0);
        }
    }

    /*-------------------- Implementation of MovieItemClickListener -----------------------*/

    @Override
    public void onMovieClick(View v, int position) {
        mActionListener.openMovieDetails(mAdapter.get(position), v);
    }

    @Override
    public void onResume() {
        if (mActionListener != null) {
            mActionListener.setProgressIndicator(false);
            if (mActionListener.getCurrentMovieFilter().equals(MovieFilterList.FAVORITES)) {
                loadFavoriteMovies(); //reload, this can improve.
            }
        }
        super.onResume();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_filter) {
            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle(getString(R.string.movies_filter))
                    .setSingleChoiceItems(R.array.sort_values, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    mActionListener.loadMovies(MovieFilterList.FAVORITES);
                                    break;
                                case 1:
                                    mActionListener.loadMovies(MovieFilterList.HIGHEST_RATED);
                                    break;
                                case 2:
                                    mActionListener.loadMovies(MovieFilterList.MOST_POPULAR);
                                    break;
                                case 3:
                                    mActionListener.loadMovies(MovieFilterList.NOW_PLAYING);
                                    break;
                                case 4:
                                    mActionListener.loadMovies(MovieFilterList.UPCOMING);
                            }
                            dialog.dismiss();
                        }
                    }).create();
            dialog.show();
        }

        if(id== R.id.action_about){
            Intent intent = new Intent(mContext, About.class);
            mContext.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
