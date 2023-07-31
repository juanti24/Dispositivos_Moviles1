package com.example.dispositivosmoviles.ui.activities

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.from
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNotification.setOnClickListener {
            //createNotificationChannel()
            sendNotification()
        }
    }


    val CHANNEL : String = "Notificaciones"

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Variedades"
            val descriptionText = "Notificaciones simples de variedades"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }




    @SuppressLint("MissingPermission")
    fun sendNotification(){
        val noti = NotificationCompat.Builder(this, CHANNEL)

        noti.setContentTitle("Primera notificacion")
        noti.setContentText("Tienes una notificacion")
        noti.setSmallIcon(R.drawable.home_48px)
        noti.setPriority(NotificationCompat.PRIORITY_DEFAULT)
        noti.setStyle(NotificationCompat.BigTextStyle()
            .bigText("Esta es una notificacion para recordar que estamos trabajando en Android"))

        with(from(this)){
            notify(1,noti.build())
        }

    }

}