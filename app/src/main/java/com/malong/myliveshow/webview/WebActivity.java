package com.malong.myliveshow.webview;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.malong.myliveshow.R;

/**
 * Created by Malong
 * on 18/7/25.
 */
public class WebActivity extends AppCompatActivity implements JsBridge {

    private static final String TAG = WebActivity.class.getSimpleName();

    private WebView webview;
    private TextView tv_result;
    private EditText et_input;
    private Button btn_change;
    private Button btn;
    private Handler mhandler;
    private Animation animation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web);
        Log.d(TAG, ">>  onCreate");

        initView();

        initWebtoAndroid();

        initAndroidtoWeb();

    }

    private void initView() {
        webview = findViewById(R.id.webview);
        tv_result = findViewById(R.id.tv_result);
        et_input = findViewById(R.id.et_input);
        btn_change = findViewById(R.id.btn_change);
        btn = findViewById(R.id.btn);


        //两种都可以，XML和代码都可以实现动画，但是，推荐XML，代码写的话，只能支持一种
        animation = AnimationUtils.loadAnimation(this, R.anim.translate);
        btn.setAnimation(animation);

//        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
//        alphaAnimation.setDuration(10000);
//        btn.startAnimation(alphaAnimation);

    }


    /*************************************webview调安卓方法**************************************************/
    private void initWebtoAndroid() {
        //初始化Handler
        mhandler = new Handler();

        //第 1 步：  允许webview加载js代码    第2步是写  WebViewInterface 类
        webview.getSettings().setJavaScriptEnabled(true);
        //第 3 步：  给webview添加js接口
        webview.addJavascriptInterface(new WebViewInterface(this), "malong");

        //加载本地web页
        webview.loadUrl("file:///android_asset/web_index.html");
        //file字段，表示读取本地文件，不读取网络文件
        //android_asset表示读取当前应用的assets目录下的文件
        //web_index.html表示assets目录下的HTML页面
    }

    @Override
    public void setTextValue(final String value) {

        //声明hanlder将值post到UI线程
        mhandler.post(new Runnable() {
            @Override
            public void run() {
                tv_result.setText(value);
            }
        });
    }


    /*************************************安卓调webview方法**************************************************/
    private void initAndroidtoWeb() {

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String change_txt = et_input.getText().toString().trim();
                webview.loadUrl("javascript:if(window.change){window.change('" + change_txt + "')}");
            }
        });

    }


}
