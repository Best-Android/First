package com.best.jiangshang.first.been;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/3/16.
 */

public class Json {
    public static PicturesData Json(String json){

        PicturesData picturesData=null;

        try {
            JSONObject jsonObject=new JSONObject(json);
            boolean error=jsonObject.getBoolean("error");
            JSONArray jsonArray=jsonObject.getJSONArray("results");
            List<Results> list=new ArrayList<Results>();
           // List<String> imagesList=new ArrayList<String>();
            for(int i=0;i<jsonArray.length();i++){
                List<String> imagesList=new ArrayList<String>();
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
        return picturesData;

    }
}
