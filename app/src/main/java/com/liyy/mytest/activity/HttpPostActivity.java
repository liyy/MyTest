package com.liyy.mytest.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.liyy.mytest.R;
import com.liyy.mytest.SharedHelper;
import com.liyy.mytest.activity.BaseActivity;
import com.liyy.mytest.activity.HttpGetActivity;
import com.liyy.mytest.config.GlobalConfig;
import com.liyy.mytest.util.SPUtils;
import com.liyy.mytest.util.ToastUtil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpPostActivity extends BaseActivity
{
    /**
     * post方法 以输入输出流的方式向服务器发送请求
     */
    private Context mContext;
    private String urlAddress = "http://www.edusoa.com/dsideal_yy/ypt/uploadres/";//服务器地址
    private String method = "getSignatureInfo";//方法名
    private TextView mTextView;
    private String mString;
    private Button mButton;
    private EditText mUserName;
    private EditText mPasswd;
    private SharedHelper sh;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_http_post);
        mTextView = (TextView)findViewById(R.id.post_text);
        mButton = (Button)findViewById(R.id.button);
        mUserName = findViewById(R.id.usr);
        mPasswd = findViewById(R.id.pwd);
        sh = new SharedHelper(mContext);

        final HttpGetActivity httpGetActivity = new HttpGetActivity();

        mButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                sh.save(mUserName.getText().toString(), mPasswd.getText().toString());
                SPUtils.put(mContext, "username", mUserName.getText().toString());
                SPUtils.put(mContext, "passwd", mPasswd.getText().toString());
                ToastUtil.showToastShort("信息已写入SharedPreference中");
//                httpGetActivity.get();
                post();
            }
        });

        ToastUtil.showToastShort("进入post");
//        post();
//        sendByOKHttp();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
//        Map<String, String> data = sh.read();
//        mUserName.setText(data.get("username"));
//        mPasswd.setText(data.get("passwd"));
        mUserName.setText((String)SPUtils.get(mContext,"username", ""));
        mPasswd.setText((String)SPUtils.get(mContext,"passwd", ""));
    }

    private void post() {
        new Thread(new Runnable() {
            @Override
            public void run() {
            try {
                URL url = new URL(urlAddress + method);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                //设置输入输出流
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.setRequestMethod("POST");//设置为POST方法

                //开始设置请求头
                connection.setRequestProperty("Accept-Charset", "UTF-8");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                //开始连接
                connection.connect();

                //以输出流的形式进行给服务器传输数据
                DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                String content = "person_id=137536;identity_id=5;token=59c61656383c094220d91a9db8cb4732";
                outputStream.writeBytes(content);
                outputStream.flush();
                outputStream.close();

                //服务器进行响应
                if (connection.getResponseCode() == 200) {
                    InputStream is = connection.getInputStream();

                    //读取信息
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                    //使用StringBuffer来存储所有信息
                    StringBuffer stringBuffer = new StringBuffer();

                    //使用readLine方法来存储整行信息
                    String readLine = "";

                    while ((readLine = reader.readLine()) != null) {
                        stringBuffer.append(readLine);
                    }

                    is.close();
                    reader.close();
                    connection.disconnect();

                    Log.d("Message", stringBuffer.toString());
                    mString = stringBuffer.toString();

                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                } else {
                    //打印错误的信息
                    Log.d("TAG", "ERROR ");
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            }
        }).start();
    }

    /**
     * 发送请求（使用 OKHttp）
     */
    private void sendByOKHttp() {

//        RequestBody requestBody = new FormBody.Builder().add("param1", "value1").add("param2", "value2").build();
//        Request request = new Request.Builder().url("www.163.com").post(requestBody).build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://www.163.com").build();
                try {
                    Response response = client.newCall(request).execute();//发送请求
                    String result = response.body().string();
                    Log.d(GlobalConfig.TAG, "result: " + result);
                    show(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 展示
     *
     * @param result
     */
    private void show(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.setText(result);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1)
            {
                mTextView.setText(mString);
            }
        }
    };
}
