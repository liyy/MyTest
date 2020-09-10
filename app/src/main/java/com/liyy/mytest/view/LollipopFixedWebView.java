package com.liyy.mytest.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * 重写 webview
 * Created by liyy on 2020/07/22.
 */
public class LollipopFixedWebView extends WebView
{
    public LollipopFixedWebView(Context context) {
        super(getFixedContext(context));
    }

    public LollipopFixedWebView(Context context, AttributeSet attrs) {
        super(getFixedContext(context), attrs);
    }

    public LollipopFixedWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(getFixedContext(context), attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LollipopFixedWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(getFixedContext(context), attrs, defStyleAttr, defStyleRes);
    }

    private static Context getFixedContext(Context context) {
        // Android Lollipop 5.0 & 5.1
        if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT < 23)
            return context.createConfigurationContext(new Configuration());
        return context;
    }
}