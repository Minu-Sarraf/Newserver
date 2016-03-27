package com.examples.user.newserver.Table1;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.examples.user.newserver.App;
import com.examples.user.newserver.DataHandler.HttpRequest;
import com.examples.user.newserver.Database.Contentprovider;
import com.examples.user.newserver.R;
import com.examples.user.newserver.Table1.Datalist;
import com.examples.user.newserver.Database.Dbase;
import com.examples.user.newserver.Table1.MyAdapter;

import java.util.ArrayList;

/**
 * Created by User on 3/24/2016.
 */
public class Loaderlistview extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    String URL = "http://202.79.36.225/jsttest.php/test.php";
    //"http://nepalipatro.com.np/forex/get";
    HttpRequest mHttpRequest;
    ImageView view;
    static MyAdapter adapter;

    CursorLoader cursorLoader;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    static ArrayList<Datalist> list = new ArrayList<>();


    public static final int LOADER = 0x01;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

        viewall();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.linearlayout, container, false);
        // Inflate the layout for this fragment
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(App.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        adapter = new MyAdapter(App.getContext(), null);
        mRecyclerView.setAdapter(adapter);
        return rootview;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {Dbase.ID, Dbase.colm, Dbase.colm3};
        cursorLoader = new CursorLoader(App.getContext(), Contentprovider.CONTENT_URI, null, null, null, null);
        return cursorLoader;

    }

    public void viewall() {

        getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
        list.clear();
        c.moveToFirst();

        do {


            list.add(new Datalist(
                    c.getString(c.getColumnIndex(Dbase.colm))
                    , c.getString(c.getColumnIndex(Dbase.colm3))));
            adapter.refresh(list);
        } while (c.moveToNext());
        c.close();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //  adapter.swapCursor(null);

    }
}
