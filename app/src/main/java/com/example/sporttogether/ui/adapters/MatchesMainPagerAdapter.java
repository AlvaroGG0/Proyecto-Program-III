package com.example.sporttogether.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.sporttogether.R;
import com.example.sporttogether.database.Database;
import com.example.sporttogether.partido.Partido;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MatchesMainPagerAdapter extends PagerAdapter {

    private List<String> mItems = new ArrayList<>();
    private List<Partido> partidos = new ArrayList<>();
    private LocalDate initialDate = Calendar.getInstance().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    public MatchesMainPagerAdapter() {
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.layout_page_activity_matches_main, container, false);

        TextView text = view.findViewById(R.id.textView4);
        RecyclerView recyclerview_matches = view.findViewById(R.id.recyclerview_matches);

        if (mItems.get(position).equals(container.getResources().getString(R.string.today))){
            partidos = Database.getDateMatches(String.valueOf(initialDate));
        }else if (mItems.get(position).equals(container.getResources().getString(R.string.tomorrow))){
            partidos = Database.getDateMatches(String.valueOf(initialDate.plusDays(1)));
        }else if (mItems.get(position).equals(container.getResources().getString(R.string.yesterday))){
            partidos = Database.getDateMatches(String.valueOf(initialDate.minusDays(1)));
        }else{
            partidos = Database.getDateMatches(mItems.get(position));
        }

        if (partidos.isEmpty()){
            text.setVisibility(View.VISIBLE);
            recyclerview_matches.setVisibility(View.INVISIBLE);
        }else {
            text.setVisibility(View.INVISIBLE);
            recyclerview_matches.setVisibility(View.VISIBLE);
            MatchesMainRecyclerAdapter adapter = new MatchesMainRecyclerAdapter(partidos);
            recyclerview_matches.setAdapter(adapter);
            recyclerview_matches.setLayoutManager(new LinearLayoutManager(container.getContext()));

        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public String getPageTitle(int position) {
        return mItems.get(position);
    }

    public void addAll(List<String> items) {
        mItems = new ArrayList<>(items);
    }
}