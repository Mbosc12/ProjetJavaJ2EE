package model;

public class ProduitEntity {

    int reference;
    String nom;
    double prix;

    public ProduitEntity(int reference, String nom, Double prix) {
        this.reference = reference;
        this.nom = nom;
        this.prix = prix;
    }

    public int getReference() {
        return reference;
    }
    
    public String getNom() {
        return nom;
    }

    public Double getPrix() {
        return prix;
    }
}
