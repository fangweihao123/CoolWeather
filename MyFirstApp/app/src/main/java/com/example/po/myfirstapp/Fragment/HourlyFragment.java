package com.example.po.myfirstapp.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.po.myfirstapp.R;
import com.example.po.myfirstapp.gson.Daily_Forecast;
import com.example.po.myfirstapp.gson.HeWeather;
import com.example.po.myfirstapp.gson.Hourly_Forecast;

/**
 * Created by 13701 on 2017/10/11.
 */

public class HourlyFragment extends WeatherFragment {
    private ImageView hourlyImage;
    @Override
    void showContent(HeWeather heWeather) {
        Hourly_Forecast hourlyForecast = heWeather.hourly_forecast.get(0);
        if(hourlyForecast.cond.txt.equals("阴")||hourlyForecast.cond.txt.equals("多云")){
            hourlyImage.setBackgroundResource(R.drawable.cloudy);
            hourlyImage.setAlpha(0.6f);
        } else if(hourlyForecast.cond.txt.equals("阵雨")||hourlyForecast.cond.txt.equals("小雨")||hourlyForecast.cond.txt.equals("大雨")){
            hourlyImage.setBackgroundResource(R.drawable.rain);
            hourlyImage.setAlpha(0.6f);
        }else if(hourlyForecast.cond.txt.equals("晴")){
            hourlyImage.setBackgroundResource(R.drawable.sunny);
            hourlyImage.setAlpha(0.6f);
        }
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.hourly_fragment,container,false);
        hourlyImage = (ImageView) v.findViewById(R.id.hourly_image);
        return v;
    }
}
