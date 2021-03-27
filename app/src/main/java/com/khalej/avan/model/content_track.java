package com.khalej.avan.model;

import com.google.gson.annotations.SerializedName;

public class content_track {
    @SerializedName("payload")
    payload_ payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;


    public payload_ getPayload() {
        return payload;
    }

    public void setPayload(payload_ payload) {
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

    public class payload_{
        @SerializedName("is_delivered")
        boolean is_delivered;
        @SerializedName("in_shipping")
        boolean in_shipping;
        @SerializedName("with_driver")
        boolean with_driver;
        @SerializedName("is_paid")
        boolean is_paid;
        @SerializedName("in_warehouse")
        boolean in_warehouse;
        @SerializedName("track_code")
        String track_code;

        public boolean isIn_warehouse() {
            return in_warehouse;
        }

        public void setIn_warehouse(boolean in_warehouse) {
            this.in_warehouse = in_warehouse;
        }

        public String getTrack_code() {
            return track_code;
        }

        public void setTrack_code(String track_code) {
            this.track_code = track_code;
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

        public boolean isIs_paid() {
            return is_paid;
        }

        public void setIs_paid(boolean is_paid) {
            this.is_paid = is_paid;
        }
    }


}
