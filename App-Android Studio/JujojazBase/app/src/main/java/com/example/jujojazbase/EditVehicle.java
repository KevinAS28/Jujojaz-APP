package com.example.jujojazbase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import json.JSONObject;

public class EditVehicle extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private Toolbar toolbar;
    private FloatingActionButton fabEdit;
    private RecyclerView recyclerView;
    public static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public List<ModelData> data = new ArrayList<>();

    @SuppressLint("Assert")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vehicle);

        Bundle bundle = getIntent().getExtras();
        int positionAdapter = bundle.getInt("POSITION");
        List<Integer> dataID = HomeActivity.id.get(positionAdapter);

        for (ModelData o : Auth.datas) {
            if (dataID.contains(o.getId())) {
                data.add(o);
            }
        }
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
/*
        fabEdit = findViewById(R.id.fabEdit);
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddVehicle.class);
                startActivity(intent);
            }
        });
*/

        recyclerView = findViewById(R.id.recyclerViewEdit);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new AdapterEditRecycler(this, data);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        MenuItem logOut = menu.findItem(R.id.menuLogOut);
        logOut.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                File file = new File(getApplicationContext().getFilesDir(), "Auth");
                new File(file, "Login").delete();
                Intent intent = new Intent(getApplicationContext(), Auth.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            }
        });
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput =  newText.toLowerCase();
        List<ModelData> newData = new ArrayList<>();
        for (ModelData data : data) {
            if (data.getMerk().toLowerCase().contains(userInput)) {
                newData.addAll(Collections.singleton(data));
            }
        }

        AdapterEditRecycler.datas = new ArrayList<>();
        AdapterEditRecycler.datas.addAll(newData);
        adapter.notifyDataSetChanged();
        return true;
    }
}
