package com.example.organnize.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.organnize.R;
import com.example.organnize.helper.DateCustom;
import com.google.android.material.textfield.TextInputEditText;

public class ExpensesActivity extends AppCompatActivity {

    private EditText campValue, campDate, campCategory, campDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        campValue = findViewById(R.id.editTextNumberDecimalExpences);
        campDate = findViewById(R.id.editTextDateExpenses);
        campCategory = findViewById(R.id.editTextCategoryExpenses);
        campDescription = findViewById(R.id.editTextDescriptionExpenses);

        campDate.setText(DateCustom.dateToday());
    }
}