package com.moringaschool.bookmeal.UI;

import static com.moringaschool.bookmeal.UI.MainActivity.MyPREFERENCES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.moringaschool.bookmeal.Admin.OrderListActivity;
import com.moringaschool.bookmeal.Admin.ViewMenuActivity;
import com.moringaschool.bookmeal.ApiClient;
import com.moringaschool.bookmeal.Model.Orders;
import com.moringaschool.bookmeal.R;
import com.moringaschool.bookmeal.Recycleview.MenuAdapter;
import com.moringaschool.bookmeal.Recycleview.MenuUserAdapter;
import com.moringaschool.bookmeal.Recycleview.OrderAdapter;
import com.moringaschool.bookmeal.Recycleview.OrderUserAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCartActivity extends AppCompatActivity  {
    TextView textViewResult;
    String token,id;
    RecyclerView mRecyclerView;
    RecyclerView rvFood;
    OrderUserAdapter orderAdapter;
    List<Orders> ordersList;
    TextInputEditText food_search;
    ProgressBar mProgressBar;
    SharedPreferences sharedpreferences;
    TextView mErrorTextView;
    OrderUserAdapter.RecyclerViewClickListener listener;
    ImageView backhome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        // mRecyclerView=findViewById(R.id.)
        // textViewResult = findViewById(R.id.text_view_result);
        // mErrorTextView=findViewById(R.id.errorTextView);
        rvFood=findViewById(R.id.orderList);
        backhome=findViewById(R.id.back_btn);
        //backhome.setOnClickListener(this);
        // mProgressBar=findViewById(R.id.progressBar);
        ordersList=new ArrayList<>();
        setMenuInfo();
    }
    private void setMenuInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("access_token", "");
        id = sharedPreferences.getString("idKey", "");
        token = "Bearer " + token;
        Call<List<Orders>> ordersResponseCall = ApiClient.getService().orderUserResponse(token);
        ordersResponseCall.enqueue(new Callback<List<Orders>>() {

            @Override
            public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                if(response.isSuccessful()) {
                    ordersList  = response.body();
                    setOnclickListener();
                    OrderUserAdapter orderAdapter = new OrderUserAdapter((ArrayList<Orders>) ordersList,listener,id);
                    rvFood.setAdapter(orderAdapter);
                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(MyCartActivity.this);
                    rvFood.setLayoutManager(layoutManager);
                    rvFood.setHasFixedSize(true);
                    rvFood.setVisibility(View.VISIBLE);

                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(MyCartActivity.this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(MyCartActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    };
                }

            }

            @Override
            public void onFailure(Call<List<Orders>> call, Throwable t) {
                String message=t.getLocalizedMessage();
                Toast.makeText(MyCartActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void SetupFoodAdapter() {
        setOnclickListener();
    }


    private void setOnclickListener() {
        listener = new OrderUserAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Toast.makeText(MyCartActivity.this,"message",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onItemClick(View v, int position) { ;
                SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                token = sharedPreferences.getString("access_token", "");
                token = "Bearer " + token;
                String order_id=ordersList.get(position).getId();
                Call<Void> deleteOrdercall= ApiClient.getService().deleteOrder(token,order_id);
                deleteOrdercall.enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> deleteOrdercall, Response<Void> response) {
                        Toast.makeText(MyCartActivity.this,"The order was deleted successfully",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MyCartActivity.this, MyCartActivity.class));
                        finish();

                    }

                    @Override
                    public void onFailure(Call<Void> deleteOrdercall, Throwable t) {
                        String message=t.getLocalizedMessage();
                        Toast.makeText(MyCartActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                });


            }

            @Override
            public void onItemChange(View V, int position) {
                SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                token = sharedPreferences.getString("access_token", "");
                token = "Bearer " + token;
                String order_id=ordersList.get(position).getId();
                Call<Void> completeOrdercall= ApiClient.getService().completeOrder(token,order_id);
                completeOrdercall.enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> completeOrdercall, Response<Void> response) {
                        Toast.makeText(MyCartActivity.this,"The order was completed successfully",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MyCartActivity.this, OrderCompleteActivity.class));
                        finish();

                    }

                    @Override
                    public void onFailure(Call<Void> completeOrdercall, Throwable t) {
                        String message=t.getLocalizedMessage();
                        Toast.makeText(MyCartActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                });

            }
        };
    }


}