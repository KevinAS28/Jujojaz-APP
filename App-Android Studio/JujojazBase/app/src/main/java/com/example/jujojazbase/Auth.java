package com.example.jujojazbase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import json.JSONObject;


public class Auth extends AppCompatActivity implements View.OnClickListener {
    EditText email, password;
    TextView signUp;
    Button signIn;
    Intent intent;

    public void loginApiFail(){

    }

    public void loginApiSucceed(JSONObject data){

    }

    public void loginApiError(String msg){

    }

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



    public JSONObject loginApi(String username, String password, Boolean apiOnly){
        JujojazLib net = new JujojazLib(){

            @Override
            public <List> void onDone(List x)  {

                JSONObject data = new JSONObject(new String( Lib.Companion.Bytetobyte ((Byte[])((java.util.List<Byte>) x).toArray()) ));
                if (data.get("success")=="true"){
                    intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("EMAIL", email.getText().toString().trim());
                    startActivity(intent);
                }
                else{
                    intent = new Intent(getApplicationContext(), EmailConfirmation.class);
                    intent.putExtra("EMAIL", email.getText().toString().trim());
                    startActivity(intent);
                }

            }


        };
        JSONObject authJson = new JSONObject();
        authJson.put("username", username);
        authJson.put("password", password);
        return authJson;
    }
    public JSONObject loginApi(String username, String password){
        return loginApi(username, password, true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn :
                Log.d("Auth", v.toString());
                intent = new Intent(getApplicationContext(), HomeActivity.class);
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
