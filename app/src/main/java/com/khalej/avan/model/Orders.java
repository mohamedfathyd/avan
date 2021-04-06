package com.khalej.avan.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Orders {
    @SerializedName("payload")
    List<order_data> payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;

    public List<order_data> getPayload() {
        return payload;
    }

    public void setPayload(List<order_data> payload) {
        this.payload = payload;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public  class  order_data{
        @SerializedName("id")
        String id;
        @SerializedName("track_code")
        String track_code;
        @SerializedName("sender_phone")
        String sender_phone;
        @SerializedName("sender_email")
        String sender_email;
        @SerializedName("sender_name")
        String sender_name;
        @SerializedName("sender_address")
        String sender_address;
        @SerializedName("sender_latitude")
        String sender_latitude;
        @SerializedName("sender_longitude")
        String sender_longitude;
        @SerializedName("receiver_name")
        String receiver_name;
        @SerializedName("receiver_phone")
        String receiver_phone;
        @SerializedName("receiver_latitude")
        String receiver_latitude;
        @SerializedName("receiver_longitude")
        String receiver_longitude;
        @SerializedName("receiver_city")
        String receiver_city;
        @SerializedName("receiver_address")
        String receiver_address;
        @SerializedName("day")
        String day;
        @SerializedName("weight")
        double weight;
        @SerializedName("size")
        String size;
        @SerializedName("payment_method")
        String payment_method;
        @SerializedName("quantity")
        int quantity;
        @SerializedName("description")
        String description;
        @SerializedName("declared_value")
        String price;
        @SerializedName("currency")
        String currency;
        @SerializedName("time")
        String time;
        @SerializedName("is_delivered")
        boolean is_delivered;
        @SerializedName("in_shipping")
        boolean in_shipping;
        @SerializedName("with_driver")
        boolean with_driver;
        @SerializedName("in_warehouse")
        boolean in_warehouse;
        @SerializedName("is_canceled")
        boolean is_canceled;
        @SerializedName("is_paid")
        boolean is_paid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTrack_code() {
            return track_code;
        }

        public void setTrack_code(String track_code) {
            this.track_code = track_code;
        }

        public String getSender_phone() {
            return sender_phone;
        }

        public void setSender_phone(String sender_phone) {
            this.sender_phone = sender_phone;
        }

        public String getSender_email() {
            return sender_email;
        }

        public void setSender_email(String sender_email) {
            this.sender_email = sender_email;
        }

        public String getSender_name() {
            return sender_name;
        }

        public void setSender_name(String sender_name) {
            this.sender_name = sender_name;
        }

        public String getSender_address() {
            return sender_address;
        }

        public void setSender_address(String sender_address) {
            this.sender_address = sender_address;
        }

        public String getSender_latitude() {
            return sender_latitude;
        }

        public void setSender_latitude(String sender_latitude) {
            this.sender_latitude = sender_latitude;
        }

        public String getSender_longitude() {
            return sender_longitude;
        }

        public void setSender_longitude(String sender_longitude) {
            this.sender_longitude = sender_longitude;
        }

        public String getReceiver_name() {
            return receiver_name;
        }

        public void setReceiver_name(String receiver_name) {
            this.receiver_name = receiver_name;
        }

        public String getReceiver_phone() {
            return receiver_phone;
        }

        public void setReceiver_phone(String receiver_phone) {
            this.receiver_phone = receiver_phone;
        }

        public String getReceiver_latitude() {
            return receiver_latitude;
        }

        public void setReceiver_latitude(String receiver_latitude) {
            this.receiver_latitude = receiver_latitude;
        }

        public String getReceiver_longitude() {
            return receiver_longitude;
        }

        public void setReceiver_longitude(String receiver_longitude) {
            this.receiver_longitude = receiver_longitude;
        }

        public String getReceiver_city() {
            return receiver_city;
        }

        public void setReceiver_city(String receiver_city) {
            this.receiver_city = receiver_city;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public double getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public boolean isIs_delivered() {
            return is_delivered;
        }

        public void setIs_delivered(boolean is_delivered) {
            this.is_delivered = is_delivered;
        }

        public boolean isIn_shipping() {
            return in_shipping;
        }

        public void setIn_shipping(boolean in_shipping) {
            this.in_shipping = in_shipping;
        }

        public boolean isWith_driver() {
            return with_driver;
        }

        public void setWith_driver(boolean with_driver) {
            this.with_driver = with_driver;
        }

        public boolean isIn_warehouse() {
            return in_warehouse;
        }

        public void setIn_warehouse(boolean in_warehouse) {
            this.in_warehouse = in_warehouse;
        }

        public boolean isIs_canceled() {
            return is_canceled;
        }

        public void setIs_canceled(boolean is_canceled) {
            this.is_canceled = is_canceled;
        }

        public boolean isIs_paid() {
            return is_paid;
        }

        public void setIs_paid(boolean is_paid) {
            this.is_paid = is_paid;
        }

        public String getReceiver_address() {
            return receiver_address;
        }

        public void setReceiver_address(String receiver_address) {
            this.receiver_address = receiver_address;
        }

        public String getPayment_method() {
            return payment_method;
        }

        public void setPayment_method(String payment_method) {
            this.payment_method = payment_method;
        }
    }
}
