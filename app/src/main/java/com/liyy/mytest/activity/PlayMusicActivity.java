package com.liyy.mytest.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.liyy.mytest.R;

import java.io.File;

public class PlayMusicActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private boolean isInside = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_music);

        RadioGroup mRadioGroup = findViewById(R.id.music_source);
        Button btnPlay = findViewById(R.id.play);
        Button btnPause = findViewById(R.id.pause);
        Button btnStop = findViewById(R.id.stop);

        mRadioGroup.setOnCheckedChangeListener(new CheckListener());
        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        //权限判断，如果没有权限就请求权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            initMediaPlayer();//初始化播放器 MediaPlayer
        }
    }

    private void initMediaPlayer() {
        if (isInside) {
            // 程序内部读取音频文件
            try {
                AssetManager assetManager = getAssets();
                AssetFileDescriptor afd = assetManager.openFd("music.mp3");
                mediaPlayer = new MediaPlayer();
                // 指定音频文件路径
                mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                // 设置为循环播放
                mediaPlayer.setLooping(true);
                // 初始化播放器MediaPlayer
                mediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // 程序外部有读取音频文件
            try {
                File file = new File(Environment.getExternalStorageDirectory(), "music.mp3");
                // 指定音频文件路径
                mediaPlayer.setDataSource(file.getPath());
                // 设置为循环播放
                mediaPlayer.setLooping(true);
                // 初始化播放器MediaPlayer
                mediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initMediaPlayer();
            } else {
                Toast.makeText(this, "拒绝权限，将无法使用程序。", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                //如果没在播放中，立刻开始播放。
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
                break;
            case R.id.pause:
                //如果在播放中，立刻暂停。
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                break;
            case R.id.stop:
                //如果在播放中，立刻停止。
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.reset();
                    initMediaPlayer();//初始化播放器 MediaPlayer
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    class CheckListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.inside:
                    isInside = true;
                    mediaPlayer.reset();
                    initMediaPlayer();
                    break;

                case R.id.outside:
                    isInside = false;
                    mediaPlayer.reset();
                    initMediaPlayer();
                    break;

                default:
                    break;
            }
        }
    }
}