package feeds;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dainguyen on 8/28/17.
 */

public  class ImageCache {
    final static int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    final static int cacheSize = maxMemory/2;
    private static LruCache<String,Bitmap> mcache=new LruCache<String, Bitmap>(cacheSize) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            // The cache size will be measured in kilobytes rather than
            // number of items.

            return bitmap.getByteCount() / 1024;
        }
    };

    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mcache.put(key, bitmap);
        }
    }

    public static  Bitmap getBitmapFromMemCache(String key) {
        return mcache.get(key);
    }


}
