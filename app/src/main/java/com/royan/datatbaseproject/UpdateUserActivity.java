package com.royan.datatbaseproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateUserActivity extends AppCompatActivity
{
    DatabaseHelper db;
    private String first;
    private String last;

    EditText txtTitle;
    EditText txtFirstName;
    EditText txtLastName;
    EditText txtGender;
    EditText txtStreet;
    EditText txtCity;
    EditText txtCountry;
    EditText txtPostcode;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        db = new DatabaseHelper(this);
        Intent intent = getIntent();

        first = intent.getStringExtra("first");
        last = intent.getStringExtra("last");
        User user = db.getUser(first, last);

        txtTitle = findViewById(R.id.txtTitle);
        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtGender = findViewById(R.id.txtGender);
        txtStreet = findViewById(R.id.txtStreet);
        txtCity = findViewById(R.id.txtCity);
        txtCountry = findViewById(R.id.txtCountry);
        txtPostcode = findViewById(R.id.txtPostcode);
        Button btnUpdate = findViewById(R.id.btnUpdate);


        txtTitle.setText(user.getTitle());
        txtFirstName.setText(user.getFirst());
        txtLastName.setText(user.getLast());
        txtGender.setText(user.getGender());
        txtStreet.setText(user.getStreet());
        txtCity.setText(user.getCity());
        txtCountry.setText(user.getCountry());
        txtPostcode.setText(user.getPostcode());
        btnUpdate.setOnClickListener(new UpdateUserListener());

    }


    private class UpdateUserListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            try
            {
                String title = txtTitle.getText().toString();
                String first = txtFirstName.getText().toString();
                String last = txtLastName.getText().toString();
                String gender = txtGender.getText().toString();
                String street = txtStreet.getText().toString();
                String city = txtCity.getText().toString();
                String country = txtCountry.getText().toString();
                String postcode = txtPostcode.getText().toString();

                User user = new User(title, first, last, gender, street, city, country, postcode);
                db.updateUser(user, UpdateUserActivity.this.first, UpdateUserActivity.this.last);
                finish();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

}
