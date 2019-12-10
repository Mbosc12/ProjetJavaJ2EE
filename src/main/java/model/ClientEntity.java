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
public class ClientEntity {
    
    String code;
    String societe;
    String contact;
    String fonction;
    String adresse;
    String ville;
    String region;
    String code_postal;
    String pays;
    String telephone;
    String fax;
    
    public ClientEntity(String code, String societe, String contact, 
            String fonction, String adresse, String ville, String region,
            String code_postal, String pays, String telephone, String fax) {
        
        this.code = code;
        this.societe = societe;
        this.contact = contact;
        this.fonction = fonction;
        this.adresse = adresse;
        this.ville = ville;
        this.region = region;
        this.code_postal = code_postal;
        this.pays = pays;
        this.telephone = telephone;
        this.fax = fax;
        
    }
    
    public String getCode() {
        return code;
    }
    
    public String getSociete() {
        return societe;
    }
    
    public String getContact() {
        return contact;
    }
    
    public String getFonction() {
        return fonction;
    }
    
    public String getAdresse() {
        return adresse;
    }
    
    public String getVille() {
        return ville;
    }
    
    public String getRegion() {
        return region;
    }
    
    public String getCodePostal() {
        return code_postal;
    }
    
    public String getPays() {
        return pays;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public String getFax() {
        return fax;
    }
}
