package com.example.sporttogether.ui.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.sporttogether.R;
import com.example.sporttogether.ui.adapters.MatchesMainPagerAdapter;
import com.example.sporttogether.ui.adapters.MatchesMainRecyclerAdapter;
import com.example.sporttogether.ui.dialogs.CreateMatchDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nshmura.recyclertablayout.RecyclerTabLayout;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class MatchesMainActivity extends AppCompatActivity {

    LocalDate startDate;
    LocalDate endDate;
    LocalDate initialDate;
    List<String> dates = new ArrayList<>();
    int sorting = 2;

    MatchesMainPagerAdapter adapter;
    ViewPager viewPager;
    RecyclerTabLayout recyclerTabLayout;
    FloatingActionButton sortMatchesButton;
    FloatingActionButton createMatchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches_main);

        sortMatchesButton = findViewById(R.id.sortMatchesButton);
        sortMatchesButton.setOnClickListener(v -> {
            AlertDialog dialog1;
            dialog1 = new AlertDialog.Builder(this)
                    .setTitle("Ordenar por:")
                    .setItems(R.array.sorting_array, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sorting=which;
                            Toast.makeText(getApplicationContext(), Integer.toString(sorting), Toast.LENGTH_SHORT).show();
                            restartAdapter();
                            }
                    }) .show();
        });

        createMatchButton = findViewById(R.id.createMatchButton);
        createMatchButton.setOnClickListener(v -> CreateMatchDialog.display(getSupportFragmentManager()));

        startDate = Calendar.getInstance().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().minusYears(1); //Obtains actual date and minus it one year
        endDate = Calendar.getInstance().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusYears(1); //Obtains actual date and pluses it one year
        initialDate = Calendar.getInstance().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        for (LocalDate date = startDate; date.isBefore(endDate); date=date.plusDays(1)) {
            if (date.equals(initialDate)){
                dates.add(getString(R.string.today));
                continue;
            }else if (date.equals(initialDate.plusDays(1))){
                dates.add(getString(R.string.tomorrow));
                continue;
            }else if (date.equals(initialDate.minusDays(1))){
                dates.add(getString(R.string.yesterday));
                continue;
            }else{
                dates.add(String.valueOf(date));
            }
        }

        adapter = new MatchesMainPagerAdapter(sorting);
        adapter.addAll(dates);

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem((int) ChronoUnit.DAYS.between(startDate, initialDate));

        recyclerTabLayout = findViewById(R.id.recycler_tab_layout);
        recyclerTabLayout.setUpWithViewPager(viewPager);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus==true){
            restartAdapter();
        }
    }

    public void restartAdapter(){
        int position = viewPager.getCurrentItem();

        adapter = new MatchesMainPagerAdapter(sorting);
        adapter.addAll(dates);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);

        recyclerTabLayout.setUpWithViewPager(viewPager);
    }
}