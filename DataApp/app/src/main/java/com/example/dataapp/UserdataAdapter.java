package com.example.dataapp;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.List;
// This handles all user data CRUD functionality
public class UserdataAdapter extends ArrayAdapter<Userdata> {
    // Init variables
    Context mCtx;
    int list_layout;
    List<Userdata> userdataList;
    HomeDBHelper DB;

    // Load userdata context
    public UserdataAdapter(Context mCtx, int list_layout, List<Userdata> userdataList, HomeDBHelper DB) {
        super(mCtx, list_layout, userdataList);

        this.mCtx = mCtx;
        this.list_layout = list_layout;
        this.userdataList = userdataList;
        this.DB = DB;
    }
    // Assign values
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(list_layout, null);

        // Get specific item
        Userdata userdata = userdataList.get(position);


        // Link views
        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewSalary = view.findViewById(R.id.textViewValue);

        // Assign data to views
        textViewName.setText(userdata.getName());
        textViewSalary.setText(String.valueOf(userdata.getValue()));

        // Link update and delete buttons
        Button buttonDelete = view.findViewById(R.id.buttonDeleteValue);
        Button buttonUpdate = view.findViewById(R.id.buttonEdit);

        // Add click listener to update button
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateItem(userdata);
            }
        });

        // Add click listener and functionality for Delete button
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pop up confirmation box
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    // Fetch DB Delete operation
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sql = "DELETE FROM employees WHERE id = ?";
                        DB.deletedata(userdata.name);
                        reloadEmployeesFromDatabase();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

    // Update item function
    private void updateItem(final Userdata userdata) {

        // Create pop up input box
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.dialog_update, null);
        builder.setView(view);

        final EditText editTextName = view.findViewById(R.id.editTextName);
        final EditText editTextSalary = view.findViewById(R.id.editTextValue);

        editTextName.setText(userdata.getName());
        editTextSalary.setText(String.valueOf(userdata.getValue()));

        final AlertDialog dialog = builder.create();
        dialog.show();

        // Fetch Update function in DB
        view.findViewById(R.id.buttonUpdateItems).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String value = editTextSalary.getText().toString().trim();

                if (name.isEmpty()) {
                    editTextName.setError("Name can't be blank");
                    editTextName.requestFocus();
                    return;
                }

                if (value.isEmpty()) {
                    editTextSalary.setError("Salary can't be blank");
                    editTextSalary.requestFocus();
                    return;
                }
                DB.updateuserdata(name, value);

                Toast.makeText(mCtx, "Item Updated", Toast.LENGTH_SHORT).show();
                reloadEmployeesFromDatabase();

                dialog.dismiss();
            }
        });
    }
    // Reload view after update
    private void reloadEmployeesFromDatabase() {
        Cursor res = DB.getdata();
        if (res.moveToFirst()) {
            userdataList.clear();
            // Loop all values
            do {
                // Fill list with vaues
                userdataList.add(new Userdata(
                        res.getString(0),
                        res.getDouble(1)
                ));
            } while (res.moveToNext());
        }
        res.close();
        notifyDataSetChanged();
    }
}