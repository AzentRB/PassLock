package com.example.passlock;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button btnlogin;
    DBHelper DB;

   // SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        btnlogin = (Button) findViewById(R.id.btnsignin1);
        DB = new DBHelper(this);
        //sharedpreferences = getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if(user.equals("") || pass.equals(""))
                {
                    Toast.makeText(LoginActivity.this,"Please Fill all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                   // SharedPreferences.Editor editor = sharedpreferences.edit();
                    boolean checkuserpass = DB.chechusernamepassword(user,pass);
                    if(checkuserpass)
                    {

                        //editor.putString("Username", user);
                        //editor.apply();

                        Toast.makeText(LoginActivity.this,"Signed in", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("username", user);

                        startActivity(intent);

                    }

                    else
                    {
                        Toast.makeText(LoginActivity.this,"Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }
}