package com.example.organnize.model;

import com.example.organnize.config.ConfigFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class User {
    private String name, email, password, id;

    public User() {
    }

    public void save(){
        DatabaseReference mReferenceFirebase = ConfigFirebase.getFirebaseDatabase();
        mReferenceFirebase.child("user")
                .child(this.id)
                .setValue(this);
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

    //usar exclude para não salvar no firebasedatabase
    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //usar exclude para não salvar no firebasedatabase
    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
