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
public class CommandeEntity {
    
    int numero;
    String client;
    String saise_le;
    String envoye_le;
    String port;
    String destinataire;
    String adresse_livraison;
    String ville_livraison;
    String region_livraison;
    String code_postal_livrais;
    String pays_livraison;
    String remise;
    
    public CommandeEntity(int numero, String client, String destinataire,
            String adresse_livraison, String ville_livraison, 
            String region_livraison, String code_postal_livrais,
            String pays_livraison) {

        this.numero = numero;
        this.client = client;
        this.destinataire = destinataire;
        this.adresse_livraison = adresse_livraison;
        this.ville_livraison = ville_livraison;
        this.region_livraison = region_livraison;
        this.code_postal_livrais = code_postal_livrais;
        this.pays_livraison = pays_livraison;
    }
    
    public int getNumero() {
        return numero;
    }
    
    public String getClient() {
        return client;
    }
    
    public String getDestinaire() {
        return destinataire;
    }
    
    public String getAdresseLivraison() {
        return adresse_livraison;
    }
    
    public String getVilleLivraison() {
        return ville_livraison;
    }
    
    public String getRegionLivraisson() {
        return region_livraison;
    }
    
    public String getCodePostalLivrais() {
        return code_postal_livrais;
    }
    
    public String getPaysLivraison() {
        return pays_livraison;
    }
    
}
