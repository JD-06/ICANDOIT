package com.somi.imakeit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter3 extends RecyclerView.Adapter<RecyclerViewAdapter3.ViewHolder> {
    public ArrayList<Profile> profiles;
    public Context context;

    public RecyclerViewAdapter3(Context c , ArrayList<Profile> p)
    {
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(profiles.get(position).getName());
        holder.rango.setText(profiles.get(position).getRango());
        holder.recompensa.setText(profiles.get(position).getRecompensa());
        holder.coron.setText(profiles.get(position).getCorona());
        holder.profesion.setText(profiles.get(position).getProfesion());
        holder.disponible.setText(profiles.get(position).getDisponible());
        Picasso.get().load(profiles.get(position).getImagen()).into(holder.imagen);
        holder.onClick(profiles.get(position).getId(), context);

    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, rango, recompensa, coron, profesion, disponible;
        ImageView imagen;
        RelativeLayout btn;
        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            rango = itemView.findViewById(R.id.rango);
            recompensa = itemView.findViewById(R.id.recompensa);
            coron = itemView.findViewById(R.id.corona);
            profesion = itemView.findViewById(R.id.profesion);
            imagen = itemView.findViewById(R.id.imagen);
            disponible = itemView.findViewById(R.id.disponible);
            btn = itemView.findViewById(R.id.btncard);

        }
        public void onClick(final String position, final Context context)
        {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    

                    Intent intent = new Intent(context, info3.class);
                    intent.putExtra("dato",position);
                    ContextCompat.startActivity(context,intent, Bundle.EMPTY);
                }
            });
        }
    }
}