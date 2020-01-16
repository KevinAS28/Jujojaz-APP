package com.example.jujojazbase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {
    EditText email, password;
    Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.signUpEmailEText);
        password = findViewById(R.id.signUpPassEText);
        signIn = findViewById(R.id.signUpBtn);
    }

    @Override
    protected void onStart() {
        super.onStart();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SignUp", v.toString());
                Intent intent = new Intent(getApplicationContext(), EmailConfirmation.class);
                startActivity(intent);
            }
        });
    }
}
