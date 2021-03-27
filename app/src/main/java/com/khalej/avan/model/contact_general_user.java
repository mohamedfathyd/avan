package com.khalej.avan.model;
import com.google.gson.annotations.SerializedName;


public class contact_general_user {
    @SerializedName("payload")
    contact_user payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;

    public contact_user getPayload() {
        return payload;
    }

    public void setPayload(contact_user payload) {
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
    public class contact_user {
        @SerializedName("token")
        String token;
        @SerializedName("user")
        contact_user_info  user_info;

       public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public contact_user_info getUser_info() {
            return user_info;
        }

        public void setUser_info(contact_user_info user_info) {
            this.user_info = user_info;
        }
    }
    public class contact_user_info {
     @SerializedName("name")
        String full_name;
     @SerializedName("email")
        String email;
     @SerializedName("phone")
        String phone;
        @SerializedName("type")
        String type;
     @SerializedName("id")
        String id;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
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
