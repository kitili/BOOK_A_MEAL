package com.moringaschool.bookmeal.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.moringaschool.bookmeal.ApiClient;
import com.moringaschool.bookmeal.Authentication.LoginActivity;
import com.moringaschool.bookmeal.Authentication.ProfileActivity;
import com.moringaschool.bookmeal.Authentication.RegisterActivity;
import com.moringaschool.bookmeal.Model.Data;
import com.moringaschool.bookmeal.R;
import com.moringaschool.bookmeal.Recycleview.MenuUserAdapter;
import com.moringaschool.bookmeal.Model.Tokens;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextInputEditText food_search;
    Button order;
    TextView textViewResult;
    String token;
    RecyclerView mRecyclerView;
    RecyclerView rvFood;
    MenuUserAdapter menuAdapter;
    List<com.moringaschool.bookmeal.Model.Menu> menuList;
    ProgressBar mProgressBar;
    SharedPreferences sharedpreferences;
    TextView mErrorTextView;
    Data data;
    MenuUserAdapter.RecyclerViewClickListener listener;


    //shared preference
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Username = "usernameKey";
    public static final String AccessToken = "access_token";
    public static final String RefreshToken = "refresh_token";
    public static final String Email = "emailKey";
    public static final String Id = "idKey";
    public static final String FirstName = "first_name_key";
    public static final String OtherName = "other_name_key";
    public static final String UserImage = "UserImageKey";


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
        if (intent.getExtras() != null) {
            data = (Data) getIntent().getSerializableExtra("data");
            Tokens token = data.getTokens();

            String logged_access_token = token.getAccess();
            String logged_refresh_token = token.getRefresh();
            String logged_email = data.getEmail();
            String logged_username = data.getUsername();
            String logged_id = data.getId();
            String logged_first_name = (String) data.getFirstName();
            String logged_other_name = (String) data.getOtherName();
            String logged_userImage = data.getUserImage();

            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Id, logged_id);
            editor.putString(Username, logged_username);
            editor.putString(Email, logged_email);
            editor.putString(AccessToken, logged_access_token);
            editor.putString(RefreshToken, logged_refresh_token);
            editor.putString(FirstName, logged_first_name);
            editor.putString(OtherName, logged_other_name);
            editor.putString(UserImage, logged_userImage);
            editor.commit();


        }
        rvFood = findViewById(R.id.foodlist);
        // mProgressBar=findViewById(R.id.progressBar);
        menuList = new ArrayList<>();
        setMenuInfo();

    }

    private void filter(String text) {
        ArrayList<com.moringaschool.bookmeal.Model.Menu> filteredList = new ArrayList<>();
        for (com.moringaschool.bookmeal.Model.Menu item : menuList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);

            }

        }
        menuAdapter.filterList(filteredList);
    }

    private void setMenuInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("access_token", "");
        token = "Bearer " + token;
        Call<List<com.moringaschool.bookmeal.Model.Menu>> menuResponseCall = ApiClient.getService().menuResponse(token);
        menuResponseCall.enqueue(new Callback<List<com.moringaschool.bookmeal.Model.Menu>>() {

            @Override
            public void onResponse(Call<List<com.moringaschool.bookmeal.Model.Menu>> call, Response<List<com.moringaschool.bookmeal.Model.Menu>> response) {
                if (response.isSuccessful()) {
                    menuList = response.body();
                    setOnclickListener();
                    MenuUserAdapter menuAdapter = new MenuUserAdapter((ArrayList<com.moringaschool.bookmeal.Model.Menu>) menuList, listener);
                    rvFood.setAdapter(menuAdapter);
                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(MainActivity.this);
                    rvFood.setLayoutManager(layoutManager);
                    rvFood.setHasFixedSize(true);
                    rvFood.setVisibility(View.VISIBLE);

                    // Log.e(TAG,"===============>"+menuResponse);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(MainActivity.this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    ;
                }

            }

            @Override
            public void onFailure(Call<List<com.moringaschool.bookmeal.Model.Menu>> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });


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
                intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("data", data);
                startActivity(intent);
                break;
            case R.id.nav_contact:
                intent = new Intent(MainActivity.this, contacts.class);
                startActivity(intent);
                break;
            case R.id.nav_cart:
                intent = new Intent(MainActivity.this, MyCartActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void SetupFoodAdapter() {
        setOnclickListener();
    }

    private void setOnclickListener() {
        listener = new MenuUserAdapter.RecyclerViewClickListener(){

            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), MenuDetailActivity.class);
                intent.putExtra("id",menuList.get(position).getId());
                intent.putExtra("name", menuList.get(position).getName());
                intent.putExtra("price", menuList.get(position).getPrice());
                intent.putExtra("description", menuList.get(position).getDescription());
                intent.putExtra("imageURL", menuList.get(position).getMenuImage());
                startActivity(intent);
            }

            ;
        };
    }

    private void showFailureMessage() {
//        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
//        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnsuccessfulMessage() {
//        mErrorTextView.setText("Something went wrong. Please try again later");
//        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showRestaurants() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }




}