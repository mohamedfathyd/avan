package com.khalej.avan.model;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    @POST("api/request-shipments")
    Call<ResponseBody> confirmShipment(@Field("description") String description, @Field("address_id") String address_id);

    @FormUrlEncoded
    @POST("api/forget/password")
    Call<Reset>getcontacts_ResetPassword(@Field("email") String kayWord);


    @FormUrlEncoded
    @POST("api/forget/password/new")
    Call<Reset>getcontacts_updatePassword(@Field("email") String kayWord, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/forget/password/reset")
    Call<Reset>getcontacts_tokenPassword(@Field("email") String kayWord, @Field("token") String password);

    @GET("api/general?lang=ar")
    Call<contact_general> getcontacts_generalData();



    @FormUrlEncoded
    @POST("api/feedback")
    Call<ResponseBody> getcontacts_AddRate( @Field("rate") String rate,
                                           @Field("des") String des);






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
    Call <contact_general_user_update> updateProfile(@HeaderMap Map<String, String> headers, @Field("name") String name,
                                                     @Field("email") String email, @Field("phone") String phone,
                                                     @Field("national_id_expiry_date") String national_id_expiry_date,
                                                     @Field("national_id") String national_id);


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

    @GET("api/general")
    Call<contact_general_> getcontacts_g(@Query("lang") String lang);

    @FormUrlEncoded
    @POST("api/shipments/track")
    Call<content_track> content_track( @Field("track_code") String track_code);

    @Multipart
    @POST("api/register")
    Call<contact_general_user>getcontact_newaccount(@Part MultipartBody.Part image, @Part("first_name") RequestBody first_name,
                                                    @Part("last_name") RequestBody last_name, @Part("email") RequestBody email,
                                                    @Part("password") RequestBody password, @Part("phone") RequestBody phone,
                                                    @Part("national_id") RequestBody national_id,
                                                    @Part("national_id_expiry_date") RequestBody national_id_expiry_date);

    @GET("api/profile/addresses")
    Call<contact_address> user_address(@HeaderMap Map<String, String> headers);


    @DELETE("/api/profile/addresses/{id}/delete")
    Call<ResponseBody> deleteAddress(@HeaderMap Map<String, String> headers,@Path("id") String id);

    @GET("api/profile/")
    Call<contact_general_profile> getcontacts_profile(@HeaderMap Map<String, String> headers);

    @FormUrlEncoded
    @POST("api/profile/addresses")
    Call<ResponseBody> add_newAddress(@HeaderMap Map<String, String> headers, @Field("address") String address,
                                      @Field("building_number") int building_number,@Field("floor")int floor,
                                      @Field("flat_number") int flat_number,
                                      @Field("additional_info") String additional_info, @Field("landline_number")String landline_number,
                                      @Field("type") String type,@Field("is_primary") int is_primary ,
                                      @Field("latitude") double latitude,@Field("longitude") double longitude,
                                      @Field("city_id") String city_id);

}

