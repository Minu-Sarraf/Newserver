package com.examples.user.newserver.Table2;

/**
 * Created by User on 3/25/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.examples.user.newserver.Display.picasoround12;
import com.examples.user.newserver.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by minu on 1/17/2016.
 */

public class Myadapter2 extends RecyclerView.Adapter<Myadapter2.ViewHolder> {
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



    public Myadapter2(Context context,ArrayList<Datalist2> list2) {
        this.list2 = list2;
          this.mcontext=context;

    }



   public void refresh2(ArrayList<Datalist2> list2) {
        this.list2 = list2;
        notifyDataSetChanged();

    }

    //
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {

      View  v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final TextView gridtitle = (TextView) holder.view.findViewById(R.id.gridtextView);


        ImageView gridimage = (ImageView) holder.view.findViewById(R.id.imageView2);

         final ProgressBar gridprogressBar = (ProgressBar) holder.view.findViewById(R.id.progressBar2);
         gridprogressBar.setVisibility(View.VISIBLE);



        gridtitle.setText((Html.fromHtml((list2).get(position).getDescription())));

      Picasso.with(mcontext).load((list2).get(position).getImageurl())
                .transform(new picasoround12()).resize(400, 100).centerInside()
                .into(gridimage, new Callback() {
                    @Override
                    public void onSuccess() {
                        gridprogressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        // TODO Auto-generated method stub

                    }
                });

    }

    @Override
    public int getItemCount() {
        if (list2 == null) {
            return -1;
        } else {
            return list2.size();
        }
    }
}
