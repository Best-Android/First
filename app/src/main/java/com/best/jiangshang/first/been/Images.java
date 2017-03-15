package com.best.jiangshang.first.been;

/**
 * Created by Administrator on 2017/3/15.
 */

public class Images {
    private String images;

    public Images(String images) {
        this.images = images;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Images{" +
                "images='" + images + '\'' +
                '}';
    }
}
