package com.example.po.myfirstapp.gson;

import java.util.List;

/**
 * Created by 13701 on 2017/10/10.
 */

public class HeWeather {
    public Alarm alarms;
    public AQI aqi;
    public Basic basic;
    public List<Daily_Forecast> daily_forecast;                 //储存的是接下来几天的天气数据
    public List<Hourly_Forecast> hourly_forecast;
    public Now now;
    public String status;
    public Suggestion suggestion;
}
