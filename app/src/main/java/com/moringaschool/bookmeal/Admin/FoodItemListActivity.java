package com.moringaschool.bookmeal.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.moringaschool.bookmeal.R;

    public class FoodItemListActivity extends AppCompatActivity implements View.OnClickListener{
        ImageView backhome;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_food_item_list);
            backhome=findViewById(R.id.back_btn);
            backhome.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (view == backhome) {
                onBackPressed();

            }
        }
    }