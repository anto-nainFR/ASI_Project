package df.spring.helloserver;

import jakarta.persistence.*;

import java.util.GregorianCalendar;

@Entity
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private GregorianCalendar date;
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

    public Cours(int id, GregorianCalendar date, String nom, String sport, String lieu, int nbPlace, Utilisateur prof) {
        this.id = id;
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

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
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
        return "Cours{" +
                "id=" + id +
                ", date=" + date.getTime() +
                ", nom='" + nom + '\'' +
                ", sport='" + sport + '\'' +
                ", lieu='" + lieu + '\'' +
                ", nbPlace=" + nbPlace +
                ", prof=" + prof.toString() +
                '}';
    }

}


