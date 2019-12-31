package com.example.jujojazbase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EmailConfirmation extends AppCompatActivity {
    EditText emailConfirm_1, emailConfirm_2, emailConfirm_3, emailConfirm_4;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_confirmation);

        emailConfirm_1 = findViewById(R.id.emailConfirm_1);
        emailConfirm_2 = findViewById(R.id.emailConfirm_2);
        emailConfirm_3 = findViewById(R.id.emailConfirm_3);
        emailConfirm_4 = findViewById(R.id.emailConfirm_4);
        done = findViewById(R.id.btnDoneEmailConfirm);
    }

    @Override
    protected void onStart() {
        super.onStart();
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("EmailConfirmation", v.toString());
                Intent intent = new Intent(EmailConfirmation.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
