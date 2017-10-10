package com.example.po.myfirstapp.gson;

/**
 * Created by 13701 on 2017/10/10.
 */

public class AQI {                      //城市空气质量预报
    public AQICity city;
    public class AQICity{
        public String pm25;
        public String qlty;
    }
}
