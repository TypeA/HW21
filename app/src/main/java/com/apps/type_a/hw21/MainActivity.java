package com.apps.type_a.hw21;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.type_a.hw21.helpers.DBHelper;
import com.apps.type_a.hw21.helpers.Person;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DBHelper dbHelper;
    private Button btnAdd, btnRead, btnClear;
    private EditText etFullName, etAge;
    private RecyclerView personsList;
    private ArrayList<Person> persons = new ArrayList<Person>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDBHelper();
        initViews();
        setButtonsListener();
    }

    private void initDBHelper() {
        dbHelper = new DBHelper(this);
    }

    private void initViews() {
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        btnRead = (Button) findViewById(R.id.buttonLoad);
        btnClear = (Button) findViewById(R.id.buttonClear);
        etFullName = (EditText) findViewById(R.id.editFullName);
        etAge = (EditText) findViewById(R.id.editAge);
        personsList = (RecyclerView) findViewById(R.id.personsList);
    }

    private void fillPersonsList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        personsList.setLayoutManager(layoutManager);
        personsList.setAdapter(new PersonListAdapter(persons, this));
    }

    private void setButtonsListener() {
        btnAdd.setOnClickListener(this);
        btnRead.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (view.getId()) {
            case R.id.buttonAdd: {
                String name = "", surname = "";
                int age =0;
                String fName[] = etFullName.getText().toString().split(" ");
                if (fName.length < 2) {
                    Toast.makeText(this, getText(R.string.incorrect_name_toast), Toast.LENGTH_SHORT).show();
                } else {
                    name = fName[0];
                    surname = fName[1];
                }
                try {
                    age = Integer.valueOf(etAge.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(this,getText(R.string.incorrect_age_toast),Toast.LENGTH_SHORT).show();
                }
                if (!name.isEmpty() && !surname.isEmpty() && age != 0) {
                    cv.put("name",name);
                    cv.put("surname",surname);
                    cv.put("age",age);
                    db.insert("person",null,cv);
                }
                break;
            }
            case R.id.buttonLoad: {
                Cursor cursor = db.query("person", null, null, null, null, null, null);
                if(cursor.moveToFirst()) {
                    int nameColIndex = cursor.getColumnIndex("name");
                    int surnameColIndex = cursor.getColumnIndex("surname");
                    int ageColIndex = cursor.getColumnIndex("age");
                    do {
                        persons.add(new Person(cursor.getString(nameColIndex),cursor.getString(surnameColIndex),cursor.getInt(ageColIndex)));
                    } while (cursor.moveToNext());
                    fillPersonsList();
                } else {
                    Toast.makeText(this,getText(R.string.empty_db),Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.buttonClear: {
                int clearCount = db.delete("person", null, null);
                Toast.makeText(this,getText(R.string.cb_cleared).toString()+clearCount,Toast.LENGTH_SHORT).show();
                break;
            }
        }
        dbHelper.close();
    }
}
