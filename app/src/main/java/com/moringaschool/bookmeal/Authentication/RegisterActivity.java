package com.moringaschool.bookmeal.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.moringaschool.bookmeal.ApiClient;
import com.moringaschool.bookmeal.R;
import com.moringaschool.bookmeal.RegisterRequest;
import com.moringaschool.bookmeal.RegisterResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
Button toLogin,toRegister;
ImageView backhome;
TextInputLayout fullName,email,password,confirmPassword;
ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toRegister=findViewById(R.id.toRegister);
        toLogin=findViewById(R.id.backLogin);
        toRegister.setOnClickListener(this);
        toLogin.setOnClickListener(this);


        fullName =findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);

    }
    public boolean validateName(){
        String val=fullName.getEditText().getText().toString();
        if(val.isEmpty()){
            fullName.setError("Field cannot be empty");
            return false;
        }
        else{
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }

    }
    public boolean validateEmail(){
        String val=email.getEditText().getText().toString();
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

    public boolean validatePassword(){
        String val = password.getEditText().getText().toString();
        String val2 = confirmPassword.getEditText().getText().toString();
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
        } else if(val2.isEmpty()){
            password.setError("Field cannot be empty");
            return false;
        }
        else if (!val.matches(passwordVal)) {
            password.setError("Password is too weak");
            return false;
        }
        else if(!val.equals(val2)){
            password.setError("Password do not match");
            return false;
        }

        else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }

    }
    public void registerUser(RegisterRequest registerRequest){
        Call<RegisterResponse> registerResponseCall= ApiClient.getService().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful()){
                    String message="Registration successfull";
                    Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                }else{
                    //String message="an error occurred ease try again later";
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(RegisterActivity.this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    };
                }

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                String message=t.getLocalizedMessage();
                Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view == toRegister){
           if (!validateName() | !validatePassword()  | !validateEmail() | !validateName()) {
               return;
            }
            //initializing progress dialog
            progressDialog=new ProgressDialog(RegisterActivity.this);
            //show dialog
            progressDialog.show();
            //set content
            progressDialog.setContentView(R.layout.progress_dialog);
            //set transparent bg
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            //register
            RegisterRequest registerRequest=new RegisterRequest();
            registerRequest.setEmail(email.getEditText().getText().toString());
            registerRequest.setUsername(fullName.getEditText().getText().toString());
            registerRequest.setPassword(password.getEditText().getText().toString());
            registerRequest.setPassword2(confirmPassword.getEditText().getText().toString());
            registerUser(registerRequest);

        }
        if(view == toLogin){
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);

        }


    }
}