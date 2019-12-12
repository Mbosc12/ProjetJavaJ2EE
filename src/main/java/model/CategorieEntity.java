/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

public class CategorieEntity {
    
    String libelle;
    String description;
    int code;
    
    public CategorieEntity(String libelle, String description, int code) {
        this.libelle = libelle;
        this.description = description;
        this.code = code;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getCode() {
        return code;
    }
}
