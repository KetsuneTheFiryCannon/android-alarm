package com.example.alarm;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button btnAlarmSetter;
    Calendar calendar = Calendar.getInstance();

    private final TimePickerDialog.OnTimeSetListener onTimeSetListener =
            (view, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                setAlarm();
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAlarmSetter = findViewById(R.id.btnStartTimer);

        btnAlarmSetter.setOnClickListener(view -> new TimePickerDialog(MainActivity.this,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true)
                .show());
    }

    private void setAlarm() {
        long time = calendar.getTimeInMillis();

        long currentTime = System.currentTimeMillis();
        if (time < currentTime) {
            long dayMillis = 24 * 60 * 60 * 1000;
            time += dayMillis;
        }

        Intent alarm = new Intent(MainActivity.this, MainActivity2.class);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP,
                time,
                PendingIntent.getBroadcast(getApplicationContext(),
                        0,
                        alarm,
                        0));
    }
}