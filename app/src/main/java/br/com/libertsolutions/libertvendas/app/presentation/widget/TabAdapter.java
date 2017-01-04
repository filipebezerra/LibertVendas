package br.com.libertsolutions.libertvendas.app.presentation.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class TabAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragments = new ArrayList<>();

    private final List<String> mFragmentTitles = new ArrayList<>();

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
        notifyDataSetChanged();
    }

    public boolean hasPosition(int position) {
        return getItem(position) != null;
    }

    public List<Fragment> getFragments() {
        return mFragments;
    }

    @Override public Fragment getItem(int position) {
        if (position >= 0 && position < mFragments.size()) {
            return mFragments.get(position);
        }
        return null;
    }

    @Override public int getCount() {
        return mFragments.size();
    }

    @Override public CharSequence getPageTitle(int position) {
        if (position >= 0 && position < mFragmentTitles.size()) {
            return mFragmentTitles.get(position);
        }
        return null;
    }
}
