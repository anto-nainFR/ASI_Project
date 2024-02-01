package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.owlike.genson.Genson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


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

    public void test(View view) throws IOException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "http://10.0.2.2:8080/utilisateurs/search/findByMailAndPassword?mail=Antonin@gmail.com&password=password\n";
                    URL obj = new URL(url);
                    HttpURLConnection con  =(HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");


                    int responseCode = con.getResponseCode();
                    System.out.println("Code de réponse : "+responseCode);

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while((inputLine = in.readLine()) != null){
                        response.append(inputLine);
                    }
                    in.close();
                    System.out.println("Réponse : "+response.toString());
                    Utilisateur user = new Utilisateur(response.toString());
                    System.out.println(user.toString());

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }



    // pour accéder au local host du serveur web
    // http://10.0.2.2:8080/home
}