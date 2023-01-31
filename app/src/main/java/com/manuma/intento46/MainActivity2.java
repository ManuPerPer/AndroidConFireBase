package com.manuma.intento46;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.GenericLifecycleObserver;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity2 extends AppCompatActivity {

    private String usuario;
    private StorageReference miStorage;
    private TextView bienvenida;
    private ImageButton imagen1, imagen2, imagen3, imagen4;
    private Button btnVota, btnResta;
    private DatabaseReference database;
    private int elegido=0;
    private Toast toast;
    private String resultado;
    private int miVoto;
    private TextView votados1,votados2,votados3,votados4;
    private int votos1, votos2,votos3,votos4;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        votos1=0;
        votos2=0;
        votos3=0;
        votos4=0;

        database= FirebaseDatabase.getInstance().getReference();
        miStorage = FirebaseStorage.getInstance().getReference();
        usuario = getIntent().getStringExtra("mail");
        btnVota= (Button) findViewById(R.id.btnVotar);
        btnResta=(Button) findViewById(R.id.btnQuitaVoto);
        bienvenida = (TextView) findViewById(R.id.welcome);
        bienvenida.setText("Bienvenido " + usuario);
        imagen1=(ImageButton) findViewById(R.id.imageButton1);
        imagen2=(ImageButton) findViewById(R.id.imageButton2);
        imagen3=(ImageButton) findViewById(R.id.imageButton3);
        imagen4=(ImageButton) findViewById(R.id.imageButton4);
        Glide.with(this ).load(new FirebaseImageLoader()).load("https://firebasestorage.googleapis.com/v0/b/mifirebase-6ec56.appspot.com/o/imagen3.jpg?alt=media&token=7f5182da-b8b2-4751-80aa-f465e871c899").fitCenter().centerCrop().into(imagen1);
        Glide.with(this ).load(new FirebaseImageLoader()).load("https://firebasestorage.googleapis.com/v0/b/mifirebase-6ec56.appspot.com/o/imagen4.jpg?alt=media&token=16a82ab6-d35d-4e64-964c-7fa6d3b027c1").fitCenter().centerCrop().into(imagen2);
        Glide.with(this ).load(new FirebaseImageLoader()).load("https://firebasestorage.googleapis.com/v0/b/mifirebase-6ec56.appspot.com/o/imagen5.jpg?alt=media&token=8910ecbb-d94e-484f-81a0-3ebb43bd5a2d").fitCenter().centerCrop().into(imagen3);
        Glide.with(this ).load(new FirebaseImageLoader()).load("https://firebasestorage.googleapis.com/v0/b/mifirebase-6ec56.appspot.com/o/leopardo.jpg?alt=media&token=b3af6773-2e15-4baa-bf21-62a9eedd6aca").fitCenter().centerCrop().into(imagen4);
        votados1= (TextView) findViewById(R.id.votos1);
        votados2= (TextView) findViewById(R.id.votos2);
        votados3= (TextView) findViewById(R.id.votos3);
        votados4= (TextView) findViewById(R.id.votos4);

        database.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String votoRecepcion = snapshot.child("voto").getValue().toString();
                        int casteo = Integer.parseInt(votoRecepcion);

                        if (casteo == 1) {
                            votos1++;
                        } else if (casteo == 2) {
                            votos2++;
                        } else if (casteo == 3) {
                            votos3++;
                        } else if (casteo == 4) {
                            votos4++;
                        }
                    }
                    votados1.setText("votos foto1: "+String.valueOf(votos1));
                    votados2.setText("votos foto2: "+String.valueOf(votos2));
                    votados3.setText("votos foto3: "+String.valueOf(votos3));
                    votados4.setText("votos foto4: "+String.valueOf(votos4));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        database.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    resultado= dataSnapshot.child(usuario.toString()).child("voto").getValue().toString();
                    miVoto= Integer.parseInt(resultado);

                    if(miVoto==0){
                        btnVota.setText("VOTA YA!!");
                        btnResta.setText("QUITAR VOTO");
                    }
                    else{
                        btnVota.setText("VER MI VOTO");
                        btnResta.setText("BORRAR BOTO");
                    }
                }
                else{
                    toast.makeText(MainActivity2.this,"no existe de dataSnapshot",toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Fallo la lectura: " + databaseError.getCode());
            }
        });






        imagen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegido=1;
            }
        });
        imagen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegido=2;
            }
        });
        imagen3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegido=3;
            }
        });
        imagen4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegido=4;
            }
        });

        btnVota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(miVoto==0 && elegido!=0){
                    database.child("users").child(usuario.toString()).child("voto").setValue(elegido);
                    toast.makeText(MainActivity2.this,"se ha votado correctamente",toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity2.this,MainActivity2.class);
                    intent.putExtra("mail", usuario.toString());
                    startActivity(intent);
                }
                else if(miVoto!=0){
                    Intent intent = new Intent(MainActivity2.this,muestraElegida.class);
                    intent.putExtra("mail", usuario.toString());
                    intent.putExtra("eleccion", String.valueOf(miVoto));
                    startActivity(intent);

                }
                else if(elegido==0){
                    toast.makeText(MainActivity2.this,"debes elegir una imagen",toast.LENGTH_LONG).show();
                }

            }
        });

        btnResta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(miVoto==0){


                    toast.makeText(MainActivity2.this,"Primero tienes que votar",toast.LENGTH_LONG).show();

                }
                else{
                    database.child("users").child(usuario.toString()).child("voto").setValue(0);
                    toast.makeText(MainActivity2.this,"se ha quitado tu voto correctamente",toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity2.this,MainActivity2.class);
                    intent.putExtra("mail", usuario.toString());
                    startActivity(intent);
                }
            }
        });



    }
}