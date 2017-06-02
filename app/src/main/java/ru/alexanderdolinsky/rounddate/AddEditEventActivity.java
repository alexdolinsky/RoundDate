package ru.alexanderdolinsky.rounddate;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class AddEditEventActivity extends AppCompatActivity {

    private Calendar date;
    private TextView tvCurrentDate, tvCurrentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_event);

        date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY,12);
        date.set(Calendar.MINUTE,0);
        date.set(Calendar.SECOND,0);

        tvCurrentDate = (TextView)findViewById(R.id.tvCurrentEventDate);
        tvCurrentDate.setText(date.get(Calendar.DAY_OF_MONTH) + "-" + date.get(Calendar.MONTH)
                + "-" + date.get(Calendar.YEAR));

        tvCurrentTime = (TextView)findViewById(R.id.tvCurrentEventTime);
        //tvCurrentTime.setText(date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE));

        tvCurrentTime.setText(String.format("%02d:%02d",date.get(Calendar.HOUR_OF_DAY),
                date.get(Calendar.MINUTE)));

        //tvCurrentTime.setText(DateUtils.formatDateTime(this,
        //        date.getTimeInMillis(),
        //        DateUtils.HOUR_MINUTE_24));
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
        // TODO: 28.05.2017 добавить диалоговое окно выбора даты
        // отображаем диалоговое окно для выбора даты

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

            tvCurrentDate.setText(date.get(Calendar.DAY_OF_MONTH) + "-" + date.get(Calendar.MONTH) + "-" + date.get(Calendar.YEAR));
            Log.d("MyLog", date.get(Calendar.DAY_OF_MONTH) + "-" + date.get(Calendar.MONTH) + "-" + date.get(Calendar.YEAR));
            date.add(Calendar.MINUTE,10000000);
            Log.d("MyLog", date.get(Calendar.DAY_OF_MONTH) + "-" + date.get(Calendar.MONTH) + "-" + date.get(Calendar.YEAR));
            //setInitialDateTime();
        }
    };



    public void onChoiceEventTime(View view) {
        // TODO: 28.05.2017 добавить диалоговое окно выбора времени
        TimePickerDialog ttp = new TimePickerDialog(AddEditEventActivity.this, t,
                date.get(Calendar.HOUR_OF_DAY),
                date.get(Calendar.MINUTE),
                true);
        ttp.show();
        Toast.makeText(this, "Всплывает окно выбора времени", Toast.LENGTH_SHORT).show();
    }

    // установка обработчика выбора даты
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            date.set(Calendar.HOUR_OF_DAY, hourOfDay);
            date.set(Calendar.MINUTE, minute);

            //tvCurrentTime.setText(date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE));
            tvCurrentTime.setText(String.format("%02d:%02d",date.get(Calendar.HOUR_OF_DAY),
                    date.get(Calendar.MINUTE)));
            Log.d("MyLog", String.format("%02d:%02d",date.get(Calendar.HOUR_OF_DAY),
                    date.get(Calendar.MINUTE)));
//            date.add(Calendar.MINUTE,10000000);

        }
    };

    public void onCancel(View view) {
        finish();
    }





}
