package com.moringaschool.bookmeal.Admin;

import static com.moringaschool.bookmeal.UI.MainActivity.MyPREFERENCES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.moringaschool.bookmeal.ApiClient;
import com.moringaschool.bookmeal.Authentication.LoginActivity;
import com.moringaschool.bookmeal.Data;
import com.moringaschool.bookmeal.LoginResponse;
import com.moringaschool.bookmeal.MenuResponse;
import com.moringaschool.bookmeal.Model.Food;
import com.moringaschool.bookmeal.Model.Menu;
import com.moringaschool.bookmeal.R;
import com.moringaschool.bookmeal.Recycleview.FoodAdapter;
import com.moringaschool.bookmeal.Recycleview.MenuAdapter;
import com.moringaschool.bookmeal.UI.FoodDetailsActivity;
import com.moringaschool.bookmeal.UI.MainActivity;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewMenuActivity extends AppCompatActivity implements View.OnClickListener{
    TextView textViewResult;
    String token;
    RecyclerView mRecyclerView;
    RecyclerView rvFood;
    MenuAdapter menuAdapter;
    List<Menu>menuList;
    TextInputEditText food_search;
    ProgressBar mProgressBar;
    SharedPreferences sharedpreferences;
    TextView mErrorTextView;
    MenuAdapter.RecyclerViewClickListener listener;
    ImageView  backhome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_menu);
        // mRecyclerView=findViewById(R.id.)
       // textViewResult = findViewById(R.id.text_view_result);
       // mErrorTextView=findViewById(R.id.errorTextView);
        rvFood=findViewById(R.id.foodlist);
        backhome=findViewById(R.id.back_btn);
        backhome.setOnClickListener(this);
       // mProgressBar=findViewById(R.id.progressBar);
        menuList=new ArrayList<>();
        setMenuInfo();


    }
    private void filter(String text){
        ArrayList<Menu> filteredList=new ArrayList<>();
        for(Menu item:menuList){
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);

            }

        }
        menuAdapter.filterList(filteredList);
    }

    private void setMenuInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("access_token", "");
        token = "Bearer " + token;
        Call<List<Menu>> menuResponseCall = ApiClient.getService().menuResponse(token);
        menuResponseCall.enqueue(new Callback<List<Menu>>() {

            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {
                if(response.isSuccessful()) {
                    menuList  = response.body();
                    setOnclickListener();
                    MenuAdapter menuAdapter = new MenuAdapter((ArrayList<Menu>) menuList,listener);
                    rvFood.setAdapter(menuAdapter);
                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(ViewMenuActivity.this);
                    rvFood.setLayoutManager(layoutManager);
                    rvFood.setHasFixedSize(true);
                    rvFood.setVisibility(View.VISIBLE);

                    // Log.e(TAG,"===============>"+menuResponse);
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(ViewMenuActivity.this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(ViewMenuActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    };
                }

            }

            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {
                String message=t.getLocalizedMessage();
                Toast.makeText(ViewMenuActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }



    private void SetupFoodAdapter() {
        setOnclickListener();
    }

    private void setOnclickListener() {
        listener = new MenuAdapter.RecyclerViewClickListener() {

            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), FoodDetailsActivity.class);
                intent.putExtra("id",menuList.get(position).getId());
                intent.putExtra("name", menuList.get(position).getName());
                intent.putExtra("price", menuList.get(position).getPrice());
                intent.putExtra("description", menuList.get(position).getDescription());
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

    @Override
    public void onClick(View view) {
        if (view == backhome) {
            onBackPressed();

        }

    }
}