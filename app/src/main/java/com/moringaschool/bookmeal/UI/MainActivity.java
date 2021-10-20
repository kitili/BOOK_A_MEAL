package com.moringaschool.bookmeal.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.moringaschool.bookmeal.Authentication.LoginActivity;
import com.moringaschool.bookmeal.Authentication.RegisterActivity;
import com.moringaschool.bookmeal.Model.Food;
import com.moringaschool.bookmeal.R;
import com.moringaschool.bookmeal.Recycleview.FoodAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    RecyclerView rvFood;
    FoodAdapter foodAdapter;
    List<Food> mdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        menu.findItem(R.id.nav_logout).setVisible(false);
        menu.findItem(R.id.nav_profile).setVisible(false);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        initViews();
        initmdataFood();
        SetupFoodAdapter();
    }

    private void SetupFoodAdapter() {
        foodAdapter = new FoodAdapter(mdata);
        rvFood.setAdapter(foodAdapter);
    }

    private void initmdataFood() {
        //for testing a ranfom array of food items
        mdata = new ArrayList<>();
        mdata.add(new Food(R.drawable.food1));
        mdata.add(new Food(R.drawable.food_2));
        mdata.add(new Food(R.drawable.food3));
        mdata.add(new Food(R.drawable.food1));
        mdata.add(new Food(R.drawable.food_2));
        mdata.add(new Food(R.drawable.food3));


    }

    private void initViews() {
        rvFood = findViewById(R.id.foodlist);
        rvFood.setLayoutManager(new LinearLayoutManager(this));
        rvFood.setHasFixedSize(true);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_orders:
                intent = new Intent(MainActivity.this, OrderActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_history:
                intent = new Intent(MainActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_login:
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_register:
                intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_contact:
                intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contact_form_title: {
                setContentView(R.layout.activity_contacts);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}