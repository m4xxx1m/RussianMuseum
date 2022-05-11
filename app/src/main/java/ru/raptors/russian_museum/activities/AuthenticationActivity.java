package ru.raptors.russian_museum.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.raptors.russian_museum.R;

public class AuthenticationActivity extends AppCompatActivity {

    private TextInputEditText emailInputText;
    private TextInputEditText passwordInputText;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        emailInputText = findViewById(R.id.emailInput);
        passwordInputText = findViewById(R.id.passwordInput);

        mAuth = FirebaseAuth.getInstance();
/*        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            goToMainMenu();
        }*/
    }

    public void goToMainMenu()
    {
        goToMainMenu(null);
    }


    public void goToMainMenu(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void signIn(View v)
    {
        String inputEmail = emailInputText.getText().toString().trim();
        String inputPassword = passwordInputText.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    goToMainMenu();
                }

                else {
                    Log.i("error signing in", task.getException().getMessage());
                }
            }

        });
    }

    public void signUp(View v)
    {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }
}