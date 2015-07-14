package connectors;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import java.util.ArrayList;

import models.User;

/**
 * Created by AKINDE-PETERS on 7/12/2015.
 */
public class Database extends SQLiteOpenHelper {
    static String DATABASE_NAME = "our_db";
    static int DATABASE_VERSION = 1;

    public Database(Context context){
        super(context, DATABASE_NAME, (CursorFactory) null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (_id INTEGER PRIMARY KEY AUTOINCREMENT,first TEXT,last TEXT, id LONG, gender TEXT);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveUser(ArrayList <User> users){
        for(int g = 0;g< users.size();g++){
     //       String query = " SELECT * FROM users WHERE first = '"+users.get(g).getFirst()+"'";
       //     Cursor cursor = getReadableDatabase().rawQuery(query,null);

            ContentValues cv = new ContentValues();
            cv.put("first", users.get(g).getFirst());
            cv.put("last", users.get(g).getLast());
            cv.put("id", users.get(g).getId());
            cv.put("gender", users.get(g).getGender());
         //   if(cursor.getCount() + 0 == 0){
                getWritableDatabase().insert("users","first", cv);
           // }else{
             //   String[] args={users.get(g).getFirst()};
              //  getWritableDatabase().update("users",cv,"first=?", args);
            //}
            //cursor.close();
        }
    }

    public ArrayList <User> getUsers(){
        ArrayList<User> user = new ArrayList<User>();
        String query = " SELECT * FROM users";
        Cursor cursor = getReadableDatabase().rawQuery(query,null);
        while(cursor.moveToNext()){
            user.add(new User(
                    cursor.getString(cursor.getColumnIndex("first")),
                    cursor.getString(cursor.getColumnIndex("last")),
                    cursor.getLong(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("gender"))
                    ));
        }
        cursor.close();
        return user;
    }


    public void deleteUser(Long user_id){
        String query = "DELETE FROM users WHERE id ='"+user_id+"'";
        getWritableDatabase().execSQL(query);
    }



}





