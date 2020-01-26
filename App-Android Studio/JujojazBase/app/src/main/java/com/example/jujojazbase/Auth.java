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
//        intent = new Intent(getApplicationContext(), EmailConfirmation.class);
//        intent.putExtra("EMAIL", email.getText().toString().trim());
//        startActivity(intent);
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


    public JSONObject loginApi(String username, String password, final Boolean apiOnly){
        JujojazLib net = new JujojazLib(){

            @Override
            public  void onDone(List<Byte> x)  {
                Byte[] byteArray = new Byte[x.size()];
                x.toArray(byteArray);
                JSONObject data = new JSONObject(new String( Lib.Companion.Bytetobyte (byteArray) ));
                System.out.println(data.toString());
                if (data.get("success").equals("true")){
                    loginApiSucceed(data);
                    if (!apiOnly){
                        loginSucceed(data);
                    }
                }
                else{
                    System.out.println("Fail");
                    loginApiFail(data);
                    if (!apiOnly){
                        loginFail(data);
                    }
                }

            }



            @Override
            public void onError(String msg){
                System.out.println("ERROR: " + msg);
            }
        };
        JSONObject authJson = new JSONObject();
        authJson.put("username", username);
        authJson.put("password", password);
        net.sendUrl("http://10.0.2.2:8080/api/allvehicles/", Lib.Companion.byteToByte(("data="+authJson.toString()).getBytes()) , 0);

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
