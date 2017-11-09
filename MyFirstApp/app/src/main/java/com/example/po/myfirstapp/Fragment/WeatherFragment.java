package com.example.po.myfirstapp.Fragment;

import android.support.v4.app.Fragment;

import com.example.po.myfirstapp.gson.HeWeather;

/**
 * Created by 13701 on 2017/10/14.
 */

public abstract class WeatherFragment extends Fragment {
    abstract void showContent(HeWeather heWeather);
}
