package com.best.jiangshang.first;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 */

public class MyselfAdapter extends BaseAdapter {
   List<MySelf> list;
    Context context;
    public MyselfAdapter(List<MySelf> lists,Context context){
        this.list=lists;
        this.context=context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int index, View view, ViewGroup arg2) {
        ViewHolder viewHolder=null;
        if(view==null){
            view=((Activity)context).getLayoutInflater().inflate(R.layout.listview_fx,null);
            viewHolder=new ViewHolder();
            view.setTag(viewHolder);
            viewHolder.imageView=(ImageView)view.findViewById(R.id.imageView_myself);
            viewHolder.name=(TextView)view.findViewById(R.id.tv_name_myself);
        }else{
            viewHolder=(ViewHolder)view.getTag();
        }
        MySelf mySelf=list.get(index);
        viewHolder.imageView.setImageResource(mySelf.getImageId());
        viewHolder.name.setText(mySelf.getName());
        return view;
    }
    class ViewHolder{
        ImageView imageView;
        TextView name;
    }
}
