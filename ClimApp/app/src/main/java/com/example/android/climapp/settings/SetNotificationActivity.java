package com.example.android.climapp.settings;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.climapp.R;
import com.example.android.climapp.utils.AlarmReceiver;

import java.util.Calendar;

/**
 * Created by frksteenhoff on 28-03-2018.
 * Setting custom user notifications
 * Credit for method to Nilanchala:
 * http://stacktips.com/tutorials/android/repeat-alarm-example-in-android
 */

public class SetNotificationActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private AlarmManager alarmMgr;
    private PendingIntent pendingIntent;
    private Intent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_notification);
        preferences = this.getSharedPreferences("ClimApp", Context.MODE_PRIVATE);

        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Retrieve a PendingIntent that will perform broadcast
        alarmIntent = new Intent(SetNotificationActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(SetNotificationActivity.this, 0, alarmIntent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        // Get and set correct time for user notification
        setAlarmTime(calendar, preferences.getInt("Notification", 0));

        // Set repetition of alarm
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    /**
     * Setting time for notification to user
     * TODO: make sure intervals only include workdays
     */
    private void setAlarmTime(Calendar calendar, int alarmTime) {
        switch (alarmTime){
            case 0:
                calendar.set(Calendar.HOUR_OF_DAY, 18);
                break;
            case 1:
                calendar.set(Calendar.HOUR_OF_DAY, 6);
                break;
            case 2:
                calendar.set(Calendar.HOUR_OF_DAY, 7);
                break;
            case 3:
                calendar.set(Calendar.HOUR_OF_DAY, 7);
                break;
            // If none of the above, no notifications wanted
            default:
                break;
        }
    }
}
