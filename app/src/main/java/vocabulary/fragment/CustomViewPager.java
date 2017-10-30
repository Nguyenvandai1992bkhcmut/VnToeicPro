package vocabulary.fragment;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.io.Serializable;

/**
 * Created by giang on 10/23/17.
 */

public class CustomViewPager extends ViewPager implements ScrollViewPagerListener, Serializable{

    private boolean enableScroll = true;

    public CustomViewPager(Context context) {
        this(context, null);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(!enableScroll) return false;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void enableScroll(boolean scrollable) {
        enableScroll = scrollable;
    }
}

