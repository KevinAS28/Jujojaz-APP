package com.example.jujojazbase;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditInformation extends AppCompatActivity {
    EditText textFrom, textCarName, textMerk, textKuitansi, textPajak, textServis;
    ImageButton addPicture, addPhoto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        textFrom = findViewById(R.id.textFrom);
        textCarName = findViewById(R.id.textCarName);
        textMerk = findViewById(R.id.textMerk);
        textKuitansi = findViewById(R.id.textKuitansi);
        textPajak = findViewById(R.id.textPajak);
        textServis = findViewById(R.id.textPajak);

        addPicture = findViewById(R.id.addPicture);
        addPhoto = findViewById(R.id.addPhoto);
    }
}
