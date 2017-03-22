package com.best.jiangshang.first;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
   final  static  int ACTION_DOWNLOAD=0;
    final static int ACTION_PULL_DOWN=1;
    final  static int ACTION_PULL_UP=2;

    @BindView(R.id.recyclerView_wx)
    RecyclerView rvPicture;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tvRefreshHint)
    TextView tvRefreshHint;

    ArrayList<Results> mPicturesList;
    RecyclerViewAdapter recyclerViewAdapter;
    LinearLayoutManager linearLayoutManager;
    Unbinder unbinder;
    int mPageId=1;
    int mNewState;
    private static final String TAG="Jiangshang";
    private final static String Myurl="http://gank.io/api/data/Android/10/";



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
        setListener();
        return view;

    }
    private void setListener(){
        setPullDownListener();
        setPullUpListener();
    }
    private void setPullDownListener(){
        rvPicture.setOnScrollListener(new RecyclerView.OnScrollListener(){
            int lastPosition;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mNewState=newState;
                int lastPosition=linearLayoutManager.findLastVisibleItemPosition();
                if(lastPosition>=recyclerViewAdapter.getItemCount()-1
                        && newState==RecyclerView.SCROLL_STATE_IDLE
                        && recyclerViewAdapter.isMore()){
                    mPageId++;
                    downloadData(ACTION_PULL_UP,mPageId);
                }
                if(newState!=RecyclerView.SCROLL_STATE_DRAGGING){
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastPosition=linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }
    private void setPullUpListener(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {
                //mPageId=1;
                Log.d("jiangshang","invoke onRefresh...");
/*                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       List<String> newDatas=new ArrayList<String>();
                        for(int i=0;i<5;i++){
                            int index=i+1;
                            newDatas.add("new item"+index);
                        }


                    }
                },5000);*/
//                swipeRefreshLayout.setEnabled(true);
//                swipeRefreshLayout.setRefreshing(true);
//                downloadData(ACTION_PULL_DOWN,mPageId);
                swipeRefreshLayout.setRefreshing(true);
                swipeRefreshLayout.setEnabled(true);
                tvRefreshHint.setVisibility(View.VISIBLE);
                mPageId=1;
                downloadData(ACTION_PULL_DOWN,mPageId);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        downloadData(ACTION_DOWNLOAD, 1);
    }

        private void downloadData(final int actionDown,int pageId){
            Log.d(TAG,"downloadData:"+actionDown+", "+pageId);
            String url=Myurl + pageId;
            Log.d(TAG, "downloadData: "+url);
            OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    swipeRefreshLayout.setRefreshing(false);
                    Log.d(TAG, "onError: "+e.getMessage());
                }

                @Override
                public void onResponse(String response, int id) {
                    PicturesData data = Json.Json(response);
                    List<Results> list=data.getResults();
                    //recyclerViewAdapter.initData(list);
                    recyclerViewAdapter.setMore(list!=null && list.size()>0);
                    if(!recyclerViewAdapter.isMore()){
                        if(actionDown==ACTION_PULL_UP){
                            recyclerViewAdapter.setFooter("nothing");
                        }
                        return;
                    }
                    recyclerViewAdapter.setFooter("加载更多数据");
                    switch (actionDown){
                        case ACTION_DOWNLOAD:
                            recyclerViewAdapter.initData(list);
                            recyclerViewAdapter.setFooter("加载更多数据");
                            break;
                        case ACTION_PULL_DOWN:
                            recyclerViewAdapter.initData(list);
                            recyclerViewAdapter.setFooter("加载更多数据");
                            swipeRefreshLayout.setRefreshing(false);
                            tvRefreshHint.setVisibility(View.GONE);
                            break;
                        case ACTION_PULL_UP:
                            recyclerViewAdapter.addData(list);
                            break;
                    }
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

            @Override
            public void onDestroyView() {
                super.onDestroyView();
                unbinder.unbind();
            }
}
