package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    private EditText login;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void seConnecterAccueil(View view){
        setContentView(R.layout.connexion);
        if (login == null){
            login = findViewById(R.id.login);
        }
        if (password == null){
            password = findViewById(R.id.mdp);
        }
    }

    public void retourConnexionToAccueil(View view){setContentView(R.layout.activity_main);}

    public void connexionProf(View view){
        login = findViewById(R.id.login);
        password = findViewById(R.id.mdp);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String log = login.getText().toString();
                    String mdp = password.getText().toString();

                    System.out.println(log + " " + mdp);

                    String url = "http://10.0.2.2:8080/utilisateurs/search/findByMailAndPassword?mail="+log+"&password="+mdp+"\n";
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


                    if ( !response.toString().contains("utilisateur\" : [ ]")){
                        Utilisateur user = new Utilisateur(response.toString());
                        System.out.println(user.toString());
                        login.setText("");
                        password.setText("");
                        log = "";
                        mdp = "";
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (user.getProfessor()){
                                    setContentView(R.layout.professeur);
                                }
                            }
                        });
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void connexionEleve(View view){
        login = findViewById(R.id.login);
        password = findViewById(R.id.mdp);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String log = login.getText().toString();
                    String mdp = password.getText().toString();

                    System.out.println(log + " " + mdp);

                    String url = "http://10.0.2.2:8080/utilisateurs/search/findByMailAndPassword?mail="+log+"&password="+mdp+"\n";
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


                    if ( !response.toString().contains("utilisateur\" : [ ]")){
                        Utilisateur user = new Utilisateur(response.toString());
                        System.out.println(user.toString());
                        login.setText("");
                        password.setText("");
                        log = "";
                        mdp = "";
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                setContentView(R.layout.eleve);
                            }
                        });
                    }
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