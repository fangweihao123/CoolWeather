package com.example.po.myfirstapp.gson;

/**
 * Created by 13701 on 2017/10/10.
 */

public class Hourly_Forecast {
    public Cond cond;
    public class Cond{
        public String txt;
    }
    public String date;
    public String hum;
    public String tmp;
    public Wind wind;
    public class Wind{
        public String dir;
        public String sc;
        public String spd;
    }

}
