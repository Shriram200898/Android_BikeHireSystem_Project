package com.example.bikehire.Model;

import android.graphics.Bitmap;

public class CategoryModel {
    private int id;
    private String catename, catedesc;
    private Bitmap catepicture;

    public CategoryModel(int id, String catename, String catedesc, Bitmap catepicture) {
        this.id = id;
        this.catename = catename;
        this.catedesc = catedesc;
        this.catepicture = catepicture;
    }

    @Override
    public String toString() {
        return "CategoryModel{" +
                "id=" + id +
                ", catename='" + catename + '\'' +
                ", catedesc='" + catedesc + '\'' +
                ", catepicture=" + catepicture +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatename() {
        return catename;
    }

    public void setCatename(String catename) {
        this.catename = catename;
    }

    public String getCatedesc() {
        return catedesc;
    }

    public void setCatedesc(String catedesc) {
        this.catedesc = catedesc;
    }

    public Bitmap getCatepicture() {
        return catepicture;
    }

    public void setCatepicture(Bitmap catepicture) {
        this.catepicture = catepicture;
    }
}
