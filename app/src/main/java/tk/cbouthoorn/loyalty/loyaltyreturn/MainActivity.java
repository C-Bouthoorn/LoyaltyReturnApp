package tk.cbouthoorn.loyalty.loyaltyreturn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        // Enable navigation menu
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onStart() {
        super.onStart();

        // Hide menus
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        SharedPreferences sharedPreferences = getSharedPreferences(App.PRIVATE_PREFS, Context.MODE_PRIVATE);

        Menu navigationMenu = navigationView.getMenu();
        View navigationHeader = navigationView.getHeaderView(0);

        if (sharedPreferences.getBoolean(App.LOGIN_OK, false)) {
            new AlertBuilder(this).infoAlert("Nice!", "It seems you've logged in successfully!");

            navigationMenu.setGroupVisible(R.id.nav_group_login, false);
            navigationMenu.setGroupVisible(R.id.nav_group_logged_in, true);

            TextView mUsername = (TextView) navigationHeader.findViewById(R.id.text_username);
            mUsername.setText(sharedPreferences.getString(App.LOGIN_USERNAME, null));
        } else {
            new AlertBuilder(this).infoAlert("Okay?", "It seems you're not yet logged in.");

            navigationMenu.setGroupVisible(R.id.nav_group_login, true);
            navigationMenu.setGroupVisible(R.id.nav_group_logged_in, false);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_register) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_logout) {
            logout();

            // Restart activity to see effects
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    void logout() {
        getSharedPreferences(App.PRIVATE_PREFS, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(App.LOGIN_OK, false)
                .putString(App.LOGIN_USERNAME, null)
                .apply();
    }
}
