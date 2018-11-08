package com.malong.myliveshow.http;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.malong.bean.LoginBean;
import com.malong.myliveshow.AndroidOSystemActivity;
import com.malong.myliveshow.R;
import com.malong.piaoliuping.BrowserActivity;
import com.malong.piaoliuping.MainActivity1;
import com.malong.piaoliuping.WebActivity;
import com.malong.view.CanvasActivity;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Malong
 * on 18/7/31.
 */
public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_test;
    private Button btn_test2;
    private Button btn_test3;
    private Button btn_test4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        btn_test = findViewById(R.id.btn_test);
        btn_test.setOnClickListener(this);

        btn_test2 = findViewById(R.id.btn_test2);
        btn_test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, CanvasActivity.class);
                startActivity(intent);
            }
        });

        btn_test3 = findViewById(R.id.btn_test3);
        btn_test3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, AndroidOSystemActivity.class);
                startActivity(intent);
            }
        });

        btn_test4 = findViewById(R.id.btn_test4);
        btn_test4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, WebActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {

        HttpManager httpManager = new HttpManager();
        OkHttpClient okHttpClient = httpManager.getOkHttpClient();
        Retrofit retrofit = httpManager.getRetrofit(okHttpClient);

        //1.创建Retrofit对象
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        //2.获取HttpService对象
        HttpService httpService = retrofit.create(HttpService.class);
        //3.调用登录方法
        Call<LoginBean> topNews = httpService.getTopNews("top", Constant.APPKEY);
        //4.发送请求
        topNews.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                String reason = response.body().getReason();
                if (reason.equals("成功的返回")) {

                    //第一种传递值方法
                    LoginBean body = response.body();
                    Intent intent = new Intent(TestActivity.this, TopNewsActivity.class);
                    Gson gson = new Gson();
                    String json = gson.toJson(body);
                    intent.putExtra("data", json);
                    startActivity(intent);


                    //第二种传递值方法
                    LoginBean body1 = response.body();
                    Intent intent1 = new Intent(TestActivity.this, TopNewsActivity.class);
                    intent1.putExtra("data", body1);
                    startActivity(intent);


                    //第三种传递值方法
//                    Intent intent = new Intent(TestActivity.this, TopNewsActivity.class);
//                    intent.putExtra("title",body.getResult().getData().get(0).getTitle().toString());
//                    intent.putExtra("time",body.getResult().getData().get(0).getDate().toString());
//                    intent.putExtra("author",body.getResult().getData().get(0).getAuthor_name().toString());
//                    intent.putExtra("img",body.getResult().getData().get(0).getThumbnail_pic_s().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(TestActivity.this, response.body().getReason().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                Toast.makeText(TestActivity.this, "错误", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
