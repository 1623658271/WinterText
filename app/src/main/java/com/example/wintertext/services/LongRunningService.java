package com.example.wintertext.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;

import androidx.core.app.NotificationCompat;

import com.example.wintertext.R;
import com.example.wintertext.activities.MainActivity;

public class LongRunningService extends Service {
    public LongRunningService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent intent1 = new Intent("android.intent.action.MAIN");
        intent1.addCategory("toThis");
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent1,0);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("1","name",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        Notification notification = new NotificationCompat.Builder(this,"1")
                .setContentTitle("恩索的邀请")
                .setContentText("永恩:是兄弟就来砍我！")
                .setSmallIcon(R.drawable.android_image)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.yasuo))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(1,notification);
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int Hours = 60*60*1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + Hours;
        Intent i = new Intent(this,LongRunningService.class);
        PendingIntent pi = PendingIntent.getService(this,0,i,0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);
    }
}