package com.vntoeic.bkteam.vntoeicpro.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by giang on 6/2/17.
 */

public class DownloadImage extends AsyncTask{

    private String mImageRequestUrl, mToken;
    private ImageView mImageView;
    public Bitmap mBitmap;
    private Context mContext;

    public DownloadImage(Context context, String requestUrl, ImageView imageView, String token) {

        this.mContext = context;
        this.mImageRequestUrl = requestUrl;
        this.mImageView = imageView;
        this.mToken = token;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            URL url = new URL(mImageRequestUrl);
            URLConnection connection = url.openConnection();
            mBitmap = BitmapFactory.decodeStream(connection.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mBitmap;
    }

    @Override
    protected void onPostExecute(Object o) {
        Glide.with(mContext)
                .load(mBitmap)
                .into(mImageView);
    }
}
