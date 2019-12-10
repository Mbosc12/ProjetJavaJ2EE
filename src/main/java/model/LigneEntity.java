/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author lauriecoumes
 */
public class LigneEntity {
    
    int commande;
    int produit;
    int quantite;
    
    public LigneEntity(int commande, int produit, int quantite) {
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
    }
    
    public int getCommande() {
        return commande;
    }
    
    public int getProduit() {
        return produit;
    }
    
    public int getQuantite() {
        return quantite;
    }
    
}
