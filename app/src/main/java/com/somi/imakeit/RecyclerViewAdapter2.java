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
import androidx.core.app.BundleCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder> {
    public ArrayList<Profile2> profiles;
    public Context context;

    public RecyclerViewAdapter2(Context c , ArrayList<Profile2> p)
    {
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview2,parent,false));
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
        holder.onClick(profiles.get(position).getId());

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
            name = itemView.findViewById(R.id.name2);
            rango = itemView.findViewById(R.id.rango2);
            recompensa = itemView.findViewById(R.id.recompensa2);
            coron = itemView.findViewById(R.id.corona2);
            profesion = itemView.findViewById(R.id.profesion2);
            imagen = itemView.findViewById(R.id.imagen2);
            disponible = itemView.findViewById(R.id.disponible2);
            btn = itemView.findViewById(R.id.btncard2);

        }
        public void onClick(final String position)
        {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v2) {
                    

                    Intent intent2 = new Intent(context, info2.class);
                    intent2.putExtra("dato",position);
                    ContextCompat.startActivity(context,intent2,Bundle.EMPTY);
                }
            });
        }
    }
}