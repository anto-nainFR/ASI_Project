package df.spring.helloserver;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Utilisateur {
    private static int cpt = 3;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String prenom;
    @Column(nullable = false)
    private GregorianCalendar birthday;
    @Column(nullable = false)
    private String adresse;
    @Column(nullable = false)
    private String mail;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
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

    public Utilisateur(String nom, String prenom, GregorianCalendar birthday, String adresse, String mail, String password, Boolean isProfessor) {
        this.id = cpt++;
        this.nom = nom;
        this.prenom = prenom;
        this.birthday = birthday;
        this.adresse = adresse;
        this.mail = mail;
        this.password = password;
        this.isProfessor = isProfessor;
    }


    public String toString() {
        return "Utilisateur : " + id +
                " " + nom +
                " " + prenom +
                " " + birthday.getTime() +
                " " + adresse +
                " " + mail +
                " " + password +
                " " + isProfessor;
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
}

