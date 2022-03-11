package com.example.medred;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class HomeActivity extends AppCompatActivity {
    Toolbar mToolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -3);


        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 3);


        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)

                .range(startDate, endDate)

                .datesNumberOnScreen(5)

                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                // on below line we are printing date
                // in the logcat which is selected.
                Log.i("TAG", "CURRENT DATE IS " + date);
            }
        });
        mToolbar  = findViewById(R.id.toolBar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        FloatingActionButton fabb=findViewById(R.id.fab);
        fabb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG","click fab");
            }
        });
       // getSupportFragmentManager().beginTransaction().replace(R.id.flContent,new HomeFragment()).commit();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // menuItem.setChecked(true);
               // Toast.makeText(getApplicationContext(), "hh"+menuItem.getItemId(), Toast.LENGTH_SHORT).show();
                int z=menuItem.getItemId();
                Log.i("TAG",menuItem.getItemId()+"id");
                switch (z){
                    case R.id.nav_home:
                        homeActivity();
                        break;
                    case R.id.nav_healthtaker:
                       // changeFragment(new HomeFragment());

                }
                menuItem.setChecked(true);
                // Set action bar title
                setTitle(menuItem.getTitle());
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout,mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
      //

    }

    private void changeFragment(Fragment fragment) {
       // getSupportFragmentManager().beginTransaction().replace(R.id.flContent,fragment).commit();
       FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

    }
    private void homeActivity() {

        Intent i = new Intent(HomeActivity.this, HomeActivity.class);
        startActivity(i);
    }

}