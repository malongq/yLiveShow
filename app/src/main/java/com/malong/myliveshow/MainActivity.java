package com.malong.myliveshow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.malong.myliveshow.http.TestActivity;
import com.malong.myliveshow.webview.WebActivity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRe_live_contents;
    private TextView tv_web;
    private LinkedHashMap<String, String> map;
    private List<String> url_data;
    private List<String> title_data;
    private TextView tv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        map = new LinkedHashMap<>();
        map.put("中央一套", "rtmp://live.hkstv.hk.lxdns.com/live/hks");
        map.put("中央二套", "rtmp://kv3.tp33.net/sat/tv701");
        map.put("中央三套", "http://202.69.67.66:443/webcast/bshdlive-pc/playlist.m3u8");
        map.put("中央四套", "rtsp://c.itvitv.com/etm.jhfuowamedr");
        map.put("中央五套", "http://60.199.188.65/HLS/WG_USTV-N/index.m3u8");
        map.put("中央六套", "http://stream.mastvnet.com/MSTV/SD/live.m3u8");
        map.put("中央七套", "http://skydvn-nowtv-atv-prod.skydvn.com/atv/skynews/1404/live/04.m3u8");
        map.put("中央八套", "http://live.streamingfast.net/osmflivech1.m3u8");

        url_data = new ArrayList<>();
        title_data = new ArrayList<>();

        initView();
    }

    private void initView() {

        mRe_live_contents = findViewById(R.id.re_live_contents);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRe_live_contents.setLayoutManager(linearLayoutManager);

        LiveContentsAdapter mLiveContentsAdapter = new LiveContentsAdapter(this, map);

        mRe_live_contents.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        mRe_live_contents.setAdapter(mLiveContentsAdapter);

        for (Map.Entry<String, String> entries : map.entrySet()) {
            url_data.add(entries.getValue());
            title_data.add(entries.getKey());
        }

        mLiveContentsAdapter.setItemClickListener(new LiveContentsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = new Intent(MainActivity.this, LiveActivity.class);
                intent.putExtra("url",url_data.get(position));
                intent.putExtra("title",title_data.get(position));
                startActivity(intent);

            }
        });


        //web
        tv_web = findViewById(R.id.tv_web);
        tv_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WebActivity.class));
            }
        });

        //test
        tv_test = findViewById(R.id.tv_test);
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });

    }
}
