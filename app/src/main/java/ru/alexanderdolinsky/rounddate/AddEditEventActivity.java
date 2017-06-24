package ru.alexanderdolinsky.rounddate;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class AddEditEventActivity extends AppCompatActivity {

    private Calendar date;
    private TrackSettings eventTrackSettings;
    private TrackSettings eventGroupTrackSettings;

    private RadioGroup rdTrackSettings;
    private TextView tvCurrentDate, tvCurrentTime;
    private TextView tvYears, tvMonths, tvWeeks, tvDays, tvHours, tvMinutes, tvSecs;

    private EditText etNameEvent, etCommentEvent, etNameNewEventGroup;

    //private DatabaseAdapter adapter;

    ArrayAdapter<EventGroup> arrayAdapter;
    private Spinner eventGroupsSpinner;
    private EventGroup selectedEventGroup;


    public void setEventTrackSettings(TrackSettings eventTrackSettings) {
        this.eventTrackSettings = eventTrackSettings;
    }

    public TrackSettings getEventTrackSettings() {
        return eventTrackSettings;
    }

    public void setTvYears(CharSequence rdVariant) {
        this.tvYears.setText(rdVariant);
    }

    public void setTvMonths(CharSequence rdVariant) {
        this.tvMonths.setText(rdVariant);
    }

    public void setTvWeeks(CharSequence rdVariant) {
        this.tvWeeks.setText(rdVariant);
    }

    public void setTvDays(CharSequence rdVariant) {
        this.tvDays.setText(rdVariant);
    }

    public void setTvHours(CharSequence rdVariant) {
        this.tvHours.setText(rdVariant);
    }

    public void setTvMinutes(CharSequence rdVariant) {
        this.tvMinutes.setText(rdVariant);
    }

    public void setTvSecs(CharSequence rdVariant) {
        this.tvSecs.setText(rdVariant);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_event);

        eventGroupsSpinner = (Spinner) findViewById(R.id.spinnerEventGroups);

        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();

        final List<EventGroup> eventGroups = adapter.getEventGroups(false, true);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventGroups);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventGroupsSpinner.setAdapter(arrayAdapter);


        eventGroupsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSelectedEventGroup(eventGroups.get(position));
                EditText et = (EditText) findViewById(R.id.etNewEventsGroup);
                switch ((int) getSelectedEventGroup().getId()) {
                    case DatabaseAdapter.LAST_ELEMENT_ID:
                        et.setVisibility(View.VISIBLE);
                        break;
                    default:
                        et.setVisibility(View.GONE);
                        break;
                }
                // Log.d("MyLog", "selectedEventGroup=" + selectedEventGroup.getId() + selectedEventGroup.getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapter.close();

        date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY, 12);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd - MM - yyyy");
        tvCurrentDate = (TextView) findViewById(R.id.tvCurrentEventDate);
        tvCurrentDate.setText(sdfDate.format(date.getTime()));
        SimpleDateFormat sdfTime = new SimpleDateFormat("kk : mm");
        tvCurrentTime = (TextView) findViewById(R.id.tvCurrentEventTime);
        tvCurrentTime.setText(sdfTime.format(date.getTime()));

        final CharSequence[] rdVariants = getResources().getStringArray(R.array.rd_variants);

        eventTrackSettings = new TrackSettings(0, 0, 0, 0, 0, 0, 0);
        tvYears = (TextView) findViewById(R.id.tvRoundDateInYears);
        tvYears.setText(rdVariants[eventTrackSettings.getRdInYears()]);
        tvMonths = (TextView) findViewById(R.id.tvRoundDateInMonths);
        tvMonths.setText(rdVariants[eventTrackSettings.getRdInMonths()]);
        tvWeeks = (TextView) findViewById(R.id.tvRoundDateInWeeks);
        tvWeeks.setText(rdVariants[eventTrackSettings.getRdInWeeks()]);
        tvDays = (TextView) findViewById(R.id.tvRoundDateInDays);
        tvDays.setText(rdVariants[eventTrackSettings.getRdInDays()]);
        tvHours = (TextView) findViewById(R.id.tvRoundDateInHours);
        tvHours.setText(rdVariants[eventTrackSettings.getRdInHours()]);
        tvMinutes = (TextView) findViewById(R.id.tvRoundDateInMinutes);
        tvMinutes.setText(rdVariants[eventTrackSettings.getRdInMinutes()]);
        tvSecs = (TextView) findViewById(R.id.tvRoundDateInSecs);
        tvSecs.setText(rdVariants[eventTrackSettings.getRdInSecs()]);


        rdTrackSettings = (RadioGroup) findViewById(R.id.rgTracksettings);
        rdTrackSettings.check(R.id.rbUseEventsGroupSettings);
        rdTrackSettings.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                LinearLayout llTrackSettings = (LinearLayout) findViewById(R.id.llTrackSettings);
                ScrollView sv = (ScrollView) findViewById(R.id.svAddEditEvent);
                switch (checkedId) {
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

    @Override
    protected void onResume() {
        super.onResume();


    }

    public void onSaveEvent(View view) {
        //  Проверка правильности ввода названия события
        // TODO: 12.06.2017 Сделать проверку на ввод опасных символов
        EditText etEventName = (EditText) findViewById(R.id.etEventName);
        String eventName = etEventName.getText().toString().trim();
        if (eventName.isEmpty()) {
            Toast.makeText(this, R.string.event_name_not_fill, Toast.LENGTH_SHORT).show();
            return;
        }
        ;

        // TODO: 12.06.2017 Проверка корректности ввода комментария, Сделать проверку на ввод опасных символов

        EditText etEventComment = (EditText) findViewById(R.id.etEventComment);
        String eventComment = etEventComment.getText().toString().trim();

        //Log.d("MyLog", "eventComment="+eventComment);

        // Связь с БД и ее открытие
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();
        adapter.beginTransaction();
        try {
            // Проверка на уникальность названия события
            if (adapter.isEventExists(eventName)) {
                Toast.makeText(this, R.string.event_name_is_exists, Toast.LENGTH_SHORT).show();
                return;
            }

            EditText etNewEventsGroupName = (EditText) findViewById(R.id.etNewEventsGroup);
            String newEventsGroupName = etNewEventsGroupName.getText().toString();
            if (getSelectedEventGroup().getId() == -1) {
                // Проверка корректности ввода новой группы событий, если выбрана новая группа
                if (newEventsGroupName.isEmpty()) {
                    Toast.makeText(this, R.string.events_group_name_not_fill, Toast.LENGTH_SHORT).show();
                    return;
                }

                // Проверка на уникальность названия группы событий
                if (adapter.isEventGroupExists(newEventsGroupName)) {
                    Toast.makeText(this, R.string.events_group_is_exists, Toast.LENGTH_SHORT).show();
                    return;
                }

                // Запись группы событий в БД, если выбрана новая группа и название уникально

                EventGroup eventGroup = new EventGroup(-1, newEventsGroupName, 0, new TrackSettings(0, 0, 0, 0, 0, 0, 0));
                getSelectedEventGroup().setId(adapter.addEventsGroup(eventGroup));

                //Log.d("MyLog", "id группы событий=" + getSelectedEventGroup().getId());

            }


            // Запись события в БД

            Event event = new Event(-1, eventName,
                    eventComment,
                    getSelectedEventGroup().getId(),
                    getSelectedEventGroup().getName(),
                    getDate(),
                    rdTrackSettings.getCheckedRadioButtonId(),
                    getEventTrackSettings());

            event.setId(adapter.addEvent(event));

            //Log.d("MyLog", "id=" + event.getId() + " nameEvent=" + event.getName());


            List<RoundDate> roundDates;

            //определение действующих настроек слежения
            TrackSettings trackSettings;
            switch (event.getSourceTrackSettings()) {
                case Event.SOURCE_TRACK_SETTINGS_APP:
                    trackSettings = new TrackSettings(this);
                    break;
                case Event.SOURCE_TRACK_SETTINGS_GROUP:
                    trackSettings = adapter.getGroupTrackSettingsById(event.getIdEventGroup());
                    break;
                case Event.SOURCE_TRACK_SETTINGS_EVENT:
                    trackSettings = event.getTrackSettings();
                    break;
                default:
                    trackSettings = new TrackSettings(this);
                    break;
            }

            // Расчет Круглых дат
            //long start = System.nanoTime();
            if (trackSettings.getRdInYears() != TrackSettings.NOT_TRACK) {
                // Расчет Круглых дат - года
                roundDates = event.getRoundDates(Event.YEAR, trackSettings.getRdInYears());
                // Запись круглых дат в БД
                adapter.addRoundDates(roundDates);


                /*for (RoundDate roundDate : roundDates) {
                    Log.d("MyLog", "ID = " + roundDate.getId() +
                            " valueOf = " + roundDate.getValueOf() +
                            " dateAndTime = " + roundDate.getDateAndTime().getTimeInMillis() / 31557600000L +
                            " idEvent = " + roundDate.getIdEvent() +
                            " NameEvent = " + roundDate.getNameEvent() +
                            " rare = " + roundDate.getRare() +
                            " important = " + roundDate.getImportant()
                    );
                }*/
            }

            if (trackSettings.getRdInMonths() != TrackSettings.NOT_TRACK) {
                // Расчет Круглых дат - месяцы
                roundDates = event.getRoundDates(Event.MONTH, trackSettings.getRdInMonths());
                // Запись круглых дат в БД
                adapter.addRoundDates(roundDates);
            }

            if (trackSettings.getRdInWeeks() != TrackSettings.NOT_TRACK) {
                // Расчет Круглых дат - недели
                roundDates = event.getRoundDates(Event.WEEK, trackSettings.getRdInWeeks());
                // Запись круглых дат в БД
                adapter.addRoundDates(roundDates);
            }

            if (trackSettings.getRdInDays() != TrackSettings.NOT_TRACK) {
                // Расчет Круглых дат - дни
                roundDates = event.getRoundDates(Event.DAY, trackSettings.getRdInDays());
                // Запись круглых дат в БД
                adapter.addRoundDates(roundDates);
            }

            if (trackSettings.getRdInHours() != TrackSettings.NOT_TRACK) {
                // Расчет Круглых дат - часы
                roundDates = event.getRoundDates(Event.HOUR, trackSettings.getRdInHours());
                // Запись круглых дат в БД
                adapter.addRoundDates(roundDates);
            }

            if (trackSettings.getRdInMinutes() != TrackSettings.NOT_TRACK) {
                // Расчет Круглых дат - минуты
                roundDates = event.getRoundDates(Event.MINUTE, trackSettings.getRdInMinutes());
                // Запись круглых дат в БД
                adapter.addRoundDates(roundDates);
            }

            if (trackSettings.getRdInSecs() != TrackSettings.NOT_TRACK) {
                // Расчет Круглых дат - секунды
                roundDates = event.getRoundDates(Event.SEC, trackSettings.getRdInSecs());
                // Запись круглых дат в БД
                adapter.addRoundDates(roundDates);
            }

            //long finish = System.nanoTime();
            //long duration = finish - start;
            //Log.d("MyLog", "Время выполнения = " + duration);

            adapter.setTransactionSuccessful();
        } finally {
            adapter.endTransaction();
        }


        // Закрытие соединения с БД
        adapter.close();

        Toast.makeText(this, "Сохранение события и расчеты", Toast.LENGTH_SHORT).show();
        finish();
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
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
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
    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            date.set(Calendar.HOUR_OF_DAY, hourOfDay);
            date.set(Calendar.MINUTE, minute);
            SimpleDateFormat sdf = new SimpleDateFormat("kk : mm");
            tvCurrentTime.setText(sdf.format(date.getTime()));
            //Log.d("MyLog", sdf.format(date.getTime()));

        }
    };

    public void onCancel(View view) {
        finish();
    }


    public void onClick(View view) {
        DialogScreen ds;
        android.app.AlertDialog dialog;

        switch (view.getId()) {
            case R.id.llRoundDateYear:
                ds = new DialogScreen(0);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateMonth:
                ds = new DialogScreen(1);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateWeek:
                ds = new DialogScreen(2);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateDay:
                ds = new DialogScreen(3);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateHour:
                ds = new DialogScreen(4);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateMinute:
                ds = new DialogScreen(5);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateSec:
                ds = new DialogScreen(6);
                dialog = ds.getDialog(this);
                dialog.show();
                break;


        }

    }


    public TrackSettings getEventGroupTrackSettings() {
        return eventGroupTrackSettings;
    }

    public void setEventGroupTrackSettings(TrackSettings eventGroupTrackSettings) {
        this.eventGroupTrackSettings = eventGroupTrackSettings;
    }

    public EditText getEtNameEvent() {
        return etNameEvent;
    }

    public void setEtNameEvent(EditText etNameEvent) {
        this.etNameEvent = etNameEvent;
    }

    public EditText getEtCommentEvent() {
        return etCommentEvent;
    }

    public void setEtCommentEvent(EditText etCommentEvent) {
        this.etCommentEvent = etCommentEvent;
    }

    public EditText getEtNameNewEventGroup() {
        return etNameNewEventGroup;
    }

    public void setEtNameNewEventGroup(EditText etNameNewEventGroup) {
        this.etNameNewEventGroup = etNameNewEventGroup;
    }

    public EventGroup getSelectedEventGroup() {
        return selectedEventGroup;
    }

    public void setSelectedEventGroup(EventGroup selectedEventGroup) {
        this.selectedEventGroup = selectedEventGroup;
    }

    public Calendar getDate() {
        return date;
    }
}
