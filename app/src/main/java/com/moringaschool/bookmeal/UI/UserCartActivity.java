package com.moringaschool.bookmeal.UI;

import static com.moringaschool.bookmeal.UI.MainActivity.MyPREFERENCES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.moringaschool.bookmeal.ApiClient;
import com.moringaschool.bookmeal.Model.Order;
import com.moringaschool.bookmeal.Model.OrderResponse;
import com.moringaschool.bookmeal.Model.Orders;
import com.moringaschool.bookmeal.R;
import com.moringaschool.bookmeal.Recycleview.MenuAdapter;
import com.moringaschool.bookmeal.Recycleview.OrderResponseAdapter;
import com.moringaschool.bookmeal.Recycleview.OrderUserAdapter;
import com.moringaschool.bookmeal.Recycleview.OrderUserAdapter1;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserCartActivity extends AppCompatActivity {

    String token,user_id;
    TextView order_amount;
    RecyclerView mRecyclerView;
    RecyclerView rvFood;
    OrderUserAdapter orderAdapter;
    List<Order> ordersList;
    ProgressBar mProgressBar;
    SharedPreferences sharedpreferences;
    TextView mErrorTextView;
    OrderResponseAdapter.RecyclerViewClickListener listener;
    ImageView backhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart);
        order_amount=findViewById(R.id.order_amount);
        rvFood=findViewById(R.id.orderList);
        ordersList=new ArrayList<>();
        setMenuInfo();
    }
    private void setMenuInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("access_token", "");
        user_id = sharedPreferences.getString("idKey", "");
        token = "Bearer " + token;
        Call<List<OrderResponse>> orderResponseCall= ApiClient.getService().orderResponses(token);

        orderResponseCall.enqueue(new Callback<List<OrderResponse>>() {

            @Override
            public void onResponse(Call<List<OrderResponse>> call, Response<List<OrderResponse>> response) {

                if(response.isSuccessful()){
                    List<OrderResponse> orders=response.body();
                    for(OrderResponse ord:orders){
                        String TAG="this is the tag";
                        String UserId=ord.getCustomer().getId();
                        Boolean status= ord.getIsOpen();
                        if(UserId.equals(user_id) && Boolean.TRUE.equals(status)){
                            order_amount.setText(Integer.toString(ord.getTotalAmount()));
                          for(Order item:ord.getOrders()){

                              Log.e(TAG,item.getMenuItem());
                          }

                            ordersList  = ord.getOrders();
                            setOnclickListener();
                            OrderResponseAdapter orderAdapter = new OrderResponseAdapter((ArrayList<Order>) ordersList, (OrderResponseAdapter.RecyclerViewClickListener) listener,user_id);
                            rvFood.setAdapter(orderAdapter);
                            RecyclerView.LayoutManager layoutManager =
                                    new LinearLayoutManager(UserCartActivity.this);
                            rvFood.setLayoutManager(layoutManager);
                            rvFood.setHasFixedSize(true);
                            rvFood.setVisibility(View.VISIBLE);
                        }

                    }
                   // Class<? extends List> restaurantsList = response.body().getClass();
                   // Toast.makeText(UserCartActivity.this,"msg======="+restaurantsList, Toast.LENGTH_LONG).show();





                }
                else{
                    try {
                        Toast.makeText(UserCartActivity.this, (CharSequence) "1234", Toast.LENGTH_LONG).show();
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(UserCartActivity.this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(UserCartActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<OrderResponse>> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(UserCartActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
    private void SetupFoodAdapter() {
        setOnclickListener();
    }

    private void setOnclickListener() {
        listener = null;
    }
}