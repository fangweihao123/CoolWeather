package com.example.po.myfirstapp.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.po.myfirstapp.Activity.WeatherConditionActivity;
import com.example.po.myfirstapp.R;
import com.example.po.myfirstapp.db.City;
import com.example.po.myfirstapp.db.County;
import com.example.po.myfirstapp.db.Province;
import com.example.po.myfirstapp.util.HttpUtil;
import com.example.po.myfirstapp.util.Utility;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 13701 on 2017/10/7.
 */

public class LocationListFragment extends Fragment {
    final int PROVINCELEVEL = 0;
    final int CITYLEVEL = 1;
    final int COUNTYLEVEL = 2;
    int CURRENTLEVEL = 0;
    private RecyclerView mRecyclerView;
    private LocationAdapter mAdapter;
    private TextView mTitleText;
    private ImageButton imgBtn;
    private Province selectedProvince;
    private City selectedCity;
    private County selectedCounty;
    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;
    private List<String> dataList = new ArrayList<>();
    // TODO: 2017/10/7 可以用processdialog来实现加载的效果


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_location_list,container,false);
        mTitleText = (TextView) v.findViewById(R.id.title_text);
        imgBtn = (ImageButton) v.findViewById(R.id.back_button);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CURRENTLEVEL == PROVINCELEVEL){
                }else if(CURRENTLEVEL == CITYLEVEL){
                    queryProvince();
                }else if(CURRENTLEVEL == COUNTYLEVEL){
                    queryCity();
                }
            }
        });
        mRecyclerView = (RecyclerView) v
                .findViewById(R.id.crime_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        LitePal.getDatabase();
        queryProvince();
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void queryProvince(){                                           //先去数据库找 数据库找不到去服务器上找
        mTitleText.setText("中国");
        provinceList = DataSupport.findAll(Province.class);
        if(provinceList.size()>0){
            dataList.clear();
            for(Province province : provinceList){
                dataList.add(province.getProvinceName());               //datalist里面保存了数据 最后显示的是datalist的数据
            }
            mAdapter.notifyDataSetChanged();
            CURRENTLEVEL = PROVINCELEVEL;
        }else {
            String address = "http://guolin.tech/api/china";
            queryFromServer(address,"province");
        }
    }

    private void queryCity(){
        mTitleText.setText(selectedProvince.getProvinceName());
        cityList = DataSupport.where("provinceId = ?", String.valueOf(selectedProvince.getId())).find(City.class);
        if(cityList.size()>0){
            dataList.clear();
            for(City city : cityList){
                dataList.add(city.getCityName());               //datalist里面保存了数据 最后显示的是datalist的数据
            }
            mAdapter.notifyDataSetChanged();
            CURRENTLEVEL = CITYLEVEL;
        }else {
            String address = "http://guolin.tech/api/china/" + selectedProvince.getProvinceCode();
            queryFromServer(address,"city");
        }
    }

    private void queryCounty(){
        mTitleText.setText(selectedCity.getCityName());
        countyList = DataSupport.where("cityId = ?",String.valueOf(selectedCity.getId())).find(County.class);
        if(countyList.size()>0){
            dataList.clear();
            for(County county : countyList){
                dataList.add(county.getCountyName());               //datalist里面保存了数据 最后显示的是datalist的数据
            }
            mAdapter.notifyDataSetChanged();
            CURRENTLEVEL = COUNTYLEVEL;
        }else {
            String address = "http://guolin.tech/api/china/" + selectedProvince.getProvinceCode()
                    + "/" + selectedCity.getCityCode();
            queryFromServer(address,"county");
        }
    }

    private void queryFromServer(String address,final String type){
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //通过runonUIthread返回主线逻辑
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if("province".equals(type)){
                    result = Utility.handleProvinceResponse(responseText);
                }else if ("city".equals(type)){
                    result = Utility.handleCityResponse(responseText,selectedProvince.getId());
                }else if("county".equals(type)){
                    result = Utility.handleCountyResponse(responseText,selectedCity.getId());
                }
                if(result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if("province".equals(type)){
                                queryProvince();
                            }else if("city".equals(type)){
                                queryCity();
                            }else if("county".equals(type)){
                                queryCounty();
                            }
                        }
                    });
                }
            }
        });
    }

    private void updateUI(){
        mAdapter = new LocationAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    private class LocationHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mTextView;

        public LocationHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTextView = itemView.findViewById(R.id.location);
        }

        @Override
        public void onClick(View v) {
            if(CURRENTLEVEL == PROVINCELEVEL){
                selectedProvince = provinceList.get(getAdapterPosition());
                queryCity();
            }else if(CURRENTLEVEL == CITYLEVEL){
                selectedCity = cityList.get(getAdapterPosition());
                queryCounty();
            }else if(CURRENTLEVEL == COUNTYLEVEL){
                selectedCounty = countyList.get(getAdapterPosition());
                Intent intent = WeatherConditionActivity.newIntent(getActivity(),selectedCounty.getCountyName());
                startActivity(intent);
            }
        }

        public void bindLocation(String name){
            mTextView.setText(name);
        }
    }

    private class  LocationAdapter extends RecyclerView.Adapter<LocationHolder> {   //Adapter是控制器 它来创建view 而不是RecyclerView来创建
        @Override
        public LocationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_location, parent, false);
            return new LocationHolder(view);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        @Override
        public void onBindViewHolder(LocationHolder holder, int position) {
            holder.bindLocation(dataList.get(position));
        }
    }


}
