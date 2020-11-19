package com.example.sporttogether.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sporttogether.R;
import com.example.sporttogether.database.Database;
import com.example.sporttogether.ui.adapters.MatchesMainRecyclerAdapter;
import com.example.sporttogether.ui.dialogs.CreateMatchDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MatchesMainActivity extends AppCompatActivity {

    MatchesMainRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches_main);

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.createMatchButton);
        button.setOnClickListener(v -> CreateMatchDialog.display(getSupportFragmentManager()));

    }



    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvAnimals);
        adapter = new MatchesMainRecyclerAdapter(Database.getMatches());
        rvContacts.setAdapter(adapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
    }
}