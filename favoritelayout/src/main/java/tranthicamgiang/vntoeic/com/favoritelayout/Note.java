package tranthicamgiang.vntoeic.com.favoritelayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

/**
 * Created by giang on 9/23/17.
 */

public class Note {

    public static final int NORMAL_SIZE = Utils.dpToPx(40);

    public static final int MINIMAL_SIZE = Utils.dpToPx(28);

    public static final int CHOOSE_SIZE = Utils.dpToPx(80);

    private static final int MAX_WIDTH_TITLE = Utils.dpToPx(60);

    private static final float DISTANCE = Utils.dpToPx(15);

    public int mCurrentSize = NORMAL_SIZE;

    public int beginSize, endSize;

    public int beginY, endY;


    private Bitmap mImageResource;

    public float mCurrentX, mCurrentY;

    private Paint mImagePaint, mTitlePaint;


    public Note(Context context, int imageResource) {

        this.mImageResource = BitmapFactory.decodeResource(context.getResources(), imageResource);

        this.mImagePaint = new Paint();
        this.mImagePaint.setAntiAlias(true);
        Shader shader = new BitmapShader(mImageResource, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mImagePaint.setShader(shader);

        this.mTitlePaint = new Paint();
        this.mTitlePaint.setAntiAlias(true);

    }


    public void draw(Canvas canvas) {
        RectF rectF = new RectF(mCurrentX, mCurrentY, mCurrentX + mCurrentSize, mCurrentY + mCurrentSize);
        canvas.drawBitmap(mImageResource, null, rectF,  mImagePaint);
    }
}
