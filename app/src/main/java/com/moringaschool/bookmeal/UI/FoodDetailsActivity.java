package com.moringaschool.bookmeal.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.moringaschool.bookmeal.R;

import org.w3c.dom.Text;

public class FoodDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView imageview, backhome;
    TextView name,price,description;
    String food_name;
    String food_description;
    double food_price;
    String food_prices;

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
        Bundle extras=getIntent().getExtras();
        if(extras !=null){
            food_name=extras.getString("name");
            food_price=extras.getDouble("price");
            food_prices= Double.toString(food_price);
            food_description=extras.getString("description");
        }
        else{

        }
        name.setText(food_name);
        price.setText(food_prices);
        description.setText(food_description);
    }

    @Override
    public void onClick(View view) {
        if (view == backhome) {
            onBackPressed();

        }

    }
}