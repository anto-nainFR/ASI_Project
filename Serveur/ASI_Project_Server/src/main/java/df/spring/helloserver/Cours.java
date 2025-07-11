package df.spring.helloserver;

import jakarta.persistence.*;
import java.util.Date;


@Entity
public class Cours {

    private static int cpt = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private long date;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String sport;
    @Column(nullable = false)
    private String lieu;
    @Column(nullable = false)
    private int nbPlace;
    @ManyToOne(optional = false)
    private Utilisateur prof;

    public Cours(){}

    public Cours(int id, long date, String nom, String sport, String lieu, int nbPlace, Utilisateur prof) {
        this.id = id;
        this.date = date;
        this.nom = nom;
        this.sport = sport;
        this.lieu = lieu;
        this.nbPlace = nbPlace;
        this.prof = prof;
    }
    public Cours(long date, String nom, String sport, String lieu, int nbPlace, Utilisateur prof) {
        this.id = cpt++;
        this.date = date;
        this.nom = nom;
        this.sport = sport;
        this.lieu = lieu;
        this.nbPlace = nbPlace;
        this.prof = prof;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public int getNbPlace() {
        return nbPlace;
    }

    public void setNbPlace(int nbPlace) {
        this.nbPlace = nbPlace;
    }

    public Utilisateur getProf() {
        return prof;
    }

    public void setProf(Utilisateur prof) {
        this.prof = prof;
    }

    @Override
    public String toString() {
        Date d = new Date(date);
        return "Cours{" +
                "id=" + id +
                ", date=" + d.toString() +
                ", nom='" + nom + '\'' +
                ", sport='" + sport + '\'' +
                ", lieu='" + lieu + '\'' +
                ", nbPlace=" + nbPlace +
                ", prof=" + prof.toString() +
                '}';
    }

}


