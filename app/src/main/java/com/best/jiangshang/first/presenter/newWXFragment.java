package com.best.jiangshang.first.presenter;

import android.util.Log;

import com.best.jiangshang.first.RecyclerViewAdapter;
import com.best.jiangshang.first.been.Json;
import com.best.jiangshang.first.been.Model;
import com.best.jiangshang.first.been.PicturesData;
import com.best.jiangshang.first.been.Results;
import com.best.jiangshang.first.view.WXNewView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

import static com.best.jiangshang.first.been.Model.ACTION_DOWNLOAD;
import static com.best.jiangshang.first.been.Model.ACTION_PULL_DOWN;
import static com.best.jiangshang.first.been.Model.ACTION_PULL_UP;

/**
 * Created by Administrator on 2017/3/28.
 */

public class newWXFragment implements WXFragmentPresenter {
    RecyclerViewAdapter recyclerViewAdapter;
    private static String TAG="JiangShang";
    WXNewView mView;
    public newWXFragment(WXNewView mView){
        this.mView=mView;
    }
    @Override
    public void downloadData(final int actionDown, int pageId) {
        Log.d(TAG,"downloadData:"+actionDown+", "+pageId);
        String url= Model.Myurl + pageId;
        Log.d(TAG, "downloadData: "+url);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
               // swipeRefreshLayout.setRefreshing(false);
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
                        //swipeRefreshLayout.setRefreshing(false);
                      //  tvRefreshHint.setVisibility(View.GONE);
                        break;
                    case ACTION_PULL_UP:
                        recyclerViewAdapter.addData(list);
                        break;
                }
            }
        });
    }
}
