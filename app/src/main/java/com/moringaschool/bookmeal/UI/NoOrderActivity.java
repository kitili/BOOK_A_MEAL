package com.moringaschool.bookmeal.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.moringaschool.bookmeal.Authentication.LoginActivity;
import com.moringaschool.bookmeal.Authentication.RegisterActivity;
import com.moringaschool.bookmeal.R;

    public class NoOrderActivity extends AppCompatActivity implements View.OnClickListener{
        ImageView backhome;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_no_order);
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