package com.example.contactapp;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Contact")
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="avatar")
    private Bitmap avatar;
    @ColumnInfo(name = "first_name")
    private String firstname;
    @ColumnInfo(name = "last_name")
    private String lastname;
    @ColumnInfo(name = "phone")
    private String phone;
    @ColumnInfo(name = "email")
    private String email;

    public Contact(Bitmap avatar, String firstname, String lastname, String phone, String email) {
        this.avatar = avatar;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
    }
    public Contact(){

    }
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + firstname + lastname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
