package com.best.jiangshang.first;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.best.jiangshang.first.been.PicturesData;
import com.best.jiangshang.first.been.Results;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    RadioButton rb_weixin,rb_txl,rb_fx,rb_my;
    ArrayList<Fragment> pages=new ArrayList<Fragment>();
    ViewPager vPager;
    MyAdapter myadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        rb_weixin.setChecked(true);//刚开始进入第一个界面时设置为选中状态
        //从网络上下载数据
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L,TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);
        String url="http://gank.io/api/data/Android/10/1";
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
           //  Log.d(TAG, response);
               // Log.i(TAG, response);
                PicturesData picturesData=null;
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    boolean error=jsonObject.getBoolean("error");
                    JSONArray jsonArray=jsonObject.getJSONArray("results");
                    List<Results> list=new ArrayList<Results>();
                    List<String> imagesList=new ArrayList<String>();
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonResults=(JSONObject)jsonArray.get(i);
                        String _id=jsonResults.getString("_id");
                        String createdAt=jsonResults.getString("createdAt");
                        String desc=jsonResults.getString("desc");
                       //String images=jsonResults.getString("images");

                        if(jsonResults.has("images")){
                            JSONArray imagesArray=jsonResults.getJSONArray("images");
                            for(int k=0;k<imagesArray.length();k++){
                                String images=(String) imagesArray.get(k);
                                Log.d(TAG, "onResponse: "+images);
                                imagesList.add(images);
                            }
                        }

                        String publishedAt=jsonResults.getString("publishedAt");
                        String source=jsonResults.getString("source");
                        String type=jsonResults.getString("type");
                        String url=jsonResults.getString("url");
                        Boolean used=jsonResults.getBoolean("used");
                        String who=jsonResults.getString("who");
                        Results results=new Results(_id,createdAt,desc,imagesList,publishedAt,source,type,url,used,who);
                        list.add(results);
                    }
                    Log.d(TAG,list.toString());
                    picturesData = new PicturesData();
                    picturesData.setError(error);
                    picturesData.setResults(list);
                    Log.d(TAG, "onResponse: " + picturesData.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void init() {
        pages.add(new WXFragment());
        pages.add(new TXLFragment());
        pages.add(new FXFragment());
        pages.add(new MyFragment());

        vPager=(ViewPager)findViewById(R.id.vp_activity_main);
        myadapter=new MyAdapter(getSupportFragmentManager());
        vPager.setAdapter(myadapter);
        vPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int arg0) {
                if(arg0==0) {
                    rb_weixin.setChecked(true);
                }else if(arg0==1){
                    rb_txl.setChecked(true);
                }else if(arg0==2){
                    rb_fx.setChecked(true);
                }else if(arg0==3){
                    rb_my.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        rb_weixin= (RadioButton) findViewById(R.id.rb_weixin_activity_main);
        rb_weixin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    vPager.setCurrentItem(0);
                }
            }
        });
        rb_txl=(RadioButton)findViewById(R.id.rb_txl_activity_main);
        rb_txl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    vPager.setCurrentItem(1);
                }
            }
        });
        rb_fx=(RadioButton)findViewById(R.id.rb_fx_activity_main);
        rb_fx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    vPager.setCurrentItem(2);
                }
            }
        });
        rb_my=(RadioButton)findViewById(R.id.rb_my_activity_main);
        rb_my.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    vPager.setCurrentItem(3);
                }
            }
        });
    }
    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int index) {
            return pages.get(index);
        }

        @Override
        public int getCount() {
            return pages.size();
        }
    }
}

