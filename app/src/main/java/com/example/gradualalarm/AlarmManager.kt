package com.example.gradualalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock


class AlarmManager(
    private val context: Context
) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    //Todo: update function to have time passed to it
    fun scheduleAlarm(){

        // example time for test
        val fiveSecs = 1000 * 5

        val pendingIntent = PendingIntent.getBroadcast(
            context, 1, Intent(context, AlarmReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE // Immutable keeps pending intents from over writing each other even with the same request code
        )

        // setExactAndAllowWhileIdle has the alarm go off
        // at the exact time and run even in low power mode
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP, // RTC_WAKEUP used for exact time
            //todo: change the triggerAtMillis to get specific time from user
            SystemClock.elapsedRealtime() + fiveSecs, //current time plus the alarm time thats set
            pendingIntent
        )

        //repeating alarm example
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, // RTC_WAKEUP used for exact time
            //todo: change the triggerAtMillis to get specific time from user
            SystemClock.elapsedRealtime() + fiveSecs, //current time plus the alarm time thats set
            1000 * 60,
            pendingIntent)
    }

    fun cancelAlarm(){
        //same pending intent and we'll just change the action that's sent to the receiver
        val pendingIntent = PendingIntent.getBroadcast(
            context, 1, Intent(context, AlarmReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE // Immutable keeps pending intents from over writing each other even with the same request code
        )

        alarmManager.cancel(pendingIntent)
    }
}