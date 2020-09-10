package com.liyy.mytest.config;

/**
 * Created by liyy on 2020/06/16.
 */
public class GlobalConfig
{
    public static String TAG = "LIYY_TAG";

    /**
     * 保存在手机里的SP文件名
     */
    public static final String SP_FILE_NAME = "my_sp";

    public static class Calculator {
        // 默认
        public static final int DEFAULT = 0;

        // 加
        public static final int ADD = 1;

        // 减
        public static final int SUBTRACT = 2;

        // 乘
        public static final int MULTIPLY = 3;

        // 除
        public static final int DIVIDE = 4;
    }
}
