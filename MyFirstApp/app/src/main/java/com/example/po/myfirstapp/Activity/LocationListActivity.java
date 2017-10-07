package com.example.po.myfirstapp.Activity;

import android.support.v4.app.Fragment;

import com.example.po.myfirstapp.Fragment.LocationListFragment;

/**
 * Created by 13701 on 2017/10/6.
 */

public class LocationListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new LocationListFragment();
    }
}
