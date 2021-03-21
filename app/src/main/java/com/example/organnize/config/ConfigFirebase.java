package com.example.organnize.config;

import com.google.firebase.auth.FirebaseAuth;

public class ConfigFirebase {

    private static FirebaseAuth mAuthentication;

    public static FirebaseAuth getFirebaseAuthentication() {
        if(mAuthentication == null){
            mAuthentication = FirebaseAuth.getInstance();
        }
        return mAuthentication;
    }
}
