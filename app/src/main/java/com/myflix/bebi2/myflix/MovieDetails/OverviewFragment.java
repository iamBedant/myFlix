package com.myflix.bebi2.myflix.MovieDetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.myflix.bebi2.myflix.Events.OverviewLoadedEvent;
import com.myflix.bebi2.myflix.R;
import com.myflix.bebi2.myflix.Utils.Config;
import com.myflix.bebi2.myflix.Utils.EventBus;
import com.squareup.otto.Subscribe;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bebi2 on 1/1/2016.
 */
public class OverviewFragment extends Fragment {
    @Bind(R.id.synopsis)
    TextView overview;

    String overView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        ButterKnife.bind(this, view);
        overView = getArguments().getString(Config.BUNDLE_MOVIE_OVERVIEW);
        if(overView.trim().length()==0|| overView==null){
            overview.setText("No Overview available");
        }
        else {
            overview.setText(overView);
        }

        return view;
    }

}
