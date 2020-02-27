package com.example.jujojazbase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditVehicle extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private Toolbar toolbar;
    private FloatingActionButton fabEdit;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ModelEditVehicle> data;
    private ImageButton btnEdit, btnDelete;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vehicle);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        fabEdit = findViewById(R.id.fabEdit);
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddVehicle.class);
                startActivity(intent);
            }
        });

        data = new ArrayList<>();
        data.add(new ModelEditVehicle("image", "Hello", "World"));
        data.add(new ModelEditVehicle("image", "Welcome", "Back"));

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
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput =  newText.toLowerCase();
        List<ModelEditVehicle> newData = new ArrayList<>();
        for (ModelEditVehicle data : data) {
            if (data.getTitle().toLowerCase().contains(userInput)) {
                newData.addAll(Collections.singleton(data));
            }
        }

        AdapterEditRecycler.data = new ArrayList<>();
        AdapterEditRecycler.data.addAll(newData);
        adapter.notifyDataSetChanged();
        return true;
    }
}
