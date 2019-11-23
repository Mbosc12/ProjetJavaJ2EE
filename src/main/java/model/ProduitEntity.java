package model;

public class ProduitEntity {

    String nom;
    Float prix;

    public ProduitEntity(String nom, Float prix) {
        this.nom = nom;
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public Float getPrix() {
        return prix;
    }
}
