package com.example.organnize.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organnize.R;
import com.example.organnize.config.ConfigFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class MainActivity extends IntroActivity {

    private FirebaseAuth mAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //remove butões back e next dos slides
        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder()
                .background(R.color.purple_200)
                .fragment(R.layout.intro_1)
                .build()
        );
        addSlide(new FragmentSlide.Builder()
                .background(R.color.purple_200)
                .fragment(R.layout.intro_2)
                .build()
        );
        addSlide(new FragmentSlide.Builder()
                .background(R.color.purple_200)
                .fragment(R.layout.intro_3)
                .build()
        );
        addSlide(new FragmentSlide.Builder()
                .background(R.color.purple_200)
                .fragment(R.layout.intro_4)
                .build()
        );
        addSlide(new FragmentSlide.Builder()
                .background(R.color.purple_200)
                .fragment(R.layout.intro_register)
                .canGoForward(false)
                .build()
        );

    }

    @Override
    protected void onStart() {
        super.onStart();
        verifyUserLogged();
    }

    public void btRegister(View view){
       startActivity(new Intent(this, RegisterActivity.class));
    }

    public void btLogin(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void verifyUserLogged(){
        mAuthentication = ConfigFirebase.getFirebaseAuthentication();
        if(mAuthentication.getCurrentUser() != null){
            openHome();
        }
    }

    public void openHome(){
        startActivity(new Intent(this, HomeActivity.class));
    }

}