package com.example.passlock;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.content.ClipboardManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {

    String user_name;
    ListView elist;

    ArrayList<String> listEntity;
    ArrayAdapter<String> adapter;
    ClipboardManager clipboard;
    DBHelper DB = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        elist = findViewById(R.id.listentity);

        listEntity = new ArrayList<>();

        if(getIntent().getExtras() != null) {
            user_name = getIntent().getExtras().getString("username");
        }

        viewData(user_name);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = getIntent().getStringExtra("username");
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                intent.putExtra("username", user);
                startActivity(intent);
            }
        });
        
        elist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), copyPass.class);
                intent.putExtra("username", user_name);
                intent.putExtra("entity", listEntity.get(position));
                startActivity(intent);
            }
        });
    }



    private void viewData(String usn) {
        DBHelper DB = new DBHelper(this);
        Cursor cursor = DB.viewData(usn);
        if (cursor.getCount() == 0)
        {
            Toast.makeText(this, "No Data to show", Toast.LENGTH_LONG).show();
        }
        else
        {
            cursor.moveToFirst();
            do{
                listEntity.add(cursor.getString(0));
            }
            while (cursor.moveToNext());

            adapter = new ArrayAdapter<>(this, R.layout.list_text_view, listEntity);

            elist.setAdapter(adapter);
        }
    }
}