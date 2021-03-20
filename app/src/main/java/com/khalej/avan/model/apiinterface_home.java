package com.khalej.avan.model;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface apiinterface_home {


    @FormUrlEncoded
    @POST("api/login")
    Call<contact_general_user> getcontacts_login(@Field("phone") String kayWord, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/contacts")
    Call<ResponseBody> CallUs(@Field("name") String name, @Field("phone") String address, @Field("type") String type,
                              @Field("message") String message);

    @FormUrlEncoded
    @POST("api/forget/password")
    Call<Reset>getcontacts_ResetPassword(@Field("email") String kayWord);


    @FormUrlEncoded
    @POST("api/forget/password/new")
    Call<Reset>getcontacts_updatePassword(@Field("email") String kayWord, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/forget/password/reset")
    Call<Reset>getcontacts_tokenPassword(@Field("email") String kayWord, @Field("token") String password);



    @FormUrlEncoded
    @POST("maishwary/api/canceling_order")
    Call<ResponseBody> getcontacts_CancelOrder(@Field("order_id") int order_id, @Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("maishwary/api/rate")
    Call<ResponseBody> getcontacts_AddRate(@Field("to_id") int to_id, @Field("form_id") int form_id, @Field("rate") Float rate,
                                           @Field("des") String des);


    @FormUrlEncoded
    @POST("maishwary/api/delete_notification")
    Call<ResponseBody> getcontacts_CancelNotification(@Field("notification_id") int order_id, @Field("user_id") int user_id);






    @FormUrlEncoded
    @POST("api/products/order")
    Call<ResponseBody> content_addOrder(@HeaderMap Map<String, String> headers, @Field("address_id") String address_id,
                                        @Field("day") String day, @Field("time") String time,
                                        @Field("payment_method") String payment_method, @Field("comment") String comment
    );

    @FormUrlEncoded
    @POST("api/register")
    Call<contact_general_user> getcontacts_newaccount(@Field("full_name") String name, @Field("password") String password, @Field("email") String address,
                                                      @Field("phone") String phone);


@FormUrlEncoded
    @PATCH("api/profile/update")
    Call <contact_general_user_update> updateProfile(@HeaderMap Map<String, String> headers, @Field("full_name") String name,
                                                     @Field("email") String email, @Field("phone") String phone);


    @FormUrlEncoded
    @PATCH("api/profile/update/password")
    Call <contact_general_user_update> updateProfile_pass(@HeaderMap Map<String, String> headers,
                                                          @Field("password") String password,
                                                          @Field("password_confirmation") String password_confirmation);


    @FormUrlEncoded
    @POST("api/products/{product_id}/cart")
    Call<ResponseBody> content_addcard(@HeaderMap Map<String, String> headers, @Field("quantity") int quantity,
                                       @Field("product_id") String product_id);


    @DELETE("api/user/addresses/destroy/{address_id}")
    Call<ResponseBody> delete_address(@HeaderMap Map<String, String> headers, @Path("address_id") String address_id);

    @FormUrlEncoded
    @POST("api/user/addresses")
    Call<ResponseBody> content_addaddress(@HeaderMap Map<String, String> headers, @Field("flat_number") String flat_number,
                                          @Field("block_number") String block_number, @Field("floor_number") String floor_number,
                                          @Field("street_name") String street_name, @Field("type") String type,
                                          @Field("lat") double lat, @Field("lng") double lng
    );


    @PATCH("/api/orders/{order_id}/mark-as-in-shipping")
    Call<ResponseBody> markShipping(@HeaderMap Map<String, String> headers, @Path("order_id") String order_id);

    @PATCH("/api/orders/{order_id}/mark-as-not-in-shipping")
    Call<ResponseBody> marknotShipping(@HeaderMap Map<String, String> headers, @Path("order_id") String order_id);

    @PATCH("/api/orders/{order_id}/mark-as-delivered")
    Call<ResponseBody> markdelevried(@HeaderMap Map<String, String> headers, @Path("order_id") String order_id);


}

