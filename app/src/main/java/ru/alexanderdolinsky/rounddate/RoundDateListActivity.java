package ru.alexanderdolinsky.rounddate;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class RoundDateListActivity extends AppCompatActivity {

    public static final String ISEVENT = "isEvent";
    public static final int EVENT = 0;
    public static final int EVENTSGROUP = 1;
    public static final String ID = "id";
    private List<RoundDate> roundDates;
    private RoundDate selectedRoundDate;
    private int positionSelectedRoundDate;

    ListView roundDatesList;
    RoundDateAdapter roundDateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_date_list);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int i;
            if (extras.getBoolean(ISEVENT)) { // определяем тип передаваемого id - событие или группа событий
                i = EVENT;
                setTitle(R.string.rounddate_list_of_event);
            } else {
                i = EVENTSGROUP;
                setTitle(R.string.rounddate_list_of_eventsgroup);
            }
            long id = extras.getLong(ID);  // id события или группы событий

            DatabaseAdapter adapter = new DatabaseAdapter(this);
            adapter.open();


            // получаем список круглых дат по id события или группы событий
            setRoundDates(adapter.getRoundDates(i, id));
            adapter.close();

            // выводим круглые даты в список
            roundDatesList = (ListView) findViewById(R.id.lvRoundDates);
            roundDateAdapter = new RoundDateAdapter(this, R.layout.round_date_item, getRoundDates());
            roundDatesList.setAdapter(roundDateAdapter);


            // устанавливаем слушателя на список и вывод диалогового окна по ID круглой даты

            AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // получаем выбранное событие
                    setSelectedRoundDate((RoundDate) parent.getItemAtPosition(position));
                    setPositionSelectedRoundDate(position);
                    // диалоговое окно с выбором важности круглой даты по ID

                    DialogScreen ds;
                    AlertDialog dialog;
                    ds = new DialogScreen(DialogScreen.IDD_CHOICE_IMPORTANT_2);
                    dialog = ds.getDialog(RoundDateListActivity.this);
                    dialog.show();
                }
            };

            roundDatesList.setOnItemClickListener(itemListener);

        } else {
            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    public List<RoundDate> getRoundDates() {
        return roundDates;
    }

    public void setRoundDates(List<RoundDate> roundDates) {
        this.roundDates = roundDates;
    }

    public RoundDate getSelectedRoundDate() {
        return selectedRoundDate;
    }

    public void setSelectedRoundDate(RoundDate selectedRoundDate) {
        this.selectedRoundDate = selectedRoundDate;
    }

    public int getPositionSelectedRoundDate() {
        return positionSelectedRoundDate;
    }

    public void setPositionSelectedRoundDate(int positionSelectedRoundDate) {
        this.positionSelectedRoundDate = positionSelectedRoundDate;
    }
}
