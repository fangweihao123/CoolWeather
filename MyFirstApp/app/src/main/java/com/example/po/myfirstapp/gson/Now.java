package com.example.po.myfirstapp.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 13701 on 2017/10/10.
 */

public class Now {                              //现在的天气状况
    public String hum;                             //相对湿度
    public String pcpn;                            //降水量
    public String tmp;                             //温度
    @SerializedName("cond")
    public Cond condition;
    public Wind wind;

    public class Cond{
        public String txt;
    }

    public class Wind{
        public String dir;             //风向
        public String sc;                 //风力
        public String spd;                //风速
    }

}
