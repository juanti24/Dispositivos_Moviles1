package com.example.dispositivosmoviles.ui.utilities

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.ui.activities.NotificationActivity

class BradCasterNotifications :BroadcastReceiver(){
    private val CHANNEL:String="Notificacion 1"

    override fun onReceive(context: Context, intent: Intent) {


        val myIntent=Intent(context, NotificationActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            myIntent,
            PendingIntent.FLAG_MUTABLE
        )
        val notif = NotificationCompat.Builder(context, CHANNEL)
        notif.setContentTitle("Broadcaster notificacion mi llave :v")
        notif.setContentText("Abre tu wea :v")
        notif.setSmallIcon(R.drawable.baseline_chat_24)
        notif.setPriority(NotificationCompat.PRIORITY_DEFAULT)
        notif.setContentIntent(pendingIntent)
        notif.setAutoCancel(true)

        notif.setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("alo")
        )

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(
            1,
            notif.build()
        )


    }

}