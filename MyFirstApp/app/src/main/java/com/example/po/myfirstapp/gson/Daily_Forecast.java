package com.example.po.myfirstapp.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 13701 on 2017/10/10.
 */

public class Daily_Forecast {
    public Cond cond;
    public class Cond{
        public String txt_d;
        public String txt_n;
    }
    public String date;
    public String hum;
    @SerializedName("tmp")
    public Temprature temprature;

    public class Temprature{
        public String max;
        public String min;
    }
    public Wind wind;
    public class Wind{
        public String dir;
        public String sc;
        public String spd;
    }
}
