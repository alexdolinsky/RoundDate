package ru.alexanderdolinsky.rounddate;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Locale;


public class AboutActivity extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        AlarmManager.AlarmClockInfo alarmClockInfo = null;
        if (alarmManager != null) {
            alarmClockInfo = alarmManager.getNextAlarmClock();
        }
        if (alarmClockInfo != null) {
            String str = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault()).format(alarmClockInfo.getTriggerTime()) + " "
                    + DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault()).format(alarmClockInfo.getTriggerTime());
            Toast.makeText(this, "Время следующего будильника: " + str, Toast.LENGTH_LONG).show();
        }
    }


}
