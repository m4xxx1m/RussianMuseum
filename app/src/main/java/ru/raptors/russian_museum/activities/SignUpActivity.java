package ru.raptors.russian_museum.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ru.raptors.russian_museum.R;
import ru.raptors.russian_museum.fragments.DialogGameFinished;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText emailInputText;
    private TextInputEditText passwordInputText;
    private TextInputEditText passwordRepeatInputText;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailInputText = findViewById(R.id.signUpEmailInput);
        passwordInputText = findViewById(R.id.signUpPasswordInput);
        passwordRepeatInputText = findViewById(R.id.signUpRepeatPasswordInput);

        mAuth = FirebaseAuth.getInstance();
    }

    public void signUp(View v)
    {
        String inputEmail = emailInputText.getText().toString().trim();
        String inputPassword = passwordInputText.getText().toString().trim();

        if(!checkPasswordCorrect())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Пароль должен быть не меньше 6 символов!");
            builder.setPositiveButton("Понятно", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
            return;
        }

        if(!checkPasswordsSame())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Пароли не совпадают!");
            builder.setPositiveButton("Понятно", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            returnToSignIn();
                        } else {

                        }
                    }
                });
    }

    private boolean checkPasswordCorrect()
    {
        String inputPassword = passwordInputText.getText().toString().trim();
        return inputPassword.length() >= 6;
    }

    private boolean checkPasswordsSame()
    {
        String inputPassword = passwordInputText.getText().toString().trim();
        String inputRepeatPassword = passwordRepeatInputText.getText().toString().trim();
        return inputPassword.equals(inputRepeatPassword);
    }

    public void returnToSignIn()
    {
        returnToSignIn(null);
    }

    public void returnToSignIn(View v)
    {
        Intent intent = new Intent(this, AuthenticationActivity.class);
        startActivity(intent);
        finish();
    }
}