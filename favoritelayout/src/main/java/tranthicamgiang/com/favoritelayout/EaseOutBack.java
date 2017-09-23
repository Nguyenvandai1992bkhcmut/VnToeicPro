package tranthicamgiang.com.favoritelayout;

/**
 * Created by giang on 9/23/17.
 */

public class EaseOutBack {

    private final long mDuration;
    private final float mBegin, mChange;
    private float s = 1.70158f;

    public EaseOutBack(long duration, float begin, float end) {
        this.mDuration = duration;
        this.mBegin = begin;
        this.mChange = end - begin;
    }

    public float getCoordinateYFromTime(float currentTime) {
        return mChange * ((currentTime = currentTime / mDuration - 1) * currentTime * ((s + 1) * currentTime + s) + 1) + mBegin;
    }
}
