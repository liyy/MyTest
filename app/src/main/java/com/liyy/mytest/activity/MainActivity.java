package com.liyy.mytest.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.liyy.mytest.R;
import com.liyy.mytest.ResolutionDialog;
import com.liyy.mytest.config.GlobalConfig;
import com.liyy.mytest.util.MD5;
import com.liyy.mytest.util.MathUtil;
import com.liyy.mytest.util.ToastUtil;
import com.wenming.library.save.imp.LogWriter;

import androidx.appcompat.app.AlertDialog;

public class MainActivity extends BaseActivity
{
    private Context mContext;
    private ResolutionDialog mResolutionDialog;
    private View.OnClickListener mOnClickListener;
    private EditText mMd5Content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        mMd5Content = findViewById(R.id.md5_content);
        click();
        findById();
//        LogReport.getInstance().upload(this);
        LogWriter.writeLog(GlobalConfig.TAG, "MainActivity onCreate!");

//        MyClass mMyClass = new MyClass("Hello World From MyClass!");
//        String ssc = mMyClass.getsTest();
//        showSimpleDialog(ssc);
//
//        MyTest mMyTest = new MyTest("Hello World From MyTest!");
//        String sst = mMyTest.getsTest();
//        showSimpleDialog(sst);
    }

    private void findById()
    {
        findViewById(R.id.set_resolution_acti).setOnClickListener(mOnClickListener);
        findViewById(R.id.set_resolution_dia).setOnClickListener(mOnClickListener);
        findViewById(R.id.load_html).setOnClickListener(mOnClickListener);
        findViewById(R.id.get).setOnClickListener(mOnClickListener);
        findViewById(R.id.post).setOnClickListener(mOnClickListener);
        findViewById(R.id.start_browser).setOnClickListener(mOnClickListener);
        findViewById(R.id.random).setOnClickListener(mOnClickListener);
        findViewById(R.id.load_html_x5).setOnClickListener(mOnClickListener);
        findViewById(R.id.md5).setOnClickListener(mOnClickListener);
        findViewById(R.id.calculator).setOnClickListener(mOnClickListener);
        findViewById(R.id.play_music).setOnClickListener(mOnClickListener);
    }

    private void click()
    {
        mOnClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                switch (view.getId())
                {
                    case R.id.set_resolution_acti:
                        startActivity(new Intent(mContext, SetResolutionActivity.class));
                        break;
                    case R.id.set_resolution_dia:
                        if (Build.VERSION.SDK_INT >= 23) {
                            if(!Settings.canDrawOverlays(mContext)) {
                                Intent intentDia = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                                startActivity(intentDia);
                                return;
                            } else {
                                mResolutionDialog = new ResolutionDialog(mContext, R.style.Dialog_FS_Draw);
                                setDialogFullScreenWindow(mResolutionDialog);
                                mResolutionDialog.show();
                            }
                        } else {
                            mResolutionDialog = new ResolutionDialog(mContext, R.style.Dialog_FS_Draw);
                            setDialogFullScreenWindow(mResolutionDialog);
                            mResolutionDialog.show();
                        }
                        break;
                    case R.id.load_html:
                        // android6.0及以上才可以调用，6.0一下报错
//                        if(Build.VERSION.SDK_INT >= 23) {
//                            startActivity(new Intent(mContext, LoadPageActivity.class));
//                        } else {
//                            ToastUtil.showToast("Android6.0以下不支持！", Toast.LENGTH_LONG);
//                        }
                        startActivity(new Intent(mContext, LoadPageActivity.class));
                        break;
                    case R.id.get:
                        startActivity(new Intent(mContext, HttpGetActivity.class));
                        break;
                    case R.id.post:
                        startActivity(new Intent(mContext, HttpPostActivity.class));
                        break;
                    case R.id.start_browser:
                        Intent intentBrowser = new Intent();
                        intentBrowser.setAction(Intent.ACTION_VIEW);
                        Uri uri = Uri.parse("http://www.edusoa.com/");
                        intentBrowser.setData(uri);
                        //                intent.setClassName("com.android.browser","com.android.browser.BrowserActivity");
                        startActivity(Intent.createChooser(intentBrowser, "请选择浏览器"));
                        //                startActivity(intent);
                        break;
                    case R.id.random:
//                        ToastUtil.showToast(MathUtil.random() + "");
                        ToastUtil.showToastShort(String.valueOf(MathUtil.random1()));
                        break;
                    case R.id.load_html_x5:
                        startActivity(new Intent(mContext, LoadPageX5Activity.class));
                        break;
                    case R.id.md5:
                        String string = MD5.getMD5(mMd5Content.getText().toString());
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        AlertDialog alertDialog = builder.setIcon(R.mipmap.ic_launcher)
                                .setTitle(R.string.tips)
                                .setMessage(string)
                                .setCancelable(false)
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                    }
                                })
                                .setPositiveButton(R.string.define, new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        startActivity(new Intent(mContext, AlertDialogActivity.class));
                                    }
                                }).create();
//                                .setNeutralButton("中立", new DialogInterface.OnClickListener()
//                                {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i)
//                                    {
//                                    }
//                                }).create();
                        alertDialog.show();
                        ToastUtil.showToastShort(string);
                        break;
                    case R.id.calculator:
                        startActivity(new Intent(mContext, CalculatorActivity.class));
                        break;
                    case R.id.play_music:
                        startActivity(new Intent(mContext, PlayMusicActivity.class));
                    default:
                        break;
                }
            }
        };
    }

    private void showSimpleDialog(String dialog_message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.simple_dialog);
        builder.setMessage(dialog_message);

        //监听下方button点击事件
        builder.setPositiveButton(R.string.define, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), R.string.toast_define, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), R.string.toast_cancel, Toast.LENGTH_SHORT).show();
            }
        });

        //设置对话框是可取消的
        builder.setCancelable(true);
        AlertDialog dialog= builder.create();
        dialog.show();
    }

    private void setDialogFullScreenWindow(Dialog dialog)
    {
        Window window = dialog.getWindow();
        assert window != null;
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }
}
