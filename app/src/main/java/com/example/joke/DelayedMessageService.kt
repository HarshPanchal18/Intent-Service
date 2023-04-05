package com.example.joke

import android.annotation.SuppressLint
import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import java.lang.Thread.sleep

class DelayedMessageService : IntentService("DelayedMessageService") {

    companion object {
        val EXTRA_MSG: String = "message"
    }

    val NOTIFICATION_CHANNEL_ID = "my_channel_id"
    val NOTIFICATION_ID: Int = 5453 // random

    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        synchronized(this) {
            try {
                sleep(3000)
                /*val mediaPlayer = MediaPlayer.create(this,/*SOUND File*/)
                mediaPlayer.start()*/
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        val text = intent?.getStringExtra(EXTRA_MSG)
        showText(text)
        Log.v("inside try","Prompted")
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun showText(text: String?) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create a notification channel
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "My Channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val builder : NotificationCompat.Builder = NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.sym_def_app_icon)
            .setContentTitle(getString(R.string.question))
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(0,1000))
            .setAutoCancel(true)

        Log.v("inside try","Build")

        //Create an action
        val actionIntent = Intent(this, MainActivity::class.java)
        val actionPendingIntent = PendingIntent.getActivity(
            this,
            0,
            actionIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)
        Log.v("inside try","Action")

        builder.setContentIntent(actionPendingIntent)

        //Issue the notification
        notificationManager.notify(NOTIFICATION_ID, builder.build())
        Log.v("inside try","Final")

    }
}
