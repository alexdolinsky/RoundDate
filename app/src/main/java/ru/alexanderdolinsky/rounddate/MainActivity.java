package ru.alexanderdolinsky.rounddate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<RoundDate> roundDates;

    ListView roundDatesList;

    public static final String ISNEWEVENT = "IS_NEW_EVENT";
    public static final int ADDEVENTREQUESTCODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();


        // получаем список всех круглых дат
        setRoundDates(adapter.getRoundDates());
        adapter.close();

        // выводим круглые даты в список
        roundDatesList = (ListView) findViewById(R.id.lvRoundDates);
        RoundDateAdapter roundDateAdapter = new RoundDateAdapter(this, R.layout.round_date_item, getRoundDates());
        roundDatesList.setAdapter(roundDateAdapter);

        // устанавливаем слушателя на список и вывод диалогового окна по ID круглой даты

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // получаем выбранное событие
                RoundDate selectedRoundDate = (RoundDate) parent.getItemAtPosition(position);
                // диалоговое окно с выбором важности круглой даты по ID
                Toast.makeText(MainActivity.this, "Выбрана круглая дата номер " + position, Toast.LENGTH_SHORT).show();
            }
        };

        roundDatesList.setOnItemClickListener(itemListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // формируем меню из файла ресурса
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();
        //TODO: 27.05.2017 тестовый туду

        // операции для выбранного пункта меню
        Intent intent;
        switch (id) {
            case R.id.action_event_list:
                intent = new Intent(MainActivity.this, EventListActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_filter:
                intent = new Intent(MainActivity.this, FilterActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_about:
                intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onAddEvent(View view) {
        Intent intent = new Intent(MainActivity.this,AddEditEventActivity.class);
        intent.putExtra(AddEditEventActivity.ISNEWEVENT, true);
        startActivityForResult(intent, ADDEVENTREQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ADDEVENTREQUESTCODE) {
            if (resultCode == RESULT_OK) {
                this.recreate();
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    public List<RoundDate> getRoundDates() {
        return roundDates;
    }

    public void setRoundDates(List<RoundDate> roundDates) {
        this.roundDates = roundDates;
    }
}
