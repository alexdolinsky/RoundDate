package ru.alexanderdolinsky.rounddate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Alexsvet on 08.06.2017.
 */

public class DialogScreen {

    private final static int IDD_RD_YEARS = 0;
    private final static int IDD_RD_MONTHS = 1;
    private final static int IDD_RD_WEEKS = 2;
    private final static int IDD_RD_DAYS = 3;
    private final static int IDD_RD_HOURS = 4;
    private final static int IDD_RD_MINUTES = 5;
    private final static int IDD_RD_SECS = 6;

    private final static int IDD_CHOICE_EVENT_GROUP = 20;


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
