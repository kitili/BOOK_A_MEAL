package com.moringaschool.bookmeal.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.moringaschool.bookmeal.Authentication.LoginActivity;
import com.moringaschool.bookmeal.Authentication.RegisterActivity;
import com.moringaschool.bookmeal.R;
import com.moringaschool.bookmeal.UI.MainActivity;
import com.moringaschool.bookmeal.UI.OrderActivity;
import com.moringaschool.bookmeal.UI.OrderHistoryActivity;

public class AdminMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        /*-----------------------Hooks--------------------------*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        //textView=findViewById(R.id.textView);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(this, R.style.lobsterApperanceToolbar);
        /*----------------------------Tool Bar-----------------------*/
        setSupportActionBar(toolbar);
        /*---------------------------Navigation Menu---------------------*/
        //hide or show items
        Menu menu = navigationView.getMenu();
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_admin_home:
                break;
            case R.id.nav_admin_create_menu:
                intent = new Intent(AdminMainActivity.this, AddFoodItemActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_admin_users:
                intent = new Intent(AdminMainActivity.this, UsersListActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
            case R.id.nav_orders:
                intent = new Intent(AdminMainActivity.this, OrderListActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_history:
                intent = new Intent(AdminMainActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                intent = new Intent(AdminMainActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}