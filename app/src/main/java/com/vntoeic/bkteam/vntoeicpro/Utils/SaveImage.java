package com.vntoeic.bkteam.vntoeicpro.Utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by giang on 6/2/17.
 */

public class SaveImage {
    public static void saveImage(Bitmap bitmap, File file) {
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fOut);
            fOut.flush();
            fOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Bitmap getImageFromStorage(Context context, String fileName) {
        ContextWrapper contextWrapper = new ContextWrapper(context);
        File dir = contextWrapper.getDir("images", Context.MODE_PRIVATE);
        File file = new File(dir, fileName + ".png");
        if (file.exists()) {
            String path = file.getAbsolutePath();
            return BitmapFactory.decodeFile(path);
        }
        else return null;
    }

    public static File createFile(Context context, String token) {
        ContextWrapper contextWrapper = new ContextWrapper(context);
        File dir = contextWrapper.getDir("images", Context.MODE_PRIVATE);
        File file = new File(dir, token + ".png");
        return file;
    }

}
