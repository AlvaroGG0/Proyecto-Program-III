package com.example.sporttogether.ui.activities;

import android.os.Bundle;

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
    List<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches_main);

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.createMatchButton);
        button.setOnClickListener(v -> CreateMatchDialog.display(getSupportFragmentManager()));

        startDate = Calendar.getInstance().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().minusYears(1); //Obtains actual date and minus it one year
        endDate = Calendar.getInstance().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusYears(1); //Obtains actual date and pluses it one year
        initialDate = Calendar.getInstance().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        for (LocalDate date = startDate; date.isBefore(endDate); date=date.plusDays(1)) {
            items.add(String.valueOf(date));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        MatchesMainPagerAdapter adapter = new MatchesMainPagerAdapter();
        adapter.addAll(items);

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem((int) ChronoUnit.DAYS.between(startDate, initialDate));

        RecyclerTabLayout recyclerTabLayout = findViewById(R.id.recycler_tab_layout);
        recyclerTabLayout.setUpWithViewPager(viewPager);
    }
}