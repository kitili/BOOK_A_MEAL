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
import com.moringaschool.bookmeal.Model.LoginResponse;
import com.moringaschool.bookmeal.Model.Menu;
import com.moringaschool.bookmeal.Model.Order;
import com.moringaschool.bookmeal.Model.OrderResponse;
import com.moringaschool.bookmeal.Model.ProfileResponse;
import com.moringaschool.bookmeal.R;
import com.moringaschool.bookmeal.Recycleview.MenuAdapter;
import com.moringaschool.bookmeal.Recycleview.OrderResponseAdapter;
import com.moringaschool.bookmeal.Recycleview.UserAdapter;
import com.moringaschool.bookmeal.UI.FoodDetailsActivity;
import com.moringaschool.bookmeal.UI.NoOrderActivity;
import com.moringaschool.bookmeal.UI.UserCartActivity;

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

        }

    @Override
    public void onClick(View view) {
        if (view == backhome) {
            onBackPressed();

        }

    }

    }