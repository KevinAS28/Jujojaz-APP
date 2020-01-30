package com.example.jujojazbase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditVehicle extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<List<String>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vehicle);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        String[][] dataset = new String[][] {{"java", "Mulia", "Firmansyah", String.valueOf(false)}, {"java", "World", "Hello", String.valueOf(false)}};
        data = new ArrayList<>();
        data.addAll(Collections.singleton(Arrays.asList(dataset[0])));
        data.addAll(Collections.singleton(Arrays.asList(dataset[1])));

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
        List<List<String>> newData = new ArrayList<>();
        for (List<String> data : data) {
            if (data.get(1).toLowerCase().contains(userInput)) {
                newData.addAll(Collections.singleton(data));
            }
        }

        AdapterEditRecycler.data = new ArrayList<>();
        AdapterEditRecycler.data.addAll(newData);
        adapter.notifyDataSetChanged();
        return true;
    }

}
