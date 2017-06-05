package com.vntoeic.bkteam.vntoeicpro.Utils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.vntoeic.bkteam.vntoeicpro.Vocabulary.Activity.TestActivity;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by giang on 6/4/17.
 */

public class ImageUtils extends AsyncTask<String, Void, String> {
    private final ImageView mImageView;
    private final Context mContext;
    private final String vnToeic_url = "http://vnToeic.com/api/v1/part7/image/";

    public ImageUtils(ImageView imageView, Context context) {
        this.mImageView = imageView;
        this.mContext = context;
    }


    @Override
    protected String doInBackground(String... params) {
        FileOutputStream fileOutputStream = null;
        try {
            URL url = new URL(vnToeic_url + params[0]);
            URLConnection connection = url.openConnection();
            connection.connect();

            InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);
            fileOutputStream = mContext.openFileOutput(params[0], Context.MODE_PRIVATE);
            OutputStream outputStream = fileOutputStream;
            byte[] data = new byte[1024];

            int count = 0;
            while ((count = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, count);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mContext.getFilesDir().getAbsolutePath() + "/" + params[0];
    }

    @Override
    protected void onPostExecute(String s) {
//        Glide.with(mContext)
//                .load(BitmapFactory.decodeFile(s))
//                .into(mImageView);

        mImageView.setImageBitmap( BitmapFactory.decodeFile(s));
//        mImageView.setImageDrawable(Drawable.createFromPath(s));
        ((TestActivity)mContext).hideProgressDialog();
    }
}
