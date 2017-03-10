package com.best.jiangshang.first;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FXFragment extends Fragment {
    View view;
    String names[]={"朋友圈","扫一扫","摇一摇","附近的人","漂流瓶","购物","游戏"};
    int img[]={R.drawable.find_friends,R.drawable.find_erweima,R.drawable.find_yaoyiyao,R.drawable.find_fujin,
            R.drawable.find_piaoliuping,R.drawable.find_gouwu,R.drawable.find_youxi};
    ListView listView;
    MyselfAdapter myselfAdapter;
    List<MySelf> lists;
    public FXFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_fx, container, false);
        listView=(ListView)view.findViewById(R.id.List_fx_main);
        setData();
        createAdapter();
        showView();
        return view;
    }

    private void showView() {
        listView.setAdapter(myselfAdapter);
    }
    private void setData() {
        lists=new ArrayList<MySelf>();
        int length=img.length;
        for(int i=0;i<length;i++){
            MySelf mySelf=new MySelf(img[i],names[i]);
            lists.add(mySelf);
        }
    }
    private void createAdapter() {
        myselfAdapter=new MyselfAdapter(lists,getActivity());
    }

}
