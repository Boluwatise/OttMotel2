package com.olagoke.ottmotel;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MotellerActivity extends AppCompatActivity {

  private DrawerLayout mDrawer;
  private Toolbar toolbar;
  private NavigationView nvDrawer;

  // Make sure to be using androidx.appcompat.app.ActionBarDrawerToggle version.
  private ActionBarDrawerToggle drawerToggle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_moteller);

    // Get the string value for the "my_string_key" key, with a default value of ""
    SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);

    String myString = sharedPreferences.getString("email", "");
    Toast.makeText(this,myString,Toast.LENGTH_SHORT).show();

    // Set a Toolbar to replace the ActionBar.
    toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // This will display an Up icon (<-), we will replace it with hamburger later
    // Find our drawer view
    mDrawer =  findViewById(R.id.drawer_layout);
    drawerToggle = setupDrawerToggle();

    // Setup toggle to display hamburger icon with nice animation
    drawerToggle.setDrawerIndicatorEnabled(true);
    drawerToggle.syncState();

    // Tie DrawerLayout events to the ActionBarToggle
    mDrawer.addDrawerListener(drawerToggle);

    // Find our drawer view
    mDrawer =  findViewById(R.id.drawer_layout);
    nvDrawer = findViewById(R.id.nvView);
    // Setup drawer view
    setupDrawerContent(nvDrawer);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_info, menu);
    return true;
  }

  public void toastMessage(MenuItem item) {
    Toast.makeText(this, "You clicked on " + item.getTitle(), Toast.LENGTH_LONG).show();
  }

  public void logOut(MenuItem item) {
    SharedPreferences prefs = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = prefs.edit();
    editor.remove("is_logged_in");
    editor.remove("email");
    editor.remove("motellerId");
    editor.apply();
    Intent intent = new Intent(this, LoginActivity.class);
    startActivity(intent);

  }


  private ActionBarDrawerToggle setupDrawerToggle() {
    // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
    // and will not render the hamburger icon without it.
    return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
  }

  private void setupDrawerContent(NavigationView navigationView) {
    navigationView.setNavigationItemSelectedListener(
      new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
          selectDrawerItem(menuItem);
          return true;
        }
      });
  }

  public void selectDrawerItem(MenuItem menuItem) {
    Class<?> activityClass = null;
    switch (menuItem.getItemId()) {
      case R.id.fragment_host:
        activityClass = MotellerActivity.class;
        break;
      case R.id.nav_second_fragment:
        activityClass = BookingsActivity.class;
        break;
      case R.id.nav_third_fragment:
        finishAffinity();
        return;
      default:
        return;
    }

    // Check if the selected activity is already running
    if (!activityClass.isInstance(this)) {
      Intent intent = new Intent(this, activityClass);
      startActivity(intent);
      finish();
    }

    // Highlight the selected item has been done by NavigationView
    //menuItem.setChecked(true);
    // Set action bar title
    setTitle(menuItem.getTitle());
    // Close the navigation drawer
    mDrawer.closeDrawers();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // The action bar home/up action should open or close the drawer.
    switch (item.getItemId()) {
      case android.R.id.home:
        mDrawer.openDrawer(GravityCompat.START);
        return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {

    finishAffinity();

  }

}
