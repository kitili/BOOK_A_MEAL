package com.moringaschool.bookmeal;


import com.moringaschool.bookmeal.Model.AddMenuResponse;
import com.moringaschool.bookmeal.Model.Data;
import com.moringaschool.bookmeal.Model.EditProfile;
import com.moringaschool.bookmeal.Model.EditProfileRequest;
import com.moringaschool.bookmeal.Model.LoginRequest;
import com.moringaschool.bookmeal.Model.LoginResponse;
import com.moringaschool.bookmeal.Model.MakeOrder;
import com.moringaschool.bookmeal.Model.Menu;
import com.moringaschool.bookmeal.Model.OrderResponse;
import com.moringaschool.bookmeal.Model.OrderSummary;
import com.moringaschool.bookmeal.Model.Orders;
import com.moringaschool.bookmeal.Model.OrdersResponse;
import com.moringaschool.bookmeal.Model.ProfileResponse;
import com.moringaschool.bookmeal.Model.RegisterRequest;
import com.moringaschool.bookmeal.Model.RegisterResponse;
import com.moringaschool.bookmeal.Model.ResetPasswordRequest;
import com.moringaschool.bookmeal.Model.ResetPasswordResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @Multipart
    @PUT("https://bookameal.herokuapp.com/api/users/profile/{id}/edit/")
//  Call<EditProfile> editProfile (@Body EditProfileRequest editProfileRequest,
//                                 @Header("Authorization")  String token,
//                                 @Path("id") String id);
    Call<Data> editProfile(@Part("image\"; filename=\"myfile.jpg\" ") RequestBody file,
                           @Part("email") RequestBody email,
                           @Part("username") RequestBody username,
                           @Part("first_name") RequestBody first_name,
                           @Part("other_name") RequestBody other_name,
                           @Part MultipartBody.Part user_image,
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

    @Multipart
    @PUT("https://bookameal.herokuapp.com/api/menus/item/{menu_id}/edit/")
    Call <Menu> editMenuResponse (
            @Part("image\"; filename=\"myfile.jpg\" ") RequestBody file,
            @Part("name") RequestBody name,
            @Part("price") RequestBody price,
            @Part("description") RequestBody description,
            @Part MultipartBody.Part menu_image,
            @Header("Authorization")  String token,
            @Path("menu_id") String menu_id
    );

    @GET("api/orders/fetch-orders/")
    Call <List<Orders>>orderResponse (@Header("Authorization")  String token);


    @POST("api/orders/add/")
    Call <OrdersResponse> makeorderResponse (@Body MakeOrder makeOrder,
                                             @Header("Authorization")  String token);
    @GET("api/orders/fetch-order-history/")
    Call <List<Orders>>orderUserResponse (@Header("Authorization")  String token);

    @DELETE("api/orders/delete-order/{order_id}/")
    Call<Void> deleteOrder (@Header("Authorization")  String token,
                        @Path("order_id") String order_id);

    @POST("api/orders/close-order/{order_id}/")
    Call<Void> completeOrder (@Header("Authorization")  String token,
                            @Path("order_id") String order_id);

    @GET("api/orders/fetch-order-history/")
    Call <List<OrderResponse>> orderResponses (@Header("Authorization")  String token);

    @GET("api/orders/fetch-summary/")
    Call <OrderSummary>ordersummary (@Header("Authorization")  String token);

    @GET("api/users/profiles")
    Call <List<ProfileResponse>> userProfilesList (@Header("Authorization")  String token);


}
