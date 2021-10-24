package com.moringaschool.bookmeal.UI;

import static com.moringaschool.bookmeal.UI.MainActivity.MyPREFERENCES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.moringaschool.bookmeal.ApiClient;
import com.moringaschool.bookmeal.Authentication.RegisterActivity;
import com.moringaschool.bookmeal.Model.Food;
import com.moringaschool.bookmeal.Model.Order;
import com.moringaschool.bookmeal.Model.OrderResponse;
import com.moringaschool.bookmeal.Model.Orders;
import com.moringaschool.bookmeal.R;
import com.moringaschool.bookmeal.Recycleview.FoodAdapter;
import com.moringaschool.bookmeal.Recycleview.MenuAdapter;
import com.moringaschool.bookmeal.Recycleview.OrderAdapter;
import com.moringaschool.bookmeal.Recycleview.OrderResponseAdapter;
import com.moringaschool.bookmeal.Recycleview.OrderUserAdapter;
import com.moringaschool.bookmeal.Recycleview.OrderUserAdapter1;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryActivity extends AppCompatActivity implements View.OnClickListener{
    String token,user_id,orders_id;
    TextView order_amount,order_id;
    RecyclerView rvFood;
    List<Order> ordersList;
    ProgressBar mProgressBar;
    SharedPreferences sharedpreferences;
    OrderResponseAdapter.RecyclerViewClickListener listener;
    ImageView backhome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        order_amount=findViewById(R.id.order_amount);
        order_id=findViewById(R.id.order_id);
        backhome = findViewById(R.id.back_btn);
        backhome.setOnClickListener(this);
        rvFood=findViewById(R.id.orderList);
        ordersList=new ArrayList<>();
        setMenuInfo();
    }
    private void setMenuInfo() {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedpreferences.getString("access_token", "");
        user_id = sharedpreferences.getString("idKey", "");
        token = "Bearer " + token;
        Call<List<OrderResponse>> orderResponseCall= ApiClient.getService().orderResponses(token);

        orderResponseCall.enqueue(new Callback<List<OrderResponse>>() {

            @Override
            public void onResponse(Call<List<OrderResponse>> call, Response<List<OrderResponse>> response) {

                if(response.isSuccessful()){
                    List<OrderResponse> orders=response.body();
                    if (orders.isEmpty()) {
                        Intent intent = new Intent(OrderHistoryActivity.this, NoOrderActivity.class);
                        startActivity(intent);
                    }
                    else {
                        for (OrderResponse ord : orders) {
                            String TAG = "this is the tag";
                            String UserId = ord.getCustomer().getId();
                            Boolean status = ord.getIsOpen();
                            if (UserId.equals(user_id) && Boolean.FALSE.equals(status)) {
                               // order_amount.setText(Integer.toString(ord.getTotalAmount()));
                                orders_id = ord.getId();
                                ordersList = ord.getOrders();
                                setOnclickListener();
                                OrderResponseAdapter orderAdapter = new OrderResponseAdapter((ArrayList<Order>) ordersList, (OrderResponseAdapter.RecyclerViewClickListener) listener, user_id);

                                if(orderAdapter.getItemCount()==0){
                                    order_amount.setText("No items");
                                }
                                rvFood.setAdapter(orderAdapter);
                                RecyclerView.LayoutManager layoutManager =
                                        new LinearLayoutManager(OrderHistoryActivity.this);
                                rvFood.setLayoutManager(layoutManager);
                                rvFood.setHasFixedSize(true);
                                rvFood.setVisibility(View.VISIBLE);
                            }

                        }
                    }
                }
                else{
                    try {
                        Toast.makeText(OrderHistoryActivity.this, (CharSequence) "1234", Toast.LENGTH_LONG).show();
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(OrderHistoryActivity.this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(OrderHistoryActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<OrderResponse>> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(OrderHistoryActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
    private void SetupFoodAdapter() {
        setOnclickListener();
    }

    private void setOnclickListener() {
        listener = null;
    }

    @Override
    public void onClick(View view) {
        if (view == backhome) {
            onBackPressed();

        }
    }
}
