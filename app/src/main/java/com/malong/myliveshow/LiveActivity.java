package com.malong.myliveshow;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by Malong
 * on 18/7/25.
 */
public class LiveActivity extends AppCompatActivity {

    private static final String TAG = LiveActivity.class.getSimpleName();

    private String url;
    private String title;

    private RelativeLayout rl_loading;
    private ProgressBar pb_loading;
    private TextView tv_loading;

    private LinearLayout ll_live_top;
    private ImageView iv_back;
    private TextView tv_video;
    private TextView tv_time;

    private LinearLayout ll_live_bottom;
    private ImageView iv_stop;
    private RelativeLayout root_view;

//    private VideoView surface_view;//Vitamio视频播放

    private Handler mhanlder = new Handler(Looper.getMainLooper());
    private static final int AUTO_HIDE_TIME = 6000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
        }

        setContentView(R.layout.activity_live);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");
        Log.d(TAG, ">>  " + "url:  " + url + " ,title:  " + title);

        initView();

        initPlayer();

    }

    private boolean a;

    private void initView() {

        /**
         * 根部局控件
         */
        root_view = findViewById(R.id.root_view);

        /**
         * 加载中控件
         */
        rl_loading = findViewById(R.id.rl_loading);
        pb_loading = findViewById(R.id.pb_loading);
        tv_loading = findViewById(R.id.tv_loading);

        /**
         * 播放页顶部控件
         */
        ll_live_top = findViewById(R.id.ll_live_top);

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_video = findViewById(R.id.tv_video);
        tv_video.setText(title);

        tv_time = findViewById(R.id.tv_time);
        tv_time.setText(getSystemTime());

        /**
         * 播放页底部控件
         */
        ll_live_bottom = findViewById(R.id.ll_live_bottom);
        iv_stop = findViewById(R.id.iv_stop);
        iv_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否暂停还是播放
//                if(surface_view.isPlaying()){
                if (a) {
//                    surface_view.stopPlaying();
                    iv_stop.setImageResource(R.mipmap.tips_live_all);
                    a = false;
                } else {
//                    surface_view.setVideoURI(Uri.parse(url));
                    //准备播放
//                    surface_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                        @Override
//                        public void onPrepared(MediaPlayer mp) {
//                            surface_view.start();
//                            Log.d(TAG, "surface_view.start() >>");
//                        }
//                    });
                    iv_stop.setImageResource(R.mipmap.lesson_list_filter_play);
                    a = true;
                }
            }
        });

        /**
         * vitamio控件
         */
//        surface_view = findViewById(R.id.surface_view);

        //点击屏幕
        root_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //判断是否暂停还是播放
//                if(surface_view.isPlaying()){
                if (a) {
                    iv_stop.setImageResource(R.mipmap.tips_live_all);
                    a = false;
                } else {
                    iv_stop.setImageResource(R.mipmap.lesson_list_filter_play);
                    a = true;
                }

                //如果有一个显示着，就关闭他们
                if (ll_live_bottom.getVisibility() == View.VISIBLE || ll_live_top.getVisibility() == View.VISIBLE) {
                    ll_live_top.setVisibility(View.GONE);
                    ll_live_bottom.setVisibility(View.GONE);
                    return;
                }
                //如果没显示就显示出来
                ll_live_top.setVisibility(View.VISIBLE);
                ll_live_bottom.setVisibility(View.VISIBLE);
                //并且过3秒后自动消失--Hanlder
                mhanlder.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ll_live_top.setVisibility(View.GONE);
                        ll_live_bottom.setVisibility(View.GONE);
                    }
                }, AUTO_HIDE_TIME);
            }
        });

    }

    //获取系统时间
    private String getSystemTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(calendar.getTime());
        return time;
    }

    /**
     * Vitamio视频播放
     */
    private static final int RETRY_TIMES = 5;//设置播放错误次数超过5次就弹窗提示用户
    private int mCount = 0;

    private void initPlayer() {
        /*//检查vitamio框架是否可用
        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }
        //初始化播放组件
        if (Vitamio.isInitialized(this)) {
            surface_view.setVideoURI(Uri.parse(url));//将播放地址放入
            //准备播放
            surface_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    surface_view.start();
                    Log.d(TAG, "surface_view.start() >>");
                }
            });
            //错误处理
            surface_view.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    if (mCount > RETRY_TIMES) {

                        //设置播放错误次数超过5次就弹窗提示用户关闭重新打开
                        new AlertDialog.Builder(LiveActivity.this).setMessage("播放错误次数过多，请关闭后重新打开").setPositiveButton("好的", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LiveActivity.this.finish();
                            }
                        }).setCancelable(false).show();

                    } else {

                        //如果没有超过5次就重新加载地址
                        surface_view.stopPlayback();
                        surface_view.setVideoURI(Uri.parse(url));
                        Log.d(TAG, "错误处理 >>");
                    }
                    mCount++;
                    return false;
                }
            });

            //是否正在加载
            surface_view.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {

                    switch (what) {
                        //正在加载的状态
                        case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                            rl_loading.setVisibility(View.VISIBLE);
                            Log.d(TAG, "正在加载的状态 >>");
                            break;
                        //加载完成的状态（三种）
                        case MediaPlayer.MEDIA_INFO_BUFFERING_END://缓冲完毕
                        case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING://音频先出来视频没出来情况
                        case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED://边下边播
                            rl_loading.setVisibility(View.GONE);
                            Log.d(TAG, "加载完成的状态（三种） >>");
                            break;

                    }

                    return false;
                }
            });
        }*/
    }

}
