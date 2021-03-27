package com.khalej.avan.model;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class contact_general {
    @SerializedName("payload")
    List<media> payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;

    public List<media> getPayload() {
        return payload;
    }

    public void setPayload(List<media> payload) {
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
    public class media{
        @SerializedName("media")
        contact_slider media;
        @SerializedName("media_links")
        List<String> slider_images;

        public List<String> getSlider_images() {
            return slider_images;
        }

        public void setSlider_images(List<String> slider_images) {
            this.slider_images = slider_images;
        }

        public contact_slider getMedia() {
            return media;
        }

        public void setMedia(contact_slider media) {
            this.media = media;
        }
    }
}
