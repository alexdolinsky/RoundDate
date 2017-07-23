package ru.alexanderdolinsky.rounddate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class EventActivity extends AppCompatActivity {

    private static final int EDITEVENT_REQUESTCODE = 1;
    private Event event;
    private Timer mTimer;
    private MyTimerTask mMyTimerTask;

    private TextView tvInYears, tvInMonths, tvInWeeks, tvInDays, tvInHours, tvInMinutes, tvInSecs;


    @Override
    protected void onDestroy() {
        mTimer.cancel();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        //Calendar tempCurrentDateAndTime, tempEventDateAndTime;

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
            tvInYears = (TextView) findViewById(R.id.tvInYears);
            tvInMonths = (TextView) findViewById(R.id.tvInMonths);
            tvInWeeks = (TextView) findViewById(R.id.tvInWeeks);
            tvInDays = (TextView) findViewById(R.id.tvInDays);
            tvInHours = (TextView) findViewById(R.id.tvInHours);
            tvInMinutes = (TextView) findViewById(R.id.tvInMinutes);
            tvInSecs = (TextView) findViewById(R.id.tvInSecs);

            // заносим данные события в текстовые поля
            tvEventName.setText(event.getName());
            if (event.getComment().isEmpty()) {
                tvEventComment.setVisibility(View.GONE);
            } else {
                tvEventComment.setText(event.getComment());
            }
            tvEventsGroupName.setText(event.getNameEventGroup());
            tvDateAndTime.setText(DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault()).format(getEvent().getDateAndTime().getTime()) + " " // дата
                    + DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault()).format(getEvent().getDateAndTime().getTime())); // время

            mTimer = new Timer();
            mMyTimerTask = new MyTimerTask();
            mTimer.schedule(mMyTimerTask, 0, 1000);

        }
    }

    public void onEditEvent(View view) {
        Intent intent = new Intent(EventActivity.this, AddEditEventActivity.class);
        intent.putExtra(AddEditEventActivity.ISNEWEVENT, false);
        intent.putExtra(AddEditEventActivity.EVENT_ID, getEvent().getId());
        startActivityForResult(intent, EDITEVENT_REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == EDITEVENT_REQUESTCODE) {
            if (resultCode == RESULT_OK) {
                this.recreate();
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void onDeleteEvent(View view) {
        DialogScreen ds;
        AlertDialog dialog;
        ds = new DialogScreen(DialogScreen.DELETE_EVENT_CONFIRM);
        dialog = ds.getDialog(this);
        dialog.show();
    }


    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            Calendar currentDateAndTime, tempCurrentDateAndTime, tempEventDateAndTime;

            currentDateAndTime = new GregorianCalendar();
            final long duration = currentDateAndTime.getTimeInMillis() - event.getDateAndTime().getTimeInMillis();

            long secs = duration / 1000;
            final String sSecs = String.format(Locale.getDefault(), "%,d %s", secs, RoundDate.getUnit(EventActivity.this, secs, RoundDate.UNIT_SECS));

            long minutes = secs / 60;
            final String sMinutes = String.format(Locale.getDefault(), "%,d %s", minutes, RoundDate.getUnit(EventActivity.this, minutes, RoundDate.UNIT_MINUTES));

            long hours = minutes / 60;
            final String sHours = String.format(Locale.getDefault(), "%,d %s", hours, RoundDate.getUnit(EventActivity.this, hours, RoundDate.UNIT_HOURS));

            long days = hours / 24;
            final String sDays = String.format(Locale.getDefault(), "%,d %s", days, RoundDate.getUnit(EventActivity.this, days, RoundDate.UNIT_DAYS));

            long weeks = days / 7;
            final String sWeeks = String.format(Locale.getDefault(), "%,d %s", weeks, RoundDate.getUnit(EventActivity.this, weeks, RoundDate.UNIT_WEEKS));

            long months = secs / 2628000;
            tempCurrentDateAndTime = new GregorianCalendar();
            tempCurrentDateAndTime.setTimeInMillis(currentDateAndTime.getTimeInMillis());
            tempCurrentDateAndTime.set(Calendar.YEAR, 0);
            tempCurrentDateAndTime.set(Calendar.MONTH, 0);
            tempEventDateAndTime = new GregorianCalendar();
            tempEventDateAndTime.setTimeInMillis(event.getDateAndTime().getTimeInMillis());
            tempEventDateAndTime.set(Calendar.YEAR, 0);
            tempEventDateAndTime.set(Calendar.MONTH, 0);
            if (tempCurrentDateAndTime.getTimeInMillis() < tempEventDateAndTime.getTimeInMillis()) {
                months = months - 1;
            }
            final String sMonths = String.format(Locale.getDefault(), "%,d %s", months, RoundDate.getUnit(EventActivity.this, months, RoundDate.UNIT_MONTHS));

            long years = secs / 31536000;
            tempCurrentDateAndTime.setTimeInMillis(currentDateAndTime.getTimeInMillis());
            tempCurrentDateAndTime.set(Calendar.YEAR, 0);
            tempEventDateAndTime.setTimeInMillis(event.getDateAndTime().getTimeInMillis());
            tempEventDateAndTime.set(Calendar.YEAR, 0);
            if (tempCurrentDateAndTime.getTimeInMillis() < tempEventDateAndTime.getTimeInMillis()) {
                years = years - 1;
            }

            final String sYears = String.format(Locale.getDefault(), "%,d %s", years, RoundDate.getUnit(EventActivity.this, years, RoundDate.UNIT_YEARS));

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (duration < 0) {
                        tvInYears.setText(R.string.event_has_not_yet_occurred);
                        tvInMonths.setText(R.string.event_has_not_yet_occurred);
                        tvInWeeks.setText(R.string.event_has_not_yet_occurred);
                        tvInDays.setText(R.string.event_has_not_yet_occurred);
                        tvInHours.setText(R.string.event_has_not_yet_occurred);
                        tvInMinutes.setText(R.string.event_has_not_yet_occurred);
                        tvInSecs.setText(R.string.event_has_not_yet_occurred);
                    } else {
                        tvInYears.setText(sYears);
                        tvInMonths.setText(sMonths);
                        tvInWeeks.setText(sWeeks);
                        tvInDays.setText(sDays);
                        tvInHours.setText(sHours);
                        tvInMinutes.setText(sMinutes);
                        tvInSecs.setText(sSecs);
                    }

                }
            });
        }
    }
}
