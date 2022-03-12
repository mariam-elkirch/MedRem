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

        mToolbar  = findViewById(R.id.toolBar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);

        getSupportFragmentManager().beginTransaction().replace(R.id.flContent,new HomeFragment()).commit();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // menuItem.setChecked(true);
               // Toast.makeText(getApplicationContext(), "hh"+menuItem.getItemId(), Toast.LENGTH_SHORT).show();
                int z=menuItem.getItemId();
                Log.i("TAG",menuItem.getItemId()+"id");
                switch (z){
                    case R.id.nav_home:
                        changeFragment(new HomeFragment());
                        break;
                    case R.id.nav_healthtaker:
                        //fabb.hide();


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