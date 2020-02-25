package com.example.jujojazbase;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditInformation extends AppCompatActivity {
    ImageButton addPicture, addPhoto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);


        addPicture = findViewById(R.id.addPicture);
        addPhoto = findViewById(R.id.addPhoto);
    }
}
