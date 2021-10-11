package com.moringaschool.bookmeal.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.moringaschool.bookmeal.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
Button toLogin,toRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toRegister=findViewById(R.id.toRegister);
        toLogin=findViewById(R.id.backLogin);
        toRegister.setOnClickListener(this);
        toLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == toRegister){

        }
        if(view == toLogin){
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);

        }

    }
}