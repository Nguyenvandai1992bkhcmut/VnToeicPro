package utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by giang on 9/13/17.
 */

public class ImageHelper {
    public interface DownloadImageListener {
        void onDownloadSuccess(Bitmap bitmap);
    }

    public static class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        private final DownloadImageListener mListener;

        public DownloadImage(DownloadImageListener listener) {
            this.mListener = listener;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(params[0])
                    .build();

            try {
                Response response = client.newCall(request).execute();
                InputStream inputStream = response.body().byteStream();
                return BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mListener.onDownloadSuccess(bitmap);
        }
    }

}
