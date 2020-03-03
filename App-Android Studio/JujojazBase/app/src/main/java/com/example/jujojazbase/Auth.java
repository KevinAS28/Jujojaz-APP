package com.example.jujojazbase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import json.JSONArray;
import json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Auth extends AppCompatActivity implements View.OnClickListener {
    private EditText email, password;
    private Button signIn;
    private Intent intent;
    public static ModelUser user;
    public static List<ModelData> datas = new ArrayList<>();
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
        createFileForLogin(authJson.toString());
        Log.d("Auth", data.toString());
        for (Object o: (JSONArray) data.get("data")) {
            JSONObject dataObject = (JSONObject) o;
            Log.d("AuthData", dataObject.toString());
            JSONObject dataFields = (JSONObject) dataObject.get("fields");
            datas.add(new ModelData(Integer.valueOf(dataObject.get("pk").toString()),
                    dataFields.get("file_foto_b64").toString(),
                    dataFields.get("from_name").toString(),
                    dataFields.get("car_name").toString(),
                    dataFields.get("merk").toString(),
                    dataFields.get("tipe").toString(),
                    dataFields.get("servis_dimulai").toString(),
                    dataFields.get("servis_setiap_berapa_hari").toString(),
                    dataFields.get("pajak_dimulai").toString(),
                    dataFields.get("pajak_setiap_berapa_hari").toString()));
        }
        startActivity(intent);
        finish();
    }

    public void loginFail(JSONObject data){
        if (!isFinishing()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(Auth.this);
            builder.setCancelable(true);
            builder.setInverseBackgroundForced(true);
            builder.setMessage("Anda Salah Memasukkan Username atau Password");
            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
    }

    public void loginError(String msg){
        if (!isFinishing()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(Auth.this);
            builder.setCancelable(true);
            builder.setInverseBackgroundForced(true);
            builder.setMessage(msg);
            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
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

        signIn = findViewById(R.id.btnSignIn);
        signIn.setOnClickListener(this);

        email = findViewById(R.id.emailEText);
        password = findViewById(R.id.passEText);
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    System.out.println("Password : Tekan Enter");
                    signIn.performClick();
                    return true;
                }
                return false;
            }
        });
    }


    public JSONObject loginApi(String username, String password, final Boolean apiOnly){
        user = new ModelUser();
        user.setUsername(username);
        user.setPassword(password);
        final ProgressDialog loading = new ProgressDialog(Auth.this);
        loading.setMessage("Please Wait...");
        loading.show();
        loading.setCanceledOnTouchOutside(false);
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
                        loading.dismiss();
                        loginSucceed(data);
                    }
                }
                else{
                    System.out.println("Fail");
                    loginApiFail(data);
                    if (!apiOnly){
                        loading.dismiss();
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.dismiss();
                loginError("Koneksi Error");
            }
        }, 10000);
        net.sendUrl("http://192.168.225.236:8000/api/allvehicles/", Lib.Companion.byteToByte(("data=" + authJson.toString()).getBytes()), 0);
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
    }
}
