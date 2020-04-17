package com.somi.imakeit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class perfil extends Fragment {

    FirebaseAuth auth;
    DatabaseReference databaseReference;
    ImageView asd;
    TextView name, rango;
    public perfil() {

        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view2 =  inflater.inflate(R.layout.fragment_perfil, container, false);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        asd = view2.findViewById(R.id.xds);
        name = view2.findViewById(R.id.name);
        rango = view2.findViewById(R.id.rango);

        obtenerinfo();
        return view2;
    }

    private void obtenerinfo() {
       String id = auth.getCurrentUser().getUid();

        databaseReference.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if(Integer.parseInt(dataSnapshot.child("coronas").getValue().toString())<500)
                    {
                        rango.setText("Rango: Novato");
                    }
                    if(Integer.parseInt(dataSnapshot.child("coronas").getValue().toString())>500&&Integer.parseInt(dataSnapshot.child("coronas").getValue().toString())<1000)
                    {
                        rango.setText("Rango: Avanzado");
                    }
                    if(Integer.parseInt(dataSnapshot.child("coronas").getValue().toString())>1000&&Integer.parseInt(dataSnapshot.child("coronas").getValue().toString())<2000)
                    {
                        rango.setText("Rango: Maestro");
                    }
                    if(Integer.parseInt(dataSnapshot.child("coronas").getValue().toString())>2000)
                    {
                        rango.setText("Rango: Deidad");
                    }
                    FirebaseUser user = auth.getCurrentUser();
                    if(user!=null){
                        setUserData(user);

                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void setUserData(FirebaseUser user) {
        name .setText(user.getDisplayName());
        String photo = user.getPhotoUrl().toString();
        Picasso.get().load(photo).into(asd);
    }

}
