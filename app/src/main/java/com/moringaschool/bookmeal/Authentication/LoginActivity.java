package com.moringaschool.bookmeal.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;
import com.moringaschool.bookmeal.R;
import com.moringaschool.bookmeal.UI.MainActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    Button toRegister, login,forgot_password;
    TextInputLayout email, password;
    ImageView backhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        toRegister = findViewById(R.id.back_register);
        forgot_password = findViewById(R.id.forgot_password);
        login = findViewById(R.id.login);
        forgot_password.setOnClickListener(this);
        toRegister.setOnClickListener(this);
        login.setOnClickListener(this);
        backhome=findViewById(R.id.back_btn);
        backhome.setOnClickListener(this);
    }

    public boolean validateEmail() {
        String val = email.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            email.setError("Invalid email address");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }

    }

    public boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            password.setError("Password is too weak");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    @Override
    public void onClick(View view) {
        if (view == toRegister) {
         Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
         startActivity(intent);
        }
        if (view == login) {
            if (!validatePassword() | !validateEmail()) {
                  return;
            }
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            }
        if (view == forgot_password) {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        }
        if (view == backhome) {
            onBackPressed();

        }
    }
}