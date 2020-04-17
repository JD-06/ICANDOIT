package com.somi.imakeit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class peticion extends AppCompatActivity {
EditText nombre, recompensa,coronas,descrip;
MaterialBetterSpinner rango, profesion;
Button button;
Boolean aBoolean = true;
    Boolean aBoolean1 = true;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peticion);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        nombre =  findViewById(R.id.nombre);
        rango = findViewById(R.id.rango);
        profesion = findViewById(R.id.profesion);
        recompensa = findViewById(R.id.recompensas);
        coronas = findViewById(R.id.coronas);
        descrip = findViewById(R.id.descripcion);
        button = findViewById(R.id.enviar);
        String[] rang={"Noob","Avanzado","Master","Deidad"};
        String[] pro={"Diseñador"};
        ArrayAdapter<String> rangos=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,rang);
        rango.setAdapter(rangos);
        ArrayAdapter<String> profesione=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,pro);
        profesion.setAdapter(profesione);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             obtenerinfo();
            }
        });
    }

    public void met_sub(String s1, final String s2){

        final Map<String, Object> map = new HashMap<>();


        final String id = auth.getCurrentUser().getUid();
        map.put("name",nombre.getText().toString());
        map.put("rango",rango.getText().toString());
        map.put("id","-"+(1+Integer.parseInt(s2))+"-"+id);
        map.put("profesion",profesion.getText().toString());
        map.put("recompensa","$"+recompensa.getText().toString());
        map.put("descripcion",descrip.getText().toString());
        map.put("corona",coronas.getText().toString());
        map.put("imagen",s1);
        map.put("disponible","disponible");

        databaseReference.child("Users").child(id).child("Peticiones").child("-"+(1+Integer.parseInt(s2))+"-"+id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {
                if(task2.isSuccessful()){
                    if(aBoolean == true) {
                        aBoolean = false;
                        databaseReference.child("Mision").child("-"+(1+Integer.parseInt(s2))+"-"+id).setValue(map);
                        databaseReference.child("Users").child(id).child("numpeticiones").setValue(1 + Integer.parseInt(s2));
                        finish();
                    }
                }else{
                    Toast.makeText(peticion.this, "mames", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void obtenerinfo() {
        String id = auth.getCurrentUser().getUid();

        databaseReference.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()&&aBoolean1==true) {
                    aBoolean1=false;
                    FirebaseUser user = auth.getCurrentUser();
                    if(user!=null){
                        setUserData(user, dataSnapshot.child("numpeticiones").getValue().toString());

                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void setUserData(FirebaseUser user, String s) {
        String photo = user.getPhotoUrl().toString();
        met_sub(photo,s);
    }
}
