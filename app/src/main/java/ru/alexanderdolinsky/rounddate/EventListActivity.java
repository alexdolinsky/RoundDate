package ru.alexanderdolinsky.rounddate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class EventListActivity extends AppCompatActivity {

    private CharSequence[] eventsGroupName;
    private long selectedEventsGroupId;
    private List<EventGroup> eventsGroup;
    private List<Event> events;

    ListView eventsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();

        setEventsGroup(adapter.getEventGroups(true, false));

        for (EventGroup eGroup : eventsGroup
                ) {
            Log.d("MyLog", "ID: " + eGroup.getId() + " Имя группы: " + eGroup.getName() +
                    " Источник настроей слежения: " + eGroup.getSourceTrackSettings());

        }

        setSelectedEventsGroupId(DatabaseAdapter.FIRST_ELEMENT_ID);

        // устанавливаем по умолчанию Все события
        TextView tvGroupName = (TextView) findViewById(R.id.tvEventsGroup);
        tvGroupName.setText(R.string.all_events);

        // получаем список всех событий
        setEvents(adapter.getAllEvents());

        for (Event event : events) {
            Log.d("MyLog", "ID: " + event.getId() + " Имя события: " + event.getName() +
                    " Комментарий: " + event.getComment() +
                    " Группа событий: " + event.getNameEventGroup() +
                    " Дата: " + event.getDateAndTime());
        }


        // выводим события в список
        eventsList = (ListView) findViewById(R.id.lvEvents);
        EventAdapter eventAdapter = new EventAdapter(this, R.layout.event_item, getEvents());
        eventsList.setAdapter(eventAdapter);
        adapter.close();





    }

    public void onEditEventsGroup(View view) {
    }

    public void onChoiceEventGroup(View view) {

        DialogScreen ds;
        android.app.AlertDialog dialog;

        ds = new DialogScreen(0);
        dialog = ds.getDialog(this);
        dialog.show();
    }

    public CharSequence[] getEventsGroupName() {
        return eventsGroupName;
    }

    public long getSelectedEventsGroupId() {
        return selectedEventsGroupId;
    }

    public void setSelectedEventsGroupId(long selectedEventsGroupId) {
        this.selectedEventsGroupId = selectedEventsGroupId;
    }

    public List<EventGroup> getEventsGroup() {
        return eventsGroup;
    }

    public void setEventsGroup(List<EventGroup> eventsGroup) {
        this.eventsGroup = eventsGroup;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
