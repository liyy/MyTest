package com.liyy.mytest.util;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liyy.mytest.MyApplication;
import com.liyy.mytest.R;

/**
 * Created by liyy on 2020/06/15.
 */
public class ToastUtil
{
    public static void showToastShort(String tips) {
        Toast.makeText(MyApplication.getContext(), tips, Toast.LENGTH_SHORT).show();
    }

    public static void showToastShort(String tips, int color) {
        Toast toast = Toast.makeText(MyApplication.getContext(), tips, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL , 0, 0);  //设置显示位置
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(color);     //设置字体颜色
        toast.show();
    }

    public static void showToast(String tips, int showTime)
    {
        Toast toast = Toast.makeText(MyApplication.getContext(), tips, showTime);
        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM , 0, 20);  //设置显示位置
        LinearLayout layout = (LinearLayout) toast.getView();
        layout.setBackgroundColor(Color.YELLOW);
        ImageView image = new ImageView(MyApplication.getContext());
        image.setImageResource(R.mipmap.ic_xitongguo);
        layout.addView(image, 0);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setPadding(15, 10, 15 , 5);
        v.setTextColor(Color.BLACK);     //设置字体颜色
        toast.show();
    }

    public static void showToastLong(String tips) {
        Toast.makeText(MyApplication.getContext(), tips, Toast.LENGTH_LONG).show();
    }
}
