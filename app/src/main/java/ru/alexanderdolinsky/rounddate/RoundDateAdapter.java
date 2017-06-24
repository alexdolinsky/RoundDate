package ru.alexanderdolinsky.rounddate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Alexsvet on 24.06.2017.
 */

public class RoundDateAdapter extends ArrayAdapter<RoundDate> {

    private LayoutInflater inflater;
    private int layout;
    private List<RoundDate> roundDates;
    private Context context;

    public RoundDateAdapter(Context context, int resource, List<RoundDate> roundDates) {
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

        viewHolder.tvValue.setText(Long.toString(roundDate.getValueOf()) + " " + RoundDate.getUnit(getContext(), roundDate.getValueOf(), roundDate.getUnit()));
        viewHolder.tvEventName.setText(roundDate.getNameEvent());
        viewHolder.tvDateAndTime.setText(RoundDate.getStringOfDateAndTime(roundDate.getDateAndTime()));
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
