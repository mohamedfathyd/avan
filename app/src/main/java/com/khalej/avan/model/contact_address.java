package com.khalej.avan.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class contact_address {
    @SerializedName("payload")
    List<Address> payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;


    public List<Address> getPayload() {
        return payload;
    }

    public void setPayload(List<Address> payload) {
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

    public class Address {
        @SerializedName("id")
        String id;
        @SerializedName("address")
        String address;
        @SerializedName("type")
        String type;
        @SerializedName("building_number")
        int building_number;
        @SerializedName("floor")
        int floor;
        @SerializedName("flat_number")
        int flat_number;

        public int getBuilding_number() {
            return building_number;
        }

        public void setBuilding_number(int building_number) {
            this.building_number = building_number;
        }

        public int getFloor() {
            return floor;
        }

        public void setFloor(int floor) {
            this.floor = floor;
        }

        public int getFlat_number() {
            return flat_number;
        }

        public void setFlat_number(int flat_number) {
            this.flat_number = flat_number;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}