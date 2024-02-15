package com.example.client;

import android.icu.util.LocaleData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Utilisateur {

    private int id;
    private String nom;
    private String prenom;
    private GregorianCalendar birthday;
    private String adresse;
    private String mail;
    private String password;
    private Boolean isProfessor;

    public Utilisateur() {
    }

    public Utilisateur(int id, String nom, String prenom, GregorianCalendar birthday, String adresse, String mail, String password, Boolean isProfessor) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.birthday = birthday;
        this.adresse = adresse;
        this.mail = mail;
        this.password = password;
        this.isProfessor = isProfessor;
    }



    // ======================================= TEMPORAIRE ===========================================
    public Utilisateur(String chaine) {
        String string = chaine;

        // nom
            int index = string.indexOf("nom") + "nom".length() + 1;
            string = string.substring(index);
            index = string.indexOf("\"") + 1;
            string = string.substring(index);
            index = string.indexOf("\"");
            nom = string.substring(0,index);
        // prénom
            index = string.indexOf("prenom") + "prenom".length() + 1;
            string = string.substring(index);
            index = string.indexOf("\"") + 1;
            string = string.substring(index);
            index = string.indexOf("\"");
            prenom = string.substring(0,index);
        //birthday
            index = string.indexOf("birthday") + "birthday".length() + 1;
            string = string.substring(index);
            index = string.indexOf("\"") + 1;
            string = string.substring(index);
            index = string.indexOf("\"");
            String tmp = string.substring(0,index);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX",Locale.FRANCE);
            try {
                Date date = sdf.parse(tmp);
                birthday = new GregorianCalendar();
                assert date != null;
                birthday.setTime(date);
            }catch (ParseException e){
                e.printStackTrace();
            }

        //adresse
            index = string.indexOf("adresse") + "adresse".length() + 1;
            string = string.substring(index);
            index = string.indexOf("\"") + 1;
            string = string.substring(index);
            index = string.indexOf("\"");
            adresse = string.substring(0,index);
        //mail
            index = string.indexOf("mail") + "mail".length() + 1;
            string = string.substring(index);
            index = string.indexOf("\"") + 1;
            string = string.substring(index);
            index = string.indexOf("\"");
            mail = string.substring(0,index);
        //password
            index = string.indexOf("password") + "password".length() + 1;
            string = string.substring(index);
            index = string.indexOf("\"") + 1;
            string = string.substring(index);
            index = string.indexOf("\"");
            password = string.substring(0,index);
        //isProfessor
            index = string.indexOf("professor") + ("professor").length() + 4;
            string = string.substring(index);
            index = string.indexOf(",");

            tmp = string.substring(0,index);
            if (tmp.equals("true")) isProfessor = true;
            else isProfessor = false;
        //id
            index = string.indexOf("http://10.0.2.2:8080/utilisateurs/") + ("http://10.0.2.2:8080/utilisateurs/").length();
            string = string.substring(index);
            index = string.indexOf("\"");
            tmp = string.substring(0,index);
            id = Integer.valueOf(tmp);



        System.out.println("\n"+string+"\n\n");
        this.toString();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public GregorianCalendar getBirthday() {
        return birthday;
    }

    public void setBirthday(GregorianCalendar birthday) {
        this.birthday = birthday;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getProfessor() {
        return isProfessor;
    }

    public void setProfessor(Boolean professor) {
        isProfessor = professor;
    }

    public String toString() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.FRANCE);

        return "Utilisateur : " + id +
                " " + nom +
                " " + prenom +
                " " + sdf.format(birthday.getTime()) +
                " " + adresse +
                " " + mail +
                " " + password +
                " " + isProfessor;
    }


}