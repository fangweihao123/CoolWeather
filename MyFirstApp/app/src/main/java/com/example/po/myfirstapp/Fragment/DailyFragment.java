package com.example.po.myfirstapp.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.po.myfirstapp.R;
import com.example.po.myfirstapp.gson.Daily_Forecast;
import com.example.po.myfirstapp.gson.HeWeather;

import org.w3c.dom.Text;

/**
 * Created by 13701 on 2017/10/11.
 */

public class DailyFragment extends WeatherFragment {
    private ImageView dailyImage;
    private View v;
    private TextView date;
    private TextView weather;
    private TextView hum;
    private TextView min_tmp;
    private TextView max_tmp;
    private TextView wind_dir;
    private TextView sc;
    private TextView spd;
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.daily_fragment,container,false);
        //return super.onCreateView(inflater, container, savedInstanceState);
        dailyImage = (ImageView) v.findViewById(R.id.daily_pic);
        date = (TextView) v.findViewById(R.id.date);
        weather = (TextView) v.findViewById(R.id.weather);
        hum = (TextView) v.findViewById(R.id.hum);
        min_tmp = (TextView) v.findViewById(R.id.min_tmp);
        max_tmp = (TextView) v.findViewById(R.id.max_tmp);
        wind_dir = (TextView) v.findViewById(R.id.wind_dir);
        sc = (TextView) v.findViewById(R.id.sc);
        spd = (TextView) v.findViewById(R.id.spd);

        return v;
    }

    @Override
    void showContent(HeWeather heWeather) {
            Daily_Forecast dailyForecast = heWeather.daily_forecast.get(0);
            if(dailyForecast.cond.txt_d.equals("阴")||dailyForecast.cond.txt_d.equals("多云")||dailyForecast.cond.txt_d.equals("晴间多云")){
                dailyImage.setBackgroundResource(R.drawable.cloudy);
                dailyImage.setAlpha(0.6f);
            } else if(dailyForecast.cond.txt_d.equals("阵雨")||dailyForecast.cond.txt_d.equals("小雨")||dailyForecast.cond.txt_d.equals("大雨")){
                dailyImage.setBackgroundResource(R.drawable.rain);
                dailyImage.setAlpha(0.6f);
            }else if(dailyForecast.cond.txt_d.equals("晴")){
                dailyImage.setBackgroundResource(R.drawable.sunny);
                dailyImage.setAlpha(0.6f);
            }
            date.setText("天气预报:"+dailyForecast.date);
            weather.setText(dailyForecast.cond.txt_d);
            hum.setText(dailyForecast.hum);
            min_tmp.setText("最低温:"+dailyForecast.temprature.min);
            max_tmp.setText("最高温:"+dailyForecast.temprature.max);
            wind_dir.setText(dailyForecast.wind.dir);
            sc.setText(dailyForecast.wind.sc);
            spd.setText(dailyForecast.wind.spd);
    }
}

