package com.example.dataapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// Add items page
public class HomeActivity extends AppCompatActivity {
    EditText name, value;
    Button insert, update, delete, view;
    HomeDBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);
        name = (EditText) findViewById(R.id.editTextName);
        value = (EditText) findViewById(R.id.editTextValue);
        insert = findViewById(R.id.AddEmployee);
        view = findViewById(R.id.ViewEmployees);
        DB = new HomeDBHelper(this);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String valueTXT = value.getText().toString();

                Boolean checkinsertdata = DB.insertuserdata(nameTXT, valueTXT);
                if(checkinsertdata==true)
                    Toast.makeText(HomeActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(HomeActivity.this, "New Entry Rejected", Toast.LENGTH_SHORT).show();
            }        });

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewActivity.class);
                startActivity(intent);
            }
        });
    }}