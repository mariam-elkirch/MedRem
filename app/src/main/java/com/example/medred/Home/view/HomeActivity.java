package com.example.medred.Home.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.work.WorkManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medred.R;
import com.example.medred.Registeration.view.LoginActivity;
import com.example.medred.dependentsList.view.DependantsListFragment;
import com.example.medred.healthtakerslist.view.HealthTakersListFragment;
import com.example.medred.medicationsList.view.MedicationsListFragment;
import com.example.medred.model.Utils;
import com.example.medred.requestsList.view.RequestsListFragment;
import com.example.medred.workmanager.AlarmWorkManager.ManageWorkManager;
import com.example.medred.workmanager.RefillWorkManager.OneTimeRefillWorkManager;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    Toolbar mToolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    TextView userName;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String uid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //cancel periodic
        WorkManager.getInstance(this).cancelAllWorkByTag("periodic");
        ManageWorkManager.setPeriodicRequest(this);

        initSharedPrefs();
        String uid = sharedPreferences.getString(Utils.UID_KEY, null);
        boolean isDependant = sharedPreferences.getBoolean(Utils.IS_DEPENDANT_KEY, false);
        Log.i("TAG", "isDependant: " + isDependant);

        mToolbar  = findViewById(R.id.toolBar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.name_text);
        if(isDependant){
            navUsername.setText("Dependant");
        }
        else{
            navUsername.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        }
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
                        if(isDependant){
                            Toast.makeText(HomeActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            changeFragment(new HealthTakersListFragment());
                        }
                        break;
                    case R.id.nav_medication:
                        if(isDependant){
                        Toast.makeText(HomeActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                    }
                        else{
                        changeFragment(new MedicationsListFragment());
                    }
                        break;
                    case R.id.nav_dependent:
                        changeFragment(new DependantsListFragment());
                        break;
                    case R.id.nav_requests:
                        if(isDependant){
                            Toast.makeText(HomeActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            changeFragment(new RequestsListFragment());
                        }
                        break;
                    case R.id.nav_logout:
                        if(isDependant){
                            Toast.makeText(HomeActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            FirebaseAuth.getInstance().signOut();
                            editor.putBoolean(Utils.IS_DEPENDANT_KEY, false);
                            editor.putString(Utils.UID_KEY, null);
                            editor.apply();
                            navUsername.setText("Guest");
                            finish();
                            changeActivity(new LoginActivity());
                        }



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

    private void initSharedPrefs() {
        sharedPreferences = getSharedPreferences(Utils.SHARED_PREF, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

}