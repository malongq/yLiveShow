package com.malong.piaoliuping;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.malong.myliveshow.R;
import com.malong.utils.X5WebView2;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.utils.TbsLog;
import java.net.MalformedURLException;
import java.net.URL;

public class WebActivity extends Activity {
    /**
     * 作为一个浏览器的示例展示出来，采用android+web的模式
     */
    private X5WebView2 mWebView;
    private ViewGroup mViewParent;
    private static final String mHomeUrl = "http://192.144.132.76:3000/d/lz-jO3Jik/izhikang-system?refresh=30s&orgId=1";
    private static final String TAG = "SdkDemo";
    private boolean mNeedTestPage = false;
    private ValueCallback<Uri> uploadFile;
    private URL mIntentUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//窗口支持透明度
        Intent intent = getIntent();
        if (intent != null) {
            try {
                mIntentUrl = new URL(intent.getData().toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
            } catch (Exception e) {
            }
        }
        //安卓API level大于11（3.0以上系统）开启硬件加速
        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }
        //加载布局
        setContentView(R.layout.activity_main2);
        mViewParent = findViewById(R.id.webView1);

        mTestHandler.sendEmptyMessageDelayed(MSG_INIT_UI, 0);

    }

    private void init() {
        mWebView = new X5WebView2(this, null);
        mViewParent.addView(mWebView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT));

        //帮助WebView处理各种通知、请求事件
        mWebView.setWebViewClient(new WebViewClient() {
            //复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            //在页面加载结束时调用。我们可以关闭loading 条，切换程序动作
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mTestHandler.sendEmptyMessageDelayed(MSG_OPEN_TEST_URL, 5000);// 5s?
            }
        });

        //辅助WebView处理JavaScript的对话框，网站图标，网站title，加载进度等
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsConfirm(WebView arg0, String arg1, String arg2, JsResult arg3) {
                return super.onJsConfirm(arg0, arg1, arg2, arg3);
            }

//			View myVideoView;
//			View myNormalView;
//			CustomViewCallback callback;

//			/**
//			 * 全屏播放配置
//			 */
//			@Override
//			public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
//				FrameLayout normalView = (FrameLayout) findViewById(R.id.web_filechooser);
//				ViewGroup viewGroup = (ViewGroup) normalView.getParent();
//				viewGroup.removeView(normalView);
//				viewGroup.addView(view);
//				myVideoView = view;
//				myNormalView = normalView;
//				callback = customViewCallback;
//			}
//
//			@Override
//			public void onHideCustomView() {
//				if (callback != null) {
//					callback.onCustomViewHidden();
//					callback = null;
//				}
//				if (myVideoView != null) {
//					ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
//					viewGroup.removeView(myVideoView);
//					viewGroup.addView(myNormalView);
//				}
//			}

            @Override
            public boolean onJsAlert(WebView arg0, String arg1, String arg2, JsResult arg3) {
                //这里写入你自定义的window alert
                return super.onJsAlert(null, arg1, arg2, arg3);
            }
        });

        WebSettings webSetting = mWebView.getSettings();//声明WebSettings子类
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);

        webSetting.setSupportZoom(true);//支持缩放，默认为true。是下面那个的前提。
        webSetting.setBuiltInZoomControls(true);//设置内置的缩放控件。若为false，则该WebView不可缩放

        //设置自适应屏幕，两者合用
        webSetting.setUseWideViewPort(true);//将图片调整到适合webview的大小
        webSetting.setLoadWithOverviewMode(true);// 缩放至屏幕的大小

        webSetting.setSupportMultipleWindows(false);
        webSetting.setAppCacheEnabled(true);//开启 Application Caches 功能
        // webSetting.setDatabaseEnabled(true);//开启 database storage API 功能
        webSetting.setDomStorageEnabled(true);// 开启 DOM storage API 功能
        webSetting.setJavaScriptEnabled(true);//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
        long time = System.currentTimeMillis();
        if (mIntentUrl == null) {
            mWebView.loadUrl(mHomeUrl);
        } else {
            mWebView.loadUrl(mIntentUrl.toString());
        }
        TbsLog.d("time-cost", "cost time: " + (System.currentTimeMillis() - time));
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
    }

    /**
     * 返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            } else
                return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TbsLog.d(TAG, "onActivityResult, requestCode:" + requestCode
                + ",resultCode:" + resultCode);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    if (null != uploadFile) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFile.onReceiveValue(result);
                        uploadFile = null;
                    }
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (null != uploadFile) {
                uploadFile.onReceiveValue(null);
                uploadFile = null;
            }

        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent == null || mWebView == null || intent.getData() == null)
            return;
        mWebView.loadUrl(intent.getData().toString());
    }

    @Override
    protected void onDestroy() {
        if (mTestHandler != null)
            mTestHandler.removeCallbacksAndMessages(null);
        if (mWebView != null)
            mWebView.destroy();
        super.onDestroy();
    }

    public static final int MSG_OPEN_TEST_URL = 0;
    public static final int MSG_INIT_UI = 1;
    private Handler mTestHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_OPEN_TEST_URL:
                    if (!mNeedTestPage) {
                        return;
                    }
                    String testUrl = "file:///android_asset/web_index.html";
                    if (mWebView != null) {
                        mWebView.loadUrl(testUrl);
                    }
                    break;
                case MSG_INIT_UI:
                    init();
                    break;
            }
            super.handleMessage(msg);
        }
    };

}
