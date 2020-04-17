package com.somi.imakeit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class info1 extends AppCompatActivity {
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    ImageView asd;
    String datilli;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info1);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        asd = findViewById(R.id.xds2);
        Bundle datos = this.getIntent().getExtras();
        datilli = datos.getString("dato");
        textView = findViewById(R.id.descripcion);
        obtenerinfo();
    }
    private void obtenerinfo() {
        String id = auth.getCurrentUser().getUid();

        databaseReference.child("Users").child(id).child("Peticiones").child(datilli).addValueEventListener(new ValueEventListener() {
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
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
