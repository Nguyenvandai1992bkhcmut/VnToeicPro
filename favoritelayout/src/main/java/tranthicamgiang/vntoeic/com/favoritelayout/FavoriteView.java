package tranthicamgiang.vntoeic.com.favoritelayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Toast;

/**
 * Created by giang on 9/23/17.
 */

public class FavoriteView extends View{

    public static final int BEGINNING_DURATION_ANIMATION = 900;

    public static final int BEGINNING_DURATION_EACH_ITEM = 300;

    private Note[] mNotes = new Note[6];

    private EaseOutBack mEaseOutBack;
    private int mCurrentPosition;

    private GestureDetector mGestureDetector = new GestureDetector(new DetectSingleTap());
    private Context mContext;


    enum StateDraw {
        BEGIN,
        NORMAL,
        CHOOSING
    }

    private Board mBoard;


    private StateDraw mState;
    public FavoriteView(Context context) {
        this(context, null);
    }

    public FavoriteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;

        mBoard = new Board(context);

        mNotes[0] = new Note(context, R.mipmap.circle_red);
        mNotes[1] = new Note(context, R.mipmap.circle_orrange);
        mNotes[2] = new Note(context, R.mipmap.circle_yellow);
        mNotes[3] = new Note(context, R.mipmap.circle_green);
        mNotes[4] = new Note(context, R.mipmap.circle_blue);
        mNotes[5] = new Note(context, R.mipmap.circle_purple);
        mNotes[6] = new Note(context, R.mipmap.un_favorite);

        mBoard.mCurrentY = CommonDimen.HEIGH_VIEW_REACTION + 10;
        for (Note note : mNotes) {
            note.mCurrentY = mBoard.mCurrentY + CommonDimen.DIVIDE;
        }

        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(getResources().getColor(android.R.color.holo_blue_dark));
        if (mState != null) {
            mBoard.draw(canvas);
            for (Note note: mNotes){
                note.draw(canvas);
            }
        }
    }

    public void show() {
        mState = StateDraw.BEGIN;
        setVisibility(VISIBLE);
        beforeAnimationBeginning();
        startAnimation(new BeginningAnimation());
    }


    private Rect mRect = new Rect();

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if (mGestureDetector.onTouchEvent(event)){
            for (int i = 0; i < mNotes.length; i ++){
                float currentEmotionSize = mNotes[i].mCurrentX + mNotes[i].mCurrentSize + CommonDimen.DIVIDE;
                if (event.getX() > mNotes[i].mCurrentX
                        && event.getX() < currentEmotionSize
                        && event.getY() > mNotes[i].mCurrentY
                        && event.getY() < mNotes[i].mCurrentY + mNotes[i].mCurrentSize) {
                    onClick(i);
                }
            }
            return true;
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{

                mRect.top = getTop();
                mRect.bottom = getBottom();
                mRect.left = getLeft();
                mRect.right = getRight();
                return true;
            }
            case MotionEvent.ACTION_MOVE:{
                if (!(mRect.contains((int)(getLeft() + event.getX()), (int) (getTop() + event.getY())))){
//                    Toast.makeText(mContext, "Nha ra cung oi", Toast.LENGTH_SHORT).show();
                    backToNormal();
                    return true;
                }

                for (int i = 0; i < mNotes.length; i ++){
                    float currentEmotionSize = mNotes[i].mCurrentX + mNotes[i].mCurrentSize + CommonDimen.DIVIDE;
                    if (event.getX() > mNotes[i].mCurrentX && event.getX() < currentEmotionSize) {
                        selected(i);
                        return true;
                    }
                }

                return true;
            }
            case MotionEvent.ACTION_UP:{
                backToNormal();
                return true;
            }
        }
        return false;
    }

    private void onClick(int position) {
        Toast.makeText(mContext, "on Click " , Toast.LENGTH_SHORT).show();
    }

    class DetectSingleTap extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }
    }

    private void backToNormal() {

        mState = StateDraw.NORMAL;
        startAnimation(new ChooseEmotionAnimation());
    }

    private void selected(int position) {

        if (mCurrentPosition == position && mState == StateDraw.CHOOSING) return;
        mState = StateDraw.CHOOSING;
        mCurrentPosition = position;
        startAnimation(new ChooseEmotionAnimation());
    }

    class ChooseEmotionAnimation extends Animation {
        public ChooseEmotionAnimation(){

            if (mState == StateDraw.CHOOSING) {
                beforeAnimationChoosing();
            } else {
                beforeAnimationNormalBack();
            }

            setDuration(200);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {

            calculateInSessionChoosingAndEnding(interpolatedTime);
        }
    }

    private void beforeAnimationNormalBack() {
        for (int i = 0; i < mNotes.length; i ++) {
            mNotes[i].beginSize = mNotes[i].mCurrentSize;
            mNotes[i].endSize = Note.NORMAL_SIZE;
        }

        mBoard.beginHeight = (int) mBoard.getCurrentHeight();
        mBoard.endHeight = Board.BOARD_HEIGHT_NORMAL;

    }

    private void beforeAnimationChoosing() {
        for (int i = 0; i < mNotes.length; i ++) {
            mNotes[i].endSize = i == mCurrentPosition? Note.CHOOSE_SIZE : Note.MINIMAL_SIZE;
            mNotes[i].beginSize = mNotes[i].mCurrentSize;
        }

        mBoard.beginHeight = (int) mBoard.getCurrentHeight();
        mBoard.endHeight = Board.BOARD_HEIGHT_MINIMAL;

    }

    private void calculateInSessionChoosingAndEnding(float interpolatedTime) {

        mBoard.setCurrentHeight(mBoard.beginHeight + (mBoard.endHeight - mBoard.beginHeight) * interpolatedTime);
        for (int i = 0; i < mNotes.length; i ++) {
            Note note = mNotes[i];
            note.mCurrentSize = (int) (note.beginSize + (note.endSize - note.beginSize) * interpolatedTime);
            note.mCurrentY = Board.BASE_LINE - note.mCurrentSize;

        }
        calculateCoordinateX();
        invalidate();
    }

    private void calculateCoordinateX() {
        mNotes[0].mCurrentX =  Board.BOARD_X + CommonDimen.DIVIDE;
        mNotes[mNotes.length - 1].mCurrentX = Board.BOARD_X + Board.BOARD_WIDTH - CommonDimen.DIVIDE - mNotes[mNotes.length - 1].mCurrentSize;

        for (int i = 1; i < mCurrentPosition; i ++) {
            mNotes[i].mCurrentX = mNotes[i - 1].mCurrentX + mNotes[i - 1].mCurrentSize + CommonDimen.DIVIDE;
        }

        for (int i = mNotes.length - 2; i > mCurrentPosition; i --) {
            mNotes[i].mCurrentX = mNotes[i + 1].mCurrentX - CommonDimen.DIVIDE - mNotes[i].mCurrentSize;
        }

        if (mCurrentPosition != 0 && mCurrentPosition != mNotes.length - 1) {
            if (mCurrentPosition < mNotes.length / 2) {
                mNotes[mCurrentPosition].mCurrentX = mNotes[mCurrentPosition - 1].mCurrentX + mNotes[mCurrentPosition - 1].mCurrentSize + CommonDimen.DIVIDE;
            } else {
                mNotes[mCurrentPosition].mCurrentX = mNotes[mCurrentPosition + 1].mCurrentX - CommonDimen.DIVIDE - mNotes[mCurrentPosition].mCurrentSize;
            }
        }
    }

    class BeginningAnimation extends Animation{
        public BeginningAnimation() {
            beforeAnimationBeginning();
            setDuration(BEGINNING_DURATION_ANIMATION);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {

            calculateInSessionBeginning(interpolatedTime);
        }
    }

    private void calculateInSessionBeginning(float interpolatedTime) {

        float currentTime = BEGINNING_DURATION_ANIMATION * interpolatedTime;

        if (currentTime >= 0) {
            mBoard.mCurrentY = mBoard.endY + mEaseOutBack.getCoordinateYFromTime(
                    Math.min(currentTime, BEGINNING_DURATION_EACH_ITEM)
            );
        }


        if (currentTime >= 100) {
            mNotes[0].mCurrentY = mNotes[0].endY + mEaseOutBack.getCoordinateYFromTime(
                    Math.min(currentTime - 100, BEGINNING_DURATION_EACH_ITEM)
            );
        }

        if (currentTime >= 200) {
            mNotes[1].mCurrentY = mNotes[1].endY + mEaseOutBack.getCoordinateYFromTime(
                    Math.min(currentTime - 200, BEGINNING_DURATION_EACH_ITEM)
            );
        }

        if (currentTime >= 300) {
            mNotes[2].mCurrentY = mNotes[2].endY + mEaseOutBack.getCoordinateYFromTime(
                    Math.min(currentTime - 300, BEGINNING_DURATION_EACH_ITEM)
            );
        }

        if (currentTime >= 400) {
            mNotes[3].mCurrentY = mNotes[3].endY + mEaseOutBack.getCoordinateYFromTime(
                    Math.min(currentTime - 400, BEGINNING_DURATION_EACH_ITEM)
            );
        }

        if (currentTime >= 500) {
            mNotes[4].mCurrentY = mNotes[4].endY + mEaseOutBack.getCoordinateYFromTime(
                    Math.min(currentTime - 100, BEGINNING_DURATION_EACH_ITEM)
            );
        }

        if (currentTime >= 600) {
            mNotes[5].mCurrentY = mNotes[5].endY + mEaseOutBack.getCoordinateYFromTime(
                    Math.min(currentTime - 600, BEGINNING_DURATION_EACH_ITEM)
            );
        }

        invalidate();

    }

    private void beforeAnimationBeginning() {

        mBoard.beginHeight = Board.BOARD_HEIGHT_NORMAL;
        mBoard.beginY = Board.BOARD_BOTTOM + Utils.dpToPx(15);

        mBoard.endHeight = Board.BOARD_HEIGHT_NORMAL;
        mBoard.endY = Board.BOARD_Y;

        mEaseOutBack = new EaseOutBack(BEGINNING_DURATION_EACH_ITEM, mBoard.beginY - mBoard.endY, 0);

        for (int i = 0; i < mNotes.length; i ++) {
            mNotes[i].beginY = Board.BOARD_BOTTOM + Utils.dpToPx(15);
            mNotes[i].endY = Board.BASE_LINE - mNotes[i].mCurrentSize;
            mNotes[i].mCurrentX = i == 0? Board.BOARD_X + CommonDimen.DIVIDE : mNotes[i - 1].mCurrentX + mNotes[i - 1].mCurrentSize + CommonDimen.DIVIDE;
        }
    }

}
