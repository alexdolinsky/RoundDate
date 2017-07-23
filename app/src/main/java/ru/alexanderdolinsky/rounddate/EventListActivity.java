package ru.alexanderdolinsky.rounddate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class EventListActivity extends AppCompatActivity {

    private static final int DELETEEVENT_REQUESTCODE = 1;
    private static final int EDITEVENTSGROUP_REQUESTCODE = 2;
    private static final int EDITEVENT_REQUESTCODE = 3;
    private static final int ROUNDDATE_LIST_OF_EVENT = 0;
    private static final int EDIT_EVENT = 1;
    private static final int DELETE_EVENT = 2;
    private static final int ROUNDDATE_LIST_OF_EVENTSGROUP = 3;
    private static final int EDIT_EVENTSGROUP = 4;
    private static final int DELETE_EVENTSGROUP = 5;
    private CharSequence[] eventsGroupName;
    private long selectedEventsGroupId;
    private List<EventGroup> eventsGroup;
    private List<Event> events;
    private Event selectedEvent;

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

        registerForContextMenu(eventsList);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.lvEvents) {
            menu.add(Menu.NONE, ROUNDDATE_LIST_OF_EVENT, 0, getString(R.string.rounddate_list_of_event));
            menu.add(Menu.NONE, EDIT_EVENT, 0, getString(R.string.edit_event));
            menu.add(Menu.NONE, DELETE_EVENT, 0, getString(R.string.delete_event));
            menu.add(Menu.NONE, ROUNDDATE_LIST_OF_EVENTSGROUP, 0, getString(R.string.rounddate_list_of_eventsgroup));
            menu.add(Menu.NONE, EDIT_EVENTSGROUP, 0, getString(R.string.edit_events_group));
            menu.add(Menu.NONE, DELETE_EVENTSGROUP, 0, getString(R.string.delete_event_group));

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        DialogScreen ds;
        AlertDialog dialog;
        Intent intent;

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();

        // Сохраняем событие, по которому вызвано контекстное меню
        setSelectedEvent(getEvents().get(info.position));

        // выбор и выполнение действия
        switch (menuItemIndex) {
            case ROUNDDATE_LIST_OF_EVENT: // Список круглых дат события
                intent = new Intent(EventListActivity.this, RoundDateListActivity.class);
                intent.putExtra(RoundDateListActivity.ISEVENT, true);
                intent.putExtra(RoundDateListActivity.ID, getSelectedEvent().getId());
                startActivity(intent);
                break;
            case EDIT_EVENT: // Редактировать событие
                intent = new Intent(EventListActivity.this, AddEditEventActivity.class);
                intent.putExtra(AddEditEventActivity.ISNEWEVENT, false);
                intent.putExtra(AddEditEventActivity.EVENT_ID, getSelectedEvent().getId());
                startActivityForResult(intent, EDITEVENT_REQUESTCODE);
                break;
            case DELETE_EVENT: // Удалить событие
                ds = new DialogScreen(DialogScreen.DELETE_EVENT_CONFIRM_2);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case ROUNDDATE_LIST_OF_EVENTSGROUP: // Список круглых дат группы событий
                intent = new Intent(EventListActivity.this, RoundDateListActivity.class);
                intent.putExtra(RoundDateListActivity.ISEVENT, false);
                intent.putExtra(RoundDateListActivity.ID, getSelectedEvent().getIdEventGroup());
                startActivity(intent);
                break;
            case EDIT_EVENTSGROUP: // Редактировать группу событий
                intent = new Intent(EventListActivity.this, EditEventGroupActivity.class);
                intent.putExtra(EditEventGroupActivity.EVENTS_GROUP_ID, getSelectedEvent().getIdEventGroup());
                startActivityForResult(intent, EDITEVENTSGROUP_REQUESTCODE);
                break;
            case DELETE_EVENTSGROUP: // Удалить группу событий
                if (getSelectedEvent().getIdEventGroup() == 1) { // если группа событий - Мои события
                    Toast.makeText(this, R.string.this_events_group_not_be_deleted, Toast.LENGTH_SHORT).show();
                } else {
                    ds = new DialogScreen(DialogScreen.DELETE_EVENTS_GROUP_CONFIRM_2);
                    dialog = ds.getDialog(this);
                    dialog.show();
                }
                break;
            default:
        }
        return true;
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
            case EDITEVENT_REQUESTCODE:
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
        AlertDialog dialog;

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


    public void onDeleteEventsGroup(View view) {
        DialogScreen ds;
        AlertDialog dialog;
        ds = new DialogScreen(DialogScreen.DELETE_EVENTS_GROUP_CONFIRM);
        dialog = ds.getDialog(this);
        dialog.show();
    }

    public void onEditEventsGroup(View view) {
        Intent intent = new Intent(EventListActivity.this, EditEventGroupActivity.class);
        intent.putExtra(EditEventGroupActivity.EVENTS_GROUP_ID, getEventsGroup().get((int) getSelectedEventsGroupId()).getId());
        startActivityForResult(intent, EDITEVENTSGROUP_REQUESTCODE);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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

    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }
}
