package com.manuma.intento46;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import org.w3c.dom.Document;

public class MainActivity extends AppCompatActivity {

    private EditText txtMail, txtPass;
    private Button btnRegistro, btnLogin;
    private DatabaseReference database;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage databaseStore;
    private Toast toast;
    private String usu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        database= FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();




        txtMail=(EditText) findViewById(R.id.editMail);
        txtPass=(EditText) findViewById(R.id.editPass);
        btnLogin=(Button) findViewById(R.id.btnlog);
        btnRegistro=(Button) findViewById(R.id.btnRegistro);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail=txtMail.getText().toString().trim();
                String pass=txtPass.getText().toString().trim();

                if(TextUtils.isEmpty(mail)){
                    toast.makeText(MainActivity.this,"debe introducir un email",toast.LENGTH_LONG).show();
                }
                else{
                    if(TextUtils.isEmpty(pass)){
                        toast.makeText(MainActivity.this,"debe introducir una contraseña",toast.LENGTH_LONG).show();
                    }
                    else{



                        firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {



                                if(task.isSuccessful()){
                                    int arro=mail.indexOf("@");
                                    usu=mail.substring(0,arro);
                                    toast.makeText(MainActivity.this,"bienvenido",toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                                    intent.putExtra("mail", usu);
                                    startActivity(intent);

                                }
                                else{
                                    toast.makeText(MainActivity.this,"primero debe estar registrado",toast.LENGTH_LONG).show();

                                }
                            }
                        });

                    }
                }
            }
        });


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=txtMail.getText().toString().trim();
                String pass=txtPass.getText().toString().trim();

                if(TextUtils.isEmpty(mail)){
                    toast.makeText(MainActivity.this,"debe introducir un email",toast.LENGTH_LONG).show();
                }
                else{
                    if(TextUtils.isEmpty(pass)){
                        toast.makeText(MainActivity.this,"debe introducir una contraseña",toast.LENGTH_LONG).show();
                    }
                    else{
                        firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    int arro=mail.indexOf("@");
                                    usu= mail.substring(0,arro);

                                   database.child("users").child("usuario").child("voto").setValue(0);

                                   // database.child("users").child(usu).setValue(0);
                                    toast.makeText(MainActivity.this,"se ha registrado correctamente",toast.LENGTH_LONG).show();


                                }
                                else{
                                    toast.makeText(MainActivity.this,"error en el registro",toast.LENGTH_LONG).show();

                                }


                                //task.getException().toString()
                            }
                        });

                    }
                }




            }
        });


    }




}