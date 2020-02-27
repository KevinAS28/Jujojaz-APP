package com.example.jujojazbase;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    //private FloatingActionButton fabHome;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ModelHomeActivity> data;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.homeToolBar);
        setSupportActionBar(toolbar);

        //fabHome = findViewById(R.id.fabHome);
        //fabHome.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //   public void onClick(View v) {
        //        Log.d("Home", v.toString());
        //        Intent intent = new Intent(getApplicationContext(), AddVehicle.class);
        //        startActivity(intent);
        //    }
        //});

        String[][] dataset = new String[][] {{"image", "Motor", "Kendaraan Roda 2"}, {"image", "Mobil", "Kendaraan Roda 4"}};
        data = new ArrayList<>();
        data.add(new ModelHomeActivity("image", "Motor", "Kendaraan Roda 2"));
        data.add(new ModelHomeActivity("image", "Mobil", "Kendaraan Roda 4"));
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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput =  newText.toLowerCase();
        List<ModelHomeActivity> newData = new ArrayList<>();
        for (ModelHomeActivity dataset : data) {
            if (dataset.getTitle().toLowerCase().contains(userInput)) {
                newData.addAll(Collections.singleton(dataset));
            }
        }

        AdapterHomeRecycler.data = new ArrayList<>();
        AdapterHomeRecycler.data.addAll(newData);
        Log.d("Home", AdapterHomeRecycler.data.toString());
        Log.d("Home", data.toString());
        adapter.notifyDataSetChanged();
        return true;
    }
}
