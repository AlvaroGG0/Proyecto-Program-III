package com.example.sporttogether.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sporttogether.R;
import com.example.sporttogether.partido.Partido;

import java.util.List;

public class MatchesMainRecyclerAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static class TennisViewHolder extends RecyclerView.ViewHolder {

        private final TextView dateTextView;

        TennisViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = (TextView) itemView.findViewById(R.id.datetime_textview);
        }

        private void setTennisDetails(Partido partido) {
            dateTextView.setText(partido.getDatetime().toLocalTime().toString());
        }
    }
    static class FootballViewHolder extends RecyclerView.ViewHolder {

        private final TextView dateTextView;

        FootballViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = (TextView) itemView.findViewById(R.id.datetime_textview);
        }

        private void setFootballDetails(Partido partido) {
            dateTextView.setText(partido.getDatetime().toLocalTime().toString());
        }
    }
    
    private final List<Partido> partidos;

    public MatchesMainRecyclerAdapter(List<Partido> partidos){
        this.partidos=partidos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;

        if (viewType == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.row_matches_tennis, parent, false);
            return new TennisViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.row_matches_football, parent, false);
            return new FootballViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == 0) {
            ((TennisViewHolder) holder).setTennisDetails(partidos.get(position));
        } else {
            ((FootballViewHolder) holder).setFootballDetails(partidos.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return partidos.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (partidos.get(position).getIdDeporte() == 0) {
            return 0;
        } else {
            return 2;
        }
    }
}


