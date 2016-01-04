package com.myflix.bebi2.myflix.MovieDetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myflix.bebi2.myflix.Pojo.Reviews;
import com.myflix.bebi2.myflix.R;
import com.myflix.bebi2.myflix.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bebi2 on 1/3/2016.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private Context mContext;
    private List<Reviews> mReviews;
    private onRTItemClick itemClickedListener;

    public ReviewAdapter(Context mContext, onRTItemClick itemClickedListener) {
        this.mContext = mContext;
        this.mReviews = new ArrayList<>();
        this.itemClickedListener = itemClickedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_review_item, parent, false);
        ViewHolder holder = new ViewHolder(view, itemClickedListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Reviews review = mReviews.get(position);

        holder.textViewAuthor.setText(review.getAuthor());
        holder.textViewcontent.setText(review.getContent());
        if(review.getCut()){
            holder.RelativeLayoutreadMore.setVisibility(View.VISIBLE);
        }
        else {
            holder.RelativeLayoutreadMore.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }


    public void clear() {
        int size = mReviews.size();
        mReviews.clear();
        notifyItemRangeRemoved(0, size);
    }

    public Reviews get(int position) {
        return mReviews.get(position);
    }


    public List<Reviews> getAll() {
        return mReviews;
    }

    public void addAll(List<Reviews> movies) {
        if (mReviews.size() > 0) {
            int mSize = mReviews.size();
            mReviews.addAll(mSize, movies);
            notifyItemRangeInserted(mSize, movies.size());
        } else {
            mReviews.addAll(movies);
            notifyItemRangeInserted(0, movies.size());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @Bind(R.id.author)
        TextView textViewAuthor;
        @Bind(R.id.content)
        TextView textViewcontent;
        @Bind(R.id.readmore)
        Button buttonReadMore;
        @Bind(R.id.r_m)
        RelativeLayout RelativeLayoutreadMore;

        private onRTItemClick itemClickedListener;

        public ViewHolder(View itemView, onRTItemClick itemClickedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemClickedListener = itemClickedListener;
            buttonReadMore.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickedListener != null)
                itemClickedListener.onMovieClick(this.getLayoutPosition());
        }
    }
}
