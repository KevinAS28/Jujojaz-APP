package com.example.jujojazbase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Auth extends AppCompatActivity implements View.OnClickListener {
    private EditText email, password;
    private Button signIn;
    private Intent intent;
    private final static String LOGIN = "login";
    private boolean isLogin = false;

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
        isLogin = true;
        finish();
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

        if (savedInstanceState != null) {
            boolean isLogin = savedInstanceState.getBoolean(LOGIN);
            if (isLogin) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }

        email = findViewById(R.id.emailEText);
        password = findViewById(R.id.passEText);

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
        Log.d("Auth", authJson.toString()) ;
        net.sendUrl("http://192.168.43.129:8000/api/allvehicles/", Lib.Companion.byteToByte(("data=" + authJson.toString()).getBytes()), 0);
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(LOGIN, isLogin);
    }
}
