package com.example.po.myfirstapp.Activity;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.example.po.myfirstapp.Fragment.MainFragment;
import com.example.po.myfirstapp.R;

public class MainActivity extends SingleFragmentActivity {
    private ImageView bingPicPng;
    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bingPicPng = (ImageView) findViewById(R.id.bing_pic_png);

    }

    private void loadBingPic(){
        String requestBingPic = "http://guolin.tech/api/bing_pic";
    }
}

