package com.moringaschool.bookmeal.Authentication;

import static com.moringaschool.bookmeal.UI.MainActivity.MyPREFERENCES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.moringaschool.bookmeal.Admin.AddFoodItemActivity;
import com.moringaschool.bookmeal.Admin.ViewMenuActivity;
import com.moringaschool.bookmeal.ApiClient;
import com.moringaschool.bookmeal.Model.AddMenuResponse;
import com.moringaschool.bookmeal.Model.Data;
import com.moringaschool.bookmeal.Model.EditProfile;
import com.moringaschool.bookmeal.Model.EditProfileRequest;
import com.moringaschool.bookmeal.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "EditProfileActivity";
    ImageView profile_image, backhome;
    EditText profile_first_name,profile_other_name,profile_username,profile_email;
    Button btn_edit_profile,btn_upload;
    ProgressDialog progressDialog;
    String token,user_id,other_name,first_name,username,email,user_image;
    SharedPreferences sharedPreferences;
    Uri selectedImage;

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
        btn_upload=findViewById(R.id.btn_upload);
        btn_edit_profile.setOnClickListener(this);
        btn_upload.setOnClickListener(this);
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
        user_image=sharedPreferences.getString("UserImageKey","");

        //set data
        profile_first_name.setText(first_name);
        profile_other_name.setText(other_name);
        profile_email.setText(email);
        profile_username.setText(username);
        Picasso.get().load(user_image).into(profile_image);
        token= "Bearer "+token;

        //-----------------------get internet permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
        }


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

    //method to override the onActivity()
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            //the image URI
            selectedImage = data.getData();
            Picasso.get().load(selectedImage).into(profile_image);
            /// uploadFile();
        }
    }
    //open file chooser

    /*
     * This method is fetching the absolute path of the image file
     * if you want to upload other kind of files like .pdf, .docx
     * you need to make changes on this method only
     * Rest part will be the same
     * */
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
    private void uploadFile(Uri fileUri, String desc) {

        //creating a file
        File file = new File(getRealPathFromURI(fileUri));

        //creating request body for file
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
        RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), desc);
        RequestBody first_name = RequestBody.create(MediaType.parse("text/plain"),profile_first_name.getText().toString());
        RequestBody other_name = RequestBody.create(MediaType.parse("text/plain"),profile_other_name.getText().toString());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"),profile_email.getText().toString());
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"),profile_username.getText().toString());
        MultipartBody.Part ppp= MultipartBody.Part.createFormData("part",file.getName(),descBody);
        //String imgname = String.valueOf(Calendar.getInstance().getTimeInMillis());

        File imageFile = new File(getRealPathFromURI(fileUri));
        // Create a file using the absolute path of the image
        RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("user_image", imageFile.getName(), reqBody);


        //creating a call and calling the upload image method

        Call<Data> call = ApiClient.getService().editProfile(requestFile, email,username,first_name,other_name,partImage,token,user_id);

        //finally performing the call
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.isSuccessful()) {

                    SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
                    editor.putString("usernameKey", profile_username.getText().toString());
                    editor.putString("emailKey", profile_email.getText().toString());
                    editor.putString("first_name_key", profile_first_name.getText().toString());
                    editor.putString("other_name_key", profile_other_name.getText().toString());
                    editor.putString("UserImageKey", String.valueOf(partImage));
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "Profile updated Added Successfully...", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(EditProfileActivity.this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    };

                }

            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                String message=t.getLocalizedMessage();
                Toast.makeText(EditProfileActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onClick(View view) {
        if (view == backhome) {
            onBackPressed();

        }
        //open file chooser
        if(view == btn_upload){
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 100);

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
            uploadFile(selectedImage, "My Image");

        }

    }


//    private void editProfile(EditProfileRequest editProfileRequest) {
//
//        token= "Bearer "+token;
//        Call<EditProfile> editProfileResponseCall= ApiClient.getService().editProfile(editProfileRequest,token,user_id);
//        editProfileResponseCall.enqueue(new Callback<EditProfile>() {
//            @Override
//            public void onResponse(Call<EditProfile> call, Response<EditProfile> response) {
//                if(response.isSuccessful()){
//                    String message="Profile Edited successfully";
//                    Toast.makeText(EditProfileActivity.this,message,Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
//                    startActivity(intent);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("usernameKey", profile_username.getText().toString());
//                    editor.putString("emailKey", profile_email.getText().toString());
//                    editor.putString("first_name_key", profile_first_name.getText().toString());
//                    editor.putString("other_name_key", profile_other_name.getText().toString());
//                    editor.apply();
//
//                }
//                else{
//                    //String message="an error occurred ease try again later";
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        Toast.makeText(EditProfileActivity.this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
//                    } catch (Exception e) {
//                        Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                    };
//                }
//            }
//
//            @Override
//            public void onFailure(Call<EditProfile> call, Throwable t) {
//                String message=t.getLocalizedMessage();
//                Toast.makeText(EditProfileActivity.this,message,Toast.LENGTH_LONG).show();
//            }
//
//        });
//
//    }
}