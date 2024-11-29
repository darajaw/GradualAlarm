package com.example.gradualalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    //App element used to pick time
    TimePicker alarmTimePicker;

    //Intent sent to a place
    PendingIntent pendingIntent;

    //class used to manage the alarm
    AlarmManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TimePicker instantiation
        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);

        //AlarmManager instantiation
        manager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    //Toggle alarm on/off
    public void OnToggleClicked(View view) {
        long time;
        //check if the toggle button is switched on
        if (((ToggleButton) view).isChecked()){

            //alarm on message displayed using Toast
            Toast.makeText(MainActivity.this, "ALARM ON", Toast.LENGTH_SHORT).show();
            Calendar calendar = Calendar.getInstance();

            //get current time from the TimePicker
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());

            //Intent set to AlarmReceiver which inherits from Broadcast Receiver
            Intent intent = new Intent(this, AlarmReceiver.class);

            //call the broadcast using pendingIntent
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            //formula to round current time to the closest minute
            time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));

            //set the current time to AM or PM
            if (System.currentTimeMillis() > time){
                if (Calendar.AM_PM == 0)
                    time = time + (1000 * 60 * 60 * 12);
                else
                    time = time + (1000 * 60 * 60 * 24);
            }
            // Alarm rings continuously until toggle button is turned off
            manager.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent);

        }
        //turn alarm off otherwise
        else {
            manager.cancel(pendingIntent);

            //alarm off message
            Toast.makeText(MainActivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();
        }

    }
}