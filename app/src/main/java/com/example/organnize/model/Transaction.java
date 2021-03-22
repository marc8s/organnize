package com.example.organnize.model;

import com.example.organnize.config.ConfigFirebase;
import com.example.organnize.helper.Base64Custom;
import com.example.organnize.helper.DateCustom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Transaction {
    private String mDate;
    private String mCategory;
    private String mDescription;
    private String mType;
    private double mValue;

    public Transaction() {
    }

    public void save(){
        //é preciso recuperar o email do usuario - unico dado faltando pra ser salvo
        FirebaseAuth mAuthentication = ConfigFirebase.getFirebaseAuthentication();
        String idUser = Base64Custom.encodeBase64(mAuthentication.getCurrentUser().getEmail());
        //é preciso passar a data do formato dd/MM/yyyy para MMyyyy
        String monthYear = DateCustom.onlyMonthYear(mDate);

        DatabaseReference mReferenceFirebase = ConfigFirebase.getFirebaseDatabase();
        mReferenceFirebase.child("transaction")
                .child(idUser)
                .child(monthYear)
                .push()
                .setValue(this);
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public double getmValue() {
        return mValue;
    }

    public void setmValue(double mValue) {
        this.mValue = mValue;
    }
}
