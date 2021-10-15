package com.moringaschool.bookmeal.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.moringaschool.bookmeal.Authentication.LoginActivity;
import com.moringaschool.bookmeal.Authentication.RegisterActivity;
import com.moringaschool.bookmeal.Model.Food;
import com.moringaschool.bookmeal.R;
import com.moringaschool.bookmeal.Recycleview.FoodAdapter;
import com.moringaschool.bookmeal.Recycleview.foodCallback;

import java.util.ArrayList;
import java.util.List;
import androidx.core.util.Pair;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    RecyclerView rvFood;
    FoodAdapter foodAdapter;
    List<Food> mdata;
    FoodAdapter.RecyclerViewClickListener listener;


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
        setOnclickListener();
        foodAdapter=new FoodAdapter(mdata,listener);
        rvFood.setAdapter(foodAdapter);
    }

    private void setOnclickListener() {
        listener=new FoodAdapter.RecyclerViewClickListener() {

            @Override
            public void onClick(View v, int position) {
                Intent intent=new Intent(getApplicationContext(),FoodDetailsActivity.class);
                intent.putExtra("name",mdata.get(position).getName());
                intent.putExtra("price",mdata.get(position).getPrice());
                intent.putExtra("description",mdata.get(position).getDescription());
                startActivity(intent);
            }
        };
    }

    private void initmdataFood() {
        //for testing a ranfom array of food items
        mdata=new ArrayList<>();
        mdata.add(new Food("Fish Salad","this is the description","this is the image",50.00, R.drawable.food_1));
        mdata.add(new Food("Chicken Salad","this is the description","this is the image",50.0, R.drawable.food_1));
        mdata.add(new Food("Beef Salad","this is the description","this is the image",50.0 ,R.drawable.food_1));
        mdata.add(new Food("Chicken Salad","this is the description","this is the image",50.0, R.drawable.food_1));
        mdata.add(new Food("Fish Salad","this is the description","this is the image",50.0, R.drawable.food_1));
        mdata.add(new Food("Beef Salad","this is the description","this is the image", 50.0, R.drawable.food_1));


    }

    //set
    private void initViews() {
        rvFood=findViewById(R.id.foodlist);
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

}