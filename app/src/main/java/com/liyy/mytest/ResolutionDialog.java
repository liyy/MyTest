package com.liyy.mytest;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;

import androidx.annotation.NonNull;

/**
 * Created by liyy on 2020/06/09.
 */
public class ResolutionDialog extends Dialog
{
    private Button mButton2;
    private Button mButton3;
    private Button mButton5;
    private TextView mTextView;
    private String mMsg;
    private Context mContext;

    public ResolutionDialog(@NonNull Context context, int styles)
    {
        super(context, styles);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set_resolution);

        mTextView = (TextView)findViewById(R.id.textView);

        mButton2 = (Button)findViewById(R.id.button2);
        mButton2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mMsg = "设置1024x768分辨率成功！";
                exusecmd("wm size 1024x768");
                showToast(mMsg);
                setTextMsg(mMsg);
            }
        });

        mButton3 = (Button)findViewById(R.id.button3);
        mButton3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mMsg = "设置1280x800分辨率成功！";
                exusecmd("wm size 1280x800");
                showToast(mMsg);
                setTextMsg(mMsg);
            }
        });

        mButton5 = (Button)findViewById(R.id.button5);
        mButton5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mMsg = "分辨率还原成功！";
                exusecmd("wm size reset");
                showToast(mMsg);
                setTextMsg(mMsg);
            }
        });
    }

    private static boolean exusecmd(String command) {
        Process process = null;
        DataOutputStream os = null;

        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (process != null) {
                    process.destroy();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    private void setTextMsg(String msg) {
        mTextView.setText(msg);
    }
}
