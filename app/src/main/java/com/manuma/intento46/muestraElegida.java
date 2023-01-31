package com.manuma.intento46;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;

public class muestraElegida extends AppCompatActivity {

    private ImageView mostrada;
    private Button volver;
    private StorageReference miStorage;
    private String usuario;
    private String eleccion;
    private int imagen;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra_elegida);

        usuario = getIntent().getStringExtra("mail");
        eleccion= getIntent().getStringExtra("eleccion");
        imagen= Integer.parseInt(eleccion);
     mostrada=(ImageView) findViewById(R.id.imagenMostrada1);
     volver= (Button) findViewById(R.id.buttonVolverActivi3);

     if(imagen==1){
         Glide.with(this ).load(new FirebaseImageLoader()).load("https://firebasestorage.googleapis.com/v0/b/mifirebase-6ec56.appspot.com/o/imagen3.jpg?alt=media&token=7f5182da-b8b2-4751-80aa-f465e871c899").fitCenter().centerCrop().into(mostrada);
     }
     else if(imagen==2){
         Glide.with(this ).load(new FirebaseImageLoader()).load("https://firebasestorage.googleapis.com/v0/b/mifirebase-6ec56.appspot.com/o/imagen4.jpg?alt=media&token=16a82ab6-d35d-4e64-964c-7fa6d3b027c1").fitCenter().centerCrop().into(mostrada);
        }
     else if(imagen==3){
         Glide.with(this ).load(new FirebaseImageLoader()).load("https://firebasestorage.googleapis.com/v0/b/mifirebase-6ec56.appspot.com/o/imagen5.jpg?alt=media&token=8910ecbb-d94e-484f-81a0-3ebb43bd5a2d").fitCenter().centerCrop().into(mostrada);
        }
     else if(imagen==4){
         Glide.with(this ).load(new FirebaseImageLoader()).load("https://firebasestorage.googleapis.com/v0/b/mifirebase-6ec56.appspot.com/o/leopardo.jpg?alt=media&token=b3af6773-2e15-4baa-bf21-62a9eedd6aca").fitCenter().centerCrop().into(mostrada);
        }

            volver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(muestraElegida.this,MainActivity2.class);
                    intent.putExtra("mail", usuario.toString());
                    startActivity(intent);
                }
            });


    }
}