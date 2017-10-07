package com.example.po.myfirstapp.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 13701 on 2017/10/4.
 */

public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){             //发送请求的动作
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
