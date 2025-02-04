package com.example.cv;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String reminderName = intent.getStringExtra("reminderName");
        String reminderDescription = intent.getStringExtra("reminderDescription");
        // Handle the reminder (e.g., show a notification or a Toast)
        Toast.makeText(context, reminderName + ": " + reminderDescription, Toast.LENGTH_LONG).show();
    }
}
