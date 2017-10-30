package customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;
import favoritevocabulary.FavoriteAdapter;
import model.ModelAbstractWord;

/**
 * Created by giang on 7/30/17.
 */

public class CustomPinnedSectionListview extends PinnedSectionListView {


    private static final long DURATION = 500;
    private Context mContext;
    private List<ModelAbstractWord> mData;


    public CustomPinnedSectionListview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setDivider(null);
        mContext = context;
    }

    public void setData(List<ModelAbstractWord> data) {
        this.mData = data;
    }


    public void removeRow(final ModelAbstractWord modelAbstractFavoriteWord, final int currentPosition, final int newPosition) {

        if (currentPosition < 0 || newPosition < 0) return;

        final View removedView = getChildAt(currentPosition);

        ObjectAnimator scaleX =  ObjectAnimator.ofFloat(removedView, SCALE_X, 1.0f, 0.0f);
        ObjectAnimator scaleY =  ObjectAnimator.ofFloat(removedView, SCALE_Y, 1.0f, 0.0f);
        AnimatorSet set = new AnimatorSet();
        set.play(scaleX).with(scaleY);
        set.setDuration(300);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                final FavoriteAdapter adapter = (FavoriteAdapter) getAdapter();

                final HashMap<Long, Rect> listviewItemBounds = new HashMap<Long, Rect>();
                int firstVisiblePosition = getFirstVisiblePosition();

                for (int i = currentPosition; i < firstVisiblePosition + getChildCount(); i ++) {
                    View child = getChildAt(i);
                    int position = firstVisiblePosition + i;
                    long itemId = adapter.getItemId(position);

                    Rect rect = new Rect(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
                    listviewItemBounds.put(itemId, rect);
                }

                adapter.removeStableIdForDataAtPosition(currentPosition);
                mData.remove(currentPosition);
                adapter.notifyDataSetChanged();
                removedView.setScaleX(1.0f);
                removedView.setScaleY(1.0f);

                final ViewTreeObserver viewTreeObserver = CustomPinnedSectionListview.this.getViewTreeObserver();
                viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        viewTreeObserver.removeOnPreDrawListener(this);
                        ArrayList<Animator> animators = new ArrayList<Animator>();

                        int firstVisiblePosition = getFirstVisiblePosition();

                        for (int position = currentPosition; position < firstVisiblePosition + getChildCount(); position ++) {
                            View child = getChildAt(position);

                            long itemId = adapter.getItemId(position);
                            Rect rect = listviewItemBounds.get(itemId);

                            if (rect == null) {
                                ObjectAnimator translation =
                                        ObjectAnimator.ofFloat(child, TRANSLATION_Y, child.getHeight(), 0);
                                animators.add(translation);
                            } else {
                                int delta = rect.top - child.getTop();
                                ObjectAnimator translation =
                                        ObjectAnimator.ofFloat(child, TRANSLATION_Y, delta, 0);
                                animators.add(translation);
                            }
                        }

                        AnimatorSet set2 = new AnimatorSet();
                        set2.setDuration(400);
                        set2.playTogether(animators);
                        set2.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                addRow(modelAbstractFavoriteWord, newPosition);
                            }
                        });

                        set2.start();
                        return true;
                    }
                });
            }
        });
    }

    public void addRow(ModelAbstractWord modelAbstractFavoriteWord, final int newPosition) {
        if (newPosition < 0) return;

        final FavoriteAdapter adapter = (FavoriteAdapter) getAdapter();

        final HashMap<Long, Rect> listviewItemBounds = new HashMap<>();

        int firstVisiblePosition = getFirstVisiblePosition();
        for (int i = 0; i < getChildCount(); i ++) {
            View child = getChildAt(i);
            int position = firstVisiblePosition + i;
            long itemId = adapter.getItemId(position);

            Rect rect = new Rect(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
            listviewItemBounds.put(itemId, rect);
        }

        mData.add(newPosition, modelAbstractFavoriteWord);
        adapter.addStableIdForDataAtPosition(newPosition);

        final ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                viewTreeObserver.removeOnPreDrawListener(this);

                ArrayList<Animator> animators = new ArrayList<Animator>();

                int firstVisiblePosition = getFirstVisiblePosition();

                if (firstVisiblePosition == newPosition) {
                    View newCell = getChildAt(newPosition);
                    /**
                     * Fades in new Cell've just added
                     */

                    ObjectAnimator fadeInNewCell = ObjectAnimator.ofFloat(newCell, View.ALPHA, 0.0f, 1.0f);
                    ObjectAnimator scaleXNewCell = ObjectAnimator.ofFloat(newCell, SCALE_X, 0.0f, 1.0f);
                    ObjectAnimator scaleYNewCell = ObjectAnimator.ofFloat(newCell, SCALE_Y, 0.0f, 1.0f);

                    animators.add(fadeInNewCell);
                    animators.add(scaleXNewCell);
                    animators.add(scaleYNewCell);
                }

                for (int i = 0; i < getChildCount(); i ++) {
                    View child = getChildAt(i);
                    int position = firstVisiblePosition + i;
                    long itemId = adapter.getItemId(position);

                    Rect rect = listviewItemBounds.get(itemId);
                    if (rect == null) {
                        /**
                         * if cell wasn't visible before data set change
                         */

                        int height = child.getHeight() + getDividerHeight();
                        ObjectAnimator translationNewCell = ObjectAnimator.ofFloat(child, TRANSLATION_Y, -height, 0.0f);
                        animators.add(translationNewCell);

                    } else {

                        int delta = rect.top - child.getTop();
                        ObjectAnimator translation = ObjectAnimator.ofFloat(child, TRANSLATION_Y, delta, 0);
                        animators.add(translation);
                    }
                }

                AnimatorSet set = new AnimatorSet();
                set.setDuration(DURATION);
                set.playTogether(animators);
                set.start();

                return true;
            }
        });

        adapter.notifyDataSetChanged();


    }
}
