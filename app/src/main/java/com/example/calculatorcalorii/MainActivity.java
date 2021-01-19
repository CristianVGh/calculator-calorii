package com.example.calculatorcalorii;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase caloriesDB;
    EditText usernameField;
    EditText passwordField;

    Button loginButton;
    Button singupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CaloriesHelper dbHelper = new CaloriesHelper(this);
        caloriesDB = dbHelper.getWritableDatabase();

        usernameField = findViewById(R.id.user);
        passwordField = findViewById(R.id.password);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                usernameField.setText("");
                passwordField.setText("");
                }
        });

        singupButton = findViewById(R.id.registerButton);
        singupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUser(usernameField.getText().toString(), passwordField.getText().toString());
                usernameField.setText("");
                passwordField.setText("");
            }
        });
    }

    private void newUser(String username, String password){{
        ContentValues cv = new ContentValues();
        cv.put(CaloriesContract.UsersEntry.COLUMN_USERNAME, username);
        cv.put(CaloriesContract.UsersEntry.COLUMN_PASSWORD, password);
        caloriesDB.insert(CaloriesContract.UsersEntry.TABLE_NAME, null, cv);
        Toast.makeText(this, "Registered Successfully", Toast.LENGTH_LONG).show();
    }}

    private void login(){
        String selection = CaloriesContract.UsersEntry.COLUMN_USERNAME + " = ? AND " +
                CaloriesContract.UsersEntry.COLUMN_PASSWORD + " = ? ";
        String[] values = {usernameField.getText().toString(), passwordField.getText().toString()};

        Cursor cursor = caloriesDB.query(
                CaloriesContract.UsersEntry.TABLE_NAME,
                null,
                selection,
                values,
                null,
                null,
                CaloriesContract.UsersEntry.COLUMN_USERNAME
        );
        if (cursor.moveToNext()){
            long userID = cursor.getLong(cursor.getColumnIndex(CaloriesContract.UsersEntry._ID));
            String username = cursor.getString(cursor.getColumnIndex(CaloriesContract.UsersEntry.COLUMN_USERNAME));

            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("id", userID);
            intent.putExtra("user", username);
            startActivity(intent);
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Username or password incorrect",Toast.LENGTH_SHORT ).show();

    }

}