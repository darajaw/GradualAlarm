package com.example.gradualalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;
import android.net.Uri;

import androidx.annotation.RequiresApi;

public class AlarmReceiver extends BroadcastReceiver {
    //set minimumSDK
    @RequiresApi(api = Build.VERSION_CODES.Q)

    @Override
    public void onReceive(Context context, Intent intent) {

        //vibrate phone once for 200 milliseconds (called repeatedly until alarm toggled off)
        Vibrator vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));

        Toast.makeText(context, "WAKE UP! WAKE UP! WAKE UP!", Toast.LENGTH_SHORT).show();
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        // setting default ringtone
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);

        // play ringtone
        ringtone.play();

    }
}
