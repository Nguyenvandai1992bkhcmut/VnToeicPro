package tranthicamgiang.vntoei.com.foldingeffect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by giang on 9/9/17.
 */

public class FoldingLayout extends ViewGroup{

    private float mCurrentWidth;
    private static final float DEPT_CONSTANT = 1500;
    private float mFoldFactor = 1f;
    private int mFoldMaxWidth = 0;
    private Matrix[] mMatrices= new Matrix[2];
    private RectF[] mFoldRects = new RectF[2];
    private float[] mSrc = new float[8], mDst = new float[8];
    private int mFoldHeight;
    private boolean isReady;
    private Paint mGradientPaint;
    private LinearGradient mLinearGradient;
    private FoldListener mFoldListener = null;
    private float mCurrentFoldWidth;

    public void setCurrentWidth(int currentWidth) {
        mCurrentWidth = currentWidth;
        this.mCurrentFoldWidth = mCurrentWidth / 2.0f;
    }

    public float getCurrentWidth() {
        return mCurrentWidth;
    }


    public interface FoldListener {
        void onFold(int currentWidth);
    }

    public FoldingLayout(Context context) {
        this(context, null);
    }

    public FoldingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        mGradientPaint = new Paint();
    }

    public void setFoldFactor(float foldFactor) {
        mFoldFactor = foldFactor;
        updateFold();
    }

    public float getFoldFactor() {
        return mFoldFactor;
    }

    public void setFoldListener(FoldListener foldListener) {
        mFoldListener = foldListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View childView = getChildAt(0);
        measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

        View childView = getChildAt(0);
        childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
        updateFold();
    }

    private void updateFold() {
        if (mFoldFactor == 0.0) setCurrentWidth(getWidth());

        prepareFold();
        calculateMatrices();
        invalidate();
    }

    private void calculateMatrices() {
        this.isReady = false;
        RectF firstRect, secondRect;

        firstRect = mFoldRects[0];
        secondRect = mFoldRects[1];

        if (firstRect == null || secondRect == null) return;

        for (int i = 0; i < 2; i ++) {
            mMatrices[i].reset();
        }

        float depth = (float) Math.sqrt(this.mFoldMaxWidth * this.mFoldMaxWidth - mCurrentFoldWidth * mCurrentFoldWidth);
        float scaleHeightFactor = DEPT_CONSTANT / (DEPT_CONSTANT + depth);

        float translationFactor = 1 - mFoldFactor;
        setCurrentWidth((int) (getWidth() * translationFactor));

        float scaleHeight = mFoldHeight * scaleHeightFactor;

        this.mLinearGradient = new LinearGradient(0, 0, mCurrentFoldWidth, 0, Color.TRANSPARENT, Color.BLACK, Shader.TileMode.MIRROR);
        this.mGradientPaint.setShader(this.mLinearGradient);
        int alpha = (int) (255 * mFoldFactor);
        this.mGradientPaint.setAlpha(alpha);


        float topScalePoint = (mFoldHeight - scaleHeight) / 2.0f;
        float bottomScalePoint = topScalePoint + scaleHeight;

        mSrc[0] = 0;
        mSrc[1] = 0;
        mSrc[2] = 0;
        mSrc[3] = mFoldHeight;
        mSrc[4] = mFoldMaxWidth;
        mSrc[5] = 0;
        mSrc[6] = mFoldMaxWidth;
        mSrc[7] = mFoldHeight;


        //first Rect
        mDst[0] = 0;
        mDst[1] = 0;
        mDst[2] = mDst[0];
        mDst[3] = mFoldHeight;
        mDst[4] = mCurrentFoldWidth;
        mDst[5] = topScalePoint;
        mDst[6] = mDst[4];
        mDst[7] = bottomScalePoint;

        mMatrices[0].setPolyToPoly(mSrc, 0, mDst, 0, 4);
        firstRect.left = 0;
        firstRect.top = 0;
        firstRect.right = (int) mCurrentFoldWidth;
        firstRect.bottom = mFoldHeight;


        //second Rect
        mDst[0] = mCurrentFoldWidth;
        mDst[1] = topScalePoint;
        mDst[2] = mDst[0];
        mDst[3] = bottomScalePoint;
        mDst[4] = mCurrentWidth;
        mDst[5] = 0;
        mDst[6] = mDst[4];
        mDst[7] = mFoldHeight;

        mMatrices[1].setPolyToPoly(mSrc, 0, mDst, 0, 4);
        secondRect.left = (int) mCurrentFoldWidth;
        secondRect.top = 0;
        secondRect.right = (int) (secondRect.left + mCurrentFoldWidth);
        secondRect.bottom = mFoldHeight;


        this.isReady  = true;
    }

    private void prepareFold() {
        for (int i = 0; i < 2; i++) {
            mMatrices[i] = new Matrix();
        }

        this.mFoldMaxWidth = getWidth() / 2;
        this.mFoldHeight = getHeight();

        for (int i = 0; i < 2; i ++) {
            mFoldRects[i] = new RectF(i * mCurrentFoldWidth, 0, i * mCurrentWidth + mCurrentWidth, mFoldHeight);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (!isReady) {
            super.dispatchDraw(canvas);
            return;
        }

        RectF rect;
        isReady = false;

        if (mFoldFactor == 0) {
            super.dispatchDraw(canvas);
        } else {
            for (int i = 0; i < 2; i ++) {
                canvas.save();
                rect = mFoldRects[i];

                canvas.clipRect(rect);
                canvas.concat(mMatrices[i]);
                canvas.translate(-mFoldMaxWidth * i, 0);

                super.dispatchDraw(canvas);
                canvas.restore();

                canvas.drawRect(rect, mGradientPaint);
            }
        }

        if (mFoldListener != null) {
            mFoldListener.onFold((int) (mCurrentWidth));
        }

    }
}
