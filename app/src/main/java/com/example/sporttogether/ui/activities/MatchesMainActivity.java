package com.example.sporttogether.ui.activities;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.sporttogether.R;
import com.example.sporttogether.ui.adapters.MatchesMainPagerAdapter;
import com.example.sporttogether.ui.dialogs.CreateMatchDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nshmura.recyclertablayout.RecyclerTabLayout;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MatchesMainActivity extends AppCompatActivity {

    LocalDate startDate;
    LocalDate endDate;
    LocalDate initialDate;
    List<String> dates = new ArrayList<>();
    int sorting = 0;

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
        sortMatchesButton.setOnClickListener(v -> new AlertDialog.Builder(this)
                .setTitle("Ordenar por:")
                .setItems(R.array.sorting_array, (dialog, which) -> {
                    sorting = which;
                    restartAdapter();
                }).show());

        createMatchButton = findViewById(R.id.createMatchButton);
        createMatchButton.setOnClickListener(v -> CreateMatchDialog.display(getSupportFragmentManager()));

        startDate = Calendar.getInstance().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().minusYears(1); //Obtains actual date and minus it one year
        endDate = Calendar.getInstance().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusYears(1); //Obtains actual date and pluses it one year
        initialDate = Calendar.getInstance().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        for (LocalDate date = startDate; date.isBefore(endDate); date=date.plusDays(1)) {
            if (date.equals(initialDate)){
                dates.add(getString(R.string.today));
            }else if (date.equals(initialDate.plusDays(1))){
                dates.add(getString(R.string.tomorrow));
            }else if (date.equals(initialDate.minusDays(1))){
                dates.add(getString(R.string.yesterday));
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
        if (hasFocus){
            restartAdapter();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
            .setMessage(R.string.sign_off_question)
            .setPositiveButton(R.string.yes, (dialog, id) -> finish())
            .setNegativeButton(R.string.no, (dialog, id) -> {
            })
            .show();
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