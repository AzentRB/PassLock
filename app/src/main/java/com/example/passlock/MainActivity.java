package com.example.passlock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText username, pass, repass;
    Button signup, signin;
    DBHelper DB;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
        repass = (EditText) findViewById(R.id.repassword);
        signup =(Button) findViewById(R.id.btnsignup);
        signin =(Button) findViewById(R.id.btnsignin);
        DB = new DBHelper(this);






        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String password = pass.getText().toString();
                String rep = repass.getText().toString();

                if(user.equals("") || password.equals("") || rep.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Please Enter all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(password.equals(rep))
                    {
                        boolean checkuser = DB.checkusername(user);
                        if(!checkuser)
                        {
                            Boolean insert = DB.insertData(user,password);

                            if(insert)
                            {
                                Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                intent.putExtra("username", user);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "User already exists.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });
    }
}