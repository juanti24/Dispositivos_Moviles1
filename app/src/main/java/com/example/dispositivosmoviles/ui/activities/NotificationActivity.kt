package com.example.dispositivosmoviles.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityCameraBinding
import com.example.dispositivosmoviles.databinding.ActivityNotificationBinding
import com.example.dispositivosmoviles.ui.utilities.BradCasterNotifications
import java.util.Calendar

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)

        createNotificationChannel()

        setContentView(binding.root)

        binding.btnNotification.setOnClickListener {

            sendNotification()
        }
        binding.btnNotificationProgramada.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hora = binding.timePicker.hour
            val minutes = binding.timePicker.minute

            Toast.makeText(
                this,
                "La notificacion se activa a las: $hora con $minutes",
                Toast.LENGTH_LONG
            ).show()
            calendar.set(Calendar.HOUR, hora)
            calendar.set(Calendar.MINUTE, minutes)
            calendar.set(Calendar.SECOND, 0)

            sendNotificationTimePicker(calendar.timeInMillis)
        }
    }


    val CHANNEL: String = "Notificacion 1"



    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Canal principal1"
            val descriptionText = "Canal de notificaciones de variedades"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(CHANNEL, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    @SuppressLint("ScheduleExactAlarm")
    private fun sendNotificationTimePicker(time: Long) {
        val myIntent = Intent(applicationContext, BradCasterNotifications::class.java)

        val myPendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            myIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, myPendingIntent)
    }

    @SuppressLint("MissingPermission")
    fun sendNotification() {

        val intent = Intent(this, CameraActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_MUTABLE
        )


        val noti = NotificationCompat.Builder(this, CHANNEL)
            .setContentTitle("Primera notificacion")
            .setContentText("Tienes una notificacion") // Este texto se muestra cuando aparece la notificacion
            .setSmallIcon(R.drawable.baseline_chat_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Esta es una notificacion para recordar que estamos trabajando con Android")
            )

        with(NotificationManagerCompat.from(this)) {
            notify(1, noti.build())
        }
    }

}