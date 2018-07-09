package ru.alexanderdolinsky.rounddate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.alexanderdolinsky.rounddate.data.Event;
import ru.alexanderdolinsky.rounddate.data.EventGroup;
import ru.alexanderdolinsky.rounddate.db.DatabaseAdapter;
import ru.alexanderdolinsky.rounddate.R;
import ru.alexanderdolinsky.rounddate.data.RoundDate;
import ru.alexanderdolinsky.rounddate.data.TrackSettings;
import ru.alexanderdolinsky.rounddate.dialogs.DialogScreen;

public class EditEventGroupActivity extends AppCompatActivity {

    static final String EVENTS_GROUP_ID = "events_group_id";
    private EventGroup eventGroup;
    private long eventGroupId;
    private TextView tvYears, tvMonths, tvWeeks, tvDays, tvHours, tvMinutes, tvSecs;
    private TrackSettings eventGroupTrackSettings, oldEventGroupTrackSettings;
    private int sourceTrackSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event_group);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            setEventGroupId(extras.getLong(EVENTS_GROUP_ID));
        } else {
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
            finish();
        }

        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();
        setEventGroup(adapter.getEventGroupById(getEventGroupId()));
        adapter.close();

        EditText etEventGroupName = (EditText) findViewById(R.id.etEventsGroupName);
        etEventGroupName.setText(getEventGroup().getName());

        // Определяем начальные настройки слежения
        TrackSettings trackSettings;
        switch (getEventGroup().getSourceTrackSettings()) {
            case EventGroup.SOURCE_TRACK_SETTINGS_APP:
                trackSettings = new TrackSettings(this);
                break;
            case EventGroup.SOURCE_TRACK_SETTINGS_GROUP:
                trackSettings = eventGroup.getTrackSettings();
                break;
            default:
                trackSettings = new TrackSettings(this);
                break;
        }

        // Сохраняем начальные настройки отслеживания для последующего сравнения
        setOldEventGroupTrackSettings(new TrackSettings(
                trackSettings.getRdInYears(),
                trackSettings.getRdInMonths(),
                trackSettings.getRdInWeeks(),
                trackSettings.getRdInDays(),
                trackSettings.getRdInHours(),
                trackSettings.getRdInMinutes(),
                trackSettings.getRdInSecs()));

        RadioGroup rgTrackSettings = (RadioGroup) findViewById(R.id.rgTracksettings);


        // Установка радиобаттона в соответствующее положение
        setSourceTrackSettings(getEventGroup().getSourceTrackSettings());
        LinearLayout llTrackSettings = (LinearLayout) findViewById(R.id.llTrackSettings);
        switch (getEventGroup().getSourceTrackSettings()) {
            case EventGroup.SOURCE_TRACK_SETTINGS_GROUP:
                rgTrackSettings.check(R.id.rbUseEventsGroupSettings);
                llTrackSettings.setVisibility(View.VISIBLE);
                break;
            case EventGroup.SOURCE_TRACK_SETTINGS_APP:
                rgTrackSettings.check(R.id.rbUseAppSettings);
                llTrackSettings.setVisibility(View.GONE);
                break;
        }


        tvYears = (TextView) findViewById(R.id.tvRoundDateInYears);
        tvMonths = (TextView) findViewById(R.id.tvRoundDateInMonths);
        tvWeeks = (TextView) findViewById(R.id.tvRoundDateInWeeks);
        tvDays = (TextView) findViewById(R.id.tvRoundDateInDays);
        tvHours = (TextView) findViewById(R.id.tvRoundDateInHours);
        tvMinutes = (TextView) findViewById(R.id.tvRoundDateInMinutes);
        tvSecs = (TextView) findViewById(R.id.tvRoundDateInSecs);

        final CharSequence[] rdVariants = getResources().getStringArray(R.array.rd_variants);

        // Установка настроек из группы событий
        setEventGroupTrackSettings(getEventGroup().getTrackSettings());

        // Устанавливаем соответствующие настройкам тексты в полях настроек отслеживания
        tvYears.setText(rdVariants[getEventGroupTrackSettings().getRdInYears()]);
        tvMonths.setText(rdVariants[getEventGroupTrackSettings().getRdInMonths()]);
        tvWeeks.setText(rdVariants[getEventGroupTrackSettings().getRdInWeeks()]);
        tvDays.setText(rdVariants[getEventGroupTrackSettings().getRdInDays()]);
        tvHours.setText(rdVariants[getEventGroupTrackSettings().getRdInHours()]);
        tvMinutes.setText(rdVariants[getEventGroupTrackSettings().getRdInMinutes()]);
        tvSecs.setText(rdVariants[getEventGroupTrackSettings().getRdInSecs()]);

        // Устанавливаем обработчик на группу радиобаттонов
        rgTrackSettings.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                LinearLayout llTrackSettings = (LinearLayout) findViewById(R.id.llTrackSettings);
                ScrollView sv = (ScrollView) findViewById(R.id.svEditEventGroup);
                switch (checkedId) {
                    case R.id.rbUseEventsGroupSettings:
                        llTrackSettings.setVisibility(View.VISIBLE);
                        setSourceTrackSettings(EventGroup.SOURCE_TRACK_SETTINGS_GROUP);
                        break;
                    case R.id.rbUseAppSettings:
                        llTrackSettings.setVisibility(View.GONE);
                        setSourceTrackSettings(EventGroup.SOURCE_TRACK_SETTINGS_APP);
                        break;
                }
            }
        });
    }

    public void onSaveEventsGroup(View view) {
        //  Проверка правильности ввода названия события
        EditText etEventGroupName = (EditText) findViewById(R.id.etEventsGroupName);
        String eventGroupName = etEventGroupName.getText().toString().trim();
        if (eventGroupName.isEmpty()) {
            Toast.makeText(this, R.string.events_group_name_not_fill, Toast.LENGTH_SHORT).show();
            return;
        }

        // Связь с БД и ее открытие
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();

        // Проверка на уникальность названия группы событий
        if (adapter.isEventGroupExists(eventGroupName, getEventGroup().getId())) {
            Toast.makeText(this, R.string.events_group_is_exists, Toast.LENGTH_SHORT).show();
            return;
        }

        // начинаем транзакцию
        adapter.beginTransaction();
        try {

            // Обновление группы событий в БД
            getEventGroup().setName(eventGroupName);
            getEventGroup().setSourceTrackSettings(getSourceTrackSettings());
            getEventGroup().setTrackSettings(getEventGroupTrackSettings());

            adapter.updateEventGroup(getEventGroup());


            //определение действующих настроек слежения
            TrackSettings trackSettings;
            switch (getSourceTrackSettings()) {
                case Event.SOURCE_TRACK_SETTINGS_APP:
                    trackSettings = new TrackSettings(this);
                    break;
                case Event.SOURCE_TRACK_SETTINGS_GROUP:
                    trackSettings = eventGroup.getTrackSettings();
                    break;
                default:
                    trackSettings = new TrackSettings(this);
                    break;
            }


            // Получаем список событий данной группы, исключая события со своими настройками или настройками приложения
            List<Event> events = adapter.getEventsByEventGroupIdAndGroupTrackSettings(getEventGroup().getId(), Event.SOURCE_TRACK_SETTINGS_GROUP);

            List<RoundDate> roundDates;

            // Перерасчет круглых дат в случае если изменились настройки отслеживания

            if (trackSettings.getRdInYears() != getOldEventGroupTrackSettings().getRdInYears()) {
                for (Event event : events) {
                    // Удаление из БД текущих круглых дат - года
                    adapter.deleteRoundDates(event.getId(), RoundDate.UNIT_YEARS);
                    if (trackSettings.getRdInYears() != TrackSettings.NOT_TRACK) {
                        // Расчет Круглых дат - года
                        roundDates = event.getRoundDates(Event.YEAR, trackSettings.getRdInYears());
                        // Запись круглых дат и уведомлений в БД
                        roundDates = adapter.addRoundDates(roundDates);
                        adapter.addNotifyDates(roundDates);
                    }
                }
            }

            if (trackSettings.getRdInMonths() != getOldEventGroupTrackSettings().getRdInMonths()) {
                for (Event event : events) {
                    // Удаление из БД текущих круглых дат - месяцы
                    adapter.deleteRoundDates(event.getId(), RoundDate.UNIT_MONTHS);
                    if (trackSettings.getRdInMonths() != TrackSettings.NOT_TRACK) {
                        // Расчет Круглых дат - месяцы
                        roundDates = event.getRoundDates(Event.MONTH, trackSettings.getRdInMonths());
                        // Запись круглых дат и уведомлений в БД
                        roundDates = adapter.addRoundDates(roundDates);
                        adapter.addNotifyDates(roundDates);
                    }
                }
            }

            if (trackSettings.getRdInWeeks() != getOldEventGroupTrackSettings().getRdInWeeks()) {
                for (Event event : events) {
                    // Удаление из БД текущих круглых дат - недели
                    adapter.deleteRoundDates(event.getId(), RoundDate.UNIT_WEEKS);
                    if (trackSettings.getRdInWeeks() != TrackSettings.NOT_TRACK) {
                        // Расчет Круглых дат - недели
                        roundDates = event.getRoundDates(Event.WEEK, trackSettings.getRdInWeeks());
                        // Запись круглых дат и уведомлений в БД
                        roundDates = adapter.addRoundDates(roundDates);
                        adapter.addNotifyDates(roundDates);
                    }
                }
            }

            if (trackSettings.getRdInDays() != getOldEventGroupTrackSettings().getRdInDays()) {
                for (Event event : events) {
                    // Удаление из БД текущих круглых дат - дни
                    adapter.deleteRoundDates(event.getId(), RoundDate.UNIT_DAYS);
                    if (trackSettings.getRdInDays() != TrackSettings.NOT_TRACK) {
                        // Расчет Круглых дат - дни
                        roundDates = event.getRoundDates(Event.DAY, trackSettings.getRdInDays());
                        // Запись круглых дат и уведомлений в БД
                        roundDates = adapter.addRoundDates(roundDates);
                        adapter.addNotifyDates(roundDates);
                    }
                }
            }

            if (trackSettings.getRdInHours() != getOldEventGroupTrackSettings().getRdInHours()) {
                for (Event event : events) {
                    // Удаление из БД текущих круглых дат - часы
                    adapter.deleteRoundDates(event.getId(), RoundDate.UNIT_HOURS);
                    if (trackSettings.getRdInHours() != TrackSettings.NOT_TRACK) {
                        // Расчет Круглых дат - часы
                        roundDates = event.getRoundDates(Event.HOUR, trackSettings.getRdInHours());
                        // Запись круглых дат и уведомлений в БД
                        roundDates = adapter.addRoundDates(roundDates);
                        adapter.addNotifyDates(roundDates);
                    }
                }
            }

            if (trackSettings.getRdInMinutes() != getOldEventGroupTrackSettings().getRdInMinutes()) {
                for (Event event : events) {
                    // Удаление из БД текущих круглых дат - минуты
                    adapter.deleteRoundDates(event.getId(), RoundDate.UNIT_MINUTES);
                    if (trackSettings.getRdInMinutes() != TrackSettings.NOT_TRACK) {
                        // Расчет Круглых дат - минуты
                        roundDates = event.getRoundDates(Event.MINUTE, trackSettings.getRdInMinutes());
                        // Запись круглых дат и уведомлений в БД
                        roundDates = adapter.addRoundDates(roundDates);
                        adapter.addNotifyDates(roundDates);
                    }
                }
            }

            if (trackSettings.getRdInSecs() != getOldEventGroupTrackSettings().getRdInSecs()) {
                for (Event event : events) {
                    // Удаление из БД текущих круглых дат - секунды
                    adapter.deleteRoundDates(event.getId(), RoundDate.UNIT_SECS);
                    if (trackSettings.getRdInSecs() != TrackSettings.NOT_TRACK) {
                        // Расчет Круглых дат - секунды
                        roundDates = event.getRoundDates(Event.SEC, trackSettings.getRdInSecs());
                        // Запись круглых дат и уведомлений в БД
                        roundDates = adapter.addRoundDates(roundDates);
                        adapter.addNotifyDates(roundDates);
                    }
                }
            }

            adapter.setTransactionSuccessful();
        } finally {
            adapter.endTransaction();
        }

        // Закрытие соединения с БД
        adapter.close();

        Intent intent = new Intent();
        intent.setAction(AddEditEventActivity.NOTIFICATION_ACTION);
        sendBroadcast(intent);

        setResult(RESULT_OK);
        finish();
    }

    public void onCancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onClick(View view) {
        DialogScreen ds;
        AlertDialog dialog;

        switch (view.getId()) {
            case R.id.llRoundDateYear:
                ds = new DialogScreen(DialogScreen.IDD_RD_YEARS_2);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateMonth:
                ds = new DialogScreen(DialogScreen.IDD_RD_MONTHS_2);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateWeek:
                ds = new DialogScreen(DialogScreen.IDD_RD_WEEKS_2);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateDay:
                ds = new DialogScreen(DialogScreen.IDD_RD_DAYS_2);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateHour:
                ds = new DialogScreen(DialogScreen.IDD_RD_HOURS_2);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateMinute:
                ds = new DialogScreen(DialogScreen.IDD_RD_MINUTES_2);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateSec:
                ds = new DialogScreen(DialogScreen.IDD_RD_SECS_2);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
        }
    }

    public EventGroup getEventGroup() {
        return eventGroup;
    }

    public void setEventGroup(EventGroup eventGroup) {
        this.eventGroup = eventGroup;
    }

    public long getEventGroupId() {
        return eventGroupId;
    }

    public void setEventGroupId(long eventGroupId) {
        this.eventGroupId = eventGroupId;
    }

    public TrackSettings getEventGroupTrackSettings() {
        return eventGroupTrackSettings;
    }

    public void setEventGroupTrackSettings(TrackSettings eventGroupTrackSettings) {
        this.eventGroupTrackSettings = eventGroupTrackSettings;
    }

    public TrackSettings getOldEventGroupTrackSettings() {
        return oldEventGroupTrackSettings;
    }

    public void setOldEventGroupTrackSettings(TrackSettings oldEventGroupTrackSettings) {
        this.oldEventGroupTrackSettings = oldEventGroupTrackSettings;
    }

    public int getSourceTrackSettings() {
        return sourceTrackSettings;
    }

    public void setSourceTrackSettings(int sourceTrackSettings) {
        this.sourceTrackSettings = sourceTrackSettings;
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
