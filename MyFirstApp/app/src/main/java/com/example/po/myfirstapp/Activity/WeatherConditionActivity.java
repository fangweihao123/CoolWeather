package com.example.po.myfirstapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.po.myfirstapp.Fragment.WeatherConditionFragment;

/**
 * Created by 13701 on 2017/10/10.
 */

public class WeatherConditionActivity extends SingleFragmentActivity {
    public static final String EXTRA_WEATHER_ID =
            "com.example.po.myfirstapp.weather_id";
    public static Intent newIntent(Context packageContext,String locationName){
        Intent intent = new Intent(packageContext,WeatherConditionActivity.class);
        intent.putExtra(EXTRA_WEATHER_ID,locationName);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new WeatherConditionFragment();
    }
}
