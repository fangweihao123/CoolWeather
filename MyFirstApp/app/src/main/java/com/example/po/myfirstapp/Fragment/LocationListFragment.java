package com.example.po.myfirstapp.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.po.myfirstapp.R;
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
    private RecyclerView mRecyclerView;
    private LocationAdapter mAdapter;
    private List<Province> provinceList;
    private List<String> dataList = new ArrayList<>();
    // TODO: 2017/10/7 可以用processdialog来实现加载的效果


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_location_list,container,false);
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
        //LitePal.getDatabase();
        //queryProvince();
    }

    private void queryProvince(){                                           //先去数据库找 数据库找不到去服务器上找
        provinceList = DataSupport.findAll(Province.class);
        if(provinceList.size()>0){
            dataList.clear();
            for(Province province : provinceList){
                dataList.add(province.getProvinceName());
            }
            mAdapter.notifyDataSetChanged();
        }else {
            String address = "http://guolin.tech/api/china";
            queryFromServer(address,"province");
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
                    //result = Utility.handleCityResponse(responseText);
                }else if("county".equals(type)){

                }
                if(result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if("province".equals(type)){
                                queryProvince();
                            }else if("city".equals(type)){

                            }else if("county".equals(type)){

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

        public LocationHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),
                    "clicked!", Toast.LENGTH_SHORT)
                    .show();
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
            return 2;
        }

        @Override
        public void onBindViewHolder(LocationHolder holder, int position) {

        }
    }


}
