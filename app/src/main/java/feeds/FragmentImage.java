package feeds;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by dainguyen on 9/4/17.
 */

public class FragmentImage extends Fragment{
    private String src="";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            src = bundle.getString("data");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ImageView imageView = new ImageView(getContext());
        int W = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.95f);
        int H = (int) (getContext().getResources().getDisplayMetrics().heightPixels*0.8f);
        Picasso.with(getContext()).load(src).resize(W,H).centerInside().into(imageView);
        return imageView;
    }
}