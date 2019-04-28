package com.serhatleventyavas.gezgin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DrawerLayout drawerLayout = findViewById(R.id.activity_main_drawerLayout);
        NavigationView navigationView = findViewById(R.id.activity_main_navigationView);
        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close
        );

        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.home) {
                    // Fragment değişecek => Home Fragment gelecek.

                    replaceFragment(new HomeFragment());
                } else if (menuItem.getItemId() == R.id.profile) {
                    // Fragment değişecek => Profile fragment gelecek.

                    replaceFragment(new ProfileFragment());
                } else if (menuItem.getItemId() == R.id.notes) {
                    // Fragment değişecek=> NotesFragment gelecek.
                    replaceFragment(new NotesFragment());
                } else if (menuItem.getItemId() == R.id.logout) {
                    // Çıkış Yap
                    FirebaseAuth.getInstance().signOut();
                    SessionManager manager = new SessionManager(MainActivity.this);
                    manager.logout();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

                drawerLayout.closeDrawer(Gravity.START);

                return true;
            }
        });


        replaceFragment(new HomeFragment());
    }


    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_container, fragment);
        transaction.commit();
    }
}
