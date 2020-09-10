package com.liyy.mytest;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;
import com.wenming.library.LogReport;
import com.wenming.library.save.imp.CrashWriter;
import com.wenming.library.upload.email.EmailReporter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by liyy on 2020/06/17.
 */
public class MyApplication extends Application
{
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate(){
        super.onCreate();
        context=getApplicationContext();
        initCrashReport();
        initTbs(this);
    }
    public static Context getContext(){
        return context;
    }

    // 崩溃log初始化
    private void initCrashReport() {
        LogReport.getInstance()
                .setCacheSize(30 * 1024 * 1024)//支持设置缓存大小，超出后清空
                .setLogDir(getApplicationContext(), "sdcard/" + this.getString(this.getApplicationInfo().labelRes) + "/")//定义路径为：sdcard/[app name]/
                .setWifiOnly(true)//设置只在Wifi状态下上传，设置为false为Wifi和移动网络都上传
                .setLogSaver(new CrashWriter(getApplicationContext()))//支持自定义保存崩溃信息的样式
                //.setEncryption(new AESEncode()) //支持日志到AES加密或者DES加密，默认不开启
                .init(getApplicationContext());
        initEmailReporter();
    }

    // 使用EMAIL发送日志
    private void initEmailReporter() {
        EmailReporter email = new EmailReporter(this);
        email.setReceiver("13123456789@163.com");//收件人
        email.setSender("654111888@qq.com");//发送人邮箱
        email.setSendPassword("tzahdfdozhbjbceh");//邮箱的客户端授权码，注意不是邮箱密码
        email.setSMTPHost("smtp.qq.com");//SMTP地址
        email.setPort("465");//SMTP 端口
        LogReport.getInstance().setUploadType(email);
    }

    // x5初始化
    public void initTbs(Context context)
    {
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);

        QbSdk.setDownloadWithoutWifi(true);

        //搜集本地tbs内核信息并上报服务器,服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback()
        {
            @Override
            public void onCoreInitFinished()
            {
            }

            @Override
            public void onViewInitFinished(boolean b)
            {
                Log.d("TAG111", "1111111111111111111111" + b);
            }
        };

        QbSdk.setTbsListener(new TbsListener()
        {
            @Override
            public void onDownloadFinish(int i)
            {
            }

            @Override
            public void onInstallFinish(int i)
            {
            }

            @Override
            public void onDownloadProgress(int i)
            {
                if (i % 5 == 0)
                {
                }
            }
        });

        QbSdk.initX5Environment(context.getApplicationContext(), cb);
    }
}
