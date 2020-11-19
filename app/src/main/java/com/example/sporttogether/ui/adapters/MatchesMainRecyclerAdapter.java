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
        RecyclerView.Adapter<MatchesMainRecyclerAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.tvAnimalName);
        }
    }

    private final List<Partido> partidos;

    public MatchesMainRecyclerAdapter(List<Partido> partidos){
        this.partidos=partidos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View matchesView = inflater.inflate(R.layout.activity_matches_row, parent, false);

        // Return a new holder instance
        return new ViewHolder(matchesView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        Partido partido = partidos.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;
        textView.setText(Integer.toString(partido.getIdDeporte()));

    }

    @Override
    public int getItemCount() {
        return partidos.size();
    }
}
