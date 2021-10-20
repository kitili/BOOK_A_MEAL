package com.moringaschool.bookmeal.Admin;

import static com.moringaschool.bookmeal.UI.MainActivity.MyPREFERENCES;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.android.gms.common.api.Api;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moringaschool.bookmeal.AddMenuRequest;
import com.moringaschool.bookmeal.AddMenuResponse;
import com.moringaschool.bookmeal.ApiClient;
import com.moringaschool.bookmeal.Authentication.LoginActivity;
import com.moringaschool.bookmeal.Authentication.RegisterActivity;
import com.moringaschool.bookmeal.R;
import com.moringaschool.bookmeal.RegisterRequest;
import com.moringaschool.bookmeal.RegisterResponse;
import com.moringaschool.bookmeal.UserService;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Part;

public class AddFoodItemActivity extends AppCompatActivity implements View.OnClickListener {
        ImageView backhome, menu_image;
        TextInputLayout name;
        TextInputLayout price;
        TextInputLayout description;
        Button upload_image,add_menu;
        String token;
    Uri selectedImage;
    SharedPreferences sharedPreferences;

    ProgressDialog progressDialog;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_food_item);
            //------------------------hooks
            menu_image = findViewById(R.id.menu_image);
            name = findViewById(R.id.name);
            price = findViewById(R.id.price);
            description = findViewById(R.id.description);
            upload_image = findViewById(R.id.upload_image);
            backhome = findViewById(R.id.back_btn);
            add_menu=findViewById(R.id.add_menu);
            add_menu.setOnClickListener(this);
            backhome.setOnClickListener(this);
            upload_image.setOnClickListener(this);
            menu_image.setOnClickListener(this);
            //-----------------------get token from shared preferences
            sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            token=sharedPreferences.getString("access_token","");
            token= "Bearer "+token;

            //-----------------------get internet permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                finish();
                startActivity(intent);
            }
        }
        //method to override the onActivity()
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
                //the image URI
                selectedImage = data.getData();
                Picasso.get().load(selectedImage).into(menu_image);
               /// uploadFile();



            }
        }
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
        RequestBody names = RequestBody.create(MediaType.parse("text/plain"),name.getEditText().getText().toString());
        RequestBody prices = RequestBody.create(MediaType.parse("text/plain"),price.getEditText().getText().toString());
        RequestBody descriptions = RequestBody.create(MediaType.parse("text/plain"),description.getEditText().getText().toString());
        MultipartBody.Part ppp= MultipartBody.Part.createFormData("part",file.getName(),descBody);
        //String imgname = String.valueOf(Calendar.getInstance().getTimeInMillis());

        File imageFile = new File(getRealPathFromURI(fileUri));
        // Create a file using the absolute path of the image
        RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("menu_image", imageFile.getName(), reqBody);


        //creating a call and calling the upload image method

        Call<AddMenuResponse> call = ApiClient.getService().addMenu(requestFile, names,prices,descriptions,partImage,token);

        //finally performing the call
        call.enqueue(new Callback<AddMenuResponse>() {
            @Override
            public void onResponse(Call<AddMenuResponse> call, Response<AddMenuResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Menu Added Successfully...", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddFoodItemActivity.this,ViewMenuActivity.class));
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(AddFoodItemActivity.this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(AddFoodItemActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    };

                }

            }

            @Override
            public void onFailure(Call<AddMenuResponse> call, Throwable t) {
                String message=t.getLocalizedMessage();
                Toast.makeText(AddFoodItemActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onClick(View view) {
        if (view == backhome) {
            onBackPressed();

        }
            //open file chooser
        if(view == upload_image){
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 100);

        }
        if(view == add_menu){
            //initializing progress dialog
            progressDialog=new ProgressDialog(AddFoodItemActivity.this);
            //show dialog
            progressDialog.show();
            //set content
            progressDialog.setContentView(R.layout.progress_dialog);
            //set transparent bg
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            //calling the upload file method after choosing the file
            uploadFile(selectedImage, "My Image");

        }

    }
}
