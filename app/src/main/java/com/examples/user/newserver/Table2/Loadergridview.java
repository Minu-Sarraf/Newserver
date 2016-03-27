package com.examples.user.newserver.Table2;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.examples.user.newserver.App;
import com.examples.user.newserver.DataHandler.HttpRequest;
import com.examples.user.newserver.Database.Contentprovider;
import com.examples.user.newserver.Database.Dbase;
import com.examples.user.newserver.R;
import com.examples.user.newserver.Table2.Datalist2;

import com.examples.user.newserver.Table2.Myadapter2;

import java.util.ArrayList;

/**
 * Created by User on 3/24/2016.
 */
public class Loadergridview extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    String URL = "http://202.79.36.225/jsttest.php/test2.php";
  // String URL = "http://192.168.100.16/jsttest.php/test2.php";
    //"http://nepalipatro.com.np/forex/get";
    HttpRequest mHttpRequest;
    ImageView view;
    Myadapter2 adapter;
    Dbase controller;
    CursorLoader cursorLoader;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    ArrayList<Datalist2> list2 = new ArrayList<>();

    GridLayoutManager mLayoutManager;
    public static final int LOADER = 0x01;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        controller = new Dbase(App.getContext());



        viewall();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.gridlayout, container, false);
        // Inflate the layout for this fragment
       mRecyclerView = (RecyclerView) rootview.findViewById(R.id.my_recycler_viewbtn);

        mRecyclerView.setHasFixedSize(true);
         mLayoutManager = new GridLayoutManager(App.getContext(),3);
         mRecyclerView.setLayoutManager(mLayoutManager);
         adapter = new Myadapter2(App.getContext(),null);
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
        String[] projection = {Dbase.ID, Dbase.description, Dbase.url};
        cursorLoader = new CursorLoader(App.getContext(), Contentprovider.CONTENT_URI2, null, null, null, null);
        return cursorLoader;

    }

    public void viewall() {

        getLoaderManager().initLoader(1, null, this);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
        list2.clear();
         c.moveToFirst();

          do{


         list2.add(new Datalist2(
                 c.getString(c.getColumnIndex(Dbase.description))
                 , c.getString(c.getColumnIndex(Dbase.url))));
         adapter.refresh2(list2);
           // adapter.refresh2(list2);*/
         Log.e("table2", list2.get(0).getDescription());
          } while (c.moveToNext());

         c.close();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //  adapter.swapCursor(null);

    }
}
