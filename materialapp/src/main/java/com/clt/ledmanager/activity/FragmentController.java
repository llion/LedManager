package com.clt.ledmanager.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * Created by wyh on 15/11/7.
 */
public class FragmentController {
    private final FragmentActivity fragmentActivity;

    public FragmentController(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    public void add(boolean isShow, String tag, int resourceId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        if (isFragmentExist(tag)) {
            fragment = getFragment(tag);
        } else {
            fragmentTransaction.add(resourceId, fragment, tag);
        }
        if (!isShow) {
            fragmentTransaction.hide(fragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void changeFragment(String tag, int resourceId, Fragment targetFragment) {
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        List<Fragment> fragments = fragmentActivity.getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (int i = 0; i < fragments.size(); i++) {
                Fragment fragment = fragments.get(i);
                if (!tag.equals(fragment.getTag())) {
                    fragmentTransaction.hide(fragment);
                }
            }
        }
        if (!isFragmentExist(tag)) {
            fragmentTransaction.add(resourceId, targetFragment, tag);
        } else {
            fragmentTransaction.show(targetFragment);
        }

        fragmentTransaction.commitAllowingStateLoss();
    }

    public void changeFragment(String tag) {
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        List<Fragment> fragments = fragmentActivity.getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (int i = 0; i < fragments.size(); i++) {
                Fragment fragment = fragments.get(i);
                if (!tag.equals(fragment.getTag())) {
                    fragmentTransaction.hide(fragment);
                } else {
                    fragmentTransaction.show(fragment);
                }
            }
        }

        fragmentTransaction.commitAllowingStateLoss();
    }

    protected boolean isFragmentExist(String tag) {
        boolean isExist = false;
        if (getFragment(tag) != null) {
            isExist = true;
        }
        return isExist;
    }

    protected Fragment getFragment(String tag) {
        return fragmentActivity.getSupportFragmentManager().findFragmentByTag(tag);
    }
}
