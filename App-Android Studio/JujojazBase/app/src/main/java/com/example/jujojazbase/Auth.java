package com.example.jujojazbase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Auth extends AppCompatActivity implements View.OnClickListener {
    EditText email, password;
    TextView signUp;
    Button signIn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        email = findViewById(R.id.emailEText);
        password = findViewById(R.id.passEText);

        signUp = findViewById(R.id.textSignUp);
        signUp.setOnClickListener(this);

        signIn = findViewById(R.id.btnSignIn);
        signIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn :
                Log.d("Auth", v.toString());
                intent = new Intent(getApplicationContext(), Home.class);
                intent.putExtra("EMAIL", email.getText().toString().trim());
                startActivity(intent);
                break;
            case  R.id.textSignUp :
                Log.d("Auth", v.toString());
                intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                break;
        }
    }
}
