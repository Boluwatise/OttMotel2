package com.olagoke.ottmotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
  /**
   * The Main Activty class is used as the main point after authentication
   *
   * @author  Alex Olagoke
   * @version 1.0
   * @since   2023-07-14
   */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      SharedPreferences prefs = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
      boolean isLoggedIn = prefs.getBoolean("is_logged_in", false);
      boolean isFirstLaunch = prefs.getBoolean("is_first_launch", true);

      if (isFirstLaunch) {
        // Run the method you want to execute on the first launch here
        // ...

        DBHelper dbHelper = new DBHelper(this);

        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
          dbHelper.seedDatabase(db);
        } catch (SQLiteException e) {
          // Handle database errors here
          throw (e);
        }



        // Set the flag to indicate that the app has been launched
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_first_launch", false);
        editor.apply();
      }
      if (!isLoggedIn) {
        // show login screen
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        Toast.makeText(this,"Welcome to Ott Motel",Toast.LENGTH_SHORT).show();
      }else{
        Intent intent = new Intent(this, MotellerActivity.class);
        startActivity(intent);

      }
    }
}
