package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.snackbar.Snackbar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText login;
    private EditText password;
    private TextView titre;

    private Utilisateur user;
    private  int flag;
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
                        user = new Utilisateur(response.toString());
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
                                    titre = findViewById(R.id.titreProfesseur);
                                    titre.setText("Bonjour "+user.getPrenom()+" !");
                                    flag = 2;
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
                        user = new Utilisateur(response.toString());
                        System.out.println(user.toString());
                        login.setText("");
                        password.setText("");
                        log = "";
                        mdp = "";
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                setContentView(R.layout.eleve);
                                titre = findViewById(R.id.titreEleve);
                                titre.setText("Bonjour "+user.getPrenom()+" !");
                                flag = 1;
                            }
                        });
                    }else{
                        user = null;
                    }
                    con.disconnect();

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    public void afficherInformation1 (View view){


        setContentView(R.layout.information_user);

        TextView tmp;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);


        tmp = findViewById(R.id.textView2);
        tmp.setText("Nom : "+user.getNom());

        tmp = findViewById(R.id.textView3);
        tmp.setText("Prénom : "+user.getPrenom());

        tmp = findViewById(R.id.textView4);
        tmp.setText("Date de naissance : "+sdf.format(user.getBirthday().getTime()));

        tmp = findViewById(R.id.textView5);
        tmp.setText("Adresse : "+ user.getAdresse());

        tmp = findViewById(R.id.textView6);
        tmp.setText("Mail : "+user.getMail());

        tmp = findViewById(R.id.textView7);
        if (user.getProfessor())
            tmp.setText("Statut : Professeur et élève");
        else
            tmp.setText("Statut : Élève");


        flag = 1;
    }
    public void afficherInformation2 (View view){
        setContentView(R.layout.information_user);

        TextView tmp;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);


        tmp = findViewById(R.id.textView2);
        tmp.setText("Nom : "+user.getNom());

        tmp = findViewById(R.id.textView3);
        tmp.setText("Prénom : "+user.getPrenom());

        tmp = findViewById(R.id.textView4);
        tmp.setText("Date de naissance : "+sdf.format(user.getBirthday().getTime()));

        tmp = findViewById(R.id.textView5);
        tmp.setText("Adresse : "+ user.getAdresse());

        tmp = findViewById(R.id.textView6);
        tmp.setText("Mail : "+user.getMail());

        tmp = findViewById(R.id.textView7);
        if (user.getProfessor())
            tmp.setText("Statut : Professeur et élève");
        else
            tmp.setText("Statut : Élève");
        flag = 2;
    }
    public void retourInformation (View view){

        if (flag == 1){
            setContentView(R.layout.eleve);
            titre = findViewById(R.id.titreEleve);
            titre.setText("Bonjour "+user.getPrenom()+" !");
        }else{
            setContentView(R.layout.professeur);
            titre = findViewById(R.id.titreProfesseur);
            titre.setText("Bonjour "+user.getPrenom()+" !");
        }
    }


    DatePickerDialog picker;
    EditText eText;
    Button btnGet;
    TextView tvw;
    TimePicker timePicker;

    public void ajoutCoursPage(View view){
        setContentView(R.layout.ajout_cours);
        eText=(EditText) findViewById(R.id.editText5);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker.show();
            }
        });
        timePicker=(TimePicker)findViewById(R.id.datePicker1);
        timePicker.setIs24HourView(true);

    }


    public void ajouterCoursBDD(View view){
        EditText tmp;
        TimePicker tp;
        String nom, sport, adresse;
        int nbPlace, heure, minute;
        Date date;

        try {
            tmp = findViewById(R.id.EditText);
            nom = tmp.getText().toString();

            tmp = findViewById(R.id.editText2);
            sport = tmp.getText().toString();

            tmp = findViewById(R.id.editText3);
            adresse = tmp.getText().toString();

            tmp = findViewById(R.id.editText4);
            nbPlace = Integer.parseInt(tmp.getText().toString());

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",Locale.FRANCE);
            tmp = findViewById(R.id.editText5);
            date = formatter.parse(tmp.getText().toString());

            tp = findViewById(R.id.datePicker1);
            heure = tp.getHour();
            minute = tp.getMinute();

            date.setHours(heure);
            date.setMinutes(minute);

            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Le cours a bien été créé !",Snackbar.LENGTH_SHORT);
            snackbar.show();



            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String url = "http://10.0.2.2:8080/home/ajout/"+nom+"/"+sport+"/"+adresse+"/"+nbPlace+"/"+date.getTime()+"/"+user.getId()+"\n";
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
                        con.disconnect();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            thread.start();



            System.out.println(nom+" "+sport+" "+adresse+" "+nbPlace+" "+date.getTime()+" "+heure+":"+minute);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // pour accéder au local host du serveur web
    // http://10.0.2.2:8080/home
}