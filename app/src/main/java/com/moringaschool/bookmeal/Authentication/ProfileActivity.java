package com.moringaschool.bookmeal.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.moringaschool.bookmeal.Data;
import com.moringaschool.bookmeal.R;
import com.moringaschool.bookmeal.Tokens;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
Data data;
ImageView profile_image, backhome;
EditText profile_first_name,profile_other_name,profile_username,profile_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //get the login details
        backhome=findViewById(R.id.back_btn);
        backhome.setOnClickListener(this);
        Intent intent = getIntent();
        backhome=findViewById(R.id.back_btn);
        backhome.setOnClickListener(this);
        if(intent.getExtras()!=null) {
            //hooks
            profile_image=findViewById(R.id.profile_photo);
            profile_username=findViewById(R.id.proflie_username);
            profile_first_name=findViewById(R.id.proflie_first_name);
            profile_other_name=findViewById(R.id.proflie_other_name);
            profile_email=findViewById(R.id.proflie_email);
            //get the data object from the intent
            data = (Data) getIntent().getSerializableExtra("data");
            Tokens token = data.getTokens();
            String firstName = (String) data.getFirstName();
            String otherName = (String) data.getOtherName();
            String email = (String) data.getEmail();
            String username = (String) data.getUsername();
            String image = (String) data.getUserImage();
             image="https://bookameal.herokuapp.com"+image;
            String TAG = "MyActivity";
            Log.i(TAG,image);
            //set the obtained data to respective text on the view
            Picasso.get().load(image).into(profile_image);
            profile_first_name.setText(firstName);
            profile_other_name.setText(otherName);
            profile_email.setText(email);
            profile_username.setText(username);

        }
        else{
            //redirect to login

        }
    }

    @Override
    public void onClick(View view) {
        if (view == backhome) {
            onBackPressed();

        }

    }
}