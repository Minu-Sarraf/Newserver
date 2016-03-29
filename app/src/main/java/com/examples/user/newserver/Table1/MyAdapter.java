package com.examples.user.newserver.Table1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.examples.user.newserver.Table2.Datalist2;
import com.examples.user.newserver.Display.picasoround12;
import com.examples.user.newserver.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by minu on 1/17/2016.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Datalist> list;
    private ArrayList<Datalist2> list2;
    int a = 0;
    private Context mcontext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context context, ArrayList<Datalist> list) {
        this.list = list;
        this.mcontext = context;

    }

    public MyAdapter(ArrayList<Datalist2> list2) {
        this.list2 = list2;
        //  this.mcontext=context;

    }

    public void refresh(ArrayList<Datalist> list) {
        this.list = list;
        notifyDataSetChanged();

    }

/*    public void refresh2(ArrayList<Datalist2> list2) {
        this.list2 = list2;
        notifyDataSetChanged();

    }*/

    //
    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
     /* View  v1 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid, parent, false);*/
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TextView title = (TextView) holder.view.findViewById(R.id.textView2);
        final TextView gridtitle = (TextView) holder.view.findViewById(R.id.gridtextView);

        ImageView image2 = (ImageView) holder.view.findViewById(R.id.imageView);
        ImageView gridimage = (ImageView) holder.view.findViewById(R.id.imageView2);
        final ProgressBar progressBar = (ProgressBar) holder.view.findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.VISIBLE);
        // final ProgressBar gridprogressBar = (ProgressBar) holder.view.findViewById(R.id.progressBar2);
        // gridprogressBar.setVisibility(View.VISIBLE);
        title.setText((Html.fromHtml((list).get(position).getTitle())));


//        gridtitle.setText((Html.fromHtml((list2).get(position).getDescription())));
        Picasso.with(mcontext).load((list).get(position).getUrl())
                .transform(new picasoround12()).resize(550, 150).centerInside()
                .into(image2, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        // TODO Auto-generated method stub

                    }
                });
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return -1;
        } else {
            return list.size();
        }
    }
}