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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



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

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
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

                    MessageDigest digest = MessageDigest.getInstance("SHA-256");
                    byte[] encodedhash = digest.digest(mdp.getBytes(StandardCharsets.UTF_8));

                    String url = "http://10.0.2.2:8080/utilisateurs/search/findByMailAndPassword?mail="+log+"&password="+bytesToHex(encodedhash)+"\n";
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
                    }else{
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Erreur de connexion !",Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }catch (Exception e){}
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
                    MessageDigest digest = MessageDigest.getInstance("SHA-256");
                    byte[] encodedhash = digest.digest(mdp.getBytes(StandardCharsets.UTF_8));

                    System.out.println(log + " " + mdp);

                    String url = "http://10.0.2.2:8080/utilisateurs/search/findByMailAndPassword?mail="+log+"&password="+bytesToHex(encodedhash)+"\n";
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
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Erreur de connexion !",Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        user = null;
                    }
                    con.disconnect();

                }catch (IOException e){
                    e.printStackTrace();
                }catch (Exception e){}
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


    public ArrayList<Cours> getCoursFromString(String chaine){

        ArrayList<Cours> array = new ArrayList<Cours>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(chaine);

            /*
            JsonNode linksNode = jsonNode.get("_links");
            JsonNode selfNode = linksNode.get("self");
            String href = selfNode.get("href").asText();
*/
            JsonNode coursNode = jsonNode.get("_embedded").get("cours");
            for (JsonNode cours : coursNode) {
                String nom = cours.get("nom").asText();
                String sport = cours.get("sport").asText();
                long date = cours.get("date").asLong();
                String lieu = cours.get("lieu").asText();
                int nbPlace = cours.get("nbPlace").asInt();
                String href = cours.get("_links").get("self").get("href").asText();

                String[] parts = href.split("/");
                String idPart = parts[parts.length - 1];
                int id = Integer.parseInt(idPart);
                array.add(new Cours(id,date,nom,sport,lieu,nbPlace,user));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    public ArrayList<Inscription> getInscriptionFromString(String chaine){

        ArrayList<Inscription> array = new ArrayList<Inscription>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(chaine);

            JsonNode coursNode = jsonNode.get("_embedded").get("inscription");
            for (JsonNode cours : coursNode) {
                int iduser = Integer.valueOf(cours.get("iduser").asText());
                int idcours = Integer.valueOf(cours.get("idcours").asText());
                String href = cours.get("_links").get("self").get("href").asText();

                String[] parts = href.split("/");
                String idPart = parts[parts.length - 1];
                int id = Integer.parseInt(idPart);
                array.add(new Inscription(id,iduser,idcours));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    public Utilisateur getUtilisateurFromString(String chaine){
        Utilisateur user = new Utilisateur();

        try {
            JSONObject jsonObject = new JSONObject(chaine);

            user.setNom(jsonObject.getString("nom"));
            user.setPrenom(jsonObject.getString("prenom"));
            user.setAdresse(jsonObject.getString("adresse"));
            user.setMail(jsonObject.getString("mail"));
            user.setPassword(jsonObject.getString("password"));

        }catch (Exception e){}
        return user;
    }

    public Cours getCoursFromString2(String chaine){
        Cours cours = new Cours();

        try {
            JSONObject jsonObject = new JSONObject(chaine);

            cours.setDate(Long.valueOf(jsonObject.getString("date")));
            cours.setNom(jsonObject.getString("nom"));
            cours.setSport(jsonObject.getString("sport"));
            cours.setLieu(jsonObject.getString("lieu"));
            cours.setNbPlace(Integer.valueOf(jsonObject.getString("nbPlace")));

            System.out.println(chaine);
            int index = chaine.indexOf("http://10.0.2.2:8080/cours/") + ("http://10.0.2.2:8080/cours/").length();
            chaine = chaine.substring(index);
            index = chaine.indexOf("\"");
            String tmp = chaine.substring(0,index);
            cours.setId(Integer.valueOf(tmp));
        }catch (Exception e){}
        return cours;
    }


    public void prochainCoursProf(View view){
        setContentView(R.layout.prochain_cours_prof);
        MainActivity main = this;
        flag = 2;
        Thread  t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String url = "http://10.0.2.2:8080/cours/search/findByDateIsGreaterThanAndProfIdOrderByDateAsc?date="+ new Date().getTime()+"&id="+user.getId()+"\n";
                    URL obj = new URL(url);
                    HttpURLConnection con  =(HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");

                    int responseCode = con.getResponseCode();

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while((inputLine = in.readLine()) != null){
                        response.append(inputLine);
                    }
                    in.close();
                    con.disconnect();

                    ArrayList<Cours> array = getCoursFromString(response.toString());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            LinearLayout lr = findViewById(R.id.lr);
                            TextView tv;
                            int cpt = 0;

                            for (Cours cours : array) {
                                tv = new TextView(main);
                                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) RelativeLayout.LayoutParams.MATCH_PARENT,(int) RelativeLayout.LayoutParams.WRAP_CONTENT);
                                params.leftMargin = 50;
                                params.rightMargin = 50;
                                params.topMargin = 100;

                                tv.setTextSize(15);
                                tv.setText(cours.getSport()+" : "+cours.getNom()+" le "+new Date(cours.getDate()).toString());
                                tv.setPadding(20,50,20,50);
                                tv.setLayoutParams(params);
                                tv.setBackground(getResources().getDrawable(R.drawable.textview_border));
                                tv.setClickable(true);
                                tv.setTag(cpt);
                                cpt++;
                                tv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        setContentView(R.layout.cours_info);
                                        Cours cours = array.get((int)v.getTag());

                                        TextView tmp = findViewById(R.id.textView11);
                                        tmp.setText("Sport : "+cours.getSport());

                                        tmp = findViewById(R.id.textView14);
                                        tmp.setText("Nom : "+cours.getNom());

                                        tmp = findViewById(R.id.textView15);
                                        tmp.setText("Date : "+new Date(cours.getDate()).toString());

                                        tmp = findViewById(R.id.textView16);
                                        tmp.setText("Lieu : "+cours.getLieu());

                                        tmp = findViewById(R.id.textView17);
                                        tmp.setText("Nombre de place : "+cours.getNbPlace());

                                        Button btn = findViewById(R.id.supprimerCours);
                                        btn.setTag(cours.getId());
                                        Thread thread = new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    String url = "http://10.0.2.2:8080/inscription/search/findInscriptionsByIdcours?idcours="+ cours.getId() +"\n";
                                                    System.out.println(url);
                                                    URL obj = new URL(url);
                                                    HttpURLConnection con  =(HttpURLConnection) obj.openConnection();
                                                    con.setRequestMethod("GET");

                                                    int responseCode = con.getResponseCode();

                                                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                                                    String inputLine;
                                                    StringBuilder response = new StringBuilder();

                                                    while((inputLine = in.readLine()) != null){
                                                        response.append(inputLine);
                                                    }
                                                    System.out.println(response.toString());
                                                    in.close();
                                                    con.disconnect();

                                                    ArrayList<Inscription> array = getInscriptionFromString(response.toString());
                                                    System.out.println(array.toString());
                                                    ArrayList<Utilisateur> arrayUser = new ArrayList<Utilisateur>();


                                                    for (Inscription insc: array){
                                                        try {
                                                            url = "http://10.0.2.2:8080/utilisateurs/"+insc.getIduser()+"\n";
                                                            obj = new URL(url);
                                                            con  =(HttpURLConnection) obj.openConnection();
                                                            con.setRequestMethod("GET");

                                                            responseCode = con.getResponseCode();

                                                            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                                                            response = new StringBuilder();

                                                            while((inputLine = in.readLine()) != null){
                                                                response.append(inputLine);
                                                            }
                                                            System.out.println(response.toString());
                                                            in.close();
                                                            con.disconnect();

                                                            arrayUser.add( getUtilisateurFromString(response.toString()));
                                                        }catch (Exception e){e.printStackTrace();}

                                                    }
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            LinearLayout lr2 = findViewById(R.id.lrInfo);
                                                            TextView tv;
                                                            for (int i = 0; i < arrayUser.size(); i++) {
                                                                tv = new TextView(main);
                                                                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) RelativeLayout.LayoutParams.MATCH_PARENT,(int) RelativeLayout.LayoutParams.WRAP_CONTENT);
                                                                params.leftMargin = 50;
                                                                params.rightMargin = 50;
                                                                params.topMargin = 25;

                                                                tv.setTextSize(15);
                                                                tv.setText("Nom : "+arrayUser.get(i).getNom()+" prénom :"+arrayUser.get(i).getPrenom());
                                                                tv.setPadding(20,50,20,50);
                                                                tv.setLayoutParams(params);
                                                                tv.setBackground(getResources().getDrawable(R.drawable.textview_border));
                                                                lr2.addView(tv);
                                                            }
                                                        }
                                                    });
                                                    for (int i = 0; i < arrayUser.size(); i++) {
                                                        System.out.println(arrayUser.get(i).toString());
                                                    }
                                                }catch (Exception e){}
                                            }
                                        });
                                        thread.start();
                                    }
                                });
                                lr.addView(tv);
                            }
                        }
                    });
                }catch (Exception e){e.printStackTrace();}
            }
        });
        t1.start();

    }

    public void historiqueProf(View view){
        setContentView(R.layout.historique_prof);
        MainActivity main = this;
        flag = 2;
        Thread  t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String url = "http://10.0.2.2:8080/cours/search/findByDateIsLessThanAndProfIdOrderByDateDesc?date="+ new Date().getTime()+"&id="+user.getId()+"\n";
                    URL obj = new URL(url);
                    HttpURLConnection con  =(HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");

                    int responseCode = con.getResponseCode();

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while((inputLine = in.readLine()) != null){
                        response.append(inputLine);
                    }
                    in.close();
                    con.disconnect();

                    ArrayList<Cours> array = getCoursFromString(response.toString());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            LinearLayout lr = findViewById(R.id.lr);
                            TextView tv;
                            int cpt = 0;
                            for (Cours cours : array) {
                                tv = new TextView(main);
                                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) RelativeLayout.LayoutParams.MATCH_PARENT,(int) RelativeLayout.LayoutParams.WRAP_CONTENT);
                                params.leftMargin = 50;
                                params.rightMargin = 50;
                                params.topMargin = 100;

                                tv.setTextSize(15);
                                tv.setText(cours.getSport()+" : "+cours.getNom()+" le "+new Date(cours.getDate()).toString());
                                tv.setPadding(20,50,20,50);
                                tv.setLayoutParams(params);
                                tv.setBackground(getResources().getDrawable(R.drawable.textview_border));
                                tv.setClickable(true);
                                tv.setTag(cpt);
                                cpt++;
                                tv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        setContentView(R.layout.cours_info);
                                        Cours cours = array.get((int)v.getTag());

                                        TextView tmp = findViewById(R.id.textView11);
                                        tmp.setText("Sport : "+cours.getSport());

                                        tmp = findViewById(R.id.textView14);
                                        tmp.setText("Nom : "+cours.getNom());

                                        tmp = findViewById(R.id.textView15);
                                        tmp.setText("Date : "+new Date(cours.getDate()).toString());

                                        tmp = findViewById(R.id.textView16);
                                        tmp.setText("Lieu : "+cours.getLieu());

                                        tmp = findViewById(R.id.textView17);
                                        tmp.setText("Nombre de place : "+cours.getNbPlace());

                                        Button btn = findViewById(R.id.supprimerCours);
                                        btn.setVisibility(View.INVISIBLE);

                                        Thread thread = new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    String url = "http://10.0.2.2:8080/inscription/search/findInscriptionsByIdcours?idcours="+ cours.getId() +"\n";
                                                    System.out.println(url);
                                                    URL obj = new URL(url);
                                                    HttpURLConnection con  =(HttpURLConnection) obj.openConnection();
                                                    con.setRequestMethod("GET");

                                                    int responseCode = con.getResponseCode();

                                                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                                                    String inputLine;
                                                    StringBuilder response = new StringBuilder();

                                                    while((inputLine = in.readLine()) != null){
                                                        response.append(inputLine);
                                                    }
                                                    System.out.println(response.toString());
                                                    in.close();
                                                    con.disconnect();

                                                    ArrayList<Inscription> array = getInscriptionFromString(response.toString());
                                                    System.out.println(array.toString());
                                                    ArrayList<Utilisateur> arrayUser = new ArrayList<Utilisateur>();


                                                    for (Inscription insc: array){
                                                        try {
                                                            url = "http://10.0.2.2:8080/utilisateurs/"+insc.getIduser()+"\n";
                                                            obj = new URL(url);
                                                            con  =(HttpURLConnection) obj.openConnection();
                                                            con.setRequestMethod("GET");

                                                            responseCode = con.getResponseCode();

                                                            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                                                            response = new StringBuilder();

                                                            while((inputLine = in.readLine()) != null){
                                                                response.append(inputLine);
                                                            }
                                                            System.out.println(response.toString());
                                                            in.close();
                                                            con.disconnect();

                                                            arrayUser.add( getUtilisateurFromString(response.toString()));
                                                        }catch (Exception e){e.printStackTrace();}

                                                    }
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            LinearLayout lr2 = findViewById(R.id.lrInfo);
                                                            TextView tv;
                                                            for (int i = 0; i < arrayUser.size(); i++) {
                                                                tv = new TextView(main);
                                                                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) RelativeLayout.LayoutParams.MATCH_PARENT,(int) RelativeLayout.LayoutParams.WRAP_CONTENT);
                                                                params.leftMargin = 50;
                                                                params.rightMargin = 50;
                                                                params.topMargin = 25;

                                                                tv.setTextSize(15);
                                                                tv.setText("Nom : "+arrayUser.get(i).getNom()+" prénom :"+arrayUser.get(i).getPrenom());
                                                                tv.setPadding(20,50,20,50);
                                                                tv.setLayoutParams(params);
                                                                tv.setBackground(getResources().getDrawable(R.drawable.textview_border));
                                                                lr2.addView(tv);
                                                            }
                                                        }
                                                    });
                                                    for (int i = 0; i < arrayUser.size(); i++) {
                                                        System.out.println(arrayUser.get(i).toString());
                                                    }
                                                }catch (Exception e){}
                                            }
                                        });
                                        thread.start();
                                    }
                                });
                                lr.addView(tv);
                            }
                        }
                    });
                }catch (Exception e){e.printStackTrace();}
            }
        });
        t1.start();

    }

    public void historiqueEleve(View view){
        setContentView(R.layout.historique_prof);
        MainActivity main = this;
        flag = 1;
        Thread  t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String url = "http://10.0.2.2:8080/inscription/search/findInscriptionsByIduser?iduser="+user.getId()+"\n";
                    URL obj = new URL(url);
                    HttpURLConnection con  =(HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");

                    int responseCode = con.getResponseCode();

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while((inputLine = in.readLine()) != null){
                        response.append(inputLine);
                    }
                    in.close();
                    con.disconnect();

                    ArrayList<Inscription> array = getInscriptionFromString(response.toString());
                    ArrayList<Cours> arrayCours = new ArrayList<Cours>();
                    Date now = new Date();
                    for (Inscription insc: array) {

                        url = "http://10.0.2.2:8080/cours/"+insc.getIdcours()+"\n";
                        obj = new URL(url);
                        con  =(HttpURLConnection) obj.openConnection();
                        con.setRequestMethod("GET");

                        responseCode = con.getResponseCode();

                        in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        response = new StringBuilder();

                        while((inputLine = in.readLine()) != null){
                            response.append(inputLine);
                        }
                        in.close();
                        con.disconnect();

                        Cours cours = getCoursFromString2(response.toString());
                        System.out.println(cours.toString());
                        if (cours.getDate() < now.getTime()){
                            arrayCours.add(cours);
                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            LinearLayout lr = findViewById(R.id.lr);
                            TextView tv;
                            int cpt = 0;
                            for (Cours cours : arrayCours) {
                                tv = new TextView(main);
                                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) RelativeLayout.LayoutParams.MATCH_PARENT,(int) RelativeLayout.LayoutParams.WRAP_CONTENT);
                                params.leftMargin = 50;
                                params.rightMargin = 50;
                                params.topMargin = 100;

                                tv.setTextSize(15);
                                tv.setText(cours.getSport()+" : "+cours.getNom()+" le "+new Date(cours.getDate()).toString());
                                tv.setPadding(20,50,20,50);
                                tv.setLayoutParams(params);
                                tv.setBackground(getResources().getDrawable(R.drawable.textview_border));
                                tv.setClickable(true);
                                tv.setTag(cpt);
                                cpt++;
                                tv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        flag=1;
                                        setContentView(R.layout.cours_info);
                                        Cours cours = arrayCours.get((int)v.getTag());

                                        TextView tmp = findViewById(R.id.textView11);
                                        tmp.setText("Sport : "+cours.getSport());

                                        tmp = findViewById(R.id.textView14);
                                        tmp.setText("Nom : "+cours.getNom());

                                        tmp = findViewById(R.id.textView15);
                                        tmp.setText("Date : "+new Date(cours.getDate()).toString());

                                        tmp = findViewById(R.id.textView16);
                                        tmp.setText("Lieu : "+cours.getLieu());

                                        tmp = findViewById(R.id.textView17);
                                        tmp.setText("Nombre de place : "+cours.getNbPlace());

                                        tmp = findViewById(R.id.textView18);
                                        tmp.setVisibility(View.INVISIBLE);

                                        Button btn = findViewById(R.id.supprimerCours);
                                        btn.setVisibility(View.INVISIBLE);

                                    }
                                });
                                lr.addView(tv);
                            }
                        }
                    });

                }catch (Exception e){e.printStackTrace();}
            }
        });
        t1.start();
    }

    public void prochainCoursEleve(View view){
        setContentView(R.layout.prochain_cours_prof);
        MainActivity main = this;
        flag = 1;
        Thread  t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String url = "http://10.0.2.2:8080/inscription/search/findInscriptionsByIduser?iduser="+user.getId()+"\n";
                    URL obj = new URL(url);
                    HttpURLConnection con  =(HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");

                    int responseCode = con.getResponseCode();

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while((inputLine = in.readLine()) != null){
                        response.append(inputLine);
                    }
                    in.close();
                    con.disconnect();

                    ArrayList<Inscription> array = getInscriptionFromString(response.toString());
                    ArrayList<Cours> arrayCours = new ArrayList<Cours>();
                    Date now = new Date();
                    for (Inscription insc: array) {

                        url = "http://10.0.2.2:8080/cours/"+insc.getIdcours()+"\n";
                        obj = new URL(url);
                        con  =(HttpURLConnection) obj.openConnection();
                        con.setRequestMethod("GET");

                        responseCode = con.getResponseCode();

                        in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        response = new StringBuilder();

                        while((inputLine = in.readLine()) != null){
                            response.append(inputLine);
                        }
                        in.close();
                        con.disconnect();

                        Cours cours = getCoursFromString2(response.toString());
                        System.out.println(cours.toString());
                        if (cours.getDate() >= now.getTime()){
                            arrayCours.add(cours);
                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            LinearLayout lr = findViewById(R.id.lr);
                            TextView tv;
                            int cpt = 0;
                            for (Cours cours : arrayCours) {
                                tv = new TextView(main);
                                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) RelativeLayout.LayoutParams.MATCH_PARENT,(int) RelativeLayout.LayoutParams.WRAP_CONTENT);
                                params.leftMargin = 50;
                                params.rightMargin = 50;
                                params.topMargin = 100;

                                tv.setTextSize(15);
                                tv.setText(cours.getSport()+" : "+cours.getNom()+" le "+new Date(cours.getDate()).toString());
                                tv.setPadding(20,50,20,50);
                                tv.setLayoutParams(params);
                                tv.setBackground(getResources().getDrawable(R.drawable.textview_border));
                                tv.setClickable(true);
                                tv.setTag(cpt);
                                cpt++;
                                tv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        flag=1;
                                        setContentView(R.layout.cours_info);
                                        Cours cours = arrayCours.get((int)v.getTag());

                                        TextView tmp = findViewById(R.id.textView11);
                                        tmp.setText("Sport : "+cours.getSport());

                                        tmp = findViewById(R.id.textView14);
                                        tmp.setText("Nom : "+cours.getNom());

                                        tmp = findViewById(R.id.textView15);
                                        tmp.setText("Date : "+new Date(cours.getDate()).toString());

                                        tmp = findViewById(R.id.textView16);
                                        tmp.setText("Lieu : "+cours.getLieu());

                                        tmp = findViewById(R.id.textView17);
                                        tmp.setText("Nombre de place : "+cours.getNbPlace());

                                        tmp = findViewById(R.id.textView18);
                                        tmp.setVisibility(View.INVISIBLE);

                                        Button btn = findViewById(R.id.supprimerCours);
                                        btn.setTag(cours.getId());

                                    }
                                });
                                lr.addView(tv);
                            }
                        }
                    });

                }catch (Exception e){e.printStackTrace();}
            }
        });
        t1.start();
    }


    public void versCatalogue(View view){
        setContentView(R.layout.catalogue);
        MainActivity main = this;
        flag = 1;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String url = "http://10.0.2.2:8080/cours/search/findByDateIsGreaterThanOrderByDateAsc?date="+new Date().getTime()+"\n";
                    URL obj = new URL(url);
                    HttpURLConnection con  =(HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");

                    int responseCode = con.getResponseCode();

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while((inputLine = in.readLine()) != null){
                        response.append(inputLine);
                    }
                    in.close();
                    con.disconnect();

                    url = "http://10.0.2.2:8080/cours/search/findByProfId?id="+user.getId()+"\n";
                    obj = new URL(url);
                    con  =(HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");

                    responseCode = con.getResponseCode();
                    in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder response2 = new StringBuilder();

                    while((inputLine = in.readLine()) != null){
                        response2.append(inputLine);
                    }
                    in.close();
                    con.disconnect();

                    ArrayList<Cours> array = getCoursFromString(response2.toString());
                    ArrayList<Cours> arrayCours = getCoursFromString(response.toString());
                    ArrayList<Cours> arrayC2 = new ArrayList<Cours>();



                    for (Cours cours : array){
                        for (Cours cours1 : arrayCours){
                            if (cours.getId() == cours1.getId()){
                                arrayC2.add(cours1);
                            }
                        }
                    }

                    for (Cours cours : arrayC2){
                        arrayCours.remove(cours);
                    }

                    arrayC2.clear();

                    for (Cours cours : arrayCours){
                        url = "http://10.0.2.2:8080/inscription/search/countInscriptionByIdcours?idcours="+cours.getId()+"\n";
                        obj = new URL(url);
                        con  =(HttpURLConnection) obj.openConnection();
                        con.setRequestMethod("GET");

                        responseCode = con.getResponseCode();

                        in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        response = new StringBuilder();

                        while((inputLine = in.readLine()) != null){
                            response.append(inputLine);
                        }
                        in.close();
                        con.disconnect();
                        if (Integer.valueOf(response.toString().toString()) == cours.getNbPlace()){
                            System.out.println("Je supprime 1 "+cours.toString());
                            arrayC2.add(cours);
                        }else{
                            url = "http://10.0.2.2:8080/inscription/search/countInscriptionByIdcoursAndIduser?idcours="+cours.getId()+"&iduser="+user.getId()+"\n";
                            obj = new URL(url);
                            con  =(HttpURLConnection) obj.openConnection();
                            con.setRequestMethod("GET");

                            responseCode = con.getResponseCode();

                            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            response = new StringBuilder();

                            while((inputLine = in.readLine()) != null){
                                response.append(inputLine);
                            }
                            in.close();
                            con.disconnect();
                            if (Integer.valueOf(response.toString()) == 1){
                                System.out.println("Je supprime 2 "+cours.toString());
                                arrayC2.add(cours);
                            }
                        }
                    }
                    for (Cours cours : arrayC2){
                        arrayCours.remove(cours);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            LinearLayout lr = findViewById(R.id.lrCat);
                            TextView tv;
                            for (Cours cours : arrayCours) {
                                tv = new TextView(main);
                                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) RelativeLayout.LayoutParams.MATCH_PARENT,(int) RelativeLayout.LayoutParams.WRAP_CONTENT);
                                params.leftMargin = 50;
                                params.rightMargin = 50;
                                params.topMargin = 100;

                                tv.setTextSize(15);
                                tv.setText(cours.getSport()+" : "+cours.getNom()+" le "+new Date(cours.getDate()).toString());
                                tv.setPadding(20,50,20,50);
                                tv.setLayoutParams(params);
                                tv.setBackground(getResources().getDrawable(R.drawable.textview_border));
                                tv.setClickable(true);
                                tv.setTag(cours.getId());
                                tv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        flag=1;
                                        setContentView(R.layout.inscription_cours);
                                        Cours cours = null;
                                        for (int i = 0; i < arrayCours.size(); i++) {
                                            if (arrayCours.get(i).getId() == (int)v.getTag()){
                                                cours = arrayCours.get(i);
                                                break;
                                            }
                                        }


                                        TextView tmp = findViewById(R.id.textView110);
                                        tmp.setText("Sport : "+cours.getSport());

                                        tmp = findViewById(R.id.textView140);
                                        tmp.setText("Nom : "+cours.getNom());

                                        tmp = findViewById(R.id.textView150);
                                        tmp.setText("Date : "+new Date(cours.getDate()).toString());

                                        tmp = findViewById(R.id.textView160);
                                        tmp.setText("Lieu : "+cours.getLieu());

                                        tmp = findViewById(R.id.textView170);
                                        tmp.setText("Nombre de place : "+cours.getNbPlace());

                                        Button btn = findViewById(R.id.inscription);
                                        btn.setTag((int)v.getTag());
                                        btn.setClickable(true);
                                        btn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Thread t1 = new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {

                                                            String url = "http://10.0.2.2:8080/home/ajout/"+(int)v.getTag()+"/"+user.getId()+"/\n";
                                                            System.out.println(url);
                                                            URL obj = new URL(url);
                                                            HttpURLConnection con  =(HttpURLConnection) obj.openConnection();
                                                            con.setRequestMethod("GET");

                                                            int responseCode = con.getResponseCode();

                                                            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                                                            String inputLine;
                                                            StringBuilder response = new StringBuilder();

                                                            while((inputLine = in.readLine()) != null){
                                                                response.append(inputLine);
                                                            }
                                                            in.close();
                                                            con.disconnect();
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    setContentView(R.layout.eleve);
                                                                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"L'inscription est validée !",Snackbar.LENGTH_SHORT);
                                                                    snackbar.show();
                                                                }
                                                            });
                                                        }catch (Exception e){
                                                            e.printStackTrace();

                                                        }
                                                    }
                                                });
                                                t1.start();


                                            }
                                        });

                                    }
                                });
                                lr.addView(tv);
                            }
                        }
                    });




                }catch (Exception e){}
            }



        });
        thread.start();
    }



    public void supprimerCours(View view){

        if (flag == 1){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        String url = "http://10.0.2.2:8080/home/remove/"+(int)view.getTag()+"/"+user.getId()+"/\n";
                        System.out.println(url);
                        URL obj = new URL(url);
                        HttpURLConnection con  =(HttpURLConnection) obj.openConnection();
                        con.setRequestMethod("GET");

                        int responseCode = con.getResponseCode();

                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        StringBuilder response = new StringBuilder();

                        while((inputLine = in.readLine()) != null){
                            response.append(inputLine);
                        }
                        in.close();
                        con.disconnect();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setContentView(R.layout.eleve);
                                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Inscription annulée !",Snackbar.LENGTH_SHORT);
                                snackbar.show();
                            }
                        });
                    }catch (Exception e){
                    }
                }
            });
            thread.start();
        }else{
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String url = "http://10.0.2.2:8080/inscription/search/findInscriptionsByIdcours?idcours="+(int)view.getTag()+"\n";
                        System.out.println(url);
                        URL obj = new URL(url);
                        HttpURLConnection con  =(HttpURLConnection) obj.openConnection();
                        con.setRequestMethod("GET");

                        int responseCode = con.getResponseCode();

                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        StringBuilder response = new StringBuilder();

                        while((inputLine = in.readLine()) != null){
                            response.append(inputLine);
                        }
                        in.close();
                        con.disconnect();

                        ArrayList<Inscription> arrayInsc = getInscriptionFromString(response.toString());

                        for (Inscription insc : arrayInsc){
                            url = "http://10.0.2.2:8080/home/remove/"+(int)view.getTag()+"/"+insc.getIduser()+"/\n";
                            obj = new URL(url);
                            con  =(HttpURLConnection) obj.openConnection();
                            con.setRequestMethod("GET");

                            responseCode = con.getResponseCode();

                            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            response = new StringBuilder();

                            while((inputLine = in.readLine()) != null){
                                response.append(inputLine);
                            }
                            in.close();
                            con.disconnect();
                        }

                        url = "http://10.0.2.2:8080/home/remove/"+(int)view.getTag()+"/\n";
                        obj = new URL(url);
                        con  =(HttpURLConnection) obj.openConnection();
                        con.setRequestMethod("GET");

                        responseCode = con.getResponseCode();

                        in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        response = new StringBuilder();

                        while((inputLine = in.readLine()) != null){
                            response.append(inputLine);
                        }
                        in.close();
                        con.disconnect();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setContentView(R.layout.professeur);
                                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Le cours a bien été supprimé !",Snackbar.LENGTH_SHORT);
                                snackbar.show();
                            }
                        });


                    }catch (Exception e){
                    }
                }
            });
            thread.start();
        }

    }

    // pour accéder au local host du serveur web
    // http://10.0.2.2:8080/home
}