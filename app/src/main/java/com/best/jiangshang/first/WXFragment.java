package com.best.jiangshang.first;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.best.jiangshang.first.been.Json;
import com.best.jiangshang.first.been.PicturesData;
import com.best.jiangshang.first.been.Results;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;


/**
 * A simple {@link Fragment} subclass.
 */
public class WXFragment extends Fragment {
    @BindView(R.id.recyclerView_wx)
    RecyclerView rvPicture;
    ArrayList<Results> mPicturesList;
    RecyclerViewAdapter recyclerViewAdapter;
    LinearLayoutManager linearLayoutManager;
    Unbinder unbinder;
    String url="http://gank.io/api/data/Android/10/1";
    public WXFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_wx, container, false);
        unbinder= ButterKnife.bind(this,view);
        initView(view);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                PicturesData data = Json.Json(response);
                List<Results> list=data.getResults();
                recyclerViewAdapter.initData(list);
            }
        });
    }

    private void initView(View view) {
        mPicturesList=new ArrayList<>();
        recyclerViewAdapter=new RecyclerViewAdapter(view.getContext(),mPicturesList);
        rvPicture.setAdapter(recyclerViewAdapter);
        linearLayoutManager=new LinearLayoutManager(view.getContext());
        rvPicture.setLayoutManager(linearLayoutManager);
    }


}
