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
 * Created by Alexsvet on 17.06.2017.
 * Класс адаптер списка событий
 */

class EventAdapter extends ArrayAdapter<Event> {

    private LayoutInflater inflater;
    private int layout;
    private List<Event> events;

    EventAdapter(Context context, int resource, List<Event> events) {
        super(context, resource, events);
        this.events = events;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Event event = events.get(position);

        viewHolder.tvEventName.setText(event.getName());
        viewHolder.tvEventGroupName.setText(event.getNameEventGroup());
        viewHolder.tvDate.setText(DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault()).format(event.getDateAndTime().getTime()));
        viewHolder.tvTime.setText(DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault()).format(event.getDateAndTime().getTime()));

        return convertView;
    }

    private class ViewHolder {
        final TextView tvEventName, tvEventGroupName, tvDate, tvTime;

        ViewHolder(View view) {
            tvEventName = (TextView) view.findViewById(R.id.tvEventName);
            tvEventGroupName = (TextView) view.findViewById(R.id.tvEventGroupName);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvTime = (TextView) view.findViewById(R.id.tvTime);

        }
    }
}
