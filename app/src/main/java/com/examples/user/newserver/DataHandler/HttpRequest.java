package com.examples.user.newserver.DataHandler;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.examples.user.newserver.App;
import com.examples.user.newserver.Debug;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by User on 3/20/2016.
 */
public class HttpRequest extends AsyncTask<Void, Long, Void> {

    private int mConnectionTimeout = 150;

    private Context mContext;

    // response
    private String mResponseText = "";
    private Status mStatus;

    private Boolean mIsBinary = false;
    private String mFileName;

    // url
    private String mUrl;
    private RequestBody mRequestBody;

    private Boolean appendAppInfo = false;

    public HttpRequest(String url, RequestBody requestBody) {
        mContext = App.getContext();
        mUrl = url;
        mRequestBody = requestBody;
    }

    // interface
    private OnResultsListener mSetOnResultsListener;
    private OnProgressListener mSetOnProgressListener;
    private OnBackgroundWorkListener mSetOnBackgroundWorkListener;

    public interface OnResultsListener {
        void updateUI(Status status, String result) throws JSONException;
    }

    public interface OnProgressListener {
        void update(long bytesRead, long contentLength, boolean done);

    }

    public interface OnBackgroundWorkListener {
        void performWork(Status status, String result);
    }

    public void setOnResultsListener(OnResultsListener listener) {
        this.mSetOnResultsListener = listener;
    }

    public void setOnProgressListener(OnProgressListener listener) {
        this.mSetOnProgressListener = listener;

    }

    public void setOnBackgroundWorkListener(OnBackgroundWorkListener listener) {
        this.mSetOnBackgroundWorkListener = listener;
    }

    public void setBinary(Boolean binary) {
        mIsBinary = binary;
    }

    public void setDownloadFileName(String fileName) {
        mFileName = fileName;
    }

    @Override
    protected Void doInBackground(Void... params) {

        if (appendAppInfo) {
            mUrl = mUrl + getAppInfoUrlSection();
        }

        if (isNetworkAvailable(mContext)) {

            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(mConnectionTimeout, TimeUnit.SECONDS); // connect timeout
            client.setReadTimeout(mConnectionTimeout, TimeUnit.SECONDS);    // socket timeout
            Request request;
            if (mRequestBody == null) {
                request = new Request.Builder().url(mUrl).cacheControl(CacheControl.FORCE_NETWORK).build();
            } else {
                request = new Request.Builder().url(mUrl).post(mRequestBody).cacheControl(CacheControl.FORCE_NETWORK).build();
            }

            client.networkInterceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body()))
                            .build();
                }
            });

            try {
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                if (mIsBinary) {
                    File downloadedFile = new File(mContext.getCacheDir(), mFileName);
                    BufferedSink sink = Okio.buffer(Okio.sink(downloadedFile));
                    sink.writeAll(response.body().source());
                    sink.close();
                    mResponseText = "";
                } else {
                    mResponseText = response.body().string();
                }
                response.body().close(); //close the connection!!!

                mStatus = Status.HTTP_SUCCESS;

            } catch (Exception e) {
                if(Debug.DEBUG) {
                    e.printStackTrace();
                }
                mStatus = Status.HTTP_OTHERS;
            }

        } else {
            mStatus = Status.HTTP_NO_NETWORK;
        }

        if (mSetOnBackgroundWorkListener != null) {
            mSetOnBackgroundWorkListener.performWork(mStatus, mResponseText);
        }

        return null;

    }

    public void setStatus(Status status){
        mStatus = status;
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void mVoid) {
        super.onPostExecute(mVoid);
        try {
            if (!isCancelled()) {
                if (mSetOnResultsListener != null) {
                    mSetOnResultsListener.updateUI(mStatus, mResponseText);
                }
            }
        } catch (Exception e) {
            if (Debug.DEBUG) {
                e.printStackTrace();
            }
        }
    }

    public enum Status {
        HTTP_NO_NETWORK, HTTP_OTHERS, HTTP_SUCCESS
    }

    public void appendAppInfo(Boolean append) {
        this.appendAppInfo = append;
    }

    private class ProgressResponseBody extends ResponseBody {

        private final ResponseBody responseBody;
        private BufferedSource bufferedSource;

        public ProgressResponseBody(ResponseBody responseBody) {
            this.responseBody = responseBody;
        }

        @Override
        public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override
        public long contentLength() throws IOException {
            return responseBody.contentLength();
        }

        @Override
        public BufferedSource source() throws IOException {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    if (mSetOnProgressListener != null) {
                        mSetOnProgressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                        publishProgress(((100 * bytesRead) / responseBody.contentLength()));
                        Log.e("progressbar", String.valueOf(totalBytesRead));
                    }
                    return bytesRead;
                }
            };
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static String getAppInfoUrlSection() {
        try {
            PackageManager manager = App.getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(App.getContext().getPackageName(), 0);

            return "/vd/" + android.os.Build.VERSION.RELEASE
                    + "/vn/" + info.versionName
                    + "/vc/" + info.versionCode + "/tz/"
                    + TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT);
        } catch (Exception e) {
            if (Debug.DEBUG) {
                e.printStackTrace();
            }
            return "";
        }
    }


}
