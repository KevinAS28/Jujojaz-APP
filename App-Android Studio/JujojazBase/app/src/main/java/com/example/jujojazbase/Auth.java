package com.example.jujojazbase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Auth extends AppCompatActivity implements View.OnClickListener {
    private EditText email, password;
    private Button signIn;
    private Intent intent;
    public static ModelUser user;
    private JSONObject authJson;


    public void createFileForLogin(String userJson){
        File file = new File(getApplicationContext().getFilesDir(), "Auth");
        if (!file.exists()) {
            file.mkdir();
        }

        try {
            File gpxfile = new File(file, "Login");
            FileWriter writer = new FileWriter(gpxfile);
            System.out.println("UserJson : " + userJson);
            writer.append(userJson);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loginApiFail(JSONObject data){

    }

    public void loginApiSucceed(JSONObject data){

    }

    public void loginApiError(String msg){

    }

    public void loginSucceed(JSONObject data){

        intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        createFileForLogin(authJson.toString());
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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        2);
            }
        }

        try {
            File file = new File(getApplicationContext().getFilesDir(), "Auth");
            File gpxfile = new File(file, "Login");
            FileReader reader = new FileReader(gpxfile);
            StringBuilder stringBuilder = new StringBuilder();
            int i;
            while ((i=reader.read()) != -1) {
                stringBuilder.append((char)i);
            }
            reader.close();
            System.out.println("LoginJson : " + stringBuilder.toString());
            JSONObject loginJson = new JSONObject(stringBuilder.toString());
            loginApi(loginJson.get("username").toString(), loginJson.get("password").toString(), false);
        } catch (Exception e) {
            System.out.println("Belum Login");
        }

        setContentView(R.layout.activity_auth);

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
                if (data.get("success").equals("1")){
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
        authJson = new JSONObject();
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
        user = new ModelUser();
        user.setUsername(username);
        user.setPassword(password);
        loginApi(username, password, false);
    }
}
