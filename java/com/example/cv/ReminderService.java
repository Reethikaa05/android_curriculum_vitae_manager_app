package com.example.cv;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.app.IntentService;
import android.os.SystemClock;
import android.util.Log;

/** @noinspection ALL*/
public class ReminderService extends IntentService {

    private static final String TAG = "ReminderService";

    public ReminderService() {
        super("ReminderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent reminderIntent = new Intent(this, ReminderReceiver.class);
        reminderIntent.putExtra("reminderName", "Meeting");
        reminderIntent.putExtra("reminderDescription", "Reminder for the team meeting at 10 AM");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, reminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            long triggerAtMillis = SystemClock.elapsedRealtime() + 10000; // Example: trigger in 10 seconds
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, pendingIntent);
            Log.d(TAG, "Reminder set for: " + triggerAtMillis);
        } else {
            Log.e(TAG, "AlarmManager not available");
        }
    }
}
