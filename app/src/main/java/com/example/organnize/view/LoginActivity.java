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
import com.example.organnize.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginActivity extends AppCompatActivity {

    private EditText mCampEmail, mCampPassword;
    private Button mButtonLogin;
    private FirebaseAuth mAuthentication;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mCampEmail = findViewById(R.id.editTextTextEmailAddressLogin);
        mCampPassword = findViewById(R.id.editTextTextPasswordLogin);
        mButtonLogin = findViewById(R.id.button_login);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = mCampEmail.getText().toString();
                String textPassword = mCampPassword.getText().toString();
                if(!textEmail.isEmpty()){
                    if (!textPassword.isEmpty()){
                        mUser = new User();
                        mUser.setEmail(textEmail);
                        mUser.setPassword(textPassword);
                        validateLogin();
                    }else{
                        Toast.makeText(LoginActivity.this,
                                "Prencha o campo senha!",
                                Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(LoginActivity.this,
                            "Prencha o campo email!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void validateLogin(){
        mAuthentication = ConfigFirebase.getFirebaseAuthentication();
        mAuthentication.signInWithEmailAndPassword(
                mUser.getEmail(),
                mUser.getPassword()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,
                            "Sucesso ao fazer login",
                            Toast.LENGTH_SHORT).show();

                }else{
                    String exception = "";
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        exception = "O usuário não esta cadastrado!";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        exception = "Email e senha não correspodem ao usuário cadastrado!";
                    }catch (Exception e){
                        exception = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this,
                            exception,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}