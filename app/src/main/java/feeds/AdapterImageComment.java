package feeds;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by dainguyen on 9/4/17.
 */
public class AdapterImageComment extends FragmentStatePagerAdapter {

    private ArrayList<String>src;
    public AdapterImageComment(FragmentManager fm, ArrayList<String>src) {
        super(fm);
        this.src = src;
    }

    @Override
    public Fragment getItem(int position) {
        FragmentImage fragment = new FragmentImage();
        Bundle bundle = new Bundle();
        bundle.putString("data",src.get(position));

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return src.size();
    }
}