package com.example.po.myfirstapp.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.po.myfirstapp.Activity.WeatherConditionActivity;
import com.example.po.myfirstapp.R;
import com.example.po.myfirstapp.gson.HeWeather;
import com.example.po.myfirstapp.util.HttpUtil;
import com.example.po.myfirstapp.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 13701 on 2017/10/10.
 */

public class WeatherConditionFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.weather_condition_fragment,container,false);
        String locationName = getActivity().getIntent().
                getStringExtra(WeatherConditionActivity.EXTRA_WEATHER_ID);
        HeWeather heWeather = new HeWeather();
        queryWeatherInfo(locationName,heWeather);
        return v;
    }

    public void queryWeatherInfo(String locationName, HeWeather heWeather){
        String address = "https://free-api.heweather.com/v5/weather?city="
                + locationName + "&key=15d3758d385e45d2a0ad960f79290ce1";
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                //通过runonUIthread返回主线逻辑
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                final HeWeather weather = Utility.handleWeatherResponse(responseText);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(weather != null && "ok".equals(weather.status)){
                            Toast.makeText(getContext(),"1111",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(),"2222",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        //heWeather = Utility.handleWeatherResponse()
    }

}
