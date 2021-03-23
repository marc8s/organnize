package com.example.organnize.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organnize.R;
import com.example.organnize.config.ConfigFirebase;
import com.example.organnize.helper.Base64Custom;
import com.example.organnize.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class RegisterActivity extends AppCompatActivity {

    private EditText mCampName, mCampEmail, mCampPassword;
    private Button mButtonRegister;
    private FirebaseAuth mAuthentication;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //getSupportActionBar().setTitle("Cadastro");

        mCampName = findViewById(R.id.editTextTextPersonNameRegister);
        mCampEmail = findViewById(R.id.editTextTextEmailAddressRegister);
        mCampPassword = findViewById(R.id.editTextTextPasswordRegister);
        mButtonRegister = findViewById(R.id.button_register);

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textName = mCampName.getText().toString();
                String textEmail = mCampEmail.getText().toString();
                String textPassword = mCampPassword.getText().toString();
                if(!textName.isEmpty()){
                    if(!textEmail.isEmpty()){
                        if (!textPassword.isEmpty()){
                            mUser = new User();
                            mUser.setName(textName);
                            mUser.setEmail(textEmail);
                            mUser.setPassword(textPassword);
                            registerNewUser();
                        }else{
                            Toast.makeText(RegisterActivity.this,
                                    "Prencha o campo senha!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(RegisterActivity.this,
                                "Prencha o campo email!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this,
                            "Prencha o campo nome!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void registerNewUser(){
        mAuthentication = ConfigFirebase.getFirebaseAuthentication();
        mAuthentication.createUserWithEmailAndPassword(
                mUser.getEmail(), mUser.getPassword()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String idUser = Base64Custom.encodeBase64(mUser.getEmail());
                    mUser.setId(idUser);
                    mUser.save();
                    /*fecha tela de login e volta pra intro_register
                    onde havera uma validação na Main.Activity
                    */
                    finish();
                }else{
                    String exception = "";
                    try{
                        throw task.getException();
                    }catch(FirebaseAuthWeakPasswordException e){
                        exception = "Digite uma senha mais forte!";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        exception = "Digite um email válido!";
                    }catch (FirebaseAuthUserCollisionException e){
                        exception = "Esta conta já foi cadastrada!";
                    }catch (Exception e){
                        exception = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(RegisterActivity.this,
                            exception,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}