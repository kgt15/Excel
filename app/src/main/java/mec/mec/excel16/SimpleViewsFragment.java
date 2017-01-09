package mec.mec.excel16;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cleveroad.splittransformation.SquareViewPagerIndicator;

/**
 * Created by hassan on 8/29/16.
 */
public class SimpleViewsFragment extends Fragment {
    public static SimpleViewsFragment instance() {
        return new SimpleViewsFragment();
    }

    private ViewPager viewPager;
    private SquareViewPagerIndicator indicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        indicator = (SquareViewPagerIndicator) view.findViewById(R.id.indicator);
        return view;
    }



}
