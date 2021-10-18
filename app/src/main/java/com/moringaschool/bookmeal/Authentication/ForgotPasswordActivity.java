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
import com.moringaschool.bookmeal.RegisterResponse;
import com.moringaschool.bookmeal.ResetPasswordRequest;
import com.moringaschool.bookmeal.ResetPasswordResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener{
    Button login,reset;
    ImageView backhome;
    TextInputLayout email;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email=findViewById(R.id.email);
        login=findViewById(R.id.login);
        reset=findViewById(R.id.reset);
        login.setOnClickListener(this);
        reset.setOnClickListener(this);
        backhome=findViewById(R.id.back_btn);
        backhome.setOnClickListener(this);
        //email = findViewById(R.id.email);
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
            //initializing progress dialog
            progressDialog=new ProgressDialog(ForgotPasswordActivity.this);
            //show dialog
            progressDialog.show();
            //set content
            progressDialog.setContentView(R.layout.progress_dialog);
            //set transparent bg
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            //reset password
            ResetPasswordRequest resetPasswordRequest=new ResetPasswordRequest();
            resetPasswordRequest.setEmail(email.getEditText().getText().toString());
            resetPassword(resetPasswordRequest);

        }
        if (view == backhome) {
            onBackPressed();

        }

    }

    private void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        Call<ResetPasswordResponse> resetPasswordRequestCall= ApiClient.getService().resetPassword(resetPasswordRequest);
        resetPasswordRequestCall.enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                if(response.isSuccessful()){
                    String message="An email with the reset password details have been sent to your account";
                    Toast.makeText(ForgotPasswordActivity.this,message,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));

                }else{
                    //String message="an error occurred ease try again later";
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(ForgotPasswordActivity.this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(ForgotPasswordActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    };
                }

            }
            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
                String message=t.getLocalizedMessage();
                Toast.makeText(ForgotPasswordActivity.this,message,Toast.LENGTH_LONG).show();

            }
        });
    }

}