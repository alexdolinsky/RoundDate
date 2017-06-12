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
    private final int IDD_dialog;


    public DialogScreen (int ID) {
        this.IDD_dialog = ID;
    }

    public AlertDialog getDialog(final AddEditEventActivity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        final CharSequence[] rdVariants = activity.getResources().getStringArray(R.array.rd_variants);

        switch (getIDD_dialog()) {
            case IDD_RD_YEARS:
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.setTvYears(rdVariants[activity.getEventTrackSettings().getRdInYears()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, activity.getEventTrackSettings().getRdInYears(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.getEventTrackSettings().setRdInYears(which);
                            }
                        });
                return builder.create();


            case IDD_RD_MONTHS:
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.setTvMonths(rdVariants[activity.getEventTrackSettings().getRdInMonths()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, activity.getEventTrackSettings().getRdInMonths(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.getEventTrackSettings().setRdInMonths(which);
                            }
                        });
                return builder.create();


            case IDD_RD_WEEKS:
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.setTvWeeks(rdVariants[activity.getEventTrackSettings().getRdInWeeks()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, activity.getEventTrackSettings().getRdInWeeks(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.getEventTrackSettings().setRdInWeeks(which);
                            }
                        });
                return builder.create();


            case IDD_RD_DAYS:
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.setTvDays(rdVariants[activity.getEventTrackSettings().getRdInDays()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, activity.getEventTrackSettings().getRdInDays(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.getEventTrackSettings().setRdInDays(which);
                            }
                        });
                return builder.create();


            case IDD_RD_HOURS:
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.setTvHours(rdVariants[activity.getEventTrackSettings().getRdInHours()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, activity.getEventTrackSettings().getRdInHours(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.getEventTrackSettings().setRdInHours(which);
                            }
                        });
                return builder.create();


            case IDD_RD_MINUTES:
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.setTvMinutes(rdVariants[activity.getEventTrackSettings().getRdInMinutes()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, activity.getEventTrackSettings().getRdInMinutes(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.getEventTrackSettings().setRdInMinutes(which);
                            }
                        });
                return builder.create();


            case IDD_RD_SECS:
                builder.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.setTvSecs(rdVariants[activity.getEventTrackSettings().getRdInSecs()]);
                                dialog.dismiss();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, activity.getEventTrackSettings().getRdInSecs(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.getEventTrackSettings().setRdInSecs(which);
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
