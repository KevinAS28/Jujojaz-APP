package com.example.jujojazbase;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.RequiresApi;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class HomeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private FloatingActionButton fabHome;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<List<String>> data;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.homeToolBar);
        setSupportActionBar(toolbar);

        fabHome = findViewById(R.id.fabHome);
        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Home", v.toString());
                Intent intent = new Intent(getApplicationContext(), AddVehicle.class);
                startActivity(intent);
            }
        });

        String[][] dataset = new String[][] {{"java", "Mulia", "Firmansyah"}, {"java", "World", "Hello"}};
        data = new ArrayList<>();
        data.addAll(Collections.singleton(Arrays.asList(dataset[0])));
        data.addAll(Collections.singleton(Arrays.asList(dataset[1])));
        Log.d("Home", data.toString());

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
        for (List<String> dataset : data) {
            if (dataset.get(1).toLowerCase().contains(userInput)) {
                newData.addAll(dataset);
            }
        }

        AdapterHomeRecycler.data = new ArrayList<>();
        AdapterHomeRecycler.data.addAll(Collections.singleton(newData));
        Log.d("Home", AdapterHomeRecycler.data.toString());
        adapter.notifyDataSetChanged();
        return true;
    }
}
