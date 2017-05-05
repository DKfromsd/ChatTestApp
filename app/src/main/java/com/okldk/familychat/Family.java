package com.okldk.familychat;

/**
 * Created by dk1.lee on 5/3/2017.
 */

public class Family {

    public String email;
    public String photo;
    public String key;

    public Family() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
