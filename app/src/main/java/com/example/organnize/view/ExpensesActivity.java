package com.example.organnize.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organnize.R;
import com.example.organnize.config.ConfigFirebase;
import com.example.organnize.helper.Base64Custom;
import com.example.organnize.helper.DateCustom;
import com.example.organnize.model.Transaction;
import com.example.organnize.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ExpensesActivity extends AppCompatActivity {

    private EditText mCampValue, mCampDate, mCampCategory, mCampDescription;
    private Transaction mTransaction;
    private DatabaseReference mReferenceFirebase = ConfigFirebase.getFirebaseDatabase();
    private FirebaseAuth mAuthentication = ConfigFirebase.getFirebaseAuthentication();
    private Double mExpenseTotal;
    private Double mExpenseAdded;
    private Double mExpenseUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        mCampValue = findViewById(R.id.editTextNumberDecimalExpences);
        mCampDate = findViewById(R.id.editTextDateExpenses);
        mCampCategory = findViewById(R.id.editTextCategoryExpenses);
        mCampDescription = findViewById(R.id.editTextDescriptionExpenses);

        mCampDate.setText(DateCustom.dateToday());
        recoverTotalExpense();
    }

    public void saveExpense(View view){
        if(validateCamps()){
            mTransaction = new Transaction();

            Double valueExpense = Double.parseDouble(mCampValue.getText().toString());
            mTransaction.setmValue(valueExpense);
            mTransaction.setmCategory(mCampCategory.getText().toString());
            mTransaction.setmDescription(mCampDescription.getText().toString());
            mTransaction.setmDate(mCampDate.getText().toString());
            //e de expense
            mTransaction.setmType("e");
            //atualiza despesa total
            mExpenseAdded = valueExpense;
            updateTotalExpense();

            mTransaction.save();
            finish();
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

    public void recoverTotalExpense(){
        String emailUser = mAuthentication.getCurrentUser().getEmail();
        String idUser = Base64Custom.encodeBase64(emailUser);
        DatabaseReference userRef = mReferenceFirebase.child("user").child(idUser);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //converte o retorno do firebase em um objeto do tipo user
                User user = snapshot.getValue(User.class);
                mExpenseTotal = user.getExpenseTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateTotalExpense(){
        String emailUser = mAuthentication.getCurrentUser().getEmail();
        String idUser = Base64Custom.encodeBase64(emailUser);
        DatabaseReference userRef = mReferenceFirebase.child("user").child(idUser);

        mExpenseUpdated = mExpenseTotal + mExpenseAdded;
        userRef.child("expenseTotal").setValue(mExpenseUpdated);

    }
}