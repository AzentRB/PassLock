package com.example.passlock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

public class copyPass extends AppCompatActivity {

    ClipboardManager clipboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        copy();
    }

    private void copy() {
        String pass ="";
        DBHelper DB = new DBHelper(this);
        setContentView(R.layout.activity_copy_pass);
        String user = getIntent().getStringExtra("username");
        String entity = getIntent().getStringExtra("entity");
        Cursor cursor = DB.get_decrypted(user, entity);
        if (cursor.getCount() == 0)
        {
            Toast.makeText(this, "No data to show", Toast.LENGTH_LONG).show();
        }
        else
        {
            cursor.moveToFirst();
            pass = cursor.getString(2);
            clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            pass = AES.decrypt(pass,user);
            ClipData clip = ClipData.newPlainText("pass", pass);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(copyPass.this, "Password Copied", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra("username", user);
            startActivity(intent);
        }
    }
}