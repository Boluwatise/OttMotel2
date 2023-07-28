package com.olagoke.ottmotel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

  /**
   * The Db helper helps us in managing the sqlite database
   *
   * @author  Alex Olagoke
   * @version 1.0
   * @since   2023-07-14
   */
  private static final String DATABASE_NAME = "MotelBooking.db";
  private static final int DATABASE_VERSION = 1;

  private static final String CREATE_TABLE_USERS =
    "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, password TEXT, role TEXT)";

  private static final String CREATE_TABLE_BOOKINGS =
    "CREATE TABLE bookings (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, room_id INTEGER, check_in_time TEXT, check_out_time TEXT,FOREIGN KEY(user_id) REFERENCES users(id), FOREIGN KEY(room_id) REFERENCES rooms(id))";

  private static final String CREATE_TABLE_ROOMS =
    "CREATE TABLE rooms (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT,price INTEGER)";


  public DBHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_TABLE_USERS);
    db.execSQL(CREATE_TABLE_BOOKINGS);
    db.execSQL(CREATE_TABLE_ROOMS);

  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS users");
    db.execSQL("DROP TABLE IF EXISTS bookings");
    onCreate(db);
  }


  public ArrayList<Booking> getBookings(String email) {
    SQLiteDatabase db = this.getReadableDatabase();
    ArrayList<Booking> bookingsList = new ArrayList<>();
    String query = "SELECT * FROM bookings " +
      "WHERE user_id = (SELECT id FROM users WHERE email = ?)";
    Cursor cursor = db.rawQuery(query, new String[]{email});
    //printCursor(cursor);
    int count=0;
    while (cursor.moveToNext()) {

      Integer booking_id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
      Integer user_id = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
      Integer room_id = cursor.getInt(cursor.getColumnIndexOrThrow("room_id"));

      String check_in_time = cursor.getString(cursor.getColumnIndexOrThrow("check_in_time"));
      String check_out_time = cursor.getString(cursor.getColumnIndexOrThrow("check_out_time"));

      count++;

      Booking booking = new Booking(booking_id, user_id,room_id,check_in_time,check_out_time);
      bookingsList.add(booking);


    }
    Log.i("Logs","Number of results in the cursor "+count);


    cursor.close();
    return bookingsList;
  }

  public void seedDatabase(SQLiteDatabase db) {

    // Seed admins
    ContentValues adminValues = new ContentValues();
    adminValues.put("name", "Admin 1");
    adminValues.put("email", "admin1@example.com");
    adminValues.put("password", "password1");
    adminValues.put("role", "admin");
    db.insert("users", null, adminValues);

    adminValues = new ContentValues();
    adminValues.put("name", "Admin 2");
    adminValues.put("email", "admin2@example.com");
    adminValues.put("password", "password2");
    adminValues.put("role", "admin");
    db.insert("users", null, adminValues);

    adminValues = new ContentValues();
    adminValues.put("name", "Admin 3");
    adminValues.put("email", "admin3@example.com");
    adminValues.put("password", "password3");
    adminValues.put("role", "admin");
    db.insert("users", null, adminValues);

    // Seed staff
    ContentValues staffValues = new ContentValues();
    for (int i = 1; i <= 10; i++) {
      staffValues.put("name", "Staff " + i);
      staffValues.put("email", "staff" + i + "@example.com");
      staffValues.put("password", "password" + i);
      staffValues.put("role", "staff");

      db.insert("users", null, staffValues);
    }

    // Seed motellers
    ContentValues motellerValues = new ContentValues();
    for (int i = 1; i <= 3; i++) {
      motellerValues.put("name", "Moteller " + i);
      motellerValues.put("email", "moteller" + i + "@example.com");
      motellerValues.put("password", "password" + i);
      motellerValues.put("role", "moteller");

      long motellerId = db.insert("users", null, motellerValues);

      // Seed rooms for each moteller
      ContentValues roomValues = new ContentValues();
      for (int j = 1; j <= 5; j++) {
        roomValues.put("name", "Room " + j);
        roomValues.put("price", (j * 10) + 50);

        long roomId = db.insert("rooms", null, roomValues);

        // Seed bookings for each room
        ContentValues bookingValues = new ContentValues();
        bookingValues.put("user_id", motellerId); // user_id = 1 is the first admin user
        bookingValues.put("room_id", roomId);
        bookingValues.put("check_in_time", "2023-04-15 12:00:00");
        bookingValues.put("check_out_time", "2023-04-16 12:00:00");
        db.insert("bookings", null, bookingValues);


      }
    }
  }


}
