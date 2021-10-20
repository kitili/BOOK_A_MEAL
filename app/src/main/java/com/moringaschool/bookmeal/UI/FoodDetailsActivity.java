package com.moringaschool.bookmeal.UI;

import static com.moringaschool.bookmeal.UI.MainActivity.MyPREFERENCES;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moringaschool.bookmeal.Admin.EditFoodItemActivity;
import com.moringaschool.bookmeal.Admin.ViewMenuActivity;
import com.moringaschool.bookmeal.ApiClient;
import com.moringaschool.bookmeal.Authentication.LoginActivity;
import com.moringaschool.bookmeal.LoginResponse;
import com.moringaschool.bookmeal.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView imageview, backhome;
    TextView name,price,description;
    String food_name;
    String food_description;
    double food_price;
    String food_prices;
    String food_id,token,food_image;
    Button delete,edit,make;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_food_details);
        imageview=findViewById(R.id.item_food_img);
        name=findViewById(R.id.item_food_name);
        price=findViewById(R.id.item_food_price);
        description=findViewById(R.id.item_food_desc);
        backhome=findViewById(R.id.back_btn);
        backhome.setOnClickListener(this);
        delete=findViewById(R.id.item_food_delete_btn);
        delete.setOnClickListener(this);
        edit=findViewById(R.id.item_food_edit_btn);
        edit.setOnClickListener(this);
        make=findViewById(R.id.item_food_make_btn);
        make.setOnClickListener(this);
        Bundle extras=getIntent().getExtras();
        if(extras !=null){
            food_name=extras.getString("name");
            food_price=extras.getInt("price");
            food_prices= Integer.toString((int) food_price);
            String TAG="FoodDetails";
            Log.e(TAG,"mmmmm==============================>"+food_prices);
            food_description=extras.getString("description");
            food_id=extras.getString("id");
            food_image=extras.getString("imageURL");
        }
        else{

        }
        name.setText(food_name);
        price.setText(food_prices);
        description.setText(food_description);
        Picasso.get().load(food_image).into(imageview);
    }

    @Override
    public void onClick(View view) {
        if (view == backhome) {
            onBackPressed();

        }
        if(view==make){
            //initializing progress dialog
            progressDialog=new ProgressDialog(FoodDetailsActivity.this);
            //show dialog
            progressDialog.show();
            //set content
            progressDialog.setContentView(R.layout.progress_dialog);
            //set transparent bg
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            token = sharedPreferences.getString("access_token", "");
            token = "Bearer " + token;
            Call<Void> setmenuCall= ApiClient.getService().setMenu(token,food_id);
            setmenuCall.enqueue(new Callback<Void>() {

                @Override
                public void onResponse(Call<Void> setmenuCall, Response<Void> response) {
                    Toast.makeText(FoodDetailsActivity.this,"The Menu was successfully set as today's menu item",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(FoodDetailsActivity.this, ViewMenuActivity.class));
                    finish();

                }

                @Override
                public void onFailure(Call<Void> setmenuCall, Throwable t) {
                    String message=t.getLocalizedMessage();
                    Toast.makeText(FoodDetailsActivity.this,message,Toast.LENGTH_LONG).show();
                }
            });

        }
        if(view==edit){
            Intent i = new Intent(FoodDetailsActivity.this, EditFoodItemActivity.class);
            i.putExtra("name", food_name);
            i.putExtra("price", food_prices);
            i.putExtra("description", food_description);
            i.putExtra("image", food_image);
            i.putExtra("menu_id", food_id);
            startActivity(i);

        }
        if(view == delete){
            //initializing progress dialog
            progressDialog=new ProgressDialog(FoodDetailsActivity.this);
            //show dialog
            progressDialog.show();
            //set content
            progressDialog.setContentView(R.layout.progress_dialog);
            //set transparent bg
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            token = sharedPreferences.getString("access_token", "");
            token = "Bearer " + token;
            Call<Void> deleteMenuCall= ApiClient.getService().deleteMenu(token,food_id);
            deleteMenuCall.enqueue(new Callback<Void>() {

                @Override
                public void onResponse(Call<Void> deleteMenuCall, Response<Void> response) {
                    Toast.makeText(FoodDetailsActivity.this,"The Menu was deleted successfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(FoodDetailsActivity.this, ViewMenuActivity.class));
                    finish();

                }

                @Override
                public void onFailure(Call<Void> deleteMenuCall, Throwable t) {
                    String message=t.getLocalizedMessage();
                    Toast.makeText(FoodDetailsActivity.this,message,Toast.LENGTH_LONG).show();
                }
            });

        }

    }
}