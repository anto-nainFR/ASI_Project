package df.spring.helloserver;

import jakarta.persistence.*;

@Entity
public class Inscription {

    private static int cpt = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private Integer iduser;

    @Column(nullable = false)
    private Integer idcours;

    public Inscription() {}

    public Inscription(int id, Integer iduser, Integer idcours) {
        this.id = id;
        this.iduser = iduser;
        this.idcours = idcours;
        cpt++;
    }

    public Inscription(Integer iduser, Integer idcours) {
        this.id = cpt++;
        this.iduser = iduser;
        this.idcours = idcours;
        cpt++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIduser() {
        return iduser;
    }

    public void setIduser(Integer iduser) {
        this.iduser = iduser;
    }

    public Integer getIdcours() {
        return idcours;
    }

    public void setIdcours(Integer idcours) {
        this.idcours = idcours;
    }

    @Override
    public String toString() {
        return "Inscription{" +
                "id=" + id +
                ", iduser=" + iduser +
                ", idcours='" + idcours + '\'' +
                '}';
    }
}
