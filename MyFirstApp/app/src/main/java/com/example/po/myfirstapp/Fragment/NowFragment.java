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
import com.example.po.myfirstapp.gson.HeWeather;

import org.w3c.dom.Text;

/**
 * Created by 13701 on 2017/10/11.
 */

public class NowFragment extends WeatherFragment {
    private ImageView nowImage;
    private TextView weather;
    private TextView nowHum;
    private TextView pcpn;                              //降水量
    private TextView tmp;
    private TextView wind_dir;
    private TextView sc;
    private TextView spd;
    private TextView comfort;
    private TextView carwash;
    private TextView sport;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.now_fragment,container,false);
        nowImage = (ImageView) v.findViewById(R.id.now_image);
        weather = (TextView) v.findViewById(R.id.weather);
        nowHum = (TextView) v.findViewById(R.id.now_hum);
        pcpn = (TextView) v.findViewById(R.id.pcpn);
        tmp = (TextView) v.findViewById(R.id.tmp);
        wind_dir = (TextView) v.findViewById(R.id.wind_dir);
        sc = (TextView) v.findViewById(R.id.sc);
        spd = (TextView) v.findViewById(R.id.spd);
        comfort = (TextView) v.findViewById(R.id.comfort);
        carwash = (TextView) v.findViewById(R.id.carwash);
        sport = (TextView) v.findViewById(R.id.sport);
        //pcpn = (TextView) v.findViewById(R.id.pcpn);

        return v;
    }

    @Override
    void showContent(HeWeather heWeather) {
        if(heWeather.now.condition.txt.equals("阴")||heWeather.now.condition.txt.equals("多云")||heWeather.now.condition.txt.equals("晴间多云")){
            nowImage.setBackgroundResource(R.drawable.cloudy);
            nowImage.setAlpha(0.6f);
        } else if(heWeather.now.condition.txt.equals("阵雨")||heWeather.now.condition.txt.equals("小雨")||heWeather.now.condition.txt.equals("大雨")){        //强阵雨
            nowImage.setBackgroundResource(R.drawable.rain);
            nowImage.setAlpha(0.6f);
        }else if(heWeather.now.condition.txt.equals("晴")){
            nowImage.setBackgroundResource(R.drawable.sunny);
            nowImage.setAlpha(0.6f);
        }
        weather.setText(heWeather.now.condition.txt);
        nowHum.setText("湿度"+heWeather.now.hum);
        pcpn.setText("降雨量"+heWeather.now.pcpn);
        tmp.setText("温度"+heWeather.now.tmp);
        wind_dir.setText(heWeather.now.wind.dir);
        sc.setText(heWeather.now.wind.sc);
        spd.setText(heWeather.now.wind.spd);
        comfort.setText(heWeather.suggestion.comfort.info);
        carwash.setText(heWeather.suggestion.carWash.info);
        sport.setText(heWeather.suggestion.sport.info);
    }
}
