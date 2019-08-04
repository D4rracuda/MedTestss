package com.example.dbbalalinjava;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.igalata.bubblepicker.BubblePickerListener;
import com.igalata.bubblepicker.adapter.BubblePickerAdapter;
import com.igalata.bubblepicker.model.BubbleGradient;
import com.igalata.bubblepicker.model.PickerItem;
import com.igalata.bubblepicker.rendering.BubblePicker;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    BubblePicker picker;
    Toolbar mTopToolbar;

    @Override
    protected void onResume() {
        super.onResume();
        picker.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void showDialogThird(){
        Bundle args = new Bundle();
        args.putString("title", "Меню");
        ActionBarDialog actionbarDialog = new ActionBarDialog();
        actionbarDialog.setArguments(args);
        actionbarDialog.show(getSupportFragmentManager(),
                "third_dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu) {
            showDialogThird();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTopToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
        setTitle("Дисциплины");

        //Переменная для работы с БД
        DataBaseHelper mDBHelper = new DataBaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        SQLiteDatabase mDb;
        mDb = mDBHelper.getWritableDatabase();


        picker = findViewById(R.id.picker);
        picker.setBubbleSize(1);
        picker.setCenterImmediately(true);

        ArrayList<HashMap<String, Object>> disciplines = new ArrayList<>();

        //Список параметров конкретного клиента
        HashMap<String, Object> discipline;

        //Отправляем запрос в БД
        Cursor cursor = mDb.rawQuery("SELECT * FROM disciplines", null);
        cursor.moveToFirst();
        //Пробегаем по всем клиентам
        while (!cursor.isAfterLast()) {
            discipline = new HashMap<>();
            if(cursor.getString(3).equals("institution")) {
                //Заполняем клиента
                discipline.put("name", cursor.getString(1));
                discipline.put("type", cursor.getString(3));
                //Закидываем клиента в список клиентов
                disciplines.add(discipline);
            }
            //Переходим к следующему клиенту
            cursor.moveToNext();
        }

        String[] preTitles = new String[disciplines.size()];

        cursor.moveToFirst();
        int n = 0;
        while (!cursor.isAfterLast()) {
            if(cursor.getString(3).equals("institution")) {
                preTitles[n] = cursor.getString(1);
                Log.e("map", preTitles[n]);
                n++;
            }
            cursor.moveToNext();
        }
        cursor.close();

        final String[] titles = getResources().getStringArray(R.array.disciplines);
        @SuppressLint("Recycle") final TypedArray colors = getResources().obtainTypedArray(R.array.colors);

        picker.setAdapter(new BubblePickerAdapter() {
            @Override
            public int getTotalCount() {
                return titles.length;
            }

            @NotNull
            @Override
            public PickerItem getItem(int position) {
                PickerItem item = new PickerItem();
                item.setTitle(titles[position]);
                item.setGradient(new BubbleGradient(colors.getColor((position * 2) % 8, 0),
                        colors.getColor((position * 2) % 8 + 1, 0), BubbleGradient.VERTICAL));
                item.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.white));
                return item;
            }
        });
        picker.setListener(new BubblePickerListener() {
            @Override
            public void onBubbleSelected(@NotNull PickerItem item) {
                Toast.makeText(getApplicationContext(),item.getTitle(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onBubbleDeselected(@NotNull PickerItem item) {

            }
        });
    }
}
