package com.moringaschool.bookmeal.Admin;

import static com.moringaschool.bookmeal.UI.MainActivity.MyPREFERENCES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.moringaschool.bookmeal.AddMenuResponse;
import com.moringaschool.bookmeal.ApiClient;
import com.moringaschool.bookmeal.Authentication.EditProfileActivity;
import com.moringaschool.bookmeal.EditProfileRequest;
import com.moringaschool.bookmeal.Model.Menu;
import com.moringaschool.bookmeal.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditFoodItemActivity extends AppCompatActivity implements View.OnClickListener{
        ImageView menu_image, backhome;
        Button edit_menu,upload_image;
        ProgressDialog progressDialog;
        TextInputEditText name,price,description;
        Uri selectedImage;
        String menu_name,menu_price,menu_description,menu_img,token,menu_id;


        SharedPreferences sharedPreferences;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_food_item);
            name=findViewById(R.id.name);
            price=findViewById(R.id.price);
            description=findViewById(R.id.description);
            menu_image=findViewById(R.id.menu_image);
            edit_menu=findViewById(R.id.edit_menu);
            edit_menu.setOnClickListener(this);
            backhome=findViewById(R.id.back_btn);
            backhome.setOnClickListener(this);
            upload_image = findViewById(R.id.upload_image);
            upload_image.setOnClickListener(this);
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                menu_name = extras.getString("name");
                menu_price = extras.getString("price");
                menu_description= extras.getString("description");
                menu_img = extras.getString("image");
                menu_id = extras.getString("menu_id");
                name.setText(menu_name);
                price.setText(menu_price);
                description.setText(menu_description);
                Picasso.get().load(menu_img).into(menu_image);
                // and get whatever type user account id is
            }
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
            if (selectedImage == null) {
                selectedImage = data.getData();
            }
            else{
                Uri myUri = Uri.parse(menu_img);
                selectedImage=myUri;

            }
            selectedImage = data.getData();
            //Picasso.get().load(selectedImage).into(menu_image);
            Picasso.get().load(selectedImage).into(menu_image);



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
        RequestBody names = RequestBody.create(MediaType.parse("text/plain"), name.getText().toString());
        RequestBody prices = RequestBody.create(MediaType.parse("text/plain"), price.getText().toString());
        RequestBody descriptions = RequestBody.create(MediaType.parse("text/plain"), description.getText().toString());
        MultipartBody.Part ppp = MultipartBody.Part.createFormData("part", file.getName(), descBody);

        File imageFile = new File(getRealPathFromURI(fileUri));
        // Create a file using the absolute path of the image
        RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("menu_image", imageFile.getName(), reqBody);


        //creating a call and calling the upload image method

        Call<List<Menu>> call = ApiClient.getService().editMenuResponse(requestFile, names, prices, descriptions, partImage, token,menu_id);
        //finally performing the call
        call.enqueue( new Callback<List<Menu>>() {
            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Edited Menu Successfully...", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(EditFoodItemActivity.this, ViewMenuActivity.class));
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(EditFoodItemActivity.this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(EditFoodItemActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(EditFoodItemActivity.this, message, Toast.LENGTH_LONG).show();
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
        if(view == edit_menu){
            //initializing progress dialog
            progressDialog=new ProgressDialog(EditFoodItemActivity.this);
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