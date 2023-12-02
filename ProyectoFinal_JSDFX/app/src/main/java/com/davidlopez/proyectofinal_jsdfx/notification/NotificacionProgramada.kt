package com.davidlopez.proyectofinal_jsdfx.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.davidlopez.proyectofinal_jsdfx.R

class NotificacionProgramada:BroadcastReceiver() {
    companion object{
        const val NOTIFICACION_ID=5
    }
    override fun onReceive(context: Context, intent: Intent?) {
        crearNotificacion(context)
    }

    private fun crearNotificacion(context: Context){
        val notificacion= NotificationCompat.Builder(context, "CanalNotas")
            .setSmallIcon(R.drawable.calendario)
            .setContentTitle("Tarea Programada")
            .setContentText("¡No me olvides!")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Tienes una tarea pendiente, hazla cuanto antes")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val manager=context.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        manager.notify(NOTIFICACION_ID,notificacion)
    }
}