package supportview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.vntoeic.bkteam.vntoeicpro.R;

/**
 * Created by giang on 5/28/17.
 */

public abstract class BaseFragment extends Fragment  {
    public FloatingActionButton mFloatingActionButton;
    private boolean isOpen = false;
    public CustomNavigation topNavi, bottomNavi;
    public Context mContext;
    public FrameLayout mFrameLayout, mBlurLayout;

    int orgX;
    int offsetX;

    public void setItemOnClick(CustomNavigation.OnItemClickedListener listener) {
        topNavi.setOnItemClickedListener(listener);
        bottomNavi.setOnItemClickedListener(listener);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);

        mContext = getActivity();

        mFrameLayout = (FrameLayout) view.findViewById(R.id.frame_layout);
        mBlurLayout = (FrameLayout) view.findViewById(R.id.blur_effect);
        topNavi = (CustomNavigation) view.findViewById(R.id.customNavigation);
        bottomNavi = (CustomNavigation) view.findViewById(R.id.customNavigation2);

        setUpContent(R.id.frame_layout);
        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingButton);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpen = !isOpen;
                if (isOpen) {
                    mBlurLayout.setVisibility(View.VISIBLE);
                    onOpen();
                } else {
                    mBlurLayout.setVisibility(View.GONE);
                    onClose();
                }
            }
        });

        mBlurLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClose();
                isOpen = !isOpen;
                mBlurLayout.setVisibility(View.GONE);

            }
        });


        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        orgX = (int) event.getX();
                        // iCallManager.fun();
                        return true;

                    case MotionEvent.ACTION_UP:
                        offsetX = (int) (event.getX() - orgX);
                        if(offsetX<-150)nextQuestion();
                        else if(offsetX>150)backQuestion();
                        return false;
                    case MotionEvent.ACTION_POINTER_DOWN:
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:

                }
                return false;
            }});

        return view;
    }

    public abstract void nextQuestion();

    public abstract void backQuestion();

    public abstract void setUpContent(int contentLayoutId);

    public abstract void onOpen();

    public abstract void onClose();
}
