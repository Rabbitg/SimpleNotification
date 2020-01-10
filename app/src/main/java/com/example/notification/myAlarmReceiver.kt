package com.example.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class myAlarmReceiver : BroadcastReceiver(){
    companion object {
        const val ID= "CHANEL_ID"
        const val channelName = "CHANEL_NAME"

    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onReceive(context: Context?, intent: Intent?) {
        val mNotificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val mBuilder : NotificationCompat.Builder = NotificationCompat.Builder(
                context!!, ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Alarm Manager")
                .setContentText(intent?.getStringExtra(EXTRA_PESAN))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val channel = NotificationChannel(ID,
            channelName,NotificationManager.IMPORTANCE_HIGH)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            mNotificationManager.createNotificationChannel(channel)
            mNotificationManager.notify(1,mBuilder.build())
        }

    }
}