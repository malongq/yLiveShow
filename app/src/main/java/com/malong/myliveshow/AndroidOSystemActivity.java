package com.malong.myliveshow;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.RemoteViews;

import static android.app.Notification.VISIBILITY_SECRET;

/**
 * Created by Malong
 * on 18/8/21.
 *
 * 测试通知栏必须将gradle
 * minSdkVersion 14  改为 26  才行
 */
public class AndroidOSystemActivity extends AppCompatActivity {

    private NotificationManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_system);

//        sendNormalNotification();
    }

    //创建通知消息类
    private NotificationManager getManager(){
        if(manager == null){
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    //设置通知消息
//    private void sendNormalNotification(){
//        if (Build.VERSION.SDK_INT >= 26){//26 也可以写成 Build.VERSION.O
//            NotificationChannel channel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT);
//            channel.setBypassDnd(true);//手机设置请勿打扰不管用
//            channel.setLightColor(Color.GREEN);//提醒颜色灯
//            channel.enableLights(true);//闪光
//            channel.setShowBadge(true);//桌面lanucher消息角标
//            channel.enableVibration(true);//允许震动
//            channel.setLockscreenVisibility(VISIBILITY_SECRET);//锁屏显示通知
//
//            getManager().createNotificationChannel(channel);
//        }
//        Notification notification = new Notification.Builder(this)
//                .setAutoCancel(true)
//                .setChannelId("channel_id")
//                .setContentTitle("马龙：新消息来啦:")
//                .setContentText("这是一个新消息，你高兴不？\n这是一个新消息，你高兴不？\n这是一个新消息，你高兴不？\n这是一个新消息，你高兴不？\n这是一个新消息，你高兴不？")
//                .setSmallIcon(R.mipmap.run_icon)
//                .build();
//
//        getManager().notify(1,notification);
//
//    }

    //这是自定义通知栏，不用打开了，布局不对，没有写布局
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private void sendCustomNotification(){
//
//        if (Build.VERSION.SDK_INT >= 26){//26 也可以写成 Build.VERSION.O
//            NotificationChannel channel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT);
//            channel.setBypassDnd(true);//手机设置请勿打扰不管用
//            channel.setLightColor(Color.GREEN);//提醒颜色灯
//            channel.enableLights(true);//闪光
//            channel.setShowBadge(true);//桌面lanucher消息角标
//            channel.enableVibration(true);//允许震动
//            channel.setLockscreenVisibility(VISIBILITY_SECRET);//锁屏显示通知
//
//            getManager().createNotificationChannel(channel);
//        }
//        Notification.Builder notification = new Notification.Builder(this);
//        notification.setAutoCancel(true);
////        notification.setChannelId("channel_id");
//        notification.setContentTitle("马龙：新消息来啦:");
//        notification.setContentText("这是一个新消息，你高兴不？");
//        notification.setSmallIcon(R.mipmap.run_icon);
//
//        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.activity_item);//这布局是随便填充的，
//        remoteViews.setTextViewText(R.id.root_view,"custom_view");
//        remoteViews.setTextViewText(R.id.root_view2,"custom_view2");
//
//        Intent intent = new Intent(this, AndroidOSystemActivity.class);
//        PendingIntent activity = PendingIntent.getActivity(this, -1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteViews.setOnClickPendingIntent(R.id.root_view,activity);
//
//
//        notification.setCustomContentView(remoteViews);
//        notification.build();
//        getManager().notify(2,notification.build());
//    }

}
