package ru.alexanderdolinsky.rounddate.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import ru.alexanderdolinsky.rounddate.data.Event;
import ru.alexanderdolinsky.rounddate.data.EventGroup;
import ru.alexanderdolinsky.rounddate.db.DatabaseAdapter;
import ru.alexanderdolinsky.rounddate.dialogs.DialogScreen;
import ru.alexanderdolinsky.rounddate.notifications.NotificationReceiver;
import ru.alexanderdolinsky.rounddate.R;
import ru.alexanderdolinsky.rounddate.data.RoundDate;
import ru.alexanderdolinsky.rounddate.data.TrackSettings;


public class AddEditEventActivity extends AppCompatActivity {


    private Calendar date;
    private TrackSettings eventTrackSettings, oldEventTrackSettings;
    private TrackSettings eventGroupTrackSettings;
    private Event event;

    private TextView tvCurrentDate, tvCurrentTime;
    private TextView tvYears, tvMonths, tvWeeks, tvDays, tvHours, tvMinutes, tvSecs;

    private EditText etNameEvent, etCommentEvent, etNameNewEventGroup;

    static final String ISNEWEVENT = "isNewEvent";
    static final String EVENT_ID = "ID";

    ArrayAdapter<EventGroup> arrayAdapter;
    private EventGroup selectedEventGroup;
    private int sourceTrackSettings;
    private boolean isNewEvent;
    private long eventId = -1;
    private long oldDateInMillis = 0;

    private NotificationReceiver notificationReceiver;
    public static final String NOTIFICATION_ACTION = "ru.alexanderdolinsky.rounddate.NOTIFICATION_SERVICE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_event);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            setNewEvent(extras.getBoolean(ISNEWEVENT));
            setEventId(extras.getLong(EVENT_ID));
        } else {
            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Устанавливаем заголовок окна и первое текстовое поле
        TextView tvAddEditEvent = (TextView) findViewById(R.id.tvAddEditEvent);
        if (isNewEvent()) {
            setTitle(getString(R.string.adding_event));
            tvAddEditEvent.setText(R.string.new_event);
        } else {
            setTitle(getString(R.string.editing_event));
            tvAddEditEvent.setText(R.string.event);
        }

        // Загружаем из базы список групп событий, а также Событие, если режим редактирования

        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();

        final List<EventGroup> eventGroups = adapter.getEventGroups(false, true);
        // TODO: 25.06.2017 Сделать нормальный адаптер
        Spinner eventGroupsSpinner = (Spinner) findViewById(R.id.spinnerEventGroups);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventGroups);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventGroupsSpinner.setAdapter(arrayAdapter);

        if (!isNewEvent()) {
            setEvent(adapter.getEventById(getEventId())); // Извлечение из БД события по ID
            eventGroupsSpinner.setSelection((int) getEvent().getIdEventGroup() - 1); // Установка соответствующей группы событий
        } else {
            setEvent(new Event());
        }


        eventGroupsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(0xaa303030);
                ((TextView) parent.getChildAt(0)).setTextSize(18);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Определение полей по их ID

        EditText etEventName = (EditText) findViewById(R.id.etEventName);
        EditText etEventComment = (EditText) findViewById(R.id.etEventComment);
        tvCurrentDate = (TextView) findViewById(R.id.tvCurrentEventDate);
        tvCurrentTime = (TextView) findViewById(R.id.tvCurrentEventTime);
        RadioGroup rgTrackSettings = (RadioGroup) findViewById(R.id.rgTracksettings);

        tvYears = (TextView) findViewById(R.id.tvRoundDateInYears);
        tvMonths = (TextView) findViewById(R.id.tvRoundDateInMonths);
        tvWeeks = (TextView) findViewById(R.id.tvRoundDateInWeeks);
        tvDays = (TextView) findViewById(R.id.tvRoundDateInDays);
        tvHours = (TextView) findViewById(R.id.tvRoundDateInHours);
        tvMinutes = (TextView) findViewById(R.id.tvRoundDateInMinutes);
        tvSecs = (TextView) findViewById(R.id.tvRoundDateInSecs);

        //Варианты круглых дат
        final CharSequence[] rdVariants = getResources().getStringArray(R.array.rd_variants);


        // Установка значений в полях
        if (isNewEvent()) {// если событие добавляется

            // Вычисляется текущая дата, время выставляется на полдень
            date = new GregorianCalendar();
            date.set(Calendar.HOUR_OF_DAY, 12);
            date.set(Calendar.MINUTE, 0);
            date.set(Calendar.SECOND, 0);

            // Устанавливается дата и время
            tvCurrentDate.setText(DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault()).format(date.getTime()));
            tvCurrentTime.setText(DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault()).format(date.getTime()));

            // Установка настроек по умолчанию
            setEventTrackSettings(new TrackSettings(this));

            // Установка источника настроек отслеживания по умолчанию (по группе событий)
            setSourceTrackSettings(Event.SOURCE_TRACK_SETTINGS_GROUP);
            // Установка радиобаттона в соответствующее положение
            rgTrackSettings.check(R.id.rbUseEventsGroupSettings);

        } else {  // если событие редактируется

            etEventName.setText(getEvent().getName());
            etEventComment.setText(getEvent().getComment());

            // Время выставляется из события
            date = new GregorianCalendar();
            date.setTimeInMillis(getEvent().getDateAndTime().getTimeInMillis());

            // Устанавливается дата и время из события
            tvCurrentDate.setText(DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault()).format(date.getTime()));
            tvCurrentTime.setText(DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault()).format(date.getTime()));

            // Сохраняем начальные значения времени в миллисекундах
            setOldDateInMillis(getEvent().getDateAndTime().getTimeInMillis());

            // Установка настроек из события
            setEventTrackSettings(getEvent().getTrackSettings());

            // Определяем начальные настройки слежения
            TrackSettings trackSettings;
            switch (getEvent().getSourceTrackSettings()) {
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

            // Сохраняем начальные настройки отслеживания для последующего сравнения
            setOldEventTrackSettings(new TrackSettings(
                    trackSettings.getRdInYears(),
                    trackSettings.getRdInMonths(),
                    trackSettings.getRdInWeeks(),
                    trackSettings.getRdInDays(),
                    trackSettings.getRdInHours(),
                    trackSettings.getRdInMinutes(),
                    trackSettings.getRdInSecs()));


            // Установка источника настроек отслеживания из события
            setSourceTrackSettings(getEvent().getSourceTrackSettings());
            // Установка радиобаттона в соответствующее положение
            LinearLayout llTrackSettings = (LinearLayout) findViewById(R.id.llTrackSettings);
            switch (getEvent().getSourceTrackSettings()) {
                case Event.SOURCE_TRACK_SETTINGS_EVENT:
                    rgTrackSettings.check(R.id.rbUseEventSettings);
                    llTrackSettings.setVisibility(View.VISIBLE);
                    break;
                case Event.SOURCE_TRACK_SETTINGS_GROUP:
                    rgTrackSettings.check(R.id.rbUseEventsGroupSettings);
                    llTrackSettings.setVisibility(View.GONE);
                    break;
                case Event.SOURCE_TRACK_SETTINGS_APP:
                    rgTrackSettings.check(R.id.rbUseAppSettings);
                    llTrackSettings.setVisibility(View.GONE);
                    break;
            }
        }

        // Устанавливаем соответствующие настройкам тексты в полях настроек отслеживания
        tvYears.setText(rdVariants[getEventTrackSettings().getRdInYears()]);
        tvMonths.setText(rdVariants[getEventTrackSettings().getRdInMonths()]);
        tvWeeks.setText(rdVariants[getEventTrackSettings().getRdInWeeks()]);
        tvDays.setText(rdVariants[getEventTrackSettings().getRdInDays()]);
        tvHours.setText(rdVariants[getEventTrackSettings().getRdInHours()]);
        tvMinutes.setText(rdVariants[getEventTrackSettings().getRdInMinutes()]);
        tvSecs.setText(rdVariants[getEventTrackSettings().getRdInSecs()]);

        // Устанавливаем обработчик на группу радиобаттонов
        rgTrackSettings.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                LinearLayout llTrackSettings = (LinearLayout) findViewById(R.id.llTrackSettings);
                ScrollView sv = (ScrollView) findViewById(R.id.svAddEditEvent);
                switch (checkedId) {
                    case R.id.rbUseEventSettings:
                        llTrackSettings.setVisibility(View.VISIBLE);
                        setSourceTrackSettings(Event.SOURCE_TRACK_SETTINGS_EVENT);
                        // TODO: 07.06.2017 сделать скролл вниз до настроек слежения
                        //sv.smoothScrollBy(0,400);
                        break;
                    case R.id.rbUseEventsGroupSettings:
                        llTrackSettings.setVisibility(View.GONE);
                        setSourceTrackSettings(Event.SOURCE_TRACK_SETTINGS_GROUP);
                        break;
                    case R.id.rbUseAppSettings:
                        llTrackSettings.setVisibility(View.GONE);
                        setSourceTrackSettings(Event.SOURCE_TRACK_SETTINGS_APP);
                        break;
                }
            }
        });
        adapter.close();


    }

    public void onSaveEvent(View view) {

        Intent intent;

        //  Проверка правильности ввода названия события
        EditText etEventName = (EditText) findViewById(R.id.etEventName);
        String eventName = etEventName.getText().toString().trim();
        if (eventName.isEmpty()) {
            Toast.makeText(this, R.string.event_name_not_fill, Toast.LENGTH_SHORT).show();
            return;
        }

        EditText etEventComment = (EditText) findViewById(R.id.etEventComment);
        String eventComment = etEventComment.getText().toString().trim();

        // Проверка корректности ввода новой группы событий, если выбрана новая группа
        EditText etNewEventsGroupName = (EditText) findViewById(R.id.etNewEventsGroup);
        String newEventsGroupName = etNewEventsGroupName.getText().toString();
        if ((getSelectedEventGroup().getId() == -1) && (newEventsGroupName.isEmpty())) {
            Toast.makeText(this, R.string.events_group_name_not_fill, Toast.LENGTH_SHORT).show();
            return;
        }

        // Связь с БД и ее открытие
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();

        // Проверка на уникальность названия события
        if (adapter.isEventExists(eventName, getEvent().getId())) {
            Toast.makeText(this, R.string.event_name_is_exists, Toast.LENGTH_SHORT).show();
            return;
        }

        // Проверка на уникальность названия группы событий
        if ((getSelectedEventGroup().getId() == -1) && (adapter.isEventGroupExists(newEventsGroupName, getSelectedEventGroup().getId()))) {
            Toast.makeText(this, R.string.events_group_is_exists, Toast.LENGTH_SHORT).show();
            return;
        }


        // Если событие добавляется
        if (isNewEvent()) {
            // начинаем транзакцию
            adapter.beginTransaction();
            try {

                // Запись группы событий в БД, если выбрана новая группа и название уникально
                if ((getSelectedEventGroup().getId() == -1) && !(adapter.isEventGroupExists(newEventsGroupName, getSelectedEventGroup().getId()))) {
                    EventGroup eventGroup = new EventGroup(-1, newEventsGroupName, 0, new TrackSettings(this));
                    getSelectedEventGroup().setId(adapter.addEventsGroup(eventGroup));
                }

                // Установка полей события
                getEvent().setName(eventName);
                getEvent().setComment(eventComment);
                getEvent().setIdEventGroup(getSelectedEventGroup().getId());
                getEvent().setNameEventGroup(getSelectedEventGroup().getName());
                getEvent().setDateAndTime(getDate());
                getEvent().setSourceTrackSettings(getSourceTrackSettings());
                getEvent().setTrackSettings(getEventTrackSettings());
                // Запись события в БД
                getEvent().setId(adapter.addEvent(getEvent()));


                List<RoundDate> roundDates;

                //определение действующих настроек слежения
                TrackSettings trackSettings;
                switch (getSourceTrackSettings()) {
                    case Event.SOURCE_TRACK_SETTINGS_APP:
                        trackSettings = new TrackSettings(this);
                        break;
                    case Event.SOURCE_TRACK_SETTINGS_GROUP:
                        EventGroup eventGroup = adapter.getEventGroupById(event.getIdEventGroup());
                        if (eventGroup.getSourceTrackSettings() == Event.SOURCE_TRACK_SETTINGS_APP) {
                            trackSettings = new TrackSettings(this);
                        } else {
                            trackSettings = eventGroup.getTrackSettings();
                        }
                        break;
                    case Event.SOURCE_TRACK_SETTINGS_EVENT:
                        trackSettings = event.getTrackSettings();
                        break;
                    default:
                        trackSettings = new TrackSettings(this);
                        break;
                }


                // Расчет Круглых дат


                if (trackSettings.getRdInYears() != TrackSettings.NOT_TRACK) {
                    // Расчет Круглых дат - года
                    roundDates = event.getRoundDates(Event.YEAR, trackSettings.getRdInYears());
                    // Запись круглых дат и уведомлений в БД
                    roundDates = adapter.addRoundDates(roundDates);
                    adapter.addNotifyDates(roundDates);
                }

                if (trackSettings.getRdInMonths() != TrackSettings.NOT_TRACK) {
                    // Расчет Круглых дат - месяцы
                    roundDates = event.getRoundDates(Event.MONTH, trackSettings.getRdInMonths());
                    // Запись круглых дат и уведомлений в БД
                    roundDates = adapter.addRoundDates(roundDates);
                    adapter.addNotifyDates(roundDates);
                }

                if (trackSettings.getRdInWeeks() != TrackSettings.NOT_TRACK) {
                    // Расчет Круглых дат - недели
                    roundDates = event.getRoundDates(Event.WEEK, trackSettings.getRdInWeeks());
                    // Запись круглых дат и уведомлений в БД
                    roundDates = adapter.addRoundDates(roundDates);
                    adapter.addNotifyDates(roundDates);
                }

                if (trackSettings.getRdInDays() != TrackSettings.NOT_TRACK) {
                    // Расчет Круглых дат - дни
                    roundDates = event.getRoundDates(Event.DAY, trackSettings.getRdInDays());
                    // Запись круглых дат и уведомлений в БД
                    roundDates = adapter.addRoundDates(roundDates);
                    adapter.addNotifyDates(roundDates);
                }

                if (trackSettings.getRdInHours() != TrackSettings.NOT_TRACK) {
                    // Расчет Круглых дат - часы
                    roundDates = event.getRoundDates(Event.HOUR, trackSettings.getRdInHours());
                    // Запись круглых дат и уведомлений в БД
                    roundDates = adapter.addRoundDates(roundDates);
                    adapter.addNotifyDates(roundDates);
                }

                if (trackSettings.getRdInMinutes() != TrackSettings.NOT_TRACK) {
                    // Расчет Круглых дат - минуты
                    roundDates = event.getRoundDates(Event.MINUTE, trackSettings.getRdInMinutes());
                    // Запись круглых дат и уведомлений в БД
                    roundDates = adapter.addRoundDates(roundDates);
                    adapter.addNotifyDates(roundDates);
                }

                if (trackSettings.getRdInSecs() != TrackSettings.NOT_TRACK) {
                    // Расчет Круглых дат - секунды
                    roundDates = event.getRoundDates(Event.SEC, trackSettings.getRdInSecs());
                    // Запись круглых дат и уведомлений в БД
                    roundDates = adapter.addRoundDates(roundDates);
                    adapter.addNotifyDates(roundDates);
                }

                adapter.setTransactionSuccessful();
            } finally {
                adapter.endTransaction();
            }

            // Закрытие соединения с БД
            adapter.close();

            intent = new Intent();
            intent.setAction(NOTIFICATION_ACTION);
            sendBroadcast(intent);

            setResult(RESULT_OK);
            finish();

        } else { // Если событие редактируется

            // начинаем транзакцию
            adapter.beginTransaction();
            try {

                // Запись группы событий в БД, если выбрана новая группа и название уникально
                if ((getSelectedEventGroup().getId() == -1) && !(adapter.isEventGroupExists(newEventsGroupName, getSelectedEventGroup().getId()))) {
                    EventGroup eventGroup = new EventGroup(-1, newEventsGroupName, 0, new TrackSettings(this));
                    getSelectedEventGroup().setId(adapter.addEventsGroup(eventGroup));
                }

                // Обновление полей события
                getEvent().setName(eventName);
                getEvent().setComment(eventComment);
                getEvent().setIdEventGroup(getSelectedEventGroup().getId());
                getEvent().setNameEventGroup(getSelectedEventGroup().getName());
                getEvent().setDateAndTime(getDate());
                getEvent().setSourceTrackSettings(getSourceTrackSettings());
                getEvent().setTrackSettings(getEventTrackSettings());

                // Обновление события в БД
                adapter.updateEvent(getEvent());

                //определение действующих настроек слежения
                TrackSettings trackSettings;
                switch (getSourceTrackSettings()) {
                    case Event.SOURCE_TRACK_SETTINGS_APP:
                        trackSettings = new TrackSettings(this);
                        break;
                    case Event.SOURCE_TRACK_SETTINGS_GROUP:
                        EventGroup eventGroup = adapter.getEventGroupById(event.getIdEventGroup());
                        if (eventGroup.getSourceTrackSettings() == Event.SOURCE_TRACK_SETTINGS_APP) {
                            trackSettings = new TrackSettings(this);
                        } else {
                            trackSettings = eventGroup.getTrackSettings();
                        }
                        break;
                    case Event.SOURCE_TRACK_SETTINGS_EVENT:
                        trackSettings = event.getTrackSettings();
                        break;
                    default:
                        trackSettings = new TrackSettings(this);
                        break;
                }

                // Проверяем, менялась ли дата/время
                boolean isDateChange;
                if (getOldDateInMillis() == getEvent().getDateAndTime().getTimeInMillis()) {
                    isDateChange = false;
                } else {
                    isDateChange = true;
                }

                List<RoundDate> roundDates;
                // Перерасчет круглых дат в случае если поменялась дата или изменились настройки отслеживания
                if (isDateChange || trackSettings.getRdInYears() != getOldEventTrackSettings().getRdInYears()) {
                    // Удаление из БД текущих круглых дат - года
                    adapter.deleteRoundDates(getEvent().getId(), RoundDate.UNIT_YEARS);
                    if (trackSettings.getRdInYears() != TrackSettings.NOT_TRACK) {
                        // Расчет Круглых дат - года
                        roundDates = getEvent().getRoundDates(Event.YEAR, trackSettings.getRdInYears());
                        // Запись круглых дат и уведомлений в БД
                        roundDates = adapter.addRoundDates(roundDates);
                        adapter.addNotifyDates(roundDates);
                    }
                }

                if (isDateChange || trackSettings.getRdInMonths() != getOldEventTrackSettings().getRdInMonths()) {
                    // Удаление из БД текущих круглых дат - месяцы
                    adapter.deleteRoundDates(getEvent().getId(), RoundDate.UNIT_MONTHS);
                    if (trackSettings.getRdInMonths() != TrackSettings.NOT_TRACK) {
                        // Расчет Круглых дат - месяцы
                        roundDates = getEvent().getRoundDates(Event.MONTH, trackSettings.getRdInMonths());
                        // Запись круглых дат и уведомлений в БД
                        roundDates = adapter.addRoundDates(roundDates);
                        adapter.addNotifyDates(roundDates);
                    }
                }

                if (isDateChange || trackSettings.getRdInWeeks() != getOldEventTrackSettings().getRdInWeeks()) {
                    // Удаление из БД текущих круглых дат - недели
                    adapter.deleteRoundDates(getEvent().getId(), RoundDate.UNIT_WEEKS);
                    if (trackSettings.getRdInWeeks() != TrackSettings.NOT_TRACK) {
                        // Расчет Круглых дат - недели
                        roundDates = getEvent().getRoundDates(Event.WEEK, trackSettings.getRdInWeeks());
                        // Запись круглых дат и уведомлений в БД
                        roundDates = adapter.addRoundDates(roundDates);
                        adapter.addNotifyDates(roundDates);
                    }
                }

                if (isDateChange || trackSettings.getRdInDays() != getOldEventTrackSettings().getRdInDays()) {
                    // Удаление из БД текущих круглых дат - дни
                    adapter.deleteRoundDates(getEvent().getId(), RoundDate.UNIT_DAYS);
                    if (trackSettings.getRdInDays() != TrackSettings.NOT_TRACK) {
                        // Расчет Круглых дат - дни
                        roundDates = getEvent().getRoundDates(Event.DAY, trackSettings.getRdInDays());
                        // Запись круглых дат и уведомлений в БД
                        roundDates = adapter.addRoundDates(roundDates);
                        adapter.addNotifyDates(roundDates);
                    }
                }

                if (isDateChange || trackSettings.getRdInHours() != getOldEventTrackSettings().getRdInHours()) {
                    // Удаление из БД текущих круглых дат - часы
                    adapter.deleteRoundDates(getEvent().getId(), RoundDate.UNIT_HOURS);
                    if (trackSettings.getRdInHours() != TrackSettings.NOT_TRACK) {
                        // Расчет Круглых дат - часы
                        roundDates = getEvent().getRoundDates(Event.HOUR, trackSettings.getRdInHours());
                        // Запись круглых дат и уведомлений в БД
                        roundDates = adapter.addRoundDates(roundDates);
                        adapter.addNotifyDates(roundDates);
                    }
                }

                if (isDateChange || trackSettings.getRdInMinutes() != getOldEventTrackSettings().getRdInMinutes()) {
                    // Удаление из БД текущих круглых дат - минуты
                    adapter.deleteRoundDates(getEvent().getId(), RoundDate.UNIT_MINUTES);
                    if (trackSettings.getRdInMinutes() != TrackSettings.NOT_TRACK) {
                        // Расчет Круглых дат - минуты
                        roundDates = getEvent().getRoundDates(Event.MINUTE, trackSettings.getRdInMinutes());
                        // Запись круглых дат и уведомлений в БД
                        roundDates = adapter.addRoundDates(roundDates);
                        adapter.addNotifyDates(roundDates);
                    }
                }

                if (isDateChange || trackSettings.getRdInSecs() != getOldEventTrackSettings().getRdInSecs()) {
                    // Удаление из БД текущих круглых дат - секунды
                    adapter.deleteRoundDates(getEvent().getId(), RoundDate.UNIT_SECS);
                    if (trackSettings.getRdInSecs() != TrackSettings.NOT_TRACK) {
                        // Расчет Круглых дат - секунды
                        roundDates = getEvent().getRoundDates(Event.SEC, trackSettings.getRdInSecs());
                        // Запись круглых дат и уведомлений в БД
                        roundDates = adapter.addRoundDates(roundDates);
                        adapter.addNotifyDates(roundDates);
                    }
                }

                adapter.setTransactionSuccessful();
            } finally {
                adapter.endTransaction();
            }

            // Закрытие соединения с БД
            adapter.close();

            intent = new Intent();
            intent.setAction(NOTIFICATION_ACTION);
            sendBroadcast(intent);

            setResult(RESULT_OK);
            finish();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // регистрируем ресивер
        IntentFilter filter = new IntentFilter();
        filter.addAction(NOTIFICATION_ACTION);
        filter.addAction("android.intent.action.BOOT_COMPLETED");

        notificationReceiver = new NotificationReceiver();
        registerReceiver(notificationReceiver, filter);

    }

    @Override
    protected void onDestroy() {
        if (notificationReceiver != null) {
            unregisterReceiver(notificationReceiver);
            notificationReceiver = null;
        }
        super.onDestroy();
    }

    public void onChoiceEventDate(View view) {
        //  диалоговое окно выбора даты
        DatePickerDialog dtp = new DatePickerDialog(AddEditEventActivity.this, R.style.TimePickerTheme, d,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH));
        dtp.show();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, monthOfYear);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            tvCurrentDate.setText(DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault()).format(date.getTime()));
        }
    };


    public void onChoiceEventTime(View view) {
        // диалоговое окно выбора времени
        TimePickerDialog ttp = new TimePickerDialog(AddEditEventActivity.this, R.style.TimePickerTheme, t,
                date.get(Calendar.HOUR_OF_DAY),
                date.get(Calendar.MINUTE),
                true);
        ttp.show();
    }

    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            date.set(Calendar.HOUR_OF_DAY, hourOfDay);
            date.set(Calendar.MINUTE, minute);
            tvCurrentTime.setText(DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault()).format(date.getTime()));

        }
    };

    public void onCancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }


    public void onClick(View view) {
        DialogScreen ds;
        AlertDialog dialog;

        switch (view.getId()) {
            case R.id.llRoundDateYear:
                ds = new DialogScreen(DialogScreen.IDD_RD_YEARS);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateMonth:
                ds = new DialogScreen(DialogScreen.IDD_RD_MONTHS);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateWeek:
                ds = new DialogScreen(DialogScreen.IDD_RD_WEEKS);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateDay:
                ds = new DialogScreen(DialogScreen.IDD_RD_DAYS);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateHour:
                ds = new DialogScreen(DialogScreen.IDD_RD_HOURS);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateMinute:
                ds = new DialogScreen(DialogScreen.IDD_RD_MINUTES);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateSec:
                ds = new DialogScreen(DialogScreen.IDD_RD_SECS);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
        }
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

    public void setSourceTrackSettings(int sourceTrackSettings) {
        this.sourceTrackSettings = sourceTrackSettings;
    }

    public int getSourceTrackSettings() {
        return sourceTrackSettings;
    }

    public boolean isNewEvent() {
        return isNewEvent;
    }

    public void setNewEvent(boolean newEvent) {
        isNewEvent = newEvent;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public TrackSettings getOldEventTrackSettings() {
        return oldEventTrackSettings;
    }

    public void setOldEventTrackSettings(TrackSettings oldEventTrackSettings) {
        this.oldEventTrackSettings = oldEventTrackSettings;
    }

    public long getOldDateInMillis() {
        return oldDateInMillis;
    }

    public void setOldDateInMillis(long oldDateInMillis) {
        this.oldDateInMillis = oldDateInMillis;
    }

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
}
