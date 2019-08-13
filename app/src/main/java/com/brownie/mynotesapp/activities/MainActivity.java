package com.brownie.mynotesapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.brownie.mynotesapp.R;
import com.brownie.mynotesapp.adapters.MyListViewAdapter;
import com.brownie.mynotesapp.model.Note;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private ListView listView;

    private ArrayList<Note> noteList, savedNoteList;

    private MyListViewAdapter myListViewAdapter;

    private EditText addReminder;

    private ImageButton btnAddReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init()
    {
        addReminder = findViewById(R.id.main_et_note);
        btnAddReminder = findViewById(R.id.btn_add_reminder);
        btnAddReminder.setOnClickListener(this);

        savedNoteList = new ArrayList<>();
        savedNoteList = getData();

        if(!(getData() == null))
        {
            noteList = savedNoteList;
        }
        else
        {
            noteList = new ArrayList<>();
        }
        //noteList.add(new Note("This is the first reminder","12:00 pm"));

        myListViewAdapter = new MyListViewAdapter(MainActivity.this, R.layout.list_view_cell, noteList);

        listView = findViewById(R.id.main_list_view);
        listView.setAdapter(myListViewAdapter);

        setUpListItemClickListener();
    }

    private void setUpListItemClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "Note " + position + " clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_add_reminder:
            {
                if(!addReminder.getText().toString().isEmpty())
                {

                    addReminder.clearComposingText();
                    addReminder.clearFocus();

                    Note newNote = new Note();
                    newNote.setrDescription(addReminder.getText().toString());
                    newNote.setrTime("Time not set");

                    noteList.add(newNote);

                    addReminder.setText("");
                    myListViewAdapter.notifyDataSetChanged();


                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigate, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_logout)
        {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout()
    {
        Toast.makeText(this, "Logged Out Successfully!", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, LoginActivity.class));
    }

    private void saveData(ArrayList<Note> noteList)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(noteList);
        editor.putString("Note", json);
        editor.apply();

    }

    private ArrayList<Note> getData()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        Gson gson = new Gson();
        String json = prefs.getString("Note", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @Override
    protected void onPause() {
        super.onPause();

        saveData(noteList);
    }
}
