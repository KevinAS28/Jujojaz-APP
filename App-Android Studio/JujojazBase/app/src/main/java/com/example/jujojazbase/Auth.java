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

import java.util.Arrays;
import java.util.List;


public class Auth extends AppCompatActivity implements View.OnClickListener {
    EditText email, password;
    TextView signUp;
    Button signIn;
    Intent intent;

    public void loginApiFail(JSONObject data){

    }

    public void loginApiSucceed(JSONObject data){

    }

    public void loginApiError(String msg){

    }

    public void loginSucceed(JSONObject data){
        intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.putExtra("EMAIL", email.getText().toString().trim());
        startActivity(intent);
    }

    public void loginFail(JSONObject data){
        intent = new Intent(getApplicationContext(), EmailConfirmation.class);
        intent.putExtra("EMAIL", email.getText().toString().trim());
        startActivity(intent);
    }

    public void loginError(String msg){

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
    public Byte[] byteToByte(byte[] bytes){
        Byte[] toreturn = new Byte[bytes.length];
        for (byte i : bytes){
            toreturn[i] = bytes[i];
        }
        return toreturn;
    }


    public JSONObject loginApi(String username, String password, final Boolean apiOnly){
        JujojazLib net = new JujojazLib(){

            @Override
            public <List> void onDone(List x)  {

                JSONObject data = new JSONObject(new String( Lib.Companion.Bytetobyte ((Byte[])((java.util.List<Byte>) x).toArray()) ));
                if (data.get("success")=="true"){
                    loginApiSucceed(data);
                    if (!apiOnly){
                        loginSucceed(data);
                    }
                }
                else{
                    loginApiFail(data);
                    if (!apiOnly){
                        loginFail(data);
                    }
                }
            }

            @Override
            public void onError(String msg){

            }
        };
        JSONObject authJson = new JSONObject();
        authJson.put("username", username);
        authJson.put("password", password);
        net.sendUrl("http://127.0.0.1:8080/api/allvehicles/", byteToByte(authJson.toString().getBytes()) , 0);
        return authJson;
    }


    public JSONObject loginApi(String username, String password){
        return loginApi(username, password, true);
    }

    @Override
    public void onClick(View v) {
        String username = ((EditText)findViewById(R.id.emailEText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passEText)).getText().toString();
        loginApi(username, password, false);
    }
}
