package com.examples.user.newserver;

import android.app.Activity;
import android.content.CursorLoader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.examples.user.newserver.DataHandler.HttpRequest;
import com.examples.user.newserver.Table1.Datalist;
import com.examples.user.newserver.Database.Dbase;
import com.examples.user.newserver.Table1.Loaderlistview;
import com.examples.user.newserver.Table1.MyAdapter;
import com.examples.user.newserver.Table2.Listner2;
import com.examples.user.newserver.Table2.Loadergridview;

import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener, UIcallback {
    private AppBarLayout appBarLayout;


    HttpRequest mHttpRequest;
    ImageView view;
    MyAdapter adapter;
    Dbase controller;
    CursorLoader cursorLoader;
    static RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    ArrayList<Datalist> list = new ArrayList<>();
    public static RecyclerView.LayoutManager mLayoutManager;
    public static SwipeRefreshLayout mswipe;
    public static final int LOADER = 0x01;
    public static ProgressBar progressBar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);


        collapsingToolbarLayout.setTitle("Server Data");

        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        view = (ImageView) findViewById(R.id.imageView);
        controller = new Dbase(App.getContext());
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(this);
        mswipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
      //  progreeslistner();
        mswipe.setColorSchemeResources(android.R.color.holo_blue_dark, android.R.color.holo_red_dark, android.R.color.holo_green_light, android.R.color.holo_purple);
        mswipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mswipe.setRefreshing(true);


                run123();
            }
        });

//swiperefresh s=new swiperefresh(this);

    }

    /*@Override
    public void update() {
        Loaderlistview fragment = (Loaderlistview) getFragmentManager().findFragmentById(R.id.table1);
        fragment.refreshloader();
        //  new Loaderlistview();
        mswipe.setRefreshing(false);
    }
*/


    @Override
    public void update2(String loadertype) {
        if(loadertype=="listview"){
        Loaderlistview fragment1 = (Loaderlistview) getFragmentManager().findFragmentById(R.id.table1);
        fragment1.refreshloader();}
        else if(loadertype=="gridview"){
            Loadergridview fragment2 = (Loadergridview) getFragmentManager().findFragmentById(R.id.table2);
            fragment2.refreshloader();
        }
        //  new Loaderlistview();
        mswipe.setRefreshing(false);
    }


    public void run123() {

        Listner2.listener(this);
        Httplistener.listenerbtn(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
      //  if (collapsingToolbarLayout.getHeight() + verticalOffset < 2 * ViewCompat.getMinimumHeight(collapsingToolbarLayout)) {
          if(verticalOffset ==0){
            mswipe.setEnabled(true);
        } else {
            mswipe.setEnabled(false);
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
    }


}
