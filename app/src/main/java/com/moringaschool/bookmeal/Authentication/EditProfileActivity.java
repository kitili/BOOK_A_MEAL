package com.moringaschool.bookmeal.Authentication;

import static com.moringaschool.bookmeal.UI.MainActivity.MyPREFERENCES;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.moringaschool.bookmeal.ApiClient;
import com.moringaschool.bookmeal.Model.EditProfile;
import com.moringaschool.bookmeal.Model.EditProfileRequest;
import com.moringaschool.bookmeal.R;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "EditProfileActivity";
    ImageView profile_image, backhome;
    EditText profile_first_name,profile_other_name,profile_username,profile_email;
    Button btn_edit_profile;
    ProgressDialog progressDialog;
    String token,user_id,other_name,first_name,username,email;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        //hooks
        profile_image=findViewById(R.id.profile_photo);
        profile_username=findViewById(R.id.profile_username);
        profile_first_name=findViewById(R.id.profile_first_name);
        profile_other_name=findViewById(R.id.profile_other_name);
        profile_email=findViewById(R.id.profile_email);
        btn_edit_profile=findViewById(R.id.btn_edit_profile);
        btn_edit_profile.setOnClickListener(this);
        backhome=findViewById(R.id.back_btn);
        backhome.setOnClickListener(this);


        //get dat from shared preferences
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token=sharedPreferences.getString("access_token","");
        user_id=sharedPreferences.getString("idKey","");
        first_name=sharedPreferences.getString("first_name_key","");
        other_name=sharedPreferences.getString("other_name_key","");
        username=sharedPreferences.getString("usernameKey","");
        email=sharedPreferences.getString("emailKey","");

        //set data
        profile_first_name.setText(first_name);
        profile_other_name.setText(other_name);
        profile_email.setText(email);
        profile_username.setText(username);


    }
    public boolean validateFirstName(){
        String val=profile_first_name.getText().toString();
        if(val.isEmpty()){
            profile_first_name.setError("Field cannot be empty");
            return false;
        }
        else{
            profile_first_name.setError(null);
            return true;
        }

    }
    public boolean validateOtherName(){
        String val=profile_other_name.getText().toString();
        if(val.isEmpty()){
            profile_other_name.setError("Field cannot be empty");
            return false;
        }
        else{
            profile_other_name.setError(null);
            return true;
        }

    }
    public boolean validateUserName(){
        String val=profile_username.getText().toString();
        if(val.isEmpty()){
            profile_username.setError("Field cannot be empty");
            return false;
        }
        else{
            profile_username.setError(null);
            return true;
        }

    }
    public boolean validateEmail(){
        String val=profile_email.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            profile_email.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            profile_email.setError("Invalid email address");
            return false;
        } else {
            profile_email.setError(null);
            return true;
        }

    }


    @Override
    public void onClick(View view) {
        if (view == backhome) {
            onBackPressed();

        }
        if(view == btn_edit_profile){
            if (!validateFirstName() | !validateOtherName()  | !validateEmail() | !validateUserName()) {
                return;
            }
            //initializing progress dialog
            progressDialog=new ProgressDialog(EditProfileActivity.this);
            //show dialog
            progressDialog.show();
            //set content
            progressDialog.setContentView(R.layout.progress_dialog);
            //set transparent bg
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            EditProfileRequest editProfileRequest=new EditProfileRequest();
            editProfileRequest.setFirst_name(profile_first_name.getText().toString());
            editProfileRequest.setOther_names(profile_other_name.getText().toString());
            editProfileRequest.setUsername(profile_username.getText().toString());
            editProfileRequest.setEmail(profile_email.getText().toString());
            editProfile(editProfileRequest);

        }

    }

    private void editProfile(EditProfileRequest editProfileRequest) {

        token= "Bearer "+token;
        Call<EditProfile> editProfileResponseCall= ApiClient.getService().editProfile(editProfileRequest,token,user_id);
        editProfileResponseCall.enqueue(new Callback<EditProfile>() {
            @Override
            public void onResponse(Call<EditProfile> call, Response<EditProfile> response) {
                if(response.isSuccessful()){
                    String message="Profile Edited successfully";
                    Toast.makeText(EditProfileActivity.this,message,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("usernameKey", profile_username.getText().toString());
                    editor.putString("emailKey", profile_email.getText().toString());
                    editor.putString("first_name_key", profile_first_name.getText().toString());
                    editor.putString("other_name_key", profile_other_name.getText().toString());
                    editor.apply();

                }else{
                    //String message="an error occurred ease try again later";
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(EditProfileActivity.this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    };
                }
            }

            @Override
            public void onFailure(Call<EditProfile> call, Throwable t) {
                String message=t.getLocalizedMessage();
                Toast.makeText(EditProfileActivity.this,message,Toast.LENGTH_LONG).show();
            }

        });

    }
}