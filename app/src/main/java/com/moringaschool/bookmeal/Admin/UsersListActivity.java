package com.moringaschool.bookmeal.Admin;

import static com.moringaschool.bookmeal.UI.MainActivity.MyPREFERENCES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.moringaschool.bookmeal.ApiClient;
import com.moringaschool.bookmeal.LoginResponse;
import com.moringaschool.bookmeal.Model.Menu;
import com.moringaschool.bookmeal.R;
import com.moringaschool.bookmeal.Recycleview.MenuAdapter;
import com.moringaschool.bookmeal.Recycleview.UserAdapter;
import com.moringaschool.bookmeal.UI.FoodDetailsActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersListActivity extends AppCompatActivity implements View.OnClickListener{
    TextView textViewResult;
    String token;
    RecyclerView mRecyclerView;
    RecyclerView rvFood;
    UserAdapter userAdapter;
    List<LoginResponse> userList;
    TextInputEditText food_search;
    ProgressBar mProgressBar;
    SharedPreferences sharedpreferences;
    TextView mErrorTextView;
    UserAdapter.RecyclerViewClickListener listener;
    ImageView  backhome;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_users_list);
            backhome=findViewById(R.id.back_btn);
            backhome.setOnClickListener(this);
            // mRecyclerView=findViewById(R.id.)
            // textViewResult = findViewById(R.id.text_view_result);
            // mErrorTextView=findViewById(R.id.errorTextView);
            rvFood=findViewById(R.id.userList);
            // mProgressBar=findViewById(R.id.progressBar);
            userList=new ArrayList<>();
            setMenuInfo();
        }
    private void setMenuInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("access_token", "");
        token = "Bearer " + token;
        Call<List<LoginResponse>> userResponseCall = ApiClient.getService().usersResponse(token);
        userResponseCall.enqueue(new Callback<List<LoginResponse>>() {

            @Override
            public void onResponse(Call<List<LoginResponse>> call, Response<List<LoginResponse>> response) {
                if(response.isSuccessful()) {
                    userList  = response.body();
                    setOnclickListener();
                    UserAdapter userAdapter = new UserAdapter((ArrayList<LoginResponse>) userList,listener);
                    rvFood.setAdapter(userAdapter);
                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(UsersListActivity.this);
                    rvFood.setLayoutManager(layoutManager);
                    rvFood.setHasFixedSize(true);
                    rvFood.setVisibility(View.VISIBLE);
                    String TAG="ABC";
                    Log.e(TAG,"msg====================="+userList);
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(UsersListActivity.this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(UsersListActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    };
                }

            }

            @Override
            public void onFailure(Call<List<LoginResponse>> call, Throwable t) {
                String message=t.getLocalizedMessage();
                Toast.makeText(UsersListActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }



    private void SetupFoodAdapter() {
        setOnclickListener();
    }

    private void setOnclickListener() {
        listener = new UserAdapter.RecyclerViewClickListener(){

            @Override
            public void onClick(View v, int position) {
//                Intent intent = new Intent(getApplicationContext(), FoodDetailsActivity.class);
//                intent.putExtra("id",menuList.get(position).getId());
//                intent.putExtra("fi", menuList.get(position).getName());
//                intent.putExtra("price", menuList.get(position).getPrice());
//                intent.putExtra("description", menuList.get(position).getDescription());
//                intent.putExtra("imageURL", menuList.get(position).getMenuImage());
//                startActivity(intent);
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