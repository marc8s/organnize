package com.example.organnize.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organnize.R;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class MainActivity extends IntroActivity {

    /*private Button buttonRegister;
    private TextView textViewLogin;*/

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

        /*buttonRegister = findViewById(R.id.button_cadastre_se);
        textViewLogin = findViewById(R.id.textView_register);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        getApplicationContext(),
                        "registrar",
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        getApplicationContext(),
                        "logar",
                        Toast.LENGTH_LONG
                ).show();
            }
        });*/

    }

    public void btRegister(View view){
       startActivity(new Intent(this, RegisterActivity.class));
    }

    public void btLogin(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }

}