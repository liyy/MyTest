package com.liyy.mytest.activity;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;

import com.liyy.mytest.R;
import com.liyy.mytest.config.GlobalConfig;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoadPageX5Activity extends AppCompatActivity
{
    private WebView mWebView;
    private WebSettings mWebSettings;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_html_x5);

//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null) {
//            actionBar.hide();
//        }

        mWebView = (WebView) findViewById(R.id.webView_x5);

        // 设置WebView的客户端
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap)
            {
                super.onPageStarted(webView, s, bitmap);
                Log.d(GlobalConfig.TAG, "开始加载");
            }

            @Override
            public void onPageFinished(WebView webView, String s)
            {
                super.onPageFinished(webView, s);
                Log.d(GlobalConfig.TAG, "加载结束");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Log.d(GlobalConfig.TAG, "Url: " + url);
//                view.loadUrl(url);
//                return true;
                return false;
            }
        });

        //baiduboxapp:
        //webView加载网页后出现ERR_UNKNOWN_URL_SCHEME
        /*mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try{
                    if(url.startsWith("baiduboxapp://")){
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                }catch (Exception e){
                    return false;
                }
                mWebView.loadUrl(url);
                return true;
            }
        });*/

        mWebSettings = mWebView.getSettings();

        // 让WebView能够执行javaScript
        mWebSettings.setJavaScriptEnabled(true);

        // 让JavaScript可以自动打开windows
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 设置缓存
        mWebSettings.setAppCacheEnabled(true);

        // 设置缓存模式,一共有四种模式
        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        // 设置缓存路径
        //        webSettings.setAppCachePath("");

        // 支持缩放(适配到当前屏幕)
        mWebSettings.setSupportZoom(true);

        // 将图片调整到合适的大小
        mWebSettings.setUseWideViewPort(true);

        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        // 设置可以被显示的屏幕控制
        mWebSettings.setDisplayZoomControls(true);

        // 设置默认字体大小
        mWebSettings.setDefaultFontSize(12);

        //3、 加载需要显示的网页
//        mWebView.loadUrl("file:///android_asset/ServiceAgreement.html");
        mWebView.loadUrl("http://www.baidu.com");
//        mWebView.loadUrl("http://sso.qxteacher.com/SignIn.aspx?ReturnUrl=http://218.2.247.174:8081/dsideal_yy/SSOsbqzx/APPSSOAuth.jsp");

        ///4、设置响应超链接，在安卓5.0系统，不使用下面语句超链接也是正常的，但在MIUI中安卓4.4.4中需要使用下面这条语句，才能响应超链接
        // mWebView.setWebViewClient(new HelloWebViewClient());
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mWebSettings.setJavaScriptEnabled(false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onResume()
    {
        super.onResume();
        mWebSettings.setJavaScriptEnabled(true);
    }

    @Override
    protected void onDestroy()
    {
        if(mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            ((ViewGroup)mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    // 设置回退监听
    // 5、覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
//        if ((keyCode == KeyEvent.KEYCODE_BACK))
//        {
//            if (mWebView.canGoBack())
//            {
//                mWebView.goBack(); //goBack()表示返回WebView的上一页面
//            }
//            else
//            {
//                finish();
//            }
//            return true;
//        }
//        return false;
    }
}
