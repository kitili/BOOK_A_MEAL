package com.moringaschool.bookmeal;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {
    @POST ("api/users/login/")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST ("api/users/register/")
    Call<RegisterResponse> registerUser (@Body RegisterRequest registerRequest);

    @POST ("api/users/change-password-request/")
    Call<ResetPasswordResponse> resetPassword (@Body ResetPasswordRequest resetPassword);
//    @POST ("api/users/change-password-request/")
//    Call<ResetPasswordRequest> resetPassword (@Body ResetPasswordRequest resetPassword, @Header("Authorization") String authToken);


}
