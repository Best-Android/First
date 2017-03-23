package com.best.jiangshang.first;

/**
 * Created by Administrator on 2017/3/17.
 */
/*
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
                holder=new FooterViewHolder(layout);
                break;
            case TYPE_ITEM:
                layout=inflater.inflate(item,viewGroup,false);
                holder=new myViewHolder(layout);
                break;
        }
        return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        if(i==getItemCount() -1){
            return;
        }
        myViewHolder myviewHolder=(myViewHolder) holder;
        Results picturesData=picturesList.get(i);
        myviewHolder.tv_desc.setText(picturesData.getDesc());
        myviewHolder.tv_who.setText(picturesData.getWho());
        myviewHolder.tv_createdat.setText(picturesData.getCreatedAt());
        if(picturesData.getImages().size()>0){
            Glide.with(context).load(picturesData.getImages().get(0))
                    .placeholder(R.mipmap.ic_launcher).into(((myViewHolder) holder).imageView);
        }
//        myviewHolder.item_rl.setTag(picturesData);
//        myviewHolder.item_rl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Results results = (Results) v.getTag();
//                String url = results.getUrl();
//                Intent intent = new Intent(context.getApplicationContext(), TXLFragment.class);
//                context.startActivity(intent);
//
//            }
//        });

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
*/

//    class myViewHolder extends RecyclerView.ViewHolder{
//        RelativeLayout item_rl;
//        ImageView imageView;
//        TextView tv_desc;
//        TextView tv_who,tv_createdat;
//        public myViewHolder(View itemView){
//            super(itemView);
//            item_rl = (RelativeLayout) itemView.findViewById(R.id.item_rl);
//            imageView=(ImageView)itemView.findViewById(R.id.iv_images);
//            tv_desc=(TextView)itemView.findViewById(R.id.tv_desc);
//            tv_who=(TextView)itemView.findViewById(R.id.tv_who);
//            tv_createdat=(TextView)itemView.findViewById(R.id.tv_createdat);
//        }
//    }

//    class FooterViewHolder extends RecyclerView.ViewHolder{
//        TextView tvFooter;
//        public FooterViewHolder(View itemView){
//            super(itemView);
//            tvFooter=(TextView)itemView.findViewById(R.id.tv_footitem);
//        }
//    }




