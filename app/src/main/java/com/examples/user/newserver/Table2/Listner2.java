package com.examples.user.newserver.Table2;

import android.util.Log;

import com.examples.user.newserver.App;
import com.examples.user.newserver.Constants;
import com.examples.user.newserver.DataHandler.HttpRequest;
import com.examples.user.newserver.DataHandler.Jsonmodel2;
import com.examples.user.newserver.DataHandler.Jsonparser;
import com.examples.user.newserver.Debug;
import com.examples.user.newserver.InsertdatatoDb;
import com.examples.user.newserver.UIcallback;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by User on 3/25/2016.
 */
public class Listner2 {

    public static void listener(final UIcallback uIcallback) {

        HttpRequest mHttpRequest;
        mHttpRequest = new HttpRequest(Constants.URLgridcable, null);
        mHttpRequest.appendAppInfo(true);
        mHttpRequest.setOnResultsListener(new HttpRequest.OnResultsListener() {
            @Override
            public void updateUI(HttpRequest.Status status, String result) throws JSONException {
                Debug.v("Got status " + status);
                if (status == HttpRequest.Status.HTTP_SUCCESS) {
                    Debug.v(result);

                    ArrayList<Jsonmodel2> output = null;
                    try {
                        output = Jsonparser.parse2(result);
                        //
                        InsertdatatoDb.top(output, App.getContext());
                       // Log.e("parse2", output.get(0).getDescription());
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
                                                      uIcallback.update2("gridview");
                                                  }

                                              }
                                          }

        );
        mHttpRequest.execute();
    }
}
