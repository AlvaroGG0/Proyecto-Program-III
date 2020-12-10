package com.example.sporttogether.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sporttogether.R;
import com.example.sporttogether.database.Database;
import com.example.sporttogether.partido.Partido;
import com.example.sporttogether.ui.activities.LogInActivity;

import java.util.List;
import java.util.Locale;

public class MatchesMainRecyclerAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Partido> partidos;

    public MatchesMainRecyclerAdapter(List<Partido> partidos){
        this.partidos=partidos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.row_matches, parent, false);
        ImageView imageView3 = view.findViewById(R.id.imageView3);

        switch (viewType) {
            case 0:
                imageView3.setImageResource(R.drawable.ic_sports_tennis_24px);
                break;
            case 1:
                imageView3.setImageResource(R.drawable.ic_padel_sport_icon_141873);
                break;
            case 2:
                imageView3.setImageResource(R.drawable.ic_sports_soccer_24px);
                break;
            case 3:
                imageView3.setImageResource(R.drawable.ic_sports_basketball_24px);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).setViewDetails(partidos.get(position));

        holder.itemView.setOnClickListener(v -> Toast.makeText(holder.itemView.getContext(), Integer.toString(position) , Toast.LENGTH_SHORT).show());

        holder.itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            if (LogInActivity.usuario.getUsername().equals(partidos.get(position).getEquipo1()[0])){
                menu.add(0, v.getId(), 0, holder.itemView.getResources().getString(R.string.delete));

                menu.getItem(0).setOnMenuItemClickListener(item -> {
                    Database.deleteMatch(partidos.get(position).getIdPartido());
                    partidos.remove(position);
                    notifyItemRemoved(position);
                    return false;
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return partidos.size();
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        switch (partidos.get(position).getIdDeporte()) {
            case 0:
                viewType=0;
                break;
            case 1:
                viewType=1;
                break;
            case 2:
                viewType=2;
                break;
            case 3:
                viewType=3;
                break;
        }
        return viewType;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView dateTextView;
        private final TextView textView7;
        private final TextView textView8;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.datetime_textview);
            textView7 = itemView.findViewById(R.id.textView7);
            textView8 = itemView.findViewById(R.id.textView8);

        }

        private void setViewDetails(Partido partido) {
            dateTextView.setText(partido.getDatetime().toLocalTime().toString());
            textView7.setText(String.format(Locale.getDefault(),"(%d/%d)", partido.getPlazasOcupadas()[0], partido.getEquipo1().length));
            textView8.setText(String.format(Locale.getDefault(),"(%d/%d)", partido.getPlazasOcupadas()[1], partido.getEquipo2().length));
            if (partido.getPlazasOcupadas()[0]==partido.getEquipo1().length){
                textView7.setTextColor(Color.parseColor("#b71c1c"));
            }if (partido.getPlazasOcupadas()[1]==partido.getEquipo2().length){
                textView7.setTextColor(Color.parseColor("#b71c1c"));
            }
        }
    }
}


