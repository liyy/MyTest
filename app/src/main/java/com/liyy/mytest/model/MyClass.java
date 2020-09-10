package com.liyy.mytest.model;

/**
 * Created by Liyy on 2020/5/13 0013.
 */
public class MyClass
{
    private String sTest = "";

    public MyClass(String sTest)
    {
        this.sTest = sTest;
    }

    public MyClass()
    {
    }

    public String getsTest()
    {
        return sTest;
    }

    public void setsTest(String sTest)
    {
        this.sTest = sTest;
    }
}
