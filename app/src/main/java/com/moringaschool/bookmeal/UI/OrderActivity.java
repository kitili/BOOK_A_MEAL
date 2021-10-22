package com.moringaschool.bookmeal.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.moringaschool.bookmeal.Model.Food;
import com.moringaschool.bookmeal.R;
import com.moringaschool.bookmeal.Recycleview.FoodAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView backhome;
    RecyclerView rvFood;
    FoodAdapter foodAdapter;
    List<Food> mdata;
    FoodAdapter.RecyclerViewClickListener listener;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_order);
            backhome=findViewById(R.id.back_btn);
            backhome.setOnClickListener(this);
            initViews();
            initmdataFood();
            SetupFoodAdapter();
        }

    private void SetupFoodAdapter() {
        setOnclickListener();
        foodAdapter=new FoodAdapter(mdata,listener);
        rvFood.setAdapter(foodAdapter);
    }

    private void setOnclickListener() {
        listener=new FoodAdapter.RecyclerViewClickListener() {

            @Override
            public void onClick(View v, int position) {
                Intent intent=new Intent(getApplicationContext(),FoodDetailsActivity.class);
                intent.putExtra("name",mdata.get(position).getName());
                intent.putExtra("price",mdata.get(position).getPrice());
                intent.putExtra("description",mdata.get(position).getDescription());
                startActivity(intent);
            }
        };
    }

    private void initmdataFood() {
        //for testing a ranfom array of food items
        mdata=new ArrayList<>();
        mdata.add(new Food("Fish Salad","this is the description","this is the image",50.00, R.drawable.food_1));
        mdata.add(new Food("Chicken Salad","this is the description","this is the image",50.0, R.drawable.food_1));
        mdata.add(new Food("Beef Salad","this is the description","this is the image",50.0 ,R.drawable.food_1));
        mdata.add(new Food("Chicken Salad","this is the description","this is the image",50.0, R.drawable.food_1));
        mdata.add(new Food("Fish Salad","this is the description","this is the image",50.0, R.drawable.food_1));
        mdata.add(new Food("Beef Salad","this is the description","this is the image", 50.0, R.drawable.food_1));


    }

    //set
    private void initViews() {
        rvFood=findViewById(R.id.orderlist);
        rvFood.setLayoutManager(new LinearLayoutManager(this));
        rvFood.setHasFixedSize(true);
    }
        @Override
        public void onClick(View view) {
            if (view == backhome) {
                onBackPressed();

            }
        }
    }