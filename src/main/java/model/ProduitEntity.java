package model;

public class ProduitEntity {

    int reference;
    String nom;
    int fournisseur;
    int categorie;
    String quantite_par_unite;
    double prix_unitaire;
    int unites_en_stock;
    int unites_commandees;
    int niveau_de_reappro;
    int indisponible;

    public ProduitEntity(int reference, String nom, int fournisseur, 
            int categorie, String quantite_par_unite, double prix_unitaire, 
            int unites_en_stock, int unites_commandees, int niveau_de_reappro,
            int indisponible) {
        this.reference = reference;
        this.nom = nom;
        this.fournisseur = fournisseur;
        this.categorie = categorie;
        this.quantite_par_unite = quantite_par_unite;
        this.prix_unitaire = prix_unitaire;
        this.unites_en_stock = unites_en_stock;
        this.unites_commandees = unites_commandees;
        this.niveau_de_reappro = niveau_de_reappro;
        this.indisponible = indisponible;
    }

    public int getReference() {
        return reference;
    }
    
    public String getNom() {
        return nom;
    }
    
    public int getFournisseur() {
        return fournisseur;
    }
    
    public int getCategorie() {
        return categorie;
    }
    
    public String getQuantiteParUnite() {
        return quantite_par_unite;
    }

    public double getPrixUnitaire() {
        return prix_unitaire;
    }
    
    public int getUnitesEnStock() {
        return unites_en_stock;
    }
    
    public int getUnitesCommandees() {
        return unites_commandees;
    }
    
    public int getNiveauDeReappro() {
        return niveau_de_reappro;
    }
    
    public int getIndisponible() {
        return indisponible;
    }
}
