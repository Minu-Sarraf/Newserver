package com.examples.user.newserver;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.examples.user.newserver.DataHandler.HttpRequest;
import com.examples.user.newserver.Table1.JsonModel;
import com.examples.user.newserver.DataHandler.Jsonparser;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by User on 3/24/2016.
 */
public class Httplistener {
    static HttpRequest mHttpRequest;

    public static void listenerbtn(final UIcallback uIcallback) {
        // mHttpRequest = new HttpRequest(URL2, null);
        mHttpRequest = new HttpRequest(Constants.URLlistcable, null);
        mHttpRequest.appendAppInfo(true);
        mHttpRequest.setOnBackgroundWorkListener(new HttpRequest.OnBackgroundWorkListener() {
            @Override
            public void performWork(HttpRequest.Status status, String result) {
                if (status == HttpRequest.Status.HTTP_SUCCESS) {
                    Debug.v(result);
                    ArrayList<JsonModel> output = null;
                    //  ArrayList<Jsonmodel2> output1 = null;
                    try {
                        output = Jsonparser.parse1(result);
                        //  output2 = Jsonparser.parse2(result);
                        InsertdatatoDb.bottom(output, App.getContext());
                        //Dataloaderbottom db=new Dataloaderbottom();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mHttpRequest.setOnResultsListener(new HttpRequest.OnResultsListener()

                                          {
                                              @Override
                                              public void updateUI(HttpRequest.Status status, String result) throws JSONException {
                                                  Debug.v("Got status " + status);
                                                  if (uIcallback != null) {
                                                      uIcallback.update2("listview");
                                                  }

                                              }
                                          }

        );
        mHttpRequest.execute();
    }

    public void progreeslistner() {
        mHttpRequest.setOnProgressListener(new HttpRequest.OnProgressListener() {
                                               @Override
                                               public void update(long bytesRead, long contentLength, boolean done) {
                                                   final int progress = (int) ((100 * bytesRead) / contentLength);

                                                   // Enable if you want to see the progress with logcat
                                                   Log.v("Progress: ", progress + "%");


                                                   //progressBar.setProgress(progress);
                                                   if (done) {
                                                       // findViewById(R.id.progressbar1).setVisibility(View.GONE);
                                                       //progressBar.setVisibility(View.GONE);
                                                       //  Toast.makeText(App.getContext(), "Loading.......progress status: ", Toast.LENGTH_LONG).show();
                                                       Log.i("progress", "Done loading");
                                                   }
                                               }
                                           }
        );
    }
}
