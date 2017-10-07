package com.example.po.myfirstapp.Activity;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.example.po.myfirstapp.Fragment.MainFragment;
import com.example.po.myfirstapp.R;

public class MainActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }
}

