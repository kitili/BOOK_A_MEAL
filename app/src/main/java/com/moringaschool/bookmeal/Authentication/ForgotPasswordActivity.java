package com.moringaschool.bookmeal.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moringaschool.bookmeal.ApiClient;
import com.moringaschool.bookmeal.R;
import com.moringaschool.bookmeal.RegisterRequest;
import com.moringaschool.bookmeal.RegisterResponse;
import com.moringaschool.bookmeal.ResetPasswordRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener{
    Button login,reset;
    TextView email;
    ImageView backhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        login=findViewById(R.id.login);
        reset=findViewById(R.id.reset);
        login.setOnClickListener(this);
        reset.setOnClickListener(this);
        backhome=findViewById(R.id.back_btn);
        backhome.setOnClickListener(this);
        //email = findViewById(R.id.email);
    }
    public boolean validateEmail(){
        String val=email.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            email.setError("Invalid email address");
            return false;
        } else {
            email.setError(null);
            return true;
        }

    }
    @Override
    public void onClick(View view) {
        if(view == login){
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        if(view == reset){
            if (!validateEmail()) {
                return;
            }
            ResetPasswordRequest resetPasswordRequest=new ResetPasswordRequest();
            resetPasswordRequest.setEmail(email.getText().toString());
            resetPassword(resetPasswordRequest);

        }
        if (view == backhome) {
            onBackPressed();

        }

    }

    private void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        Call<ResetPasswordRequest> resetPasswordRequestCall= ApiClient.getService().resetPassword(resetPasswordRequest);

    }
}