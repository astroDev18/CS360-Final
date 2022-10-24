package com.example.dataapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

// This creates the homepage list the user can view all items at
public class ViewActivity extends AppCompatActivity {

    // Init variables
    List<Userdata> userdataList;
    HomeDBHelper DB;
    ListView listViewUserdata;
    UserdataAdapter adapter;

    // Link values
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        DB = new HomeDBHelper(this);

        listViewUserdata = (ListView) findViewById(R.id.listViewItems);
        userdataList = new ArrayList<>();

        showItemsFromDatabase();
    }

    // Function to load all user items
    private void showItemsFromDatabase() {

        Cursor res = DB.getdata();
        if (res.getCount() == 0) {
            Toast.makeText(ViewActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }

        if (res.moveToFirst()) {
            //looping through all the records
            do {
                //pushing each record in the employee list
                userdataList.add(new Userdata(
                        res.getString(0),
                        res.getDouble(1)
                ));
            } while (res.moveToNext());
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Name :" + res.getString(0) + "\n");
            buffer.append("Values :" + res.getString(1) + "\n");
            buffer.append("Date :" + res.getString(2) + "\n");
        }
        //closing the cursor
        res.close();

        //creating the adapter object
        adapter = new UserdataAdapter(this, R.layout.list_layout, userdataList, DB);

        //adding the adapter to listview
        listViewUserdata.setAdapter(adapter);
    }

}
