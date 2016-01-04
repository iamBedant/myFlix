package com.myflix.bebi2.myflix.MovieDetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myflix.bebi2.myflix.Pojo.Videos;
import com.myflix.bebi2.myflix.R;
import com.myflix.bebi2.myflix.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bebi2 on 1/3/2016.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder>  {

    private Context mContext;

    private List<Videos> mTrailers;
    private onRTItemClick itemClickedListener;

    public TrailerAdapter(Context mContext, onRTItemClick itemClickedListener) {
        this.mContext = mContext;
        this.mTrailers = new ArrayList<>();
        this.itemClickedListener = itemClickedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_trailer_item, parent, false);
        ViewHolder holder = new ViewHolder(view, itemClickedListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Videos trailer = mTrailers.get(position);
        Glide.with(mContext)
                .load(Utils.getThumbnail(trailer.getKey()))
                .error(R.drawable.not_available)
                .into(holder.videoThumbnail);

        holder.textViewTitle.setText(trailer.getName());
        holder.textViewType.setText("Size : "+trailer.getSize()+"p");
        holder.language.setText("Language : "+(trailer.getIso_639_1().equals("en")?"English":"Unknown"));
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }


    public void clear() {
        int size = mTrailers.size();
        mTrailers.clear();
        notifyItemRangeRemoved(0, size);
    }

    public Videos get(int position) {
        return mTrailers.get(position);
    }


    public List<Videos> getAll() {
        return mTrailers;
    }

    public void addAll(List<Videos> movies) {
        if (mTrailers.size() > 0) {
            int mSize = mTrailers.size();
            mTrailers.addAll(mSize, movies);
            notifyItemRangeInserted(mSize, movies.size());
        } else {
            mTrailers.addAll(movies);
            notifyItemRangeInserted(0, movies.size());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.thumbnail)
        ImageView videoThumbnail;
        @Bind(R.id.name)
        TextView textViewTitle;
        @Bind(R.id.trailer)
        TextView textViewType;
        @Bind(R.id.language)
        TextView language;

        private onRTItemClick itemClickedListener;

        public ViewHolder(View itemView, onRTItemClick itemClickedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemClickedListener = itemClickedListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickedListener != null)
                itemClickedListener.onMovieClick(this.getLayoutPosition());
        }
    }
}
