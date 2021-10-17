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

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.moringaschool.bookmeal.Authentication.LoginActivity;
import com.moringaschool.bookmeal.Authentication.RegisterActivity;
import com.moringaschool.bookmeal.Data;
import com.moringaschool.bookmeal.LoginResponse;
import com.moringaschool.bookmeal.Model.Food;
import com.moringaschool.bookmeal.R;
import com.moringaschool.bookmeal.Recycleview.FoodAdapter;
import com.moringaschool.bookmeal.Recycleview.foodCallback;
import com.moringaschool.bookmeal.Tokens;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import androidx.core.util.Pair;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    RecyclerView rvFood;
    FoodAdapter foodAdapter;
    List<Food> mdata;
    FoodAdapter.RecyclerViewClickListener listener;
    TextInputEditText food_search;
    Button order;
    //shared preference
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Username = "usernameKey";
    public static final String AccessToken = "access_token";
    public static final String RefreshToken = "refresh_token";
    public static final String Email = "emailKey";
    public static final String Id = "idKey";
    SharedPreferences sharedpreferences;



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
        menu.findItem(R.id.nav_login).setVisible(false);
        menu.findItem(R.id.nav_register).setVisible(false);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        //get the login details
        Intent intent = getIntent();
        if(intent.getExtras()!=null) {
            Data data= (Data) getIntent().getSerializableExtra("data");
            Tokens token=data.getTokens();

            String logged_access_token=token.getAccess();
            String logged_refresh_token=token.getRefresh();
            String logged_email=data.getEmail();
            String logged_username=data.getUsername();
            String logged_id=data.getId();
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Id, logged_id);
            editor.putString(Username, logged_username);
            editor.putString(Email, logged_email);
            editor.putString(AccessToken, logged_access_token);
            editor.putString(RefreshToken, logged_refresh_token);
            editor.commit();


        }
        initViews();
        initmdataFood();
        SetupFoodAdapter();
        food_search=findViewById(R.id.food_search1);
        food_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());

            }
        });
    }
    private void filter(String text){
        List<Food> filteredList=new ArrayList<>();
        for(Food item:mdata){
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);

            }

        }
        foodAdapter.filterList(filteredList);
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
            case R.id.nav_logout:
                SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
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

            case R.id.nav_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/" );
                sendIntent.setType("text/plain");
                startActivity(sendIntent);


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}