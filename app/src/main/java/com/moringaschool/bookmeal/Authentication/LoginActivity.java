package com.moringaschool.bookmeal.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.moringaschool.bookmeal.Admin.AdminMainActivity;
import com.moringaschool.bookmeal.ApiClient;
import com.moringaschool.bookmeal.Data;
import com.moringaschool.bookmeal.LoginRequest;
import com.moringaschool.bookmeal.LoginResponse;
import com.moringaschool.bookmeal.R;
import com.moringaschool.bookmeal.RegisterResponse;
import com.moringaschool.bookmeal.Tokens;
import com.moringaschool.bookmeal.UI.MainActivity;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    //initializing variables
    Button toRegister, login,forgot_password;
    TextInputLayout email,password;
    ProgressDialog progressDialog;


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
        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
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
            //initializing progress dialog
            progressDialog=new ProgressDialog(LoginActivity.this);
            //show dialog
            progressDialog.show();
            //set content
            progressDialog.setContentView(R.layout.progress_dialog);
            //set transparent bg
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            //login
            LoginRequest loginRequest=new LoginRequest();
            loginRequest.setEmail(email.getEditText().getText().toString());
            loginRequest.setPassword(password.getEditText().getText().toString());
            loginUser(loginRequest);
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
            }
        if (view == forgot_password) {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        }

    }

    public void onBackPressed(){
        //dismiss progress dialog
        progressDialog.dismiss();
    }
    private void loginUser(LoginRequest loginRequest) {
        Call<LoginResponse> loginResponseCall= ApiClient.getService().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    LoginResponse loginResponse= response.body();
                    //List<Data> data= (List<Data>) response.body().getData();
                    Data data=response.body().getData();
                    String username=data.getUsername();
                    Boolean isStaffResponse=data.getIsStaff();
                    //if the loged in user is a normal user
                    if (!isStaffResponse){
                        String message="Welcome "+username;
                        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class)
                                .putExtra("data", (Serializable) data));
                        finish();
                    }
                    //if the logged in user is an admin user
                    else{
                        String message="Welcome "+username;
                        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActivity.this, AdminMainActivity.class)
                                .putExtra("data", (Serializable) data));
                        finish();
                    }
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(LoginActivity.this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    };
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                String message=t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });

    }
}