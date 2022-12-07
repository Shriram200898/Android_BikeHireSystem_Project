package com.example.bikehire.Model;

import android.graphics.Bitmap;

public class UserModel {
    private int id;
    private String name,email,pwd,phoneno,add;
    private Bitmap photo;

    public UserModel(int id, String name, String email, String pwd, String phoneno, String add, Bitmap photo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pwd = pwd;
        this.phoneno = phoneno;
        this.add = add;
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", phoneno='" + phoneno + '\'' +
                ", add='" + add + '\'' +
                ", photo=" + photo +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}
