package com.royan.datatbaseproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity
{
    DatabaseHelper db;
    UserAdapter userAdapter;
    private Button btnAddUser;
    private ListView lstAllUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnAddUser = findViewById(R.id.btnAddUser);
        lstAllUser = findViewById(R.id.lstAllUser);
        btnAddUser.setOnClickListener(new AddRandomUserListener());

        db = new DatabaseHelper(this);
        List<User> lstUser = getListUserFromDb();
        userAdapter = new UserAdapter(lstUser);
        lstAllUser.setAdapter(userAdapter);
        lstAllUser.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l)
            {
                User user = userAdapter.getItem(pos);
                Intent myIntent = new Intent(MainActivity.this, UpdateUserActivity.class);
                myIntent.putExtra("first", user.getFirst());
                myIntent.putExtra("last", user.getLast());
                startActivity(myIntent);
            }
        });

        lstAllUser.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l)
            {
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("You won't be able to recover this row!")
                        .setConfirmText("Delete!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                        {
                            @Override
                            public void onClick(SweetAlertDialog sDialog)
                            {
                                sDialog.dismissWithAnimation();
                                User user = userAdapter.getItem(pos);
                                db.deleteUser(user);
                                List<User> lstUser = getListUserFromDb();
                                userAdapter.updateList(lstUser);
                                userAdapter.notifyDataSetChanged();
                            }
                        })
                        .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener()
                        {
                            @Override
                            public void onClick(SweetAlertDialog sDialog)
                            {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();

                return true;
            }
        });

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        List<User> lstUser = getListUserFromDb();
        userAdapter.updateList(lstUser);
        userAdapter.notifyDataSetChanged();
    }

    private class GetContacts extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args)
        {
            BufferedReader br = null;
            HttpURLConnection urlConnection = null;
            String strResponse = null;
            try
            {


                String url = args[0];

                URL urlObj = new URL(url);

                // urlConnection = (HttpURLConnection) urlObj.openConnection();
                // URLConnection connection = url.openConnection();


                //URL url = new URL(urls[0]);
                URLConnection connection = urlObj.openConnection();


                // pobranie danych do InputStream
                InputStream in = new BufferedInputStream(connection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null)
                {
                    stringBuilder.append(line + "\n");
                }

                reader.close();

                strResponse = stringBuilder.toString();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if (br != null)
                    {
                        br.close();
                    }

                    if (urlConnection != null)
                    {
                        urlConnection.disconnect();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            return strResponse;
        }


        @Override
        protected void onPostExecute(String strResponse)
        {
            try
            {
                JSONObject jsonObj = new JSONObject(strResponse);
                JSONArray result = jsonObj.getJSONArray("results");
                JSONObject userObj = result.getJSONObject(0);
                User user = new User(userObj);
                addUserToDb(user);
                List<User> lstUser = getListUserFromDb();
                userAdapter.updateList(lstUser);
                userAdapter.notifyDataSetChanged();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }


    private void addUserToDb(User user)
    {
        db.insertData(user);

    }

    private List<User> getListUserFromDb()
    {
        Cursor cursor = db.getAllData();
        List<User> lstUser = new ArrayList<>();


        while (cursor.moveToNext())
        {
            String title = cursor.getString(1);
            String first = cursor.getString(2);
            String last = cursor.getString(3);
            String gender = cursor.getString(4);
            String street = cursor.getString(5);
            String city = cursor.getString(6);
            String country = cursor.getString(7);
            String postcode = cursor.getString(8);
            User user = new User(title, first, last, gender, street, city, country, postcode);
            lstUser.add(user);
        }
        return lstUser;
    }

    private class AddRandomUserListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            try
            {
                GetContacts contacts = new GetContacts();
                String url = "https://randomuser.me/api/";
                contacts.execute(url);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

}
