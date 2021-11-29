package com.onv.jalankuy.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.onv.jalankuy.R;
import com.onv.jalankuy.database.DatabaseHelper;

public class ProfileActivity extends AppCompatActivity {

    protected Cursor cursor;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    //SessionManager session;
    String name, email, hp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        dbHelper = new DatabaseHelper(this);
//
//        session = new SessionManager(getApplicationContext());
//
//        HashMap<String, String> user = session.getUserDetails();
//        email = user.get(SessionManager.KEY_EMAIL);

//        db = dbHelper.getReadableDatabase();
//        cursor = db.rawQuery("SELECT * FROM TB_USER WHERE username = '" + email + "'", null);
//        cursor.moveToFirst();
//        if (cursor.getCount() > 0) {
//            cursor.moveToPosition(0);
//            name = cursor.getString(2);
//        }

//        TextView txtName = findViewById(R.id.txtName);
//        TextView txtEmail = findViewById(R.id.txtEmail);
//        TextView txtnoHP = findViewById(R.id.txtnoHP);
//
//        txtName.setText(name);
//        txtEmail.setText(email);
//        txtnoHP.setText(hp);

        setupToolbar();

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbProfile);
        toolbar.setTitle("Identitas Pengguna");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}