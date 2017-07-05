package ru.alexanderdolinsky.rounddate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Alexsvet on 08.06.2017.
 */

public class DialogScreen {

    final static int IDD_RD_YEARS = 0;
    final static int IDD_RD_MONTHS = 1;
    final static int IDD_RD_WEEKS = 2;
    final static int IDD_RD_DAYS = 3;
    final static int IDD_RD_HOURS = 4;
    final static int IDD_RD_MINUTES = 5;
    final static int IDD_RD_SECS = 6;
    final static int IDD_RD_YEARS_2 = 10;
    final static int IDD_RD_MONTHS_2 = 11;
    final static int IDD_RD_WEEKS_2 = 12;
    final static int IDD_RD_DAYS_2 = 13;
    final static int IDD_RD_HOURS_2 = 14;
    final static int IDD_RD_MINUTES_2 = 15;
    final static int IDD_RD_SECS_2 = 16;
    final static int IDD_RD_YEARS_3 = 20;
    final static int IDD_RD_MONTHS_3 = 21;
    final static int IDD_RD_WEEKS_3 = 22;
    final static int IDD_RD_DAYS_3 = 23;
    final static int IDD_RD_HOURS_3 = 24;
    final static int IDD_RD_MINUTES_3 = 25;
    final static int IDD_RD_SECS_3 = 26;

    final static int IDD_CHOICE_EVENT_GROUP = 30;
    static final int DELETE_EVENT_CONFIRM = 40;
    static final int DELETE_EVENTS_GROUP_CONFIRM = 41;


    private final int IDD_dialog;


    DialogScreen(int ID) {
        this.IDD_dialog = ID;
    }

    AlertDialog getDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        final CharSequence[] rdVariants = activity.getResources().getStringArray(R.array.rd_variants);

        final AddEditEventActivity aeActivity;// = (AddEditEventActivity) activity;
        final EventListActivity elActivity;
        final EventActivity eActivity;
        final EditEventGroupActivity eegActivity;
        final SettingsActivity sActivity;

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


            default:
                return null;
        }
    }

    public int getIDD_dialog() {
        return IDD_dialog;
    }


}
