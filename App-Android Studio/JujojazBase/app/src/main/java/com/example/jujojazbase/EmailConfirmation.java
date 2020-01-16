package com.example.jujojazbase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EmailConfirmation extends AppCompatActivity {
    EditText emailConfirm_1, emailConfirm_2, emailConfirm_3, emailConfirm_4;
    TextView textEmail;
    Button verification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_confirmation);

        getActionBar().setTitle("Verifikasi");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        emailConfirm_1 = findViewById(R.id.emailConfirm_1);
        emailConfirm_2 = findViewById(R.id.emailConfirm_2);
        emailConfirm_3 = findViewById(R.id.emailConfirm_3);
        emailConfirm_4 = findViewById(R.id.emailConfirm_4);
        textEmail = findViewById(R.id.textEmailVerification);
        verification = findViewById(R.id.btnVerification);
    }

    @Override
    protected void onStart() {
        super.onStart();

        textEmail.setText(R.string.textEmailVerification);

        verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("EmailConfirmation", v.toString());
                Intent intent = new Intent(EmailConfirmation.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("Menu", item.toString());
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivityForResult(intent, 0);
        return true;
    }
}
