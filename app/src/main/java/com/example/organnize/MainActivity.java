package com.example.organnize;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class MainActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        /*Standart slide (SimpleSlide)
        //remove butões back e next dos slides
        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new SimpleSlide.Builder()
                .title("Titulo")
                .description("Descrição")
                .image(R.drawable.um)
                .background(R.color.purple_200)
                .build()
        );
        addSlide(new SimpleSlide.Builder()
                .title("Titulo2")
                .description("Descrição2")
                .image(R.drawable.dois)
                .background(R.color.purple_200)
                .build()
        );
        addSlide(new SimpleSlide.Builder()
                .title("Titulo3")
                .description("Descrição3")
                .image(R.drawable.tres)
                .background(R.color.purple_200)
                .build()
        );*/
    }
}