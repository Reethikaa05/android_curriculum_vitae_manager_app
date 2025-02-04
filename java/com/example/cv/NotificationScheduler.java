package com.example.cv;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/** @noinspection ALL*/
public class NotificationScheduler {

    public static void scheduleReminder(Context context, long triggerTime, String reminderName, String reminderDescription) {
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra("reminderName", reminderName);
        intent.putExtra("reminderDescription", reminderDescription);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        }
    }
}

