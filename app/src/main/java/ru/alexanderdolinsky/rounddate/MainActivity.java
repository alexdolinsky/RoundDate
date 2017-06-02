package ru.alexanderdolinsky.rounddate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        startActivity(intent);
    }
}
