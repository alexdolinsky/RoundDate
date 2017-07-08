package ru.alexanderdolinsky.rounddate;

import android.content.Intent;
import android.os.Bundle;
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


        /*AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                registerForContextMenu(view);
                return true;
            }
        };

        eventsList.setOnItemLongClickListener(itemLongClickListener);*/

        registerForContextMenu(eventsList);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.lvEvents) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            //menu.setHeaderTitle(Countries[info.position]);
            String[] menuItems = getResources().getStringArray(R.array.contextMenuOfEventList);
            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        DialogScreen ds;
        android.support.v7.app.AlertDialog dialog;
        Intent intent;

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();

        // Сохраняем событие, по которому вызвано контекстное меню
        setSelectedEvent(getEvents().get(info.position));

        // выбор и выполнение действия
        switch (menuItemIndex) {
            case 0: // Список круглых дат события
                Toast.makeText(this, "Список круглых дат события", Toast.LENGTH_SHORT).show();
                break;
            case 1: // Редактировать событие
                intent = new Intent(EventListActivity.this, AddEditEventActivity.class);
                intent.putExtra(AddEditEventActivity.ISNEWEVENT, false);
                intent.putExtra(AddEditEventActivity.EVENT_ID, getSelectedEvent().getId());
                startActivityForResult(intent, EDITEVENT_REQUESTCODE);
                break;
            case 2: // Удалить событие
                ds = new DialogScreen(DialogScreen.DELETE_EVENT_CONFIRM_2);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case 3: // Список круглых дат группы событий
                Toast.makeText(this, "Список круглых дат группы событий", Toast.LENGTH_SHORT).show();
                break;
            case 4: // Редактировать группу событий
                intent = new Intent(EventListActivity.this, EditEventGroupActivity.class);
                intent.putExtra(EditEventGroupActivity.EVENTS_GROUP_ID, getSelectedEvent().getIdEventGroup());
                startActivityForResult(intent, EDITEVENTSGROUP_REQUESTCODE);
                break;
            case 5: // Удалить группу событий
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
        android.support.v7.app.AlertDialog dialog;

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
        android.support.v7.app.AlertDialog dialog;
        ds = new DialogScreen(DialogScreen.DELETE_EVENTS_GROUP_CONFIRM);
        dialog = ds.getDialog(this);
        dialog.show();
    }

    public void onEditEventsGroup(View view) {
        Intent intent = new Intent(EventListActivity.this, EditEventGroupActivity.class);
        intent.putExtra(EditEventGroupActivity.EVENTS_GROUP_ID, getEventsGroup().get((int) getSelectedEventsGroupId()).getId());
        startActivityForResult(intent, EDITEVENTSGROUP_REQUESTCODE);
    }


    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }
}
