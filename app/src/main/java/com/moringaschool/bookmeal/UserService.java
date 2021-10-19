package com.moringaschool.bookmeal;


import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
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



//    @POST ("api/users/change-password-request/")
//    Call<ResetPasswordRequest> resetPassword (@Body ResetPasswordRequest resetPassword, @Header("Authorization") String authToken);


}
