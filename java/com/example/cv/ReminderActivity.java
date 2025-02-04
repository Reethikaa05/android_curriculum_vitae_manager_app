package com.example.cv;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setReminder() {
        // Create an Intent to trigger the broadcast receiver (ReminderReceiver)
        Intent intent = new Intent(this, ReminderReceiver.class);
        intent.putExtra("reminderName", "Meeting");
        intent.putExtra("reminderDescription", "Reminder for the team meeting at 10 AM");

        // Set the PendingIntent flags based on Android version
        int pendingIntentFlags = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                ? PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
                : PendingIntent.FLAG_UPDATE_CURRENT;

        // Create a PendingIntent to send the broadcast
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, pendingIntentFlags);

        // Get the AlarmManager service
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Set the time for the reminder (for example, 1 minute from now)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1); // Set the reminder 1 minute from now
        long triggerTime = calendar.getTimeInMillis();

        // For Android 12+ (API 31+), use setExactAndAllowWhileIdle with appropriate permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
                Toast.makeText(this, "Alarm set for 1 minute later", Toast.LENGTH_SHORT).show();
            } else {
                // If exact alarm permission is not granted, prompt the user to grant it
                Intent settingsIntent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(settingsIntent);
            }
        } else {
            // For devices below Android 12, use setExact
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
            Toast.makeText(this, "Alarm set for 1 minute later", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        // Find the "Set Reminder" button and set an OnClickListener
        Button setReminderButton = findViewById(R.id.btnSetReminder);
        setReminderButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                setReminder();
            }
        });
    }
}
