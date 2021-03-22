package com.example.organnize.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organnize.R;
import com.example.organnize.helper.DateCustom;
import com.example.organnize.model.Transaction;

public class ExpensesActivity extends AppCompatActivity {

    private EditText mCampValue, mCampDate, mCampCategory, mCampDescription;
    private Transaction mTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        mCampValue = findViewById(R.id.editTextNumberDecimalExpences);
        mCampDate = findViewById(R.id.editTextDateExpenses);
        mCampCategory = findViewById(R.id.editTextCategoryExpenses);
        mCampDescription = findViewById(R.id.editTextDescriptionExpenses);

        mCampDate.setText(DateCustom.dateToday());
    }

    public void saveExpense(View view){
        if(validateCamps()){
            mTransaction = new Transaction();
            mTransaction.setmValue(Double.parseDouble(mCampValue.getText().toString()));
            mTransaction.setmCategory(mCampCategory.getText().toString());
            mTransaction.setmDescription(mCampDescription.getText().toString());
            mTransaction.setmDate(mCampDate.getText().toString());
            //e de expense
            mTransaction.setmType("e");
            mTransaction.save();
        }

    }

    public Boolean validateCamps(){
        String textValue = mCampValue.getText().toString();
        String textDate = mCampDate.getText().toString();
        String textCategory = mCampCategory.getText().toString();
        String textDescription = mCampDescription.getText().toString();
        if(!textValue.isEmpty()){
            if(!textDate.isEmpty()){
                if(!textCategory.isEmpty()){
                    if(!textDescription.isEmpty()){
                        return true;
                    }else{
                        Toast.makeText(ExpensesActivity.this,
                                "O campo descrição não foi preenchido!",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }

                }else{
                    Toast.makeText(ExpensesActivity.this,
                            "O campo categoria não foi preenchido!",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }

            }else{
                Toast.makeText(ExpensesActivity.this,
                        "O campo data não foi preenchido!",
                        Toast.LENGTH_SHORT).show();
                return false;
            }

        }else{
            Toast.makeText(ExpensesActivity.this,
                    "O campo valor não foi preenchido!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}