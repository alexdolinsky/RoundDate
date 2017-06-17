package ru.alexanderdolinsky.rounddate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Alexsvet on 08.06.2017.
 */

public class DialogScreen {

    public final static int IDD_RD_YEARS = 0;
    public final static int IDD_RD_MONTHS = 1;
    public final static int IDD_RD_WEEKS = 2;
    public final static int IDD_RD_DAYS = 3;
    public final static int IDD_RD_HOURS = 4;
    public final static int IDD_RD_MINUTES = 5;
    public final static int IDD_RD_SECS = 6;

    public final static int IDD_CHOICE_EVENT_GROUP = 20;


    private final int IDD_dialog;


    public DialogScreen (int ID) {
        this.IDD_dialog = ID;
    }

    public AlertDialog getDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        final CharSequence[] rdVariants = activity.getResources().getStringArray(R.array.rd_variants);

        final AddEditEventActivity aeActivity;// = (AddEditEventActivity) activity;
        final EventListActivity elActivity;

        switch (getIDD_dialog()) {
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
                                ImageButton imageButton = (ImageButton) elActivity.findViewById(R.id.btnEditEventsGroup);
                                // получаем список событий по ID группы событий или же все события, если выбраны все события
                                if (which == 0) {
                                    elActivity.setEvents(adapter.getAllEvents());
                                    //imageButton.setEnabled(false);
                                    imageButton.setVisibility(View.INVISIBLE);
                                } else {
                                    elActivity.setEvents(adapter.getEventsById(elActivity.getEventsGroup().get(which).getId()));
                                    //imageButton.setEnabled(true);
                                    imageButton.setVisibility(View.VISIBLE);
                                }


                                // выводим события в список
                                elActivity.eventsList = (ListView) elActivity.findViewById(R.id.lvEvents);
                                EventAdapter eventAdapter = new EventAdapter(elActivity, R.layout.event_item, elActivity.getEvents());
                                elActivity.eventsList.setAdapter(eventAdapter);
                                adapter.close();
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
