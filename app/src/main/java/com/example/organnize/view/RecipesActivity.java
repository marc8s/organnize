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

public class RecipesActivity extends AppCompatActivity {

    private EditText mCampValue, mCampDate, mCampCategory, mCampDescription;
    private Transaction mTransaction;
    private DatabaseReference mReferenceFirebase = ConfigFirebase.getFirebaseDatabase();
    private FirebaseAuth mAuthentication = ConfigFirebase.getFirebaseAuthentication();
    private Double mRecipeTotal;
    private Double mRecipeAdded;
    private Double mRecipeUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        mCampValue = findViewById(R.id.editTextNumberDecimalRecipes);
        mCampDate = findViewById(R.id.editTextDateRecipes);
        mCampCategory = findViewById(R.id.editTextCategoryRecipes);
        mCampDescription = findViewById(R.id.editTextDescriptionRecipes);

        mCampDate.setText(DateCustom.dateToday());
        recoverTotalRecipe();
    }
    public void saveRecipe(View view){
        if(validateCamps()){
            mTransaction = new Transaction();

            Double valueRecipe = Double.parseDouble(mCampValue.getText().toString());
            mTransaction.setmValue(valueRecipe);
            mTransaction.setmCategory(mCampCategory.getText().toString());
            mTransaction.setmDescription(mCampDescription.getText().toString());
            mTransaction.setmDate(mCampDate.getText().toString());
            //r de recipe
            mTransaction.setmType("r");
            //atualiza despesa total
            mRecipeAdded = valueRecipe;
            updateTotalRecipe();

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
                        Toast.makeText(RecipesActivity.this,
                                "O campo descrição não foi preenchido!",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }

                }else{
                    Toast.makeText(RecipesActivity.this,
                            "O campo categoria não foi preenchido!",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }

            }else{
                Toast.makeText(RecipesActivity.this,
                        "O campo data não foi preenchido!",
                        Toast.LENGTH_SHORT).show();
                return false;
            }

        }else{
            Toast.makeText(RecipesActivity.this,
                    "O campo valor não foi preenchido!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public void recoverTotalRecipe(){
        String emailUser = mAuthentication.getCurrentUser().getEmail();
        String idUser = Base64Custom.encodeBase64(emailUser);
        DatabaseReference userRef = mReferenceFirebase.child("user").child(idUser);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //converte o retorno do firebase em um objeto do tipo user
                User user = snapshot.getValue(User.class);
                mRecipeTotal = user.getRecipeTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateTotalRecipe(){
        String emailUser = mAuthentication.getCurrentUser().getEmail();
        String idUser = Base64Custom.encodeBase64(emailUser);
        DatabaseReference userRef = mReferenceFirebase.child("user").child(idUser);

        mRecipeUpdated = mRecipeTotal + mRecipeAdded;
        userRef.child("recipeTotal").setValue(mRecipeUpdated);

    }
}