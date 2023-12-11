package com.example.notification1

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.app.NotificationChannel
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
    var NOTIFICATION_ID: Int = 0
    val CHANNEL_ID: String = "Channel1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createChannelNotification()
        setContentView(R.layout.activity_main)

        var btnSendNotification: Button = findViewById(R.id.btn_send_notification)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
        btnSendNotification.setOnClickListener {
            val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("This is title of push notification")
                .setContentText("This is message of push notification")
                .setSmallIcon(R.drawable.ic_notification_custom)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_large_icon)) // có thể truyền bitmap hoặc Icon
                .build()

//            var notificationManagerCompat = NotificationManagerCompat.from(this)
//            notificationManagerCompat.notify(NOTIFICATION_ID, notification)
            var notificationManager : NotificationManager? = getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            if( notificationManager != null){
                notificationManager.notify(NOTIFICATION_ID, notification)
            }

        }
    }

    private fun createChannelNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            // Create the NotificationChannel.
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }
}