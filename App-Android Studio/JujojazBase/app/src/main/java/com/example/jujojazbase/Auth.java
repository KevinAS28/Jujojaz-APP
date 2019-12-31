package com.example.jujojazbase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Auth extends AppCompatActivity {
    EditText email, password;
    Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        email = findViewById(R.id.emailEText);
        password = findViewById(R.id.passEText);
        done = findViewById(R.id.btnDoneAuth);
    }

    @Override
    protected void onStart() {
        super.onStart();
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Auth", v.toString());
                Intent intent = new Intent(Auth.this, EmailConfirmation.class);
                startActivity(intent);
            }
        });
    }
}
