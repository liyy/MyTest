package com.liyy.mytest.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.liyy.mytest.util.InputUtil;

/**
 * Created by liyy on 2020/07/23.
 */
public class BaseActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    //点击EditText之外的区域隐藏键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
//            if (InputUtil.isSoftShowing(mContext, this) && InputUtil.isShouldHideInput(v, ev)) {
            if (InputUtil.isShouldHideInput(v, ev)) {
                if(InputUtil.hideInputMethod(mContext, v)) {
                    //隐藏键盘时，其他控件不响应点击事件，注释则不拦截点击事件
//                    return true;
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}