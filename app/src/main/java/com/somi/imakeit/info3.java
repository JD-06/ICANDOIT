package com.somi.imakeit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class info3 extends AppCompatActivity {
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    ImageView asd;
    String datilli;
    TextView textView;
    Button button;
    Boolean aBoolean;
    int coronas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info3);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        asd = findViewById(R.id.xds2);
        Bundle datos = this.getIntent().getExtras();
        aBoolean = true;
        datilli = datos.getString("dato");
        textView = findViewById(R.id.descripcion);
        button = findViewById(R.id.btn3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = auth.getCurrentUser().getUid();
                obtenerinfo2();
                databaseReference.child("Users").child(id).child("Misiones").child(datilli).removeValue();
                finish();
            }
        });
        obtenerinfo();
    }
    private void obtenerinfo() {
        String id = auth.getCurrentUser().getUid();

        databaseReference.child("Users").child(id).child("Misiones").child(datilli).addValueEventListener(new ValueEventListener() {
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

                    coronas = Integer.parseInt(dataSnapshot.child("corona").getValue().toString());
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void obtenerinfo2() {
        final String id = auth.getCurrentUser().getUid();

        databaseReference.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()&&aBoolean==true){
                    aBoolean=false;
                   int corona = Integer.parseInt(dataSnapshot.child("coronas").getValue().toString());
                    databaseReference.child("Users").child(id).child("coronas").setValue(corona+coronas);


                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
