package utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

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


    public static class RoundImage extends Drawable {
        private final Paint mPaint;
        private final float mR, cX, cY;

        public RoundImage(Bitmap input, ImageView imageView) {
            BitmapShader shader = new BitmapShader(input, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            this.cX = imageView.getWidth() / 2f;
            this.cY = imageView.getHeight() / 2f;
            this.mR = imageView.getHeight() / 2f;
            this.mPaint = new Paint();
            this.mPaint.setAntiAlias(true);
            this.mPaint.setShader(shader);
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            canvas.drawCircle(cX, cY, mR, mPaint);
        }

        @Override
        public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return 0;
        }
    }

}
