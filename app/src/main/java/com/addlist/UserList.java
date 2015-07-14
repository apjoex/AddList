package com.addlist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import connectors.Database;
import models.User;

import static android.content.DialogInterface.*;
import static android.widget.Toast.LENGTH_SHORT;


public class UserList extends ActionBarActivity {

    ListView userListView;
    Database database;
    ArrayList<User> users = new ArrayList<User>();
    Context context;

  //  public UserList(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        context = this;
        database = new Database(this);
        userListView = (ListView)this.findViewById(R.id.listView);
        getUserFromDB();
        ItemClick();
    }


    private void ItemClick() {
        context = this;
       userListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

               OnClickListener dialogClickListener = new OnClickListener(){

                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       users = database.getUsers();
                       switch(which) {
                           case BUTTON_POSITIVE:
                              // Toast.makeText(context, "You chose yes", LENGTH_SHORT).show();
                               database.deleteUser(users.get(position).getId());
                               getUserFromDB();
                               break;
                           case BUTTON_NEGATIVE:
                              // Toast.makeText(context, "You chose No", LENGTH_SHORT).show();
                               break;
                       };
                   }
               };

               //Toast.makeText(context, "You have chosen " + users.get(position).getFirst() + " ", LENGTH_SHORT).show();
               AlertDialog.Builder builder = new AlertDialog.Builder(context);
               builder.setMessage("Are you sure you want to delete "+users.get(position).getFirst()+"?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No",dialogClickListener).show();
               return false;
           }
       });
    }

    private void getUserFromDB() {
        users = database.getUsers();
        database.close();
       // Toast.makeText(context, users.size()+"" , Toast.LENGTH_SHORT).show();
        displayUser();
    }

    private void displayUser() {
      //  Toast.makeText(context, "UserList showing", LENGTH_SHORT).show();
        ArrayAdapter<User> userArrayAdapter;
        userArrayAdapter = new ArrayAdapter<User>(context,android.R.layout.simple_list_item_1, users);
        userListView.setAdapter(userArrayAdapter);
       // Toast.makeText(context, "showing finish", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_view, menu);
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
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
