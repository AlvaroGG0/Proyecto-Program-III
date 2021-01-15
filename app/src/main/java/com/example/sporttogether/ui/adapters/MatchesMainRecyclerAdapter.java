package com.example.sporttogether.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sporttogether.R;
import com.example.sporttogether.database.Database;
import com.example.sporttogether.partido.Partido;
import com.example.sporttogether.ui.activities.LogInActivity;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MatchesMainRecyclerAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Partido> partidos;
    private final String username = LogInActivity.usuario.getUsername();

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

        Partido partido = partidos.get(position);

        holder.itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            if (username.equals(partido.getEquipo1()[0])){
                menu.add(0, v.getId(), 0, holder.itemView.getResources().getString(R.string.delete));
                menu.getItem(0).setOnMenuItemClickListener(item -> {
                    Database.deleteMatch(partido.getIdPartido());
                    partidos.remove(position);
                    notifyItemRemoved(position);
                    return false;
                });
            }else{
                if (Arrays.asList(partido.getEquipo1()).contains(username) || Arrays.asList(partido.getEquipo2()).contains(username)){
                    menu.add(1, v.getId(), 0, "Abandonar partido");
                    menu.getItem(0).setOnMenuItemClickListener(item -> {
                        Database.leaveMatch(username, partido);
                        return false;
                    });
                }else if (!(partido.getPlazasDisponibles()==0)){
                    menu.add(1,v.getId(), 0,"Unirme");
                    menu.getItem(0).setOnMenuItemClickListener(item -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext())
                                .setMessage("¿A qué equipo se desea unir?")
                                .setNegativeButton("Equipo 1", (dialog, id) -> {
                                    Database.joinMatch(username, partido, 1);
                                    notifyItemChanged(position);
                                })
                                .setPositiveButton("Equipo 2", (dialog, id) -> {
                                    Database.joinMatch(username, partido, 2);
                                    notifyItemChanged(position);
                                });

                        final AlertDialog dialog = builder.create();
                        dialog.show();
                        if (!Arrays.asList(partido.getEquipo1()).contains("null")){
                            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(false);
                        }else if (!Arrays.asList(partido.getEquipo2()).contains("null")){
                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                        }
                        return false;
                    });
                }
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
        private final ImageView imageView4;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.datetime_textview);
            textView7 = itemView.findViewById(R.id.textView7);
            textView8 = itemView.findViewById(R.id.textView8);
            imageView4 = itemView.findViewById(R.id.imageView4);

        }

        private void setViewDetails(Partido partido) {
            dateTextView.setText(partido.getDatetime().toLocalTime().toString());
            textView7.setText(String.format(Locale.getDefault(),"(%d/%d)", partido.getPlazasOcupadas()[0], partido.getEquipo1().length));
            textView8.setText(String.format(Locale.getDefault(),"(%d/%d)", partido.getPlazasOcupadas()[1], partido.getEquipo2().length));

            if (partido.getPlazasOcupadas()[0]==partido.getEquipo1().length){
                textView7.setTextColor(Color.parseColor("#b71c1c"));
            }if (partido.getPlazasOcupadas()[1]==partido.getEquipo2().length){
                textView8.setTextColor(Color.parseColor("#b71c1c"));
            }

            if (LogInActivity.usuario.getUsername().equals(partido.getEquipo1()[0])){
                imageView4.setVisibility(View.VISIBLE);
                imageView4.setColorFilter(Color.parseColor("#e53935"));
            }else if (Arrays.asList(partido.getEquipo1()).contains(LogInActivity.usuario.getUsername()) || Arrays.asList(partido.getEquipo2()).contains(LogInActivity.usuario.getUsername())){
                imageView4.setVisibility(View.VISIBLE);
            }
        }
    }
}


