package com.example.calculatorcalorii;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeActivity extends AppCompatActivity {
    private SQLiteDatabase caloriesDB;

    Button adaugaButton;
    Button istoricButton;

    long userID;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CaloriesHelper dbHelper = new CaloriesHelper(this);
        caloriesDB = dbHelper.getWritableDatabase();

        adaugaButton = findViewById(R.id.addButton);
        adaugaButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeFragment(v);
            }
        });

        istoricButton = findViewById(R.id.historyButton);
        istoricButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeFragment(v);
            }
        });

        Intent intent = getIntent();
        userID = intent.getLongExtra("id", -1);
        username = intent.getStringExtra("user");
        setTitle(username);
    }

    private void changeFragment(View view){
        Fragment fragment;

        if(view == findViewById(R.id.addButton)){
            fragment = new AddFragment();
            Bundle bundle = new Bundle();
            bundle.putLong("id", userID);
            fragment.setArguments(bundle);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragmentPlace, fragment);
            ft.commit();
        }

        if(view == findViewById(R.id.historyButton)){
            fragment = new HistoryFragment();
            Bundle bundle = new Bundle();
            bundle.putString("user", username);
            fragment.setArguments(bundle);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragmentPlace, fragment);
            ft.commit();
        }
    }

}
