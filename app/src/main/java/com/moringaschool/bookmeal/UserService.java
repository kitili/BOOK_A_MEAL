package com.moringaschool.bookmeal;


import com.moringaschool.bookmeal.Model.Menu;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserService {
    @POST ("api/users/login/")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST ("api/users/register/")
    Call<RegisterResponse> registerUser (@Body RegisterRequest registerRequest);

    @POST ("api/users/change-password-request/")
    Call<ResetPasswordResponse> resetPassword (@Body ResetPasswordRequest resetPassword);

    @PUT("https://bookameal.herokuapp.com/api/users/profile/{id}/edit/")
  Call<EditProfile> editProfile (@Body EditProfileRequest editProfileRequest,
                                 @Header("Authorization")  String token,
                                 @Path("id") String id);


//    @Multipart
//    @POST ("https://bookameal.herokuapp.com/api/menus/add/")
//    Call<AddMenuResponse> addMenu (@Body AddMenuRequest AddMenuRequest,
//                                   @Header("Authorization")  String token,
//                                   @Part MultipartBody.Part image);

    @Multipart
    @POST ("https://bookameal.herokuapp.com/api/menus/add/")
    //Call<AddMenuResponse> addMenu (@Part("image\",filename=\"myfile.jpg\" ")RequestBody);
    Call<AddMenuResponse> addMenu(@Part("image\"; filename=\"myfile.jpg\" ") RequestBody file,
                                  @Part("name") RequestBody name,
                                  @Part("price") RequestBody price,
                                  @Part("description") RequestBody description,
                                  @Part MultipartBody.Part menu_image,
                                  @Header("Authorization")  String token);

    @GET("https://bookameal.herokuapp.com/api/menus/items/")
    Call <List<Menu>>menuResponse (@Header("Authorization")  String token);


    @DELETE("https://bookameal.herokuapp.com/api/menus/item/{menu_id}/delete/")
    Call<Void> deleteMenu (@Header("Authorization")  String token,
                          @Path("menu_id") String menu_id);

    @GET("https://bookameal.herokuapp.com/api/menus/item/{menu_id}/set/")
    Call<Void> setMenu (@Header("Authorization")  String token,
                           @Path("menu_id") String menu_id);

    @PUT("https://bookameal.herokuapp.com/api/menus/item/{menu_id}/edit/")
    Call <List<Menu>>editMenuResponse (
            @Part("image\"; filename=\"myfile.jpg\" ") RequestBody file,
            @Part("name") RequestBody name,
            @Part("price") RequestBody price,
            @Part("description") RequestBody description,
            @Part MultipartBody.Part menu_image,
            @Header("Authorization")  String token
    );

}
