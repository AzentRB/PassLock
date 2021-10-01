package com.example.passlock;

import android.content.ClipData;
import android.content.Context;
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
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        elist = findViewById(R.id.listentity);

        listEntity = new ArrayList<>();
        DB=new DBHelper(getApplicationContext());

        if(getIntent().getExtras() != null) {
            user_name = getIntent().getExtras().getString("username");
        }

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
                Cursor cursor=DB.get_decrypted(user_name,listEntity.get(position));
                if (cursor.getCount() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Error! Could not find data", Toast.LENGTH_LONG).show();
                }
                else
                {
                    cursor.moveToFirst();
                    String pass = cursor.getString(2);
                    clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    pass = AES.decrypt(pass,user_name);
                    ClipData clip = ClipData.newPlainText("pass", pass);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getApplicationContext(), "Password Copied", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData(user_name);
    }

    private void refreshData(String usn) {
        Cursor cursor = DB.viewData(usn);
        listEntity.clear();
        if (cursor.getCount() == 0)
        {
            Toast.makeText(this, "No Data to show", Toast.LENGTH_SHORT).show();
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