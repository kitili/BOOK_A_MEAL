package com.moringaschool.bookmeal;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

//    @POST ("api/users/change-password-request/")
//    Call<ResetPasswordRequest> resetPassword (@Body ResetPasswordRequest resetPassword, @Header("Authorization") String authToken);


}
