package com.example.po.myfirstapp.util;

import android.text.TextUtils;

import com.example.po.myfirstapp.db.City;
import com.example.po.myfirstapp.db.County;
import com.example.po.myfirstapp.db.Province;
import com.example.po.myfirstapp.gson.HeWeather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 13701 on 2017/10/4.
 */

public class Utility {
    /**
     *  解析和处理服务器返回的省级数据
     */
    public static boolean handleProvinceResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allProvinces = new JSONArray(response);
                for(int i = 0;i<allProvinces.length();i++){
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province =new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析服务器返回的城市数据
     * @param response
     * @return
     */
    public static boolean handleCityResponse(String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allCitys = new JSONArray(response);
                for(int i = 0;i<allCitys.length();i++){
                    JSONObject provinceObject = allCitys.getJSONObject(i);
                    City city =new City();
                    city.setCityName(provinceObject.getString("name"));
                    city.setCityCode(provinceObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 处理服务器返回的关于县城的数据
     * @param response
     * @return
     */
    public static boolean handleCountyResponse(String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allCountys = new JSONArray(response);
                for(int i = 0;i<allCountys.length();i++){
                    JSONObject provinceObject = allCountys.getJSONObject(i);
                    County county =new County();
                    county.setCountyName(provinceObject.getString("name"));
                    county.setWeatherId(provinceObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 处理点击了县城之后所返回的相关县城的天气信息,将返回的JSON信息转化为HeWeather类 在新的activity中使用
     *
     */
    public static HeWeather handleWeatherResponse(String response){
        HeWeather weather = null;
        if(!TextUtils.isEmpty(response)){
            try{
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("HeWeather5");
                String jsonContent = jsonArray.getJSONObject(0).toString();
                weather = new Gson().fromJson(jsonContent,HeWeather.class);
                return weather;
            }catch (Exception e){
                e.printStackTrace();
            }finally {

            }
        }
        return weather;
    }
}
