package com.best.jiangshang.first;

/**
 * Created by Administrator on 2017/3/10.
 */

public class MySelf {
    int imageId;
    String name;

    public MySelf() {
        super();
    }
    public MySelf(int imageId,String name){
        super();
        this.imageId=imageId;
        this.name=name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
}
