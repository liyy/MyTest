package com.liyy.mytest;

import android.content.Context;
import android.content.SharedPreferences;

import com.liyy.mytest.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liyy on 2020/07/16.
 */
public class SharedHelper
{
    private Context mContext;

    public SharedHelper(Context context) {
        mContext = context;
    }

    // 定义一个保存数据的方法
    public void save(String username, String passwd) {
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.putString("passwd", passwd);
        editor.apply();
    }

    // 定义一个读取SP文件的方法
    public Map<String, String> read() {
        Map<String, String> data = new HashMap<>();
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        data.put("username", sp.getString("username", ""));
        data.put("passwd", sp.getString("passwd", ""));
        return data;
    }
}