package com.olagoke.ottmotel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class BookingsActivity extends AppCompatActivity {

  /**
   * The Booking Activty class is the activity in making bookings. This is where
   * the guests can see their bookings
   *
   * @author  Alex Olagoke
   * @version 1.0
   * @since   2023-07-14
   */

  private ListView bookingsListView;
  private TextView noBookingsTextView;
  private String email;
  private SQLiteDatabase database;
  private Cursor cursor;
  private BookingAdapter adapter;


  ArrayList<Booking> bookings;


  DBHelper dbHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bookings);



    SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
    dbHelper = new DBHelper(getApplicationContext());
    bookings = new ArrayList<>();
    email = sharedPreferences.getString("email","");

    loadBookings(email);
    // Get the moteller name from the Intent
    Intent intent = getIntent();

    // Set the moteller name in the layout
    TextView motellerNameTextView = findViewById(R.id.moteller_name);
    motellerNameTextView.setText(email);



    // Find the views in the layout
    bookingsListView = findViewById(R.id.bookings_list);
    noBookingsTextView = findViewById(R.id.no_bookings_message);

    adapter = new BookingAdapter(this,bookings);

    bookingsListView.setAdapter(adapter);

    // Initialize the adapter and set it to the ListView
/*    adapter = new BookingCursorAdapter(this, cursor);
    bookingsListView.setAdapter(adapter);*/


    // Show a message if there are no bookings
    if (bookings.size() == 0) {
      noBookingsTextView.setVisibility(View.VISIBLE);
    } else {
      noBookingsTextView.setVisibility(View.GONE);
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    // Close the cursor and database to prevent leaks
    dbHelper.close();
  }


  public void loadBookings(String email){
    // Get all todos from database and add to arraylist
    List<Booking> bookings = dbHelper.getBookings(email);
    this.bookings.addAll(bookings);
  }


  @Override
  public void onBackPressed() {

    if (this instanceof BookingsActivity) {

      Intent intent = new Intent(this, MotellerActivity.class);
      startActivity(intent);
      finish();
    }
  }
}
