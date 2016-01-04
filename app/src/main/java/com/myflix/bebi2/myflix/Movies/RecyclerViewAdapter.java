package com.myflix.bebi2.myflix.Movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myflix.bebi2.myflix.Pojo.Movie;
import com.myflix.bebi2.myflix.R;
import com.myflix.bebi2.myflix.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bebi2 on 12/29/2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context mContext;

    private List<Movie> mMovies;
    private MovieItemClickListener itemClickedListener;

    public RecyclerViewAdapter(Context mContext, MovieItemClickListener itemClickedListener) {
        this.mContext = mContext;
        this.mMovies = new ArrayList<>();
        this.itemClickedListener = itemClickedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_movie_item, parent, false);
        ViewHolder holder = new ViewHolder(view, itemClickedListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Movie movie = mMovies.get(position);
        Glide.with(mContext)
                .load(movie.getPosterImage())
                .error(R.drawable.not_available)
                .placeholder(R.drawable.placeholder)
                .into(holder.imageViewPoster);

        holder.textViewTitle.setText(movie.getTitle());
        holder.textViewReleaseDate.setText(Utils.getStringFromDate(movie.getRelease_date()));
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void clear() {
        int size = mMovies.size();
        mMovies.clear();
        notifyItemRangeRemoved(0, size);
    }

    public Movie get(int position) {
        return mMovies.get(position);
    }


    public List<Movie> getAll() {
        return mMovies;
    }

    public void addAll(List<Movie> movies) {
        if (mMovies.size() > 0) {
            int mSize = mMovies.size();
            mMovies.addAll(mSize, movies);
            notifyItemRangeInserted(mSize, movies.size());
        } else {
            mMovies.addAll(movies);
            notifyItemRangeInserted(0, movies.size());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.movie_poster_image_view)
        ImageView imageViewPoster;
        @Bind(R.id.title_text_view)
        TextView textViewTitle;
        @Bind(R.id.release_date_text_view)
        TextView textViewReleaseDate;

        private MovieItemClickListener itemClickedListener;

        public ViewHolder(View itemView, MovieItemClickListener itemClickedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemClickedListener = itemClickedListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickedListener != null)
                itemClickedListener.onMovieClick(v, this.getLayoutPosition());
        }
    }
}
