package com.liyy.mytest.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.liyy.mytest.R;
import com.liyy.mytest.view.LollipopFixedWebView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class LoadPageActivity extends AppCompatActivity
{
    private LollipopFixedWebView mWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_html);

        // 去掉标题
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        mWebView = (LollipopFixedWebView) findViewById(R.id.webView);

        // 设置WebView的客户端
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;// 返回false
            }
        });

        WebSettings webSettings = mWebView.getSettings();

        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);

        // 让JavaScript可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 设置缓存
        webSettings.setAppCacheEnabled(true);

        // 设置缓存模式,一共有四种模式
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        // 设置缓存路径
//        webSettings.setAppCachePath("");

        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(true);

        // 将图片调整到合适的大小
        webSettings.setUseWideViewPort(true);

        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        // 设置可以被显示的屏幕控制
        webSettings.setDisplayZoomControls(true);

        // 设置默认字体大小
        webSettings.setDefaultFontSize(12);

        //3、 加载需要显示的网页
        mWebView.loadUrl("https://www.baidu.com/");
//        mWebView.loadUrl("http://sso.qxteacher.com/SignIn.aspx?ReturnUrl=http://218.2.247.174:8081/dsideal_yy/SSOsbqzx/APPSSOAuth.jsp");

        ///4、设置响应超链接，在安卓5.0系统，不使用下面语句超链接也是正常的，但在MIUI中安卓4.4.4中需要使用下面这条语句，才能响应超链接
        // mWebView.setWebViewClient(new HelloWebViewClient());
    }

    // 设置回退监听
    // 5、覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            if (mWebView.canGoBack())
            {
                mWebView.goBack(); //goBack()表示返回WebView的上一页面
            }
            else
            {
                finish();
            }
            return true;
        }
        return false;
    }
}
