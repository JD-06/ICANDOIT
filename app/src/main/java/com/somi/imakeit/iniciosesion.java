package com.somi.imakeit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class iniciosesion extends AppCompatActivity {

    Button button;
    FirebaseAuth auth;
    static final int GOOGLE_SIGN = 123;
    GoogleSignInClient googleSignInClient;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciosesion);
        button = findViewById(R.id.btnsing);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .requestProfile()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInGoogle();
            }
        });
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider
                .getCredential(account.getIdToken(),null);
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    final String id = auth.getCurrentUser().getUid();

                    databaseReference.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Intent i = new Intent(iniciosesion.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            }else{
                                Map<String, Object> map = new HashMap<>();
                                map.put("coronas",0);
                                map.put("numpeticiones",1);
                                map.put("nummisiones",0);




                                databaseReference.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task2) {
                                        if(task2.isSuccessful()){
                                            Intent i = new Intent(iniciosesion.this,MainActivity.class);
                                            startActivity(i);
                                            finish();
                                        }else{
                                            Toast.makeText(iniciosesion.this, "mames", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }else{

                }
            }
        });

    }

    private void SignInGoogle(){
        Intent SignIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(SignIntent,GOOGLE_SIGN);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GOOGLE_SIGN){
            Task<GoogleSignInAccount> task = GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if(account != null ) firebaseAuthWithGoogle(account);

            }catch (ApiException e){


                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

        if(auth.getCurrentUser()!=null){
            Intent izzi2 = new Intent(iniciosesion.this,MainActivity.class);
            //izzi2.putExtra("dato","old");
            startActivity(izzi2);
            finish();
        }
    }

}
