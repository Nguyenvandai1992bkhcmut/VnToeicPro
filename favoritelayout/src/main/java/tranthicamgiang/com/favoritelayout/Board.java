package tranthicamgiang.com.favoritelayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by giang on 9/23/17.
 */

public class Board {

    public static final int BOARD_WIDTH =  6 * Note.NORMAL_SIZE + 7 * CommonDimen.DIVIDE;

    public static final int BOARD_HEIGHT_NORMAL = Utils.dpToPx(50);

    public static final int BOARD_HEIGHT_MINIMAL = Utils.dpToPx(38);

    public static final int BOARD_X = Utils.dpToPx(10);

    public static final int BOARD_BOTTOM = CommonDimen.HEIGH_VIEW_REACTION - Utils.dpToPx(25);

    public static final int BOARD_Y = BOARD_BOTTOM - BOARD_HEIGHT_NORMAL;

    public static final int BASE_LINE = BOARD_BOTTOM - CommonDimen.DIVIDE;

    public int beginY, endY;

    public int beginHeight, endHeight;

    public Paint mPaint;
    private float mCurrentHeight = BOARD_HEIGHT_NORMAL;
    public float mCurrentY;

    public Board(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(context.getResources().getColor(android.R.color.white));
        mPaint.setShadowLayer(10.0f, 20.0f, 10.0f, 0xFF000000);
    }


    public float getCurrentHeight() {
        return mCurrentHeight;
    }

    public void setCurrentHeight(float currentHeight) {
        mCurrentHeight = currentHeight;
        mCurrentY = BOARD_BOTTOM - mCurrentHeight;
    }

    public void draw(Canvas canvas) {

        float radius = mCurrentHeight / 2;
        RectF board = new RectF(BOARD_X, mCurrentY, BOARD_X + BOARD_WIDTH, mCurrentY + mCurrentHeight);
        canvas.drawRoundRect(board, radius, radius, mPaint);
    }
}
