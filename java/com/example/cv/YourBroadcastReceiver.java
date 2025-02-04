package com.example.cv;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class YourBroadcastReceiver extends BroadcastReceiver {

    @SuppressLint("ScheduleExactAlarm")
    @Override
    public void onReceive(Context context, Intent intent) {
        // Set a reminder
        Intent reminderIntent = new Intent(context, ReminderReceiver.class);
        reminderIntent.putExtra("reminderName", "Meeting");
        reminderIntent.putExtra("reminderDescription", "Reminder for the team meeting at 10 AM");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, reminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            long triggerAtMillis = SystemClock.elapsedRealtime() + 10000; // Example: trigger in 10 seconds
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, pendingIntent);
        }
    }
}
