package com.example.jujojazbase;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.homeToolBar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Home", v.toString());
                Intent intent = new Intent(getApplicationContext(), AddVehicle.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerViewHome);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new AdapterHomeRecycler(this, data);
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
        List<String> newData = new ArrayList<>();
        for (String data : data) {
            if (data.toLowerCase().contains(userInput)) {
                newData.add(data);
            }
        }

        ArrayList<String> adapterData = (ArrayList<String>) AdapterHomeRecycler.data;
        adapterData.clear();
        adapterData.addAll(newData);
        adapter.notifyDataSetChanged();
        return true;
    }
}
