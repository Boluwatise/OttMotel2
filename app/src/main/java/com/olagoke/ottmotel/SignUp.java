package com.olagoke.ottmotel;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

  /**
   * The Signup Activty class is the activity in signing up users
   *
   * @author  Alex Olagoke
   * @version 1.0
   * @since   2023-07-14
   */

  private EditText mUsernameEditText;
  private EditText mPasswordEditText;
  private EditText mFirstNameEditText;
  private EditText mLastNameEditText;
  private EditText mEmailEditText;
  private Button mSignupButton;
  private SQLiteDatabase mDatabase;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);

    // Initialize views
    mUsernameEditText = findViewById(R.id.username_edittext);
    mPasswordEditText = findViewById(R.id.password_edittext);
    mFirstNameEditText = findViewById(R.id.firstname_edittext);
    mLastNameEditText = findViewById(R.id.lastname_edittext);
    mEmailEditText = findViewById(R.id.email_edittext);
    mSignupButton = findViewById(R.id.signup_button);

    // Initialize database
    try {
      mDatabase = new DBHelper(this).getWritableDatabase();
    }catch (Exception e){
      throw (e);
    }

    // Set click listener for signup button
    mSignupButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Get input values
        String username = mUsernameEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        String firstName = mFirstNameEditText.getText().toString().trim();
        String lastName = mLastNameEditText.getText().toString().trim();
        String email = mEmailEditText.getText().toString().trim();

        // Validate input values
        if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
          Toast.makeText(SignUp.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
          return;
        }

        // Check if username already exists
        if (checkUsernameExists(username)) {
          Toast.makeText(SignUp.this, "Username already exists", Toast.LENGTH_SHORT).show();
          return;
        }

        // Insert user data into database
        ContentValues values = new ContentValues();
        values.put("name", firstName+" "+lastName);
        values.put("password", password);
        //values.put("firstname", firstName);
        //values.put("lastname", lastName);
        values.put("email", email);
        values.put("role", "moteller");

        mDatabase.insert("users", null, values);

        // Display success message and finish activity
        Toast.makeText(SignUp.this, "Account created successfully", Toast.LENGTH_SHORT).show();

        finish();
      }
    });
  }

  private boolean checkUsernameExists(String email) {
    Cursor cursor = mDatabase.rawQuery("SELECT * FROM   users  WHERE users.email = ?", new String[]{email});
    boolean exists = cursor.getCount() > 0;
    cursor.close();
    return exists;
  }
}
