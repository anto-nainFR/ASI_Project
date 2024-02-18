package com.example.client;

public class Inscription {
    private int id;
    private int iduser;
    private int idcours;

    public Inscription(int id, int iduser, int idcours) {
        this.id = id;
        this.iduser = iduser;
        this.idcours = idcours;
    }

    public Inscription(int iduser, int idcours) {
        this.iduser = iduser;
        this.idcours = idcours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getIdcours() {
        return idcours;
    }

    public void setIdcours(int idcours) {
        this.idcours = idcours;
    }

    @Override
    public String toString() {
        return "Inscription{" +
                "id=" + id +
                ", iduser=" + iduser +
                ", idcours=" + idcours +
                '}';
    }
}
