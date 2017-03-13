package com.best.jiangshang.first;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
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

