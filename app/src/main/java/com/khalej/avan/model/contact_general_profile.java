package com.khalej.avan.model;
import com.google.gson.annotations.SerializedName;


public class contact_general_profile {
    @SerializedName("payload")
    contact_user_info payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;

    public contact_user_info getPayload() {
        return payload;
    }

    public void setPayload(contact_user_info payload) {
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
    public class contact_user_info {
     @SerializedName("name")
        String name;
     @SerializedName("email")
        String email;
     @SerializedName("phone")
        String phone;
        @SerializedName("type")
        String type;
     @SerializedName("id")
        String id;
     @SerializedName("national_id")
        String national_id;
        @SerializedName("national_id_expiry_date")
        String national_id_expiry_date;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNational_id() {
            return national_id;
        }

        public void setNational_id(String national_id) {
            this.national_id = national_id;
        }

        public String getNational_id_expiry_date() {
            return national_id_expiry_date;
        }

        public void setNational_id_expiry_date(String national_id_expiry_date) {
            this.national_id_expiry_date = national_id_expiry_date;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }



        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


    }

}
