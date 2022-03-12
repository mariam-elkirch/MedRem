package com.example.medred.Home.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.medred.R;
import com.example.medred.addmedication.view.AddMedicationActivity;
import com.example.medred.dependentsList.view.DependantsListFragment;
import com.example.medred.medicationsList.view.MedicationsListFragment;
import com.example.medred.requestsList.view.RequestsListFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    Toolbar mToolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    TextView userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mToolbar  = findViewById(R.id.toolBar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.name_text);
        navUsername.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
      //   userName.setText("FirebaseAuth.getInstance().getCurrentUser().getDisplayName()");
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
                        changeFragment(new ListFragment());
                        break;
                    case R.id.nav_medication:
                        changeFragment(new MedicationsListFragment());
                        break;
                    case R.id.nav_dependent:
                        changeFragment(new DependantsListFragment());
                        break;
                    case R.id.nav_requests:
                        changeFragment(new RequestsListFragment());
                        break;
                    case R.id.nav_logout:
                        FirebaseAuth.getInstance().signOut();

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
    private void changeActivity(Activity activity) {

        Intent i = new Intent(HomeActivity.this, activity.getClass());
        startActivity(i);
    }

}