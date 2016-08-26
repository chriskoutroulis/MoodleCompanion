package ais.koutroulis.gr.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ais.koutroulis.gr.model.Token;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressDialog progress;
    private Response<Token> userToken;

    private Toolbar toolbar;
    private NavigationView navigationView;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                ServiceCaller.performLoginAndUpdateAll(sharedPref.getString(SettingsFragment.URL_KEY, ""),
                        sharedPref.getString(SettingsFragment.USERNAME_KEY, ""), sharedPref.getString(SettingsFragment.PASSWORD_KEY, ""),
                        MainActivity.this);
            }
        });

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        //If the shared preferences do not contain the required user details.
        if (!sharedPref.getAll().containsKey(SettingsFragment.URL_KEY) ||
                !sharedPref.getAll().containsKey(SettingsFragment.USERNAME_KEY) ||
                !sharedPref.getAll().containsKey(SettingsFragment.PASSWORD_KEY)) {

            navigationView.setCheckedItem(R.id.nav_settings);
            toolbar.setTitle(R.string.moodle_settings);

            SettingsFragment fragment = new SettingsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.coordinator, fragment);
            fragmentTransaction.commit();

        } else {

            //If the Android Version of the Phone is lower that Lollipop, remove the Floating Action Button
            //from the layout.
            int currentApiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentApiVersion < android.os.Build.VERSION_CODES.LOLLIPOP){
                fab.setVisibility(View.GONE);
            }

            ServiceCaller.performLoginAndUpdateAll(sharedPref.getString(SettingsFragment.URL_KEY, ""),
                    sharedPref.getString(SettingsFragment.USERNAME_KEY, ""), sharedPref.getString(SettingsFragment.PASSWORD_KEY, ""),
                    this);

            /*navigationView.setCheckedItem(R.id.nav_assignments);
            toolbar.setTitle(R.string.moodle_assignments);

            ContentFragment fragment = new ContentFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.coordinator, fragment);
            fragmentTransaction.commit();*/
        }
    }
//    Uncomment this method to have the application update everytime
    //that the screen goes off of the app gets minimized.
//    @Override
//    protected void onResume() {
//        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
//
//        //If the shared preferences do not contain the required user details.
//        if (!sharedPref.getAll().containsKey(SettingsFragment.URL_KEY) ||
//                !sharedPref.getAll().containsKey(SettingsFragment.USERNAME_KEY) ||
//                !sharedPref.getAll().containsKey(SettingsFragment.PASSWORD_KEY)) {
//
//            navigationView.setCheckedItem(R.id.nav_settings);
//            toolbar.setTitle(R.string.moodle_settings);
//
//            SettingsFragment fragment = new SettingsFragment();
//            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.coordinator, fragment);
//            fragmentTransaction.commit();
//
//        } else {
//
//            //If the Android Version of the Phone is lower that Lollipop, remove the Floating Action Button
//            //from the layout.
//            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
//            if (currentapiVersion < android.os.Build.VERSION_CODES.LOLLIPOP){
//                fab.setVisibility(View.GONE);
//            }
//
//            ServiceCaller.performLoginAndUpdateAll(sharedPref.getString(SettingsFragment.URL_KEY, ""),
//                    sharedPref.getString(SettingsFragment.USERNAME_KEY, ""), sharedPref.getString(SettingsFragment.PASSWORD_KEY, ""),
//                    this);
//
//            navigationView.setCheckedItem(R.id.nav_assignments);
//            toolbar.setTitle(R.string.moodle_assignments);
//
//            ContentFragment fragment = new ContentFragment();
//            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.coordinator, fragment);
//            fragmentTransaction.commit();
//        }
//
//        super.onResume();
//    }

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
//        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        CoordinatorLayout coord = (CoordinatorLayout) findViewById(R.id.coordinator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (id == R.id.nav_assignments) {
            // Handle the assignments action
            /*Snackbar.make(coord, "Go to the Assignments screen.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();*/

            toolbar.setTitle(R.string.moodle_assignments);

            ServiceCaller.itemsToShow = ServiceCaller.ITEM_ASSIGNMENT;

            ContentFragment fragment = new ContentFragment();
            fragment.setArguments(ServiceCaller.fragmentArgs);
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.coordinator, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_messages) {
            /*Snackbar.make(coord, "Go to the Messages screen.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();*/

            toolbar.setTitle(R.string.moodle_messages);

            ServiceCaller.itemsToShow = ServiceCaller.ITEM_MESSAGES;

            ContentFragment fragment = new ContentFragment();
            fragment.setArguments(ServiceCaller.fragmentArgs);
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.coordinator, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_forum) {
            /*Snackbar.make(coord, "Go to the Forum screen.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();*/

            toolbar.setTitle(R.string.moodle_posts);

            ServiceCaller.itemsToShow = ServiceCaller.ITEM_FORUMS;

            ContentFragment fragment = new ContentFragment();
            fragment.setArguments(ServiceCaller.fragmentArgs);
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.coordinator, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_settings) {
           /* Snackbar.make(coord, "Go to the Settings screen.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();*/

            toolbar.setTitle(R.string.moodle_settings);

            SettingsFragment fragment = new SettingsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.coordinator, fragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
