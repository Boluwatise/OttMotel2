package com.olagoke.ottmotel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

  /**
   * The Login Activty class where we sign up and authenticate users
   *
   * @author  Alex Olagoke
   * @version 1.0
   * @since   2023-07-14
   */

  private EditText etEmail, etPassword;
  private Button btnLogin;

  private DBHelper dbHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ProgressBar pb =  findViewById(R.id.loadingProgress);


    etEmail = findViewById(R.id.et_email);
    etPassword = findViewById(R.id.et_password);
    btnLogin = findViewById(R.id.btn_login);

    dbHelper = new DBHelper(this);

    btnLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String role = "moteller";
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
          Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
          return;
        }

        if (validateUser(email, password,role)) {
          // Login successful
          SharedPreferences prefs = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
          SharedPreferences.Editor editor = prefs.edit();
          pb.setVisibility(View.VISIBLE);
          editor.putBoolean("is_logged_in", true);
          editor.putString("email", email);
          editor.putInt("motellerId", getUserIdByEmail(email));
          editor.apply();
          Intent intent = new Intent(LoginActivity.this, MotellerActivity.class);
          startActivity(intent);
          finish();
        } else {
          // Login failed
          Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  public boolean validateUser(String email, String password, String role) {
    SQLiteDatabase db = dbHelper.getReadableDatabase();
      final String COLUMN_USER_ID = "id";
      final String COLUMN_USER_EMAIL = "email";
      final String COLUMN_USER_PASSWORD = "password";
      final String COLUMN_USER_ROLE = "role";
    String[] columns = {COLUMN_USER_ID};
    String selection = COLUMN_USER_EMAIL + " = ? AND " + COLUMN_USER_PASSWORD + " = ? AND " + COLUMN_USER_ROLE + " = ?";
    String[] selectionArgs = {email, password, role};

    Cursor cursor = db.query(
      "users",
      columns,
      selection,
      selectionArgs,
      null,
      null,
      null
    );

    boolean isValid = cursor.moveToFirst();

    cursor.close();
    db.close();

    return isValid;
  }

  public int getUserIdByEmail(String email) {
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    int id = -1;

    Cursor cursor = db.rawQuery("SELECT id FROM users WHERE email = ?", new String[] { email });
    if (cursor.moveToFirst()) {
      id = cursor.getInt(0);
    }
    cursor.close();

    return id;
  }

  public void signUp(View view){
    Intent intent = new Intent(LoginActivity.this, SignUp.class);
    startActivity(intent);
  }



}



