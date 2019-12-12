package model;

public class ProduitEntity {

    int reference;
    String nom;
    double prix;
    int stock;

    public ProduitEntity(int reference, String nom, Double prix, int stock) {
        this.reference = reference;
        this.nom = nom;
        this.prix = prix;
        this.stock = stock;
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
    
    public int getStock() {
        return stock;
    }
}
