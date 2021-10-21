package com.moringaschool.bookmeal.UI;

import static com.moringaschool.bookmeal.UI.MainActivity.MyPREFERENCES;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.moringaschool.bookmeal.Admin.EditFoodItemActivity;
import com.moringaschool.bookmeal.Admin.ViewMenuActivity;
import com.moringaschool.bookmeal.ApiClient;
import com.moringaschool.bookmeal.Authentication.LoginActivity;
import com.moringaschool.bookmeal.Authentication.RegisterActivity;
import com.moringaschool.bookmeal.MakeOrder;
import com.moringaschool.bookmeal.OrdersResponse;
import com.moringaschool.bookmeal.R;
import com.moringaschool.bookmeal.RegisterRequest;
import com.moringaschool.bookmeal.RegisterResponse;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuDetailActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageview, backhome;
    TextView name, price, description;
    String food_name;
    String food_description;
    double food_price;
    String food_prices;
    String food_id;
    String token;
    String food_image;
    TextInputLayout food_quantity;
    Button order;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);
        imageview = findViewById(R.id.item_food_img);
        name = findViewById(R.id.item_food_name);
        price = findViewById(R.id.item_food_price);
        food_quantity = findViewById(R.id.item_food_quantity);
        description = findViewById(R.id.item_food_desc);
        backhome = findViewById(R.id.back_btn);
        backhome.setOnClickListener(this);
        order = findViewById(R.id.item_food_order_btn);
        order.setOnClickListener(this);
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("access_token", "");
        token = "Bearer " + token;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            food_name = extras.getString("name");
            food_price = extras.getInt("price");
            food_prices = Integer.toString((int) food_price);
            String TAG = "FoodDetails";
            Log.e(TAG, "mmmmm==============================>" + food_prices);
            food_description = extras.getString("description");
            food_id = extras.getString("id");
            food_image = extras.getString("imageURL");
        } else {

        }
        name.setText(food_name);
        price.setText(food_prices);
        description.setText(food_description);
        //food_quantity.setText(quantity.toString());
        Picasso.get().load(food_image).into(imageview);
    }

    @Override
    public void onClick(View view) {
        if (view == backhome) {
            onBackPressed();

        }
        if (view == order) {
            //register
            MakeOrder makeOrder = new MakeOrder();   //initializing progress dialog
            makeOrder.setMenu_id(food_id);
            if(food_quantity.getEditText().getText().toString().isEmpty()){
                makeOrder.setQuantity(1);
            }
            else{
                makeOrder.setQuantity(Integer.valueOf(food_quantity.getEditText().getText().toString()));
            }
            makeorder(makeOrder);

        }
    }

    private void makeorder(MakeOrder makeOrder) {
        Call<OrdersResponse> orderResponseCall = ApiClient.getService().makeorderResponse(makeOrder, token);
        orderResponseCall.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                if (response.isSuccessful()) {
                    String message = "Menu added to your cart successful";
                    new AlertDialog.Builder(MenuDetailActivity.this)
                            .setTitle("Order Successful")
                            .setMessage(message)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(MenuDetailActivity.this, MainActivity.class));
                                    finish();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    //Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String error_message = jObjError.getJSONObject("error").getString("message");
                        new AlertDialog.Builder(MenuDetailActivity.this)
                                .setTitle("Add to cart not successful")
                                .setMessage(error_message)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(MenuDetailActivity.this, MainActivity.class));
                                        finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        //Toast.makeText(LoginActivity.this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        new AlertDialog.Builder(MenuDetailActivity.this)
                                .setTitle("Add to cart not successful")
                                .setMessage(e.getLocalizedMessage())
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(MenuDetailActivity.this, MainActivity.class));
                                        finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        // Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    ;
                }

            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                new AlertDialog.Builder(MenuDetailActivity.this)
                        .setTitle("Registration Not Successful")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(MenuDetailActivity.this, LoginActivity.class));
                                finish();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

}
