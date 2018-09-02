package com.malong.myliveshow.http;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.malong.bean.LoginBean;
import com.malong.myliveshow.R;

import java.io.Serializable;

/**
 * Created by Malong
 * on 18/7/31.
 */
public class TopNewsActivity extends AppCompatActivity {

    private TextView tv_title;
    private TextView tv_time;
    private ImageView iv_img;
    private TextView tv_author;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_activity);

        tv_title = findViewById(R.id.tv_title);
        tv_time = findViewById(R.id.tv_time);
        iv_img = findViewById(R.id.iv_img);
        tv_author = findViewById(R.id.tv_author);


        //第一种传递值方法
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        Gson gson = new Gson();
        LoginBean bean = gson.fromJson(data, LoginBean.class);
        LoginBean.ResultBean.DataBean dataBean = bean.getResult().getData().get(0);


        //第二种传递值方法
//        Intent intent = getIntent();
//        LoginBean data = (LoginBean) intent.getSerializableExtra("data");
//        LoginBean.ResultBean.DataBean dataBean = data.getResult().getData().get(0);



        tv_title.setText(dataBean.getTitle());
        tv_time.setText(dataBean.getDate());
        Glide.with(this).load(dataBean.getThumbnail_pic_s()).into(iv_img);
        tv_author.setText(dataBean.getAuthor_name());


        //第三种传递值方法
//        Intent intent = getIntent();
//        tv_title.setText(intent.getStringExtra("title"));
//        tv_time.setText(intent.getStringExtra("time"));
//        Glide.with(this).load(intent.getStringExtra("img")).into(iv_img);
//        tv_author.setText(intent.getStringExtra("author"));
    }
}
