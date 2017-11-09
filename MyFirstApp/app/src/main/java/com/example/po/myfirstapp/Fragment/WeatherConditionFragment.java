package com.example.po.myfirstapp.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 13701 on 2017/10/10.
 */

public class WeatherConditionFragment extends Fragment {
    private List<Fragment> fragments;
    private String locationName;
    private HeWeather heWeather;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.weather_condition_fragment,container,false);
        locationName = getActivity().getIntent().
                getStringExtra(WeatherConditionActivity.EXTRA_WEATHER_ID);
        heWeather = new HeWeather();
        fragments=new ArrayList<Fragment>();
        fragments.add(new NowFragment());
       //fragments.add(new HourlyFragment());
        fragments.add(new DailyFragment());                                 //最后一个fragment出现了问题
        FragmentAdapter adapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), fragments);

        //设定适配器
        ViewPager vp = (ViewPager)v.findViewById(R.id.viewpager);
        vp.setOffscreenPageLimit(3);                            //竟然是这个坑...
        vp.setAdapter(adapter);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        //queryWeatherInfo(locationName,heWeather);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queryWeatherInfo(locationName,heWeather);
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
                            //Toast.makeText(getContext(),"1111",Toast.LENGTH_SHORT).show();
                            showContent(weather);
                        }else{
                            Toast.makeText(getContext(),"发生错误,请连接网络后重试",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        //heWeather = Utility.handleWeatherResponse()
    }

    public void showContent(HeWeather heWeather){
        for(int i=0;i<fragments.size();i++){
            ((WeatherFragment)fragments.get(i)).showContent(heWeather);
        }
    }

    private class FragmentAdapter extends FragmentPagerAdapter{
        List<Fragment> mFragments;

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragments){
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
