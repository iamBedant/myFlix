package com.myflix.bebi2.myflix.MovieDetails;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.myflix.bebi2.myflix.DB.FavouriteMovieProvider;
import com.myflix.bebi2.myflix.DB.FavouriteMoviesColumn;
import com.myflix.bebi2.myflix.Events.OverviewLoadedEvent;
import com.myflix.bebi2.myflix.Events.ReviewLoadedEvent;
import com.myflix.bebi2.myflix.Events.TrailerLoadedEvent;
import com.myflix.bebi2.myflix.Pojo.Movie;
import com.myflix.bebi2.myflix.Pojo.Reviews;
import com.myflix.bebi2.myflix.Pojo.Videos;
import com.myflix.bebi2.myflix.R;
import com.myflix.bebi2.myflix.Utils.Config;
import com.myflix.bebi2.myflix.Utils.EventBus;
import com.myflix.bebi2.myflix.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.BindInt;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment implements MovieDetailsContract.View {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Bind(R.id.backdrop)
    ImageView backdrop;

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.release_date)
    TextView releaseDate;
    @Bind(R.id.rating)
    TextView rating;

    @Bind(R.id.poster)
    ImageView poster;

    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @BindDrawable(R.drawable.ic_favorite_border_white_24dp)
    Drawable mStarOutline;
    @BindDrawable(R.drawable.ic_favorite_white_24dp)
    Drawable mStarFilled;

    @BindInt(R.integer.anim_short_duration)         int mAnimShortDuration;


    Context mContext;
    public static String TAG = MovieDetailsFragment.class.getSimpleName();
    private Movie mMovie;
    private MovieDetailsContract.UserActionListener mUserActionListener;
    int mutedColor = R.attr.colorPrimary;
    Boolean isFavorite;

    Videos firstTrailer = new Videos();

    public MovieDetailsFragment() {
    }

    public static MovieDetailsFragment newInstance(Bundle arg) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(arg);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        mContext = getActivity();
        if (savedInstanceState != null) {
            mMovie = (Movie) savedInstanceState.get(Config.BUNDLE_SINGLE_MOVIES);
            isFavorite = savedInstanceState.getBoolean(Config.BUNDLE_IS_FAVOURITE);

        } else {
            mMovie = getArguments().getParcelable(Config.BUNDLE_SINGLE_MOVIES);
            Cursor cursor = getActivity().getContentResolver().query(FavouriteMovieProvider.Movies.withId(mMovie.getId()), null, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() == 0) {
                    isFavorite = false;
                } else
                    isFavorite = true;
                cursor.close();
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUserActionListener = new MovieDetailsPresenter(mMovie, this);
        mUserActionListener.showMovieDetails();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, view);
        setUpToolBar();
        setUpTabLayout();
        updateFavoriteBtn();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = getActivity().getContentResolver().query(FavouriteMovieProvider.Movies.withId(mMovie.getId()), null, null, null, null);

                if (cursor != null) {
                    if (cursor.getCount() == 0) {

                        ContentValues values = new ContentValues();
                        values.put(FavouriteMoviesColumn.ID, mMovie.getId());
                        values.put(FavouriteMoviesColumn.TITLE, mMovie.getTitle());
                        values.put(FavouriteMoviesColumn.BACKDROP_URL, mMovie.getBackdrop_url());
                        values.put(FavouriteMoviesColumn.RATING, String.valueOf(mMovie.getVote_average()));
                        values.put(FavouriteMoviesColumn.RELEASE_DATE, mMovie.getRelease_date().getTime());
                        values.put(FavouriteMoviesColumn.POSTER_URL, mMovie.getPosterImage());
                        values.put(FavouriteMoviesColumn.OVERVIEW, mMovie.getOverview());

                        getActivity().getContentResolver().insert(FavouriteMovieProvider.Movies.withId(mMovie.getId()), values);
                        showMessage(mMovie.title + " " + "added to favorites", 0);

                    } else {
                        getActivity().getContentResolver().delete(
                                FavouriteMovieProvider.Movies.withId(mMovie.getId()), null, null);

                        showMessage(mMovie.title + " " + "removed from favorites", 0);
                    }
                    updateFavoriteBtn();
                }
                cursor.close();
            }

        });

        return view;
    }

//    private void setUpTabLayout() {
//        PagerAdapter pagerAdapter = new MovieDetailsPagerAdapter(getActivity().getSupportFragmentManager());
//        viewPager.setAdapter(pagerAdapter);
//        tabLayout.setupWithViewPager(viewPager);
//
//    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        //TODO: SaveState
        super.onSaveInstanceState(outState);
    }

    private void setUpTabLayout() {
        if (viewPager != null) {
            setupViewPager();
            tabLayout.setupWithViewPager(viewPager);
        }

    }


    private void setupViewPager() {
        MovieDetailsPagerAdapter adapter = new MovieDetailsPagerAdapter(getActivity().getSupportFragmentManager());

        ReviewFragment reviewFragment = new ReviewFragment();
        TrailersFragment trailersFragment = new TrailersFragment();

        OverviewFragment overviewFragment = new OverviewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Config.BUNDLE_MOVIE_OVERVIEW, mMovie.getOverview());
        overviewFragment.setArguments(bundle);

        adapter.addFragment(trailersFragment, "TRAILERS");
        adapter.addFragment(reviewFragment, "REVIEWS");
        adapter.addFragment(overviewFragment, "OVERVIEW");


        viewPager.setAdapter(adapter);
    }


    private void setUpToolBar() {

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.inflateMenu(R.menu.menu_movie_details);


        if (!Utils.isTablet(mContext)) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        collapsingToolbarLayout.setTitle(mMovie.getTitle());
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        Glide.with(mContext)
                .load(mMovie.getBackdrop_url())
                .asBitmap()
                .error(R.drawable.not_available)
                .placeholder(R.drawable.placeholder)
                .into(new BitmapImageViewTarget(backdrop) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        super.onResourceReady(bitmap, anim);
                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @SuppressWarnings("ResourceType")
                            @Override
                            public void onGenerated(Palette palette) {
                                mutedColor = palette.getMutedColor(R.color.colorPrimary);
                                if (getLuminance(mutedColor) < 240) {
                                    collapsingToolbarLayout.setContentScrimColor(mutedColor);
                                    collapsingToolbarLayout.setStatusBarScrimColor(getDarkerColor(mutedColor));
                                    return;
                                }


                            }
                        });
                    }
                });


        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    public static int getLuminance(int argb) {
        int lum = (77 * ((argb >> 16) & 255)
                + 150 * ((argb >> 8) & 255)
                + 29 * ((argb) & 255)) >> 8;
        return lum;
    }

    public static int getDarkerColor(int color) {
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, (int) (red * 0.9), (int) (green * 0.9), (int) (blue * 0.9));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movie_details, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.share:

                if (firstTrailer.getKey() != null) {
                    String subject = mMovie.getTitle() + " - " + firstTrailer.getName();
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        //noinspection deprecation
                        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                    } else {
                        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                    }
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, Utils.getVideoUrl(firstTrailer.getKey()));
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                    startActivity(Intent.createChooser(shareIntent, getString(R.string.share_trailer)));
                } else {
                    showMessage("No Trailer available", Snackbar.LENGTH_LONG);
                }

                return true;
            case android.R.id.home:
                getActivity().supportFinishAfterTransition();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void updateFavoriteBtn() {
        fab.setImageDrawable(isFavorite ? mStarFilled : mStarOutline);
        isFavorite = !isFavorite;
        if (fab.getScaleX() == 0) {
            // credits for onPreDraw technique: http://frogermcs.github.io/Instagram-with-Material-Design-concept-part-2-Comments-transition/
            fab.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    fab.getViewTreeObserver().removeOnPreDrawListener(this);
                    fab.animate()
                            .withLayer()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setInterpolator(new DecelerateInterpolator())
                            .setDuration(mAnimShortDuration)
                            .start();
                    return true;
                }
            });
        }
    }


    /*-------------------------- Implementation of MovieDetails Contract.View------------------------- */
    @Override
    public void setProgressIndicator(boolean active) {

    }

    @Override
    public void showMessage(String msg, int duration) {
        if (getView() == null) {
            return;
        }

        Snackbar.make(getView(), msg, duration).show();
    }

    @Override
    public void setMoviePoster(String moviePoster) {
        if (moviePoster == null || moviePoster.isEmpty()) {

            poster.setImageResource(R.drawable.not_available);

        } else {
            Glide.with(getActivity())
                    .load(moviePoster)
                    .error(R.drawable.not_available)
                    .placeholder(R.drawable.placeholder)
                    .into(poster);
        }
    }

    @Override
    public void setMovieBackdropImage(String backdropImage) {

    }

    @Override
    public void setTitle(String _title) {
        title.setText(_title);
    }

    @Override
    public void setTrailers(ArrayList<Videos> trailers) {
        if (trailers.size() > 0) {
            // mToolbar.getMenu().findItem(R.id.share).setVisible(true);
            firstTrailer = trailers.get(0);
        }
        //firstTrailer = trailers.get(0);
        EventBus.getInstance().post(new TrailerLoadedEvent(trailers));
    }

    @Override
    public void setReviews(ArrayList<Reviews> reviews) {
        EventBus.getInstance().post(new ReviewLoadedEvent(reviews));
    }

    @Override
    public void setRating(Float _rating) {
        if (_rating != 0.0) {
            rating.setText(_rating + "");
        } else {
            rating.setText("NA");
        }
    }

    @Override
    public void setReleaseDate(String release_Date) {
        if (releaseDate != null) {
            releaseDate.setText(release_Date);
        } else {
            releaseDate.setText("Not Available");
        }
    }

    @Override
    public void setFavoriteButtonSrc(boolean isFavorite) {

    }

    @Override
    public void setFavoriteButtonBackgroundColor(int color) {

    }

    @Override
    public void setPlayTrailerButtonBackgroundColor(int color) {

    }

    @Override
    public void setShareTrailerButtonBackgroundColor(int color) {

    }

}
