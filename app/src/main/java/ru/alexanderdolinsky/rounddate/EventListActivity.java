package ru.alexanderdolinsky.rounddate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class EventListActivity extends AppCompatActivity {

    private static final int DELETEEVENT_REQUESTCODE = 1;
    private static final int EDITEVENTSGROUP_REQUESTCODE = 2;
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

        setEventsGroupName(getEventsGroup());


        setSelectedEventsGroupId(DatabaseAdapter.FIRST_ELEMENT_ID);

        // устанавливаем по умолчанию Все события
        TextView tvGroupName = (TextView) findViewById(R.id.tvEventsGroup);
        tvGroupName.setText(R.string.all_events);

        // деактивация кнопки редактирование группы событий
        ImageButton btnEditEventsGroup = (ImageButton) findViewById(R.id.btnEditEventsGroup);
        btnEditEventsGroup.setVisibility(View.GONE);

        // деактивация кнопки удаления группы событий
        ImageButton btnDeleteEventsGroup = (ImageButton) findViewById(R.id.btnDeleteEventsGroup);
        btnDeleteEventsGroup.setVisibility(View.GONE);

        // получаем список всех событий
        setEvents(adapter.getAllEvents());
        adapter.close();

        // выводим события в список
        eventsList = (ListView) findViewById(R.id.lvEvents);
        EventAdapter eventAdapter = new EventAdapter(this, R.layout.event_item, getEvents());
        eventsList.setAdapter(eventAdapter);

        // устанавливаем слушателя на список и переход к активити события по ID события

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // получаем выбранное событие
                Event selectedEvent = (Event) parent.getItemAtPosition(position);
                // передаем ID события в активити события
                Intent intent = new Intent(EventListActivity.this, EventActivity.class);
                intent.putExtra("ID", selectedEvent.getId());
                startActivityForResult(intent, DELETEEVENT_REQUESTCODE);

            }
        };

        eventsList.setOnItemClickListener(itemListener);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case DELETEEVENT_REQUESTCODE:
                if (resultCode == RESULT_OK) {
                    this.recreate();
                } else {
                    super.onActivityResult(requestCode, resultCode, data);
                }
                break;
            case EDITEVENTSGROUP_REQUESTCODE:
                if (resultCode == RESULT_OK) {
                    this.recreate();
                } else {
                    super.onActivityResult(requestCode, resultCode, data);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }


    }

    public void onChoiceEventGroup(View view) {

        DialogScreen ds;
        android.app.AlertDialog dialog;

        ds = new DialogScreen(DialogScreen.IDD_CHOICE_EVENT_GROUP);
        dialog = ds.getDialog(this);
        dialog.show();
    }

    public CharSequence[] getEventsGroupName() {
        return eventsGroupName;
    }

    public void setEventsGroupName(List<EventGroup> eventsGroup) {
        int i = 0;
        this.eventsGroupName = new CharSequence[eventsGroup.size()];
        for (EventGroup eventGroup : eventsGroup) {
            this.eventsGroupName[i] = eventGroup.getName();
            i++;
        }
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


    public void onDeleteEventsGroup(View view) {
        DialogScreen ds;
        android.app.AlertDialog dialog;
        ds = new DialogScreen(DialogScreen.DELETE_EVENTS_GROUP_CONFIRM);
        dialog = ds.getDialog(this);
        dialog.show();
    }

    public void onEditEventsGroup(View view) {
        Intent intent = new Intent(EventListActivity.this, EditEventGroupActivity.class);
        intent.putExtra(EditEventGroupActivity.EVENTS_GROUP_ID, getEventsGroup().get((int) getSelectedEventsGroupId()).getId());
        startActivityForResult(intent, EDITEVENTSGROUP_REQUESTCODE);
    }


}
