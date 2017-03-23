package com.best.jiangshang.first;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class TXLFragment extends Fragment {

    public TXLFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_txl, container, false);
        /*webView=(WebView)view.findViewById(R.id.tv_webView);
        Intent intent = new Intent();
        String url = intent.getStringExtra("url");
        webView.loadUrl(url);*/
        return view;
    }

}
