package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void seConnecterAccueil(View view){
        setContentView(R.layout.connexion);
    }

    public void retourConnexionToAccueil(View view){
        setContentView(R.layout.activity_main);
    }


    // pour accéder au local host du serveur web
    // http://10.0.2.2:8080/home
}