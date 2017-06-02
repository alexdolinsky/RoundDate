package ru.alexanderdolinsky.rounddate;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    //Объявляем названия файла настроек и сами настройки
    public static final String  MY_SETTINGS = "my_settings",
                                MY_SETTINGS_RD_IN_YEARS = "rd_in_years",
                                MY_SETTINGS_RD_IN_MONTHS = "rd_in_months",
                                MY_SETTINGS_RD_IN_WEEKS = "rd_in_weeks",
                                MY_SETTINGS_RD_IN_DAYS = "rd_in_days",
                                MY_SETTINGS_RD_IN_HOURS = "rd_in_hours",
                                MY_SETTINGS_RD_IN_MINUTES = "rd_in_minutes",
                                MY_SETTINGS_RD_IN_SECS = "rd_in_secs";
                                /*MY_SETTINGS_VERY_IMORTANT_RD_MONTH = "very_important_rd_month",
                                MY_SETTINGS_VERY_IMORTANT_RD_WEEK = "very_important_rd_week",
                                MY_SETTINGS_VERY_IMORTANT_RD_DAY = "very_important_rd_day",
                                MY_SETTINGS_VERY_IMORTANT_RD_HOUR = "very_important_rd_hour",
                                MY_SETTINGS_IMORTANT_RD_MONTH = "important_rd_month",
                                MY_SETTINGS_IMORTANT_RD_WEEK = "important_rd_week",
                                MY_SETTINGS_IMORTANT_RD_DAY = "important_rd_day",
                                MY_SETTINGS_IMORTANT_RD_HOUR = "important_rd_hour",
                                MY_SETTINGS_STANDART_RD_MONTH = "standart_rd_month",
                                MY_SETTINGS_STANDART_RD_WEEK = "standart_rd_week",
                                MY_SETTINGS_STANDART_RD_DAY = "standart_rd_day",
                                MY_SETTINGS_STANDART_RD_HOUR = "standart_rd_hour",
                                MY_SETTINGS_SMALL_IMPORTANT_RD_MONTH = "small_important_rd_month",
                                MY_SETTINGS_SMALL_IMPORTANT_RD_WEEK = "small_important_rd_week",
                                MY_SETTINGS_SMALL_IMPORTANT_RD_DAY = "small_important_rd_day",
                                MY_SETTINGS_SMALL_IMPORTANT_RD_HOUR = "small_important_rd_hour";*/
    private SharedPreferences mSettings, mFilter;
    private int[] mTrackSetting = {0, 0, 0, 0, 0, 0, 0};

    private final static int IDD_RD_YEARS = 0;
    private final static int IDD_RD_MONTHS = 1;
    private final static int IDD_RD_WEEKS = 2;
    private final static int IDD_RD_DAYS = 3;
    private final static int IDD_RD_HOURS = 4;
    private final static int IDD_RD_MINUTES = 5;
    private final static int IDD_RD_SECS = 6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final CharSequence[] rdVariants = getResources().getStringArray(R.array.rd_variants);
        mSettings = getSharedPreferences(MY_SETTINGS, MODE_PRIVATE);
        mTrackSetting[0] = mSettings.getInt(MY_SETTINGS_RD_IN_YEARS,
                Integer.valueOf(getString(R.string.rd_in_years)));
        mTrackSetting[1] = mSettings.getInt(MY_SETTINGS_RD_IN_MONTHS,
                Integer.valueOf(getString(R.string.rd_in_months)));
        mTrackSetting[2] = mSettings.getInt(MY_SETTINGS_RD_IN_WEEKS,
                Integer.valueOf(getString(R.string.rd_in_weeks)));
        mTrackSetting[3] = mSettings.getInt(MY_SETTINGS_RD_IN_DAYS,
                Integer.valueOf(getString(R.string.rd_in_days)));
        mTrackSetting[4] = mSettings.getInt(MY_SETTINGS_RD_IN_HOURS,
                Integer.valueOf(getString(R.string.rd_in_hours)));
        mTrackSetting[5] = mSettings.getInt(MY_SETTINGS_RD_IN_MINUTES,
                Integer.valueOf(getString(R.string.rd_in_minutes)));
        mTrackSetting[6] = mSettings.getInt(MY_SETTINGS_RD_IN_SECS,
                Integer.valueOf(getString(R.string.rd_in_secs)));
        TextView tvTrack = (TextView)findViewById(R.id.tvRoundDateInYears);
        tvTrack.setText(rdVariants[mTrackSetting[0]]);
        tvTrack = (TextView)findViewById(R.id.tvRoundDateInMonths);
        tvTrack.setText(rdVariants[mTrackSetting[1]]);
        tvTrack = (TextView)findViewById(R.id.tvRoundDateInWeeks);
        tvTrack.setText(rdVariants[mTrackSetting[2]]);
        tvTrack = (TextView)findViewById(R.id.tvRoundDateInDays);
        tvTrack.setText(rdVariants[mTrackSetting[3]]);
        tvTrack = (TextView)findViewById(R.id.tvRoundDateInHours);
        tvTrack.setText(rdVariants[mTrackSetting[4]]);
        tvTrack = (TextView)findViewById(R.id.tvRoundDateInMinutes);
        tvTrack.setText(rdVariants[mTrackSetting[5]]);
        tvTrack = (TextView)findViewById(R.id.tvRoundDateInSecs);
        tvTrack.setText(rdVariants[mTrackSetting[6]]);


    }

    public void onCancel(View view) {
        finish();
    }

    public void onSaveSettings(View view) {
        // TODO: 31.05.2017 Сохраняем настройки
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(MY_SETTINGS_RD_IN_YEARS, mTrackSetting[0]);
        editor.putInt(MY_SETTINGS_RD_IN_MONTHS, mTrackSetting[1]);
        editor.putInt(MY_SETTINGS_RD_IN_WEEKS, mTrackSetting[2]);
        editor.putInt(MY_SETTINGS_RD_IN_DAYS, mTrackSetting[3]);
        editor.putInt(MY_SETTINGS_RD_IN_HOURS, mTrackSetting[4]);
        editor.putInt(MY_SETTINGS_RD_IN_MINUTES, mTrackSetting[5]);
        editor.putInt(MY_SETTINGS_RD_IN_SECS, mTrackSetting[6]);
        editor.apply();

        // TODO: 31.05.2017 Определяем список событий, для которых сменились настройки

        // TODO: 31.05.2017 Удаляем кргулые даты из БД для полученного списка,
        // формируем новые и добавляем их в БД в единой транзакции


        finish();


    }

    public void onLoadDefaultSettings(View view) {
        // TODO: 31.05.2017 удалить файл с настройками
        /*SharedPreferences.Editor editor = mSettings.edit();
        editor.clear();
        editor.apply();
        mTrackSetting[0] = Integer.valueOf(getString(R.string.rd_in_years));
        mTrackSetting[1] = Integer.valueOf(getString(R.string.rd_in_months));
        mTrackSetting[2] = Integer.valueOf(getString(R.string.rd_in_weeks));
        mTrackSetting[3] = Integer.valueOf(getString(R.string.rd_in_days));
        mTrackSetting[4] = Integer.valueOf(getString(R.string.rd_in_hours));
        mTrackSetting[5] = Integer.valueOf(getString(R.string.rd_in_minutes));
        mTrackSetting[6] = Integer.valueOf(getString(R.string.rd_in_secs));*/
    }


    public void onClick(View view) {
        switch (view.getId()){
            case R.id.llRoundDateYear:
                showDialog(IDD_RD_YEARS);
                break;
            case R.id.llRoundDateMonth:
                showDialog(IDD_RD_MONTHS);
                break;
            case R.id.llRoundDateWeek:
                showDialog(IDD_RD_WEEKS);
                break;
            case R.id.llRoundDateDay:
                showDialog(IDD_RD_DAYS);
                break;
            case R.id.llRoundDateHour:
                showDialog(IDD_RD_HOURS);
                break;
            case R.id.llRoundDateMinute:
                showDialog(IDD_RD_MINUTES);
                break;
            case R.id.llRoundDateSec:
                showDialog(IDD_RD_SECS);
                break;


        }
        Log.d("MyLog", Integer.toString(mTrackSetting[0]) + " " +
                Integer.toString(mTrackSetting[1]) + " " +
                Integer.toString(mTrackSetting[2]) + " " +
                Integer.toString(mTrackSetting[3]) + " " +
                Integer.toString(mTrackSetting[4]) + " " +
                Integer.toString(mTrackSetting[5]) + " " +
                Integer.toString(mTrackSetting[6]));

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        final CharSequence[] rdVariants;
        switch (id) {
            case IDD_RD_YEARS:
                AlertDialog.Builder builderYears = new AlertDialog.Builder(this);
                rdVariants = getResources().getStringArray(R.array.rd_variants);
                builderYears.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, mTrackSetting[0], new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTrackSetting[0] = which;
                                TextView tv = (TextView)findViewById(R.id.tvRoundDateInYears);
                                tv.setText(rdVariants[which]);
                            }
                        });
                return builderYears.create();
            case IDD_RD_MONTHS:
                AlertDialog.Builder builderMonths = new AlertDialog.Builder(this);
                rdVariants = getResources().getStringArray(R.array.rd_variants);
                builderMonths.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, mTrackSetting[1], new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTrackSetting[1] = which;
                                TextView tv = (TextView)findViewById(R.id.tvRoundDateInMonths);
                                tv.setText(rdVariants[which]);
                            }
                        });
                return builderMonths.create();
            case IDD_RD_WEEKS:
                AlertDialog.Builder builderWeeks = new AlertDialog.Builder(this);
                rdVariants = getResources().getStringArray(R.array.rd_variants);
                builderWeeks.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, mTrackSetting[2], new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTrackSetting[2] = which;
                                TextView tv = (TextView)findViewById(R.id.tvRoundDateInWeeks);
                                tv.setText(rdVariants[which]);
                            }
                        });
                return builderWeeks.create();
            case IDD_RD_DAYS:
                AlertDialog.Builder builderDays = new AlertDialog.Builder(this);
                rdVariants = getResources().getStringArray(R.array.rd_variants);
                builderDays.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, mTrackSetting[3], new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTrackSetting[3] = which;
                                TextView tv = (TextView)findViewById(R.id.tvRoundDateInDays);
                                tv.setText(rdVariants[which]);
                            }
                        });
                return builderDays.create();
            case IDD_RD_HOURS:
                AlertDialog.Builder builderHours = new AlertDialog.Builder(this);
                rdVariants = getResources().getStringArray(R.array.rd_variants);
                builderHours.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, mTrackSetting[4], new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTrackSetting[4] = which;
                                TextView tv = (TextView)findViewById(R.id.tvRoundDateInHours);
                                tv.setText(rdVariants[which]);
                            }
                        });
                return builderHours.create();
            case IDD_RD_MINUTES:
                AlertDialog.Builder builderMinutes = new AlertDialog.Builder(this);
                rdVariants = getResources().getStringArray(R.array.rd_variants);
                builderMinutes.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, mTrackSetting[5], new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTrackSetting[5] = which;
                                TextView tv = (TextView)findViewById(R.id.tvRoundDateInMinutes);
                                tv.setText(rdVariants[which]);
                            }
                        });
                return builderMinutes.create();
            case IDD_RD_SECS:
                AlertDialog.Builder builderSecs = new AlertDialog.Builder(this);
                rdVariants = getResources().getStringArray(R.array.rd_variants);
                builderSecs.setTitle(R.string.track)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setSingleChoiceItems(rdVariants, mTrackSetting[6], new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTrackSetting[6] = which;
                                TextView tv = (TextView)findViewById(R.id.tvRoundDateInSecs);
                                tv.setText(rdVariants[which]);
                            }
                        });
                return builderSecs.create();
            default:
                return null;
        }
    }
}
