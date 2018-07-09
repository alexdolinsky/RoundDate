package ru.alexanderdolinsky.rounddate.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import ru.alexanderdolinsky.rounddate.R;
import ru.alexanderdolinsky.rounddate.activities.AddEditEventActivity;
import ru.alexanderdolinsky.rounddate.activities.EditEventGroupActivity;
import ru.alexanderdolinsky.rounddate.activities.EventActivity;
import ru.alexanderdolinsky.rounddate.activities.EventListActivity;
import ru.alexanderdolinsky.rounddate.activities.MainActivity;
import ru.alexanderdolinsky.rounddate.activities.RoundDateListActivity;
import ru.alexanderdolinsky.rounddate.activities.SettingsActivity;
import ru.alexanderdolinsky.rounddate.adapters.EventAdapter;
import ru.alexanderdolinsky.rounddate.db.DatabaseAdapter;

/**
 * Created by Alexsvet on 08.06.2017.
 * Диалоговые окна
 */

public class DialogScreen {

    public final static int IDD_RD_YEARS = 0;
    public final static int IDD_RD_MONTHS = 1;
    public final static int IDD_RD_WEEKS = 2;
    public final static int IDD_RD_DAYS = 3;
    public final static int IDD_RD_HOURS = 4;
    public final static int IDD_RD_MINUTES = 5;
    public final static int IDD_RD_SECS = 6;
    public final static int IDD_RD_YEARS_2 = 10;
    public final static int IDD_RD_MONTHS_2 = 11;
    public final static int IDD_RD_WEEKS_2 = 12;
    public final static int IDD_RD_DAYS_2 = 13;
    public final static int IDD_RD_HOURS_2 = 14;
    public final static int IDD_RD_MINUTES_2 = 15;
    public final static int IDD_RD_SECS_2 = 16;
    public final static int IDD_RD_YEARS_3 = 20;
    public final static int IDD_RD_MONTHS_3 = 21;
    public final static int IDD_RD_WEEKS_3 = 22;
    public final static int IDD_RD_DAYS_3 = 23;
    public final static int IDD_RD_HOURS_3 = 24;
    public final static int IDD_RD_MINUTES_3 = 25;
    public final static int IDD_RD_SECS_3 = 26;

    public final static int IDD_CHOICE_EVENT_GROUP = 30;
    public final static int DELETE_EVENT_CONFIRM = 40;
    public final static int DELETE_EVENTS_GROUP_CONFIRM = 41;
    public final static int IDD_CHOICE_IMPORTANT = 42;
    public final static int DELETE_EVENT_CONFIRM_2 = 43;
    public final static int DELETE_EVENTS_GROUP_CONFIRM_2 = 44;
    public final static int IDD_CHOICE_IMPORTANT_2 = 45;
    public final static int IDD_VERY_IMPORTANT_ROUNDDATE = 46;
    public final static int IDD_IMPORTANT_ROUNDDATE = 47;
    public final static int IDD_STANDART_ROUNDDATE = 48;
    public final static int IDD_SMALL_IMPORTANT_ROUNDDATE = 49;


    private final int IDD_dialog;


    public DialogScreen(int ID) {
        this.IDD_dialog = ID;
    }

    public AlertDialog getDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        final CharSequence[] rdVariants = activity.getResources().getStringArray(R.array.rd_variants);
        final CharSequence[] importantVariants = activity.getResources().getStringArray(R.array.important_variants);
        final CharSequence[] notificationsVariants = {activity.getString(R.string.one_day), activity.getString(R.string.one_week), activity.getString(R.string.one_month)};
        final boolean[] checkedItemsArray = {false, false, false};

        final AddEditEventActivity aeActivity;
        final EventListActivity elActivity;
        final EventActivity eActivity;
        final EditEventGroupActivity eegActivity;
        final SettingsActivity sActivity;
        final MainActivity mActivity;
        final RoundDateListActivity rdlActivity;


        switch (getIDD_dialog()) {

            // диалоговые окна настроек отслеживания для события

            case IDD_RD_YEARS:
                aeActivity = (AddEditEventActivity) activity;
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                aeActivity.setTvYears(rdVariants[aeActivity.getEventTrackSettings().getRdInYears()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, aeActivity.getEventTrackSettings().getRdInYears(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                aeActivity.getEventTrackSettings().setRdInYears(which);
                            }
                        });
                return builder.create();


            case IDD_RD_MONTHS:
                aeActivity = (AddEditEventActivity) activity;
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                aeActivity.setTvMonths(rdVariants[aeActivity.getEventTrackSettings().getRdInMonths()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, aeActivity.getEventTrackSettings().getRdInMonths(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                aeActivity.getEventTrackSettings().setRdInMonths(which);
                            }
                        });
                return builder.create();


            case IDD_RD_WEEKS:
                aeActivity = (AddEditEventActivity) activity;
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                aeActivity.setTvWeeks(rdVariants[aeActivity.getEventTrackSettings().getRdInWeeks()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, aeActivity.getEventTrackSettings().getRdInWeeks(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                aeActivity.getEventTrackSettings().setRdInWeeks(which);
                            }
                        });
                return builder.create();


            case IDD_RD_DAYS:
                aeActivity = (AddEditEventActivity) activity;
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                aeActivity.setTvDays(rdVariants[aeActivity.getEventTrackSettings().getRdInDays()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, aeActivity.getEventTrackSettings().getRdInDays(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                aeActivity.getEventTrackSettings().setRdInDays(which);
                            }
                        });
                return builder.create();


            case IDD_RD_HOURS:
                aeActivity = (AddEditEventActivity) activity;
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                aeActivity.setTvHours(rdVariants[aeActivity.getEventTrackSettings().getRdInHours()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, aeActivity.getEventTrackSettings().getRdInHours(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                aeActivity.getEventTrackSettings().setRdInHours(which);
                            }
                        });
                return builder.create();

            case IDD_RD_MINUTES:
                aeActivity = (AddEditEventActivity) activity;
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                aeActivity.setTvMinutes(rdVariants[aeActivity.getEventTrackSettings().getRdInMinutes()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, aeActivity.getEventTrackSettings().getRdInMinutes(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                aeActivity.getEventTrackSettings().setRdInMinutes(which);
                            }
                        });
                return builder.create();


            case IDD_RD_SECS:
                aeActivity = (AddEditEventActivity) activity;
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                aeActivity.setTvSecs(rdVariants[aeActivity.getEventTrackSettings().getRdInSecs()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, aeActivity.getEventTrackSettings().getRdInSecs(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                aeActivity.getEventTrackSettings().setRdInSecs(which);
                            }
                        });
                return builder.create();

            // диалоговые окна настроек отслеживания для групп событий

            case IDD_RD_YEARS_2:
                eegActivity = (EditEventGroupActivity) activity;
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eegActivity.setTvYears(rdVariants[eegActivity.getEventGroupTrackSettings().getRdInYears()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, eegActivity.getEventGroupTrackSettings().getRdInYears(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eegActivity.getEventGroupTrackSettings().setRdInYears(which);
                            }
                        });
                return builder.create();


            case IDD_RD_MONTHS_2:
                eegActivity = (EditEventGroupActivity) activity;
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eegActivity.setTvMonths(rdVariants[eegActivity.getEventGroupTrackSettings().getRdInMonths()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, eegActivity.getEventGroupTrackSettings().getRdInMonths(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eegActivity.getEventGroupTrackSettings().setRdInMonths(which);
                            }
                        });
                return builder.create();


            case IDD_RD_WEEKS_2:
                eegActivity = (EditEventGroupActivity) activity;
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eegActivity.setTvWeeks(rdVariants[eegActivity.getEventGroupTrackSettings().getRdInWeeks()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, eegActivity.getEventGroupTrackSettings().getRdInWeeks(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eegActivity.getEventGroupTrackSettings().setRdInWeeks(which);
                            }
                        });
                return builder.create();


            case IDD_RD_DAYS_2:
                eegActivity = (EditEventGroupActivity) activity;
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eegActivity.setTvDays(rdVariants[eegActivity.getEventGroupTrackSettings().getRdInDays()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, eegActivity.getEventGroupTrackSettings().getRdInDays(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eegActivity.getEventGroupTrackSettings().setRdInDays(which);
                            }
                        });
                return builder.create();


            case IDD_RD_HOURS_2:
                eegActivity = (EditEventGroupActivity) activity;
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eegActivity.setTvHours(rdVariants[eegActivity.getEventGroupTrackSettings().getRdInHours()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, eegActivity.getEventGroupTrackSettings().getRdInHours(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eegActivity.getEventGroupTrackSettings().setRdInHours(which);
                            }
                        });
                return builder.create();

            case IDD_RD_MINUTES_2:
                eegActivity = (EditEventGroupActivity) activity;
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eegActivity.setTvMinutes(rdVariants[eegActivity.getEventGroupTrackSettings().getRdInMinutes()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, eegActivity.getEventGroupTrackSettings().getRdInMinutes(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eegActivity.getEventGroupTrackSettings().setRdInMinutes(which);
                            }
                        });
                return builder.create();


            case IDD_RD_SECS_2:
                eegActivity = (EditEventGroupActivity) activity;
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eegActivity.setTvSecs(rdVariants[eegActivity.getEventGroupTrackSettings().getRdInSecs()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, eegActivity.getEventGroupTrackSettings().getRdInSecs(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eegActivity.getEventGroupTrackSettings().setRdInSecs(which);
                            }
                        });
                return builder.create();

            // диалоговые окна настроек отслеживания для общих настроек
            case IDD_RD_YEARS_3:
                sActivity = (SettingsActivity) activity;
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sActivity.setTvYears(rdVariants[sActivity.getTrackSettings().getRdInYears()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, sActivity.getTrackSettings().getRdInYears(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sActivity.getTrackSettings().setRdInYears(which);
                            }
                        });
                return builder.create();


            case IDD_RD_MONTHS_3:
                sActivity = (SettingsActivity) activity;
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sActivity.setTvMonths(rdVariants[sActivity.getTrackSettings().getRdInMonths()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, sActivity.getTrackSettings().getRdInMonths(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sActivity.getTrackSettings().setRdInMonths(which);
                            }
                        });
                return builder.create();


            case IDD_RD_WEEKS_3:
                sActivity = (SettingsActivity) activity;
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sActivity.setTvWeeks(rdVariants[sActivity.getTrackSettings().getRdInWeeks()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, sActivity.getTrackSettings().getRdInWeeks(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sActivity.getTrackSettings().setRdInWeeks(which);
                            }
                        });
                return builder.create();


            case IDD_RD_DAYS_3:
                sActivity = (SettingsActivity) activity;
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sActivity.setTvDays(rdVariants[sActivity.getTrackSettings().getRdInDays()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, sActivity.getTrackSettings().getRdInDays(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sActivity.getTrackSettings().setRdInDays(which);
                            }
                        });
                return builder.create();


            case IDD_RD_HOURS_3:
                sActivity = (SettingsActivity) activity;
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sActivity.setTvHours(rdVariants[sActivity.getTrackSettings().getRdInHours()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, sActivity.getTrackSettings().getRdInHours(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sActivity.getTrackSettings().setRdInHours(which);
                            }
                        });
                return builder.create();

            case IDD_RD_MINUTES_3:
                sActivity = (SettingsActivity) activity;
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sActivity.setTvMinutes(rdVariants[sActivity.getTrackSettings().getRdInMinutes()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, sActivity.getTrackSettings().getRdInMinutes(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sActivity.getTrackSettings().setRdInMinutes(which);
                            }
                        });
                return builder.create();


            case IDD_RD_SECS_3:
                sActivity = (SettingsActivity) activity;
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sActivity.setTvSecs(rdVariants[sActivity.getTrackSettings().getRdInSecs()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, sActivity.getTrackSettings().getRdInSecs(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sActivity.getTrackSettings().setRdInSecs(which);
                            }
                        });
                return builder.create();

            // Диалоговое окно выбора группы событий в EventListActivity

            case IDD_CHOICE_EVENT_GROUP:
                elActivity = (EventListActivity) activity;
                builder.setTitle(R.string.choice_event_group)
                        .setCancelable(true)
                        .setItems(elActivity.getEventsGroupName(), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                elActivity.setSelectedEventsGroupId(which);

                                DatabaseAdapter adapter = new DatabaseAdapter(elActivity);
                                adapter.open();

                                // устанавливаем имя выбранной группы событий
                                TextView tvGroupName = (TextView) elActivity.findViewById(R.id.tvEventsGroup);
                                tvGroupName.setText(elActivity.getEventsGroupName()[which]);
                                ImageButton btnEditEventsGroup = (ImageButton) elActivity.findViewById(R.id.btnEditEventsGroup);
                                ImageButton btnDeleteEventsGroup = (ImageButton) elActivity.findViewById(R.id.btnDeleteEventsGroup);
                                // получаем список событий по ID группы событий или же все события, если выбраны все события
                                if (which == 0) {
                                    elActivity.setEvents(adapter.getAllEvents());
                                    btnEditEventsGroup.setVisibility(View.GONE);
                                    btnDeleteEventsGroup.setVisibility(View.GONE);
                                } else {
                                    elActivity.setEvents(adapter.getEventsByEventGroupId((elActivity.getEventsGroup().get(which).getId())));
                                    btnEditEventsGroup.setVisibility(View.VISIBLE);
                                    if (which != 1) {
                                        btnDeleteEventsGroup.setVisibility(View.VISIBLE);
                                    } else {
                                        btnDeleteEventsGroup.setVisibility(View.GONE);
                                    }
                                }

                                // выводим события в список
                                elActivity.eventsList = (ListView) elActivity.findViewById(R.id.lvEvents);
                                EventAdapter eventAdapter = new EventAdapter(elActivity, R.layout.event_item, elActivity.getEvents());
                                elActivity.eventsList.setAdapter(eventAdapter);
                                adapter.close();
                            }
                        });
                return builder.create();

            // Диалоговое окно подтверждения удаления события из EventActivity

            case DELETE_EVENT_CONFIRM:
                eActivity = (EventActivity) activity;
                builder.setCancelable(false)
                        .setTitle(R.string.attention)
                        .setMessage(R.string.do_you_want_to_remove_the_event)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                // Связь с БД и ее открытие
                                DatabaseAdapter adapter = new DatabaseAdapter(eActivity);
                                adapter.open();
                                adapter.deleteEvent(eActivity.getEvent().getId());
                                adapter.close();

                                Intent intent = new Intent();
                                intent.setAction(AddEditEventActivity.NOTIFICATION_ACTION);
                                eActivity.sendBroadcast(intent);
                                eActivity.setResult(Activity.RESULT_OK);
                                eActivity.finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                return builder.create();

            // Диалоговое окно подтверждения удаления события из EventListActivity

            case DELETE_EVENT_CONFIRM_2:
                elActivity = (EventListActivity) activity;
                builder.setCancelable(false)
                        .setTitle(R.string.attention)
                        .setMessage(R.string.do_you_want_to_remove_the_event)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                // Связь с БД и ее открытие
                                DatabaseAdapter adapter = new DatabaseAdapter(elActivity);
                                adapter.open();
                                // удаление события
                                adapter.deleteEvent(elActivity.getSelectedEvent().getId());
                                // закрытие соединения с БД
                                adapter.close();

                                Intent intent = new Intent();
                                intent.setAction(AddEditEventActivity.NOTIFICATION_ACTION);
                                elActivity.sendBroadcast(intent);

                                // пересоздание списка событий
                                elActivity.recreate();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                return builder.create();

            // Диалоговое окно подтверждения удаления группы событий из EventListActivity по кнопке

            case DELETE_EVENTS_GROUP_CONFIRM:
                elActivity = (EventListActivity) activity;
                builder.setCancelable(false)
                        .setTitle(R.string.attention)
                        .setMessage(R.string.do_you_want_to_remove_the_events_group)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                // Связь с БД и ее открытие
                                DatabaseAdapter adapter = new DatabaseAdapter(elActivity);
                                adapter.open();
                                adapter.deleteEventsGroup(elActivity.getEventsGroup().get((int) elActivity.getSelectedEventsGroupId()).getId());
                                adapter.close();

                                Intent intent = new Intent();
                                intent.setAction(AddEditEventActivity.NOTIFICATION_ACTION);
                                elActivity.sendBroadcast(intent);

                                elActivity.recreate();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                return builder.create();

            // Диалоговое окно подтверждения удаления группы событий из EventListActivity из контекстного меню

            case DELETE_EVENTS_GROUP_CONFIRM_2:
                elActivity = (EventListActivity) activity;
                builder.setCancelable(false)
                        .setTitle(R.string.attention)
                        .setMessage(R.string.do_you_want_to_remove_the_events_group)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                // Связь с БД и ее открытие
                                DatabaseAdapter adapter = new DatabaseAdapter(elActivity);
                                adapter.open();
                                adapter.deleteEventsGroup(elActivity.getSelectedEvent().getIdEventGroup());
                                adapter.close();
                                Intent intent = new Intent();
                                intent.setAction(AddEditEventActivity.NOTIFICATION_ACTION);
                                elActivity.sendBroadcast(intent);
                                elActivity.recreate();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                return builder.create();

            // Диалоговое окно выбора важности круглой даты в MainActivity

            case IDD_CHOICE_IMPORTANT:
                mActivity = (MainActivity) activity;
                builder.setTitle(R.string.choice_importan)
                        .setCancelable(true)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseAdapter adapter = new DatabaseAdapter(mActivity);
                                adapter.open();
                                adapter.updateRoundDateImportant(mActivity.getSelectedRoundDate().getId(), mActivity.getSelectedRoundDate().getImportant());
                                // обновляем записи уведомления для данной круглой даты
                                adapter.updateNotifyDate(mActivity.getSelectedRoundDate());
                                adapter.close();
                                Intent intent = new Intent();
                                intent.setAction(AddEditEventActivity.NOTIFICATION_ACTION);
                                mActivity.sendBroadcast(intent);
                                mActivity.roundDateAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(importantVariants, mActivity.getSelectedRoundDate().getImportant(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mActivity.getSelectedRoundDate().setImportant(which);
                            }
                        });
                return builder.create();

            // Диалоговое окно выбора важности круглой даты в RoundDateListActivity

            case IDD_CHOICE_IMPORTANT_2:
                rdlActivity = (RoundDateListActivity) activity;
                builder.setTitle(R.string.choice_importan)
                        .setCancelable(true)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseAdapter adapter = new DatabaseAdapter(rdlActivity);
                                adapter.open();
                                adapter.updateRoundDateImportant(rdlActivity.getSelectedRoundDate().getId(), rdlActivity.getSelectedRoundDate().getImportant());
                                // обновляем записи уведомления для данной круглой даты
                                adapter.updateNotifyDate(rdlActivity.getSelectedRoundDate());
                                adapter.close();
                                Intent intent = new Intent();
                                intent.setAction(AddEditEventActivity.NOTIFICATION_ACTION);
                                rdlActivity.sendBroadcast(intent);
                                rdlActivity.roundDateAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(importantVariants, rdlActivity.getSelectedRoundDate().getImportant(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                rdlActivity.getSelectedRoundDate().setImportant(which);
                            }
                        });
                return builder.create();

            // Диалоговое окно задания настроек уведомления для очень важных дат

            case IDD_VERY_IMPORTANT_ROUNDDATE:
                sActivity = (SettingsActivity) activity;
                if (sActivity.getNotifySettings().getVeryImportantRdDay() == 1) {
                    checkedItemsArray[0] = true;
                } else {
                    checkedItemsArray[0] = false;
                }
                if (sActivity.getNotifySettings().getVeryImportantRdWeek() == 1) {
                    checkedItemsArray[1] = true;
                } else {
                    checkedItemsArray[1] = false;
                }
                if (sActivity.getNotifySettings().getVeryImportantRdMonth() == 1) {
                    checkedItemsArray[2] = true;
                } else {
                    checkedItemsArray[2] = false;
                }

                builder.setTitle(R.string.notice_for)
                        .setCancelable(true)
                        .setMultiChoiceItems(notificationsVariants, checkedItemsArray, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                checkedItemsArray[which] = isChecked;
                            }
                        })
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (checkedItemsArray[0]) {
                                    sActivity.getNotifySettings().setVeryImportantRdDay(1);
                                } else {
                                    sActivity.getNotifySettings().setVeryImportantRdDay(0);
                                }
                                if (checkedItemsArray[1]) {
                                    sActivity.getNotifySettings().setVeryImportantRdWeek(1);
                                } else {
                                    sActivity.getNotifySettings().setVeryImportantRdWeek(0);
                                }
                                if (checkedItemsArray[2]) {
                                    sActivity.getNotifySettings().setVeryImportantRdMonth(1);
                                } else {
                                    sActivity.getNotifySettings().setVeryImportantRdMonth(0);
                                }
                                sActivity.setTvVeryImportantRd(sActivity.getNotificationsVariants(sActivity.getNotifySettings().getVeryImportantRdDay(),
                                        sActivity.getNotifySettings().getVeryImportantRdWeek(),
                                        sActivity.getNotifySettings().getVeryImportantRdMonth()));
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Отмена",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });

                return builder.create();

            // Диалоговое окно задания настроек уведомления для важных дат

            case IDD_IMPORTANT_ROUNDDATE:
                sActivity = (SettingsActivity) activity;
                if (sActivity.getNotifySettings().getImportantRdDay() == 1) {
                    checkedItemsArray[0] = true;
                } else {
                    checkedItemsArray[0] = false;
                }
                if (sActivity.getNotifySettings().getImportantRdWeek() == 1) {
                    checkedItemsArray[1] = true;
                } else {
                    checkedItemsArray[1] = false;
                }
                if (sActivity.getNotifySettings().getImportantRdMonth() == 1) {
                    checkedItemsArray[2] = true;
                } else {
                    checkedItemsArray[2] = false;
                }

                builder.setTitle(R.string.notice_for)
                        .setCancelable(true)
                        .setMultiChoiceItems(notificationsVariants, checkedItemsArray, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                checkedItemsArray[which] = isChecked;
                            }
                        })
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (checkedItemsArray[0]) {
                                    sActivity.getNotifySettings().setImportantRdDay(1);
                                } else {
                                    sActivity.getNotifySettings().setImportantRdDay(0);
                                }
                                if (checkedItemsArray[1]) {
                                    sActivity.getNotifySettings().setImportantRdWeek(1);
                                } else {
                                    sActivity.getNotifySettings().setImportantRdWeek(0);
                                }
                                if (checkedItemsArray[2]) {
                                    sActivity.getNotifySettings().setImportantRdMonth(1);
                                } else {
                                    sActivity.getNotifySettings().setImportantRdMonth(0);
                                }
                                sActivity.setTvImportantRd(sActivity.getNotificationsVariants(sActivity.getNotifySettings().getImportantRdDay(),
                                        sActivity.getNotifySettings().getImportantRdWeek(),
                                        sActivity.getNotifySettings().getImportantRdMonth()));
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Отмена",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });

                return builder.create();

            // Диалоговое окно задания настроек уведомления для стандартных дат

            case IDD_STANDART_ROUNDDATE:
                sActivity = (SettingsActivity) activity;
                if (sActivity.getNotifySettings().getStandartRdDay() == 1) {
                    checkedItemsArray[0] = true;
                } else {
                    checkedItemsArray[0] = false;
                }
                if (sActivity.getNotifySettings().getStandartRdWeek() == 1) {
                    checkedItemsArray[1] = true;
                } else {
                    checkedItemsArray[1] = false;
                }
                if (sActivity.getNotifySettings().getStandartRdMonth() == 1) {
                    checkedItemsArray[2] = true;
                } else {
                    checkedItemsArray[2] = false;
                }

                builder.setTitle(R.string.notice_for)
                        .setCancelable(true)
                        .setMultiChoiceItems(notificationsVariants, checkedItemsArray, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                checkedItemsArray[which] = isChecked;
                            }
                        })
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (checkedItemsArray[0]) {
                                    sActivity.getNotifySettings().setStandartRdDay(1);
                                } else {
                                    sActivity.getNotifySettings().setStandartRdDay(0);
                                }
                                if (checkedItemsArray[1]) {
                                    sActivity.getNotifySettings().setStandartRdWeek(1);
                                } else {
                                    sActivity.getNotifySettings().setStandartRdWeek(0);
                                }
                                if (checkedItemsArray[2]) {
                                    sActivity.getNotifySettings().setStandartRdMonth(1);
                                } else {
                                    sActivity.getNotifySettings().setStandartRdMonth(0);
                                }
                                sActivity.setTvStandartRd(sActivity.getNotificationsVariants(sActivity.getNotifySettings().getStandartRdDay(),
                                        sActivity.getNotifySettings().getStandartRdWeek(),
                                        sActivity.getNotifySettings().getStandartRdMonth()));
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Отмена",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });

                return builder.create();

            // Диалоговое окно задания настроек уведомления для маловажных дат

            case IDD_SMALL_IMPORTANT_ROUNDDATE:
                sActivity = (SettingsActivity) activity;
                if (sActivity.getNotifySettings().getSmallImportantRdDay() == 1) {
                    checkedItemsArray[0] = true;
                } else {
                    checkedItemsArray[0] = false;
                }
                if (sActivity.getNotifySettings().getSmallImportantRdWeek() == 1) {
                    checkedItemsArray[1] = true;
                } else {
                    checkedItemsArray[1] = false;
                }
                if (sActivity.getNotifySettings().getSmallImportantRdMonth() == 1) {
                    checkedItemsArray[2] = true;
                } else {
                    checkedItemsArray[2] = false;
                }

                builder.setTitle(R.string.notice_for)
                        .setCancelable(true)
                        .setMultiChoiceItems(notificationsVariants, checkedItemsArray, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                checkedItemsArray[which] = isChecked;
                            }
                        })
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (checkedItemsArray[0]) {
                                    sActivity.getNotifySettings().setSmallImportantRdDay(1);
                                } else {
                                    sActivity.getNotifySettings().setSmallImportantRdDay(0);
                                }
                                if (checkedItemsArray[1]) {
                                    sActivity.getNotifySettings().setSmallImportantRdWeek(1);
                                } else {
                                    sActivity.getNotifySettings().setSmallImportantRdWeek(0);
                                }
                                if (checkedItemsArray[2]) {
                                    sActivity.getNotifySettings().setSmallImportantRdMonth(1);
                                } else {
                                    sActivity.getNotifySettings().setSmallImportantRdMonth(0);
                                }
                                sActivity.setTvSmallImportantRd(sActivity.getNotificationsVariants(sActivity.getNotifySettings().getSmallImportantRdDay(),
                                        sActivity.getNotifySettings().getSmallImportantRdWeek(),
                                        sActivity.getNotifySettings().getSmallImportantRdMonth()));
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.cancel,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });

                return builder.create();


            default:
                return null;
        }
    }

    private int getIDD_dialog() {
        return IDD_dialog;
    }


}
