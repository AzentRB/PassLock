package com.example.passlock;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    EditText entityname, entitypass;
    Button add;
    DBHelper DB;

    String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        entityname = findViewById(R.id.entity);
        entitypass = findViewById(R.id.enterpass);
        add = findViewById(R.id.btnadd);
        DB = new DBHelper(this);

        if(getIntent().getExtras() != null) {
            user_name = getIntent().getExtras().getString("username");
        }
        final String key = user_name;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int shift =5;

                String pass = entitypass.getText().toString();
                String ename = entityname.getText().toString();

                String f_pass = AES.encrypt(pass,key);
                f_pass =f_pass.toString();

                if (f_pass.equals("") || ename.equals("")) {
                    Toast.makeText(AddActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkuser = DB.checkusername(user_name);
                    if (checkuser) {
                        Boolean insert = DB.insert_data(ename,user_name, f_pass);
                        if (insert) {
                            Toast.makeText(AddActivity.this, "Entered Successfully", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                        else
                        {
                            Toast.makeText(AddActivity.this, "Error inserting new data", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(AddActivity.this, "Authentication Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}