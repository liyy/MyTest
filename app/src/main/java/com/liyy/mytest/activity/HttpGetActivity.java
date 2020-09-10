package com.liyy.mytest.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liyy.mytest.R;
import com.liyy.mytest.config.GlobalConfig;
import com.liyy.mytest.util.ToastUtil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpGetActivity extends AppCompatActivity
{
    private Bitmap mBitmap;
    private ImageView mImageView;
    private TextView mTextView;
    private String mString;

    private String mUser = "liyanyong2";
    private String mPwd = "2016";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_get);

        String url = "";
//        mBitmap = sendGetResquest(url);

        mImageView = (ImageView)findViewById(R.id.imageView);
        sendByOKHttp();
//        get();
        mImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ToastUtil.showToastShort("点击了图片!");
            }
        });

//        new Handler().postDelayed(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                Log.d("Msg", "=======================延迟3s");
//            }
//        }, 3000);

        mTextView = (TextView)findViewById(R.id.get_data);
        get("");
    }


    /**
     * Function   :   发送GET请求
     */
//    public static Bitmap sendGetResquest(String path) {
//        Bitmap bitmap = null;
//        HttpGet httpGet = new HttpGet(path);                           //创建一个GET方式的HttpRequest对象
//        DefaultHttpClient httpClient = new DefaultHttpClient();        //创建一个默认的HTTP客户端
//
//        try {
//            HttpResponse httpResponse = httpClient.execute(httpGet);               //执行GET方式的HTTP请求
//            int reponseCode = httpResponse.getStatusLine().getStatusCode();        //获得服务器的响应码
//            if(reponseCode == HttpStatus.SC_OK) {
//                InputStream inputStream = httpResponse.getEntity().getContent();   //获得服务器的响应内容
//                bitmap = BitmapFactory.decodeStream(inputStream);
//                inputStream.close();
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return bitmap;
//    }

    public void get() {
//        String urlAddress = "http://www.edusoa.com/dsideal_yy/admin/new_base/";
//        String method = "getServerInfo";
//        final String getUrl = urlAddress + method;

        String urlAddress = "http://www.edusoa.com/dsideal_yy/login/";
        String method = "doLogin";
        final String getUrl = urlAddress + method + "?user=" + mUser + "&pwd=" + mPwd + "&plat=DSXTPAD&sys_type=44";

        new Thread(new Runnable() {
            @Override
            public void run() {

                BufferedReader reader = null;
                HttpURLConnection connection = null;
                InputStream is = null;

                try {
                    URL url = new URL(getUrl);

                    //开启连接
                    connection = (HttpURLConnection) url.openConnection();

                    //设置请求方法
                    connection.setRequestMethod("GET");

                    //设置连接超时时间（毫秒）
                    connection.setConnectTimeout(5000);

                    //设置读取超时时间（毫秒）
                    connection.setReadTimeout(5000);

                    //连接服务器
                    connection.connect();

                    if (connection.getResponseCode() == 200) {
                        //使用字符流形式进行回复
                        is = connection.getInputStream();

                        //读取信息BufferReader
                        reader = new BufferedReader(new InputStreamReader(is));
                        StringBuffer buffer = new StringBuffer();
                        String readLine = "";

                        while ((readLine = reader.readLine()) != null) {
                            buffer.append(readLine);
                        }

                        mString = buffer.toString();

//                        is.close();
//                        reader.close();
//                        connection.disconnect();

//                        Message msg = new Message();
//                        msg.what = 2;
//                        handler.sendMessage(msg);
                        show(mString);

                        Log.d(GlobalConfig.TAG, mString);
                    } else {
                        Log.d(GlobalConfig.TAG, "ERROR CONNECTED");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {//关闭连接
                        connection.disconnect();
                    }
                    if (is != null) {
                        try
                        {
                            is.close();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    private void get(String s) {
        final String getUrl = "http://dsideal.obs.cn-north-1.myhuaweicloud.com/down/dzsb/apk/3fe86209-6e3e-4488-ae29-7d060747a17c.apk.png";

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(getUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();//开启连接
                    connection.connect();//连接服务器

                    if (connection.getResponseCode() == 200) {
                        //使用字符流形式进行回复
                        InputStream is = connection.getInputStream();

                        mBitmap = BitmapFactory.decodeStream(is);

                        is.close();
                        connection.disconnect();

                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    } else {
                        Log.d(GlobalConfig.TAG, "ERROR CONNECTED");
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void setBitmap(Bitmap bitmap)
    {
        mImageView.setImageBitmap(bitmap);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    mImageView.setImageBitmap(mBitmap);
                    break;
                case 2:
                    mTextView.setText(mString);
                    break;
            }
        }
    };

    /**
     * 发送请求（使用 OKHttp）
     */
    private void sendByOKHttp() {
        String urlAddress = "http://www.edusoa.com/dsideal_yy/admin/new_base/";
        String method = "getServerInfo";
        final String getUrl = urlAddress + method;
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(getUrl).build();
                try {
                    Response response = client.newCall(request).execute();//发送请求
                    assert response.body() != null;
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

    /**
     * Base64字符串转换成图片
     *
     * @param string
     * @return
     */
    public static Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 图片转换成base64字符串
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imgBytes = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }
}
