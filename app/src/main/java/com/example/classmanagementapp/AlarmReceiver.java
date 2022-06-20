package com.example.classmanagementapp;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Switch;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.classmanagementapp.Models.CClass;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PendingIntent pendingIntent;
        Context appContext = context.getApplicationContext();
        String title = intent.getStringExtra("classTitle");
        String content = intent.getStringExtra("classTime");
        String channel_ID = intent.getStringExtra("channelID");

        Log.d("MyLog", "onReceive");
        Log.d("MyLog", title);
        Log.d("MyLgo", channel_ID);
        Intent i = new Intent(appContext, DestinationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if(Build.VERSION.SDK_INT >= 31) {
            pendingIntent = PendingIntent.getActivity(appContext, 0, i, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity(appContext, 0, i, 0);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(appContext, channel_ID)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());
    }
}
