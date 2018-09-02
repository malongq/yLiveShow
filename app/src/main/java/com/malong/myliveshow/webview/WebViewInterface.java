package com.malong.myliveshow.webview;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by Malong
 * on 18/7/28.
 * 2.  编写js接口类
 */
public class WebViewInterface {

    private static final String TAG = "WebViewInterface";

    private JsBridge jsBridge;

    public WebViewInterface(JsBridge jsBridge) {
        this.jsBridge = jsBridge;
    }

    /**
     * 不在主线程（UI线程）执行,所以不能直接更改UI,写一个JsBridge接口，将输入的值展示,然后WebActivity实现这个接口
     * @param value
     */
    @JavascriptInterface
    public void setValue(String value) {
        Log.d(TAG, "Value: " + value);
        jsBridge.setTextValue(value);
    }

}
