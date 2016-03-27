package com.examples.user.newserver;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * Created by User on 3/25/2016.
 */
public class swiperefresh  extends SwipeRefreshLayout implements AppBarLayout.OnOffsetChangedListener {
    private AppBarLayout appBarLayout;

    public swiperefresh(Context context) {
        super(context);
    }

    public swiperefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getContext() instanceof Activity) {
            appBarLayout = (AppBarLayout) ((Activity) getContext()).findViewById(R.id.app_bar_layout);
            appBarLayout.addOnOffsetChangedListener(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        appBarLayout.removeOnOffsetChangedListener(this);
        appBarLayout = null;
        super.onDetachedFromWindow();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        this.setEnabled(i == 0);
    }
}

    /*@Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (collapsingToolbarLayout.getHeight() + verticalOffset < 2 * ViewCompat.getMinimumHeight(collapsingToolbarLayout)) {
            swipeRefreshLayout.setEnabled(false);
        } else {
            swipeRefreshLayout.setEnabled(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        appBarLayout.removeOnOffsetChangedListener(this);
    }*/