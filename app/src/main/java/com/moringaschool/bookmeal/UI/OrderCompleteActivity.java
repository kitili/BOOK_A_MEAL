package com.moringaschool.bookmeal.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.moringaschool.bookmeal.R;

    public class OrderCompleteActivity extends AppCompatActivity implements View.OnClickListener{
        ImageView backhome;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_order_complete);
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