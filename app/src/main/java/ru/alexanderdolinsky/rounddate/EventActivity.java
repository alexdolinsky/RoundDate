package ru.alexanderdolinsky.rounddate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class EventActivity extends AppCompatActivity {

    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            long id = extras.getLong("ID");

            // подключаем БД
            DatabaseAdapter adapter = new DatabaseAdapter(this);
            adapter.open();
            // получаем из БД событие по ID
            setEvent(adapter.getEventById(id));
            // закрываем соединенеие с БД
            adapter.close();
            // находим текстовые поля для внесения данных события
            TextView tvEventName = (TextView) findViewById(R.id.tvEventName);
            TextView tvEventComment = (TextView) findViewById(R.id.tvEventComment);
            TextView tvEventsGroupName = (TextView) findViewById(R.id.tvEventsGroupName);
            TextView tvDateAndTime = (TextView) findViewById(R.id.tvDateAndTime);
            TextView tvInYears = (TextView) findViewById(R.id.tvInYears);
            TextView tvInMonths = (TextView) findViewById(R.id.tvInMonths);
            TextView tvInWeeks = (TextView) findViewById(R.id.tvInWeeks);
            TextView tvInDays = (TextView) findViewById(R.id.tvInDays);
            TextView tvInHours = (TextView) findViewById(R.id.tvInHours);
            TextView tvInMinutes = (TextView) findViewById(R.id.tvInMinutes);
            TextView tvInSecs = (TextView) findViewById(R.id.tvInSecs);

            // заносим данные события в текстовые поля
            tvEventName.setText(event.getName());
            if (event.getComment().isEmpty()) {
                tvEventComment.setVisibility(View.GONE);
            } else {
                tvEventComment.setText(event.getComment());
            }
            tvEventsGroupName.setText(event.getNameEventGroup());
            tvDateAndTime.setText(event.getDate() + " " + event.getTime());
            // TODO: 25.06.2017 настроить часовые пояса при вызове текущего времени
            Calendar currentDateAndTime = new GregorianCalendar();
            long duration = currentDateAndTime.getTimeInMillis() - event.getDateAndTime().getTimeInMillis();

            if (duration < 0) {
                tvInYears.setText(R.string.event_has_not_yet_occurred);
                tvInMonths.setText(R.string.event_has_not_yet_occurred);
                tvInWeeks.setText(R.string.event_has_not_yet_occurred);
                tvInDays.setText(R.string.event_has_not_yet_occurred);
                tvInHours.setText(R.string.event_has_not_yet_occurred);
                tvInMinutes.setText(R.string.event_has_not_yet_occurred);
                tvInSecs.setText(R.string.event_has_not_yet_occurred);
            } else {
                long secs = duration / 1000;
                String sSecs = String.format("%,d %s", secs, RoundDate.getUnit(this, secs, RoundDate.UNIT_SECS));
                tvInSecs.setText(sSecs);

                long minutes = secs / 60;
                String sMinutes = String.format("%,d %s", minutes, RoundDate.getUnit(this, minutes, RoundDate.UNIT_MINUTES));
                tvInMinutes.setText(sMinutes);

                long hours = minutes / 60;
                String sHours = String.format("%,d %s", hours, RoundDate.getUnit(this, hours, RoundDate.UNIT_HOURS));
                tvInHours.setText(sHours);

                long days = hours / 24;
                String sDays = String.format("%,d %s", days, RoundDate.getUnit(this, days, RoundDate.UNIT_DAYS));
                tvInDays.setText(sDays);

                long weeks = days / 7;
                String sWeeks = String.format("%,d %s", weeks, RoundDate.getUnit(this, weeks, RoundDate.UNIT_WEEKS));
                tvInWeeks.setText(sWeeks);

                long months = secs / 2629800;
                String sMonths = String.format("%,d %s", months, RoundDate.getUnit(this, months, RoundDate.UNIT_MONTHS));
                tvInMonths.setText(sMonths);

                long years = secs / 31557600;
                String sYears = String.format("%,d %s", years, RoundDate.getUnit(this, years, RoundDate.UNIT_YEARS));
                tvInYears.setText(sYears);
            }


            Log.d("MyLog", "ID: " + event.getId() + " Имя события: " + event.getName() +
                    " Комментарий: " + event.getComment() +
                    " Группа событий: " + event.getNameEventGroup() +
                    " Дата: " + event.getDateAndTime());


        }


    }

    public void onEditEvent(View view) {
        Intent intent = new Intent(EventActivity.this, AddEditEventActivity.class);
        intent.putExtra(AddEditEventActivity.ISNEWEVENT, false);
        intent.putExtra(AddEditEventActivity.EVENT_ID, getEvent().getId());
        startActivity(intent);
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
