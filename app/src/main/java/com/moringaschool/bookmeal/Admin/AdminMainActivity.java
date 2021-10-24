package com.moringaschool.bookmeal.Admin;

import static com.moringaschool.bookmeal.UI.MainActivity.MyPREFERENCES;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.moringaschool.bookmeal.ApiClient;
import com.moringaschool.bookmeal.Authentication.EditProfileActivity;
import com.moringaschool.bookmeal.Authentication.LoginActivity;
import com.moringaschool.bookmeal.Authentication.ProfileActivity;
import com.moringaschool.bookmeal.Model.Data;
import com.moringaschool.bookmeal.Model.Order;
import com.moringaschool.bookmeal.Model.OrderResponse;
import com.moringaschool.bookmeal.Model.OrderSummary;
import com.moringaschool.bookmeal.R;
import com.moringaschool.bookmeal.Model.Tokens;
import com.moringaschool.bookmeal.Recycleview.OrderResponseAdapter;
import com.moringaschool.bookmeal.UI.MainActivity;
import com.moringaschool.bookmeal.UI.NoOrderActivity;
import com.moringaschool.bookmeal.UI.UserCartActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Data data;
    TextView amount,transactions;
    String token,user_id,orders_id;
    //shared preference
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Username = "usernameKey";
    public static final String AccessToken = "access_token";
    public static final String RefreshToken = "refresh_token";
    public static final String Email = "emailKey";
    public static final String Id = "idKey";
    public static final String FirstName = "first_name_key";
    public static final String OtherName = "other_name_key";
    public static final String UserImage = "UserImageKey";
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        /*-----------------------Hooks--------------------------*/
        amount=findViewById(R.id.amount);
        transactions=findViewById(R.id.transactions);
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
        //get the login details
        Intent intent = getIntent();
        if(intent.getExtras()!=null) {
            data= (Data) getIntent().getSerializableExtra("data");
            Tokens token=data.getTokens();}
        data= (Data) getIntent().getSerializableExtra("data");
        Tokens token=data.getTokens();
        String logged_access_token=token.getAccess();
        String logged_refresh_token=token.getRefresh();
        String logged_email=data.getEmail();
        String logged_username=data.getUsername();
        String logged_id=data.getId();
        String logged_first_name= (String) data.getFirstName();
        String logged_other_name= (String) data.getOtherName();
        String logged_userImage=data.getUserImage();

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
        setInfo();
    }

    private void setInfo() {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedpreferences.getString("access_token", "");
        user_id = sharedpreferences.getString("idKey", "");
        token = "Bearer " + token;
        Call<OrderSummary> orderSummaryCall= ApiClient.getService().ordersummary(token);

        orderSummaryCall.enqueue(new Callback<OrderSummary> () {

            @Override
            public void onResponse(Call<OrderSummary> call, Response<OrderSummary> response) {

                if(response.isSuccessful()){
                    OrderSummary orderSummary=response.body();
                    String TAG="this is it ";
                     amount.setText( String.valueOf(orderSummary.getAmount()));
                      transactions.setText(String.valueOf(orderSummary.getNumberOfTransactions()));

                    Log.e(TAG, String.valueOf(orderSummary.getNumberOfTransactions()));

                      // for (OrderSummary ord : orderSummary) {

//                        String amounts= String.valueOf(ord.getAmount());
//                        amount.setText(amounts);
//                      //  transactions.setText(ord.getNumberOfTransactions().toString());
                      //}


                }
                else{
                    try {
                        Toast.makeText(AdminMainActivity.this, (CharSequence) "1234", Toast.LENGTH_LONG).show();
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(AdminMainActivity.this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(AdminMainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderSummary>  call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(AdminMainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
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
            case R.id.nav_admin_view_menu:
                intent = new Intent(AdminMainActivity.this, ViewMenuActivity.class);
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
                intent = new Intent(AdminMainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

//            case R.id.nav_orders:
//                intent = new Intent(AdminMainActivity.this, OrderListActivity.class);
//                startActivity(intent);
//                break;
            case R.id.nav_history:
                intent = new Intent(AdminMainActivity.this, OrderListActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_edit_profile:
                intent = new Intent(AdminMainActivity.this, EditProfileActivity.class);
                intent.putExtra("data", data);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                intent = new Intent(AdminMainActivity.this, ProfileActivity.class);
                intent.putExtra("data", data);
                startActivity(intent);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}