package ru.alexanderdolinsky.rounddate;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class AddEditEventActivity extends AppCompatActivity {

    private Calendar date;
    private TextView tvCurrentDate, tvCurrentTime;
    private RadioGroup rdTrackSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_event);

        date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY,12);
        date.set(Calendar.MINUTE,0);
        date.set(Calendar.SECOND,0);

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd - MM - yyyy");
        tvCurrentDate = (TextView)findViewById(R.id.tvCurrentEventDate);
        tvCurrentDate.setText(sdfDate.format(date.getTime()));
        SimpleDateFormat sdfTime = new SimpleDateFormat("kk : mm");
        tvCurrentTime = (TextView)findViewById(R.id.tvCurrentEventTime);
        tvCurrentTime.setText(sdfTime.format(date.getTime()));

        rdTrackSettings = (RadioGroup)findViewById(R.id.rgTracksettings);
        rdTrackSettings.check(R.id.rbUseEventsGroupSettings);
        rdTrackSettings.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                LinearLayout llTrackSettings = (LinearLayout)findViewById(R.id.llTrackSettings);
                ScrollView sv = (ScrollView)findViewById(R.id.svAddEditEvent);
                switch (checkedId){
                    case R.id.rbUseEventSettings:
                        llTrackSettings.setVisibility(View.VISIBLE);
                        // TODO: 07.06.2017 сделать скролл вниз до настроек слежения 
                        //sv.smoothScrollBy(0,400);
                        break;
                    default:
                        llTrackSettings.setVisibility(View.GONE);
                        break;
                }

            }
        });
    }

    public void onSaveEvent(View view) {
        // TODO: 28.05.2017 сохранить событие, сделать расчеты, все записать в БД
        Toast.makeText(this, "Сохранение события и расчеты", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void onChoiceEventGroup(View view) {
        // TODO: 28.05.2017 добавить выбор группы событий
        Toast.makeText(this, "Выбирается группа событий", Toast.LENGTH_SHORT).show();
    }

    public void onChoiceEventDate(View view) {
        //  диалоговое окно выбора даты

        DatePickerDialog dtp = new DatePickerDialog(AddEditEventActivity.this, d,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH));
        dtp.show();

        Toast.makeText(this, "Всплывает окно выбора даты", Toast.LENGTH_SHORT).show();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, monthOfYear);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("dd - MM - yyyy");
            tvCurrentDate.setText(sdf.format(date.getTime()));
            //Log.d("MyLog", date.get(Calendar.DAY_OF_MONTH) + "-" + date.get(Calendar.MONTH) + "-" + date.get(Calendar.YEAR));
        }
    };



    public void onChoiceEventTime(View view) {
        // диалоговое окно выбора времени
        TimePickerDialog ttp = new TimePickerDialog(AddEditEventActivity.this, t,
                date.get(Calendar.HOUR_OF_DAY),
                date.get(Calendar.MINUTE),
                true);
        ttp.show();
        Toast.makeText(this, "Всплывает окно выбора времени", Toast.LENGTH_SHORT).show();
    }

    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            date.set(Calendar.HOUR_OF_DAY, hourOfDay);
            date.set(Calendar.MINUTE, minute);
            SimpleDateFormat sdf = new SimpleDateFormat("kk : mm");
            tvCurrentTime.setText(sdf.format(date.getTime()));
            Log.d("MyLog", sdf.format(date.getTime()));

        }
    };

    public void onCancel(View view) {
        finish();
    }





}
