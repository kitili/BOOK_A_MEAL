package com.moringaschool.bookmeal.Authentication;

import static com.moringaschool.bookmeal.UI.MainActivity.MyPREFERENCES;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.moringaschool.bookmeal.Model.Data;
import com.moringaschool.bookmeal.R;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
Data data;
ImageView profile_image, backhome;
Button btn_edit_profile;
EditText profile_first_name,profile_other_name,profile_username,profile_email;
String token,user_id,other_name,first_name,username,email,user_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //get the login details
        backhome=findViewById(R.id.back_btn);
        btn_edit_profile=findViewById(R.id.btn_edit_profile);
        backhome.setOnClickListener(this);
        btn_edit_profile.setOnClickListener(this);
        Intent intent = getIntent();
        backhome=findViewById(R.id.back_btn);
        backhome.setOnClickListener(this);
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //hooks
        profile_image=findViewById(R.id.profile_photo);
        profile_username=findViewById(R.id.profile_username);
        profile_first_name=findViewById(R.id.profile_first_name);
        profile_other_name=findViewById(R.id.profile_other_name);
        profile_email=findViewById(R.id.profile_email);

        token=sharedPreferences.getString("access_token","");
        user_id=sharedPreferences.getString("idKey","");
        first_name=sharedPreferences.getString("first_name_key","");
        other_name=sharedPreferences.getString("other_name_key","");
        username=sharedPreferences.getString("usernameKey","");
        email=sharedPreferences.getString("emailKey","");
        user_image=sharedPreferences.getString("UserImageKey","");

//set the obtained data to respective text on the view
       // Picasso.get().load(image).into(profile_image);
        profile_first_name.setText(first_name);
        profile_other_name.setText(other_name);
        profile_email.setText(email);
        profile_username.setText(username);
        Picasso.get().load(user_image).into(profile_image);
       // Picasso.get().load("https://res.cloudinary.com/dxouhnqpf/image/upload/v1/images/menus/icons8-cute-pumpkin-30_neiad6").into(profile_image);

    }

    @Override
    public void onClick(View view) {
        if (view == backhome) {
            onBackPressed();

        }
        if(view == btn_edit_profile){
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);
        }

    }
}