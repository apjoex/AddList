package com.addlist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import connectors.Database;
import models.User;


public class MainActivity extends ActionBarActivity {

    Context context;
    EditText first, last;
    Button send, show;
    Database database;
    Spinner sex;
    Long id = System.currentTimeMillis();

    public MainActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        database = new Database(this);

        Spinner dropdown = (Spinner)findViewById(R.id.PickGender);
        String[] items = new String[]{"Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        first = (EditText)findViewById(R.id.editText);
        last = (EditText)findViewById(R.id.editText2);
        send = (Button)findViewById(R.id.button);
        show = (Button)findViewById(R.id.button2);
        sex = (Spinner)findViewById(R.id.PickGender);

        EnlistClick();
        ShowListClick();
    }


    private void ShowListClick() {
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, "Showing...", Toast.LENGTH_SHORT).show();
                Intent intent;
                intent = new Intent(context, UserList.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }

    private void EnlistClick() {
      send.setOnClickListener(new View.OnClickListener() {

          @Override
          public void onClick(View v) {
              if(Validator()) {
                  saveData(first.getText().toString(), last.getText().toString(), id, String.valueOf(sex.getSelectedItem()));
                  Toast.makeText(context, "Saving...", Toast.LENGTH_SHORT).show();
                  first.setText("");
                  last.setText("");
              }

          }
      });

    }

    private void saveData(String first, String last, Long id, String sex) {
        ArrayList<User> ourUsers = new ArrayList<User>();
        ourUsers.add(new User(first, last, System.currentTimeMillis(), sex));
        database.saveUser(ourUsers);
        database.close();
    }

    private boolean Validator() {
        if(first.getText().toString().equals("")){
            Toast.makeText(context, "No first name...", Toast.LENGTH_SHORT).show();
        return false;
    }else if (last.getText().toString().equals("")) {
            Toast.makeText(context, "No last name...", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent;
            intent = new Intent(context, About.class);
            MainActivity.this.startActivity(intent);
            return true;
        }

        if (id == R.id.contact){
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "apjoex@gmail.com", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
            startActivity(Intent.createChooser(emailIntent, "Send email..."));

        }

        return super.onOptionsItemSelected(item);
    }
}
