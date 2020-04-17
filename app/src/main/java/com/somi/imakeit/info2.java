package com.somi.imakeit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class info2 extends AppCompatActivity {
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    ImageView asd;
    String datilli;
    TextView textView;
    Button button;
    String lol;
    final Map<String, Object> map = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info2);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        asd = findViewById(R.id.xds2);
        Bundle datos = this.getIntent().getExtras();
        datilli = datos.getString("dato");
        textView = findViewById(R.id.descripcion);
        button = findViewById(R.id.btn2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = auth.getCurrentUser().getUid();
                databaseReference.child("Users").child(id).child("Misiones").child(lol).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task2) {
                        if(task2.isSuccessful()){
                            databaseReference.child("Mision").child(datilli).removeValue();
                            finish();
                        }else{
                        }
                    }
                });
            }
        });
        obtenerinfo();
    }
    private void obtenerinfo() {
        String id = auth.getCurrentUser().getUid();

        databaseReference.child("Mision").child(datilli).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Picasso.get().load(dataSnapshot.child("imagen").getValue().toString()).into(asd);
                    textView.setText("Nombre: " + dataSnapshot.child("name").getValue().toString()+"\n");
                    textView.append("Rango: "+dataSnapshot.child("rango").getValue().toString()+"\n");
                    textView.append("Profesion: " + dataSnapshot.child("profesion").getValue().toString()+"\n");
                    textView.append("Recompensa: "+dataSnapshot.child("recompensa").getValue().toString()+"\n");
                    textView.append("Coronas: " + dataSnapshot.child("corona").getValue().toString()+"\n");
                    textView.append("Descripcion: "+dataSnapshot.child("descripcion").getValue().toString()+"\n");

                    lol= dataSnapshot.child("id").getValue().toString();
                    map.put("name",dataSnapshot.child("name").getValue().toString());
                    map.put("rango",dataSnapshot.child("rango").getValue().toString());
                    map.put("id",dataSnapshot.child("id").getValue().toString());
                    map.put("profesion",dataSnapshot.child("profesion").getValue().toString());
                    map.put("recompensa",dataSnapshot.child("recompensa").getValue().toString());
                    map.put("descripcion",dataSnapshot.child("descripcion").getValue().toString());
                    map.put("corona",dataSnapshot.child("corona").getValue().toString());
                    map.put("imagen",dataSnapshot.child("imagen").getValue().toString());
                    map.put("disponible","no");
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
