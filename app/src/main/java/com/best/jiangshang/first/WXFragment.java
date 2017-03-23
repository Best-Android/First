package com.best.jiangshang.first;


import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.best.jiangshang.first.been.Json;
import com.best.jiangshang.first.been.PicturesData;
import com.best.jiangshang.first.been.Results;
import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

import static com.best.jiangshang.first.R.layout.item;


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

    private RelativeLayout item_rl;


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
               // RelativeLayout item_rl = (RelativeLayout) view.findViewById(R.id.item_rl);
//                item_rl.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Results results = (Results) v.getTag();
//                        String url = results.getUrl();
//                        Intent intent = new Intent(getContext(), TXLFragment.class);
//                        startActivity(intent.putExtra("url",url));
//                    }
//                });
            }

            @Override
            public void onDestroyView() {
                super.onDestroyView();
                unbinder.unbind();
            }


    /*----------------------------------*/

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        final static int TYPE_ITEM = 0;
        final static int TYPE_FOOTER = 1;
        Context context;
        ArrayList<Results> picturesList;
        boolean isMore;
        String footerText;
        public RecyclerViewAdapter(Context context,ArrayList<Results> picturesList){
            this.context=context;
            this.picturesList=picturesList;

        }
        public boolean isMore(){
            return isMore;
        }
        public void setMore(boolean more){
            isMore=more;
        }
        public void setFooter(String footerText){
            this.footerText=footerText;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return picturesList==null ? 0 :picturesList.size()+1;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            RecyclerView.ViewHolder holder=null;
            LayoutInflater inflater=LayoutInflater.from(context);
            View layout=null;
            switch (i){
                case TYPE_FOOTER:
                    layout=inflater.inflate(R.layout.footitem,viewGroup,false);
                    holder=new com.best.jiangshang.first.FooterViewHolder(layout);
                    break;
                case TYPE_ITEM:
                    layout=inflater.inflate(item,viewGroup,false);
                    holder=new com.best.jiangshang.first.myViewHolder(layout);
                    break;
            }
            return holder;

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
            if(i==getItemCount() -1){
                return;
            }
            com.best.jiangshang.first.myViewHolder myviewHolder=(com.best.jiangshang.first.myViewHolder) holder;
            Results picturesData=picturesList.get(i);
            myviewHolder.tv_desc.setText(picturesData.getDesc());
            myviewHolder.tv_who.setText(picturesData.getWho());
            myviewHolder.tv_createdat.setText(picturesData.getCreatedAt());
            if(picturesData.getImages().size()>0){
                Glide.with(context).load(picturesData.getImages().get(0))
                        .placeholder(R.mipmap.ic_launcher).into(((com.best.jiangshang.first.myViewHolder) holder).imageView);
            }
            myviewHolder.item_rl.setTag(picturesData);
            myviewHolder.item_rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Results results = (Results) v.getTag();
                    String url = results.getUrl();
                    Intent intent = new Intent(getContext(), WebViewActivity.class);
                    startActivity(intent.putExtra("url",url));
                }
            });

        }

        @Override
        public int getItemViewType(int position) {
            if(position==getItemCount() -1){
                return TYPE_FOOTER;
            }else{
                return TYPE_ITEM;
            }
        }
        public void initData(List<Results> list) {
            if (picturesList != null) {
                picturesList.clear();
            }
            picturesList.addAll(list);
            notifyDataSetChanged();
        }
        public void addData(List<Results> list){
            this.picturesList.addAll(list);
            notifyDataSetChanged();
        }
    }


}
class FooterViewHolder extends RecyclerView.ViewHolder{
    TextView tvFooter;
    public FooterViewHolder(View itemView){
        super(itemView);
        tvFooter=(TextView)itemView.findViewById(R.id.tv_footitem);
    }
}

class myViewHolder extends RecyclerView.ViewHolder{
    RelativeLayout item_rl;
    ImageView imageView;
    TextView tv_desc;
    TextView tv_who,tv_createdat;
    public myViewHolder(View itemView){
        super(itemView);
        item_rl = (RelativeLayout) itemView.findViewById(R.id.item_rl);
        imageView=(ImageView)itemView.findViewById(R.id.iv_images);
        tv_desc=(TextView)itemView.findViewById(R.id.tv_desc);
        tv_who=(TextView)itemView.findViewById(R.id.tv_who);
        tv_createdat=(TextView)itemView.findViewById(R.id.tv_createdat);
    }
}




