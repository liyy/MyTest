package com.liyy.mytest.util;

import android.widget.Toast;

import com.liyy.mytest.MyApplication;

import java.util.Random;

/**
 * Created by liyy on 2020/06/15.
 */
public class MathUtil
{
    public static int random()
    {
        return (int)(Math.random() * 1000000);
    }

    public static int random1()
    {
        Random random = new Random();
        return (int)(random.nextDouble() * 1000000);
    }
}
