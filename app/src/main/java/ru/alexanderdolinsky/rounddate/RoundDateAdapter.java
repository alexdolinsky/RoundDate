package ru.alexanderdolinsky.rounddate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Alexsvet on 24.06.2017.
 * Класс адаптера круглых дат
 */

class RoundDateAdapter extends ArrayAdapter<RoundDate> {

    private LayoutInflater inflater;
    private int layout;
    private List<RoundDate> roundDates;
    private Context context;

    RoundDateAdapter(Context context, int resource, List<RoundDate> roundDates) {
        super(context, resource, roundDates);
        this.roundDates = roundDates;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        RoundDateAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new RoundDateAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RoundDateAdapter.ViewHolder) convertView.getTag();
        }


        RoundDate roundDate = roundDates.get(position);
        String str = String.format(Locale.getDefault(), "%,d %s", roundDate.getValueOf(), RoundDate.getUnit(getContext(), roundDate.getValueOf(), roundDate.getUnit()));
        if (roundDate.getImportant() == RoundDate.VERY_IMPORTANT) {
            str = "\u2605\u2605\u2605 " + str + " \u2605\u2605\u2605";
            viewHolder.tvValue.setTextColor(0xffff8800);
        } else if (roundDate.getImportant() == RoundDate.IMPORTANT) {
            str = "\u2605 " + str + " \u2605";
            viewHolder.tvValue.setTextColor(0xffff8800);
        } else if (roundDate.getImportant() == RoundDate.STANDART) {
            viewHolder.tvValue.setTextColor(0xff404040);
        } else {
            viewHolder.tvValue.setTextColor(0xaa404040);
        }
        viewHolder.tvValue.setText(str);

        viewHolder.tvEventName.setText(roundDate.getNameEvent());
        viewHolder.tvDateAndTime.setText(DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault()).format(roundDate.getDateAndTime().getTime()) + " " // дата
                + DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault()).format(roundDate.getDateAndTime().getTime())); // время
        viewHolder.tvHaveToWait.setText(RoundDate.getTimeToWait(getContext(), roundDate.getDateAndTime()));

        return convertView;
    }

    @NonNull
    @Override
    public Context getContext() {
        return context;
    }

    private class ViewHolder {
        final TextView tvValue, tvEventName, tvDateAndTime, tvHaveToWait;

        ViewHolder(View view) {
            tvValue = (TextView) view.findViewById(R.id.tvValue);
            tvEventName = (TextView) view.findViewById(R.id.tvEventName);
            tvDateAndTime = (TextView) view.findViewById(R.id.tvDateAndTime);
            tvHaveToWait = (TextView) view.findViewById(R.id.tvHaveToWait);
        }
    }
}
