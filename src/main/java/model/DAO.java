/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.sql.DataSource;

/**
 *
 * @author pedago
 */
public class DAO {

    private final DataSource myDataSource;

    /**
     * Construit le AO avec sa source de données
     *
     * @param dataSource la source de données à utiliser
     */
    public DAO(DataSource dataSource) {
        this.myDataSource = dataSource;
    }

    /**
     * @return Liste des catégories
     * @throws SQLException renvoyées par JDBC
     */
    public HashMap<Integer, Categorie> allCategories() throws SQLException {

        HashMap<Integer, Categorie> result = new HashMap<Integer, Categorie>();

        String sql = "SELECT * FROM APP.CATEGORIE";
        
        try (Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
                Statement stmt = connection.createStatement(); // On crée un statement pour exécuter une requête
                ResultSet rs = stmt.executeQuery(sql) // Un ResultSet pour parcourir les enregistrements du résultat{
                ) {
            while (rs.next()) {
                String lib = rs.getString("LIBELLE");
                String desc = rs.getString("DESCRIPTION");
                int code = rs.getInt("CODE");
                Categorie c = new Categorie(lib, desc);
                result.put(code, c);
            }
        }
        return result;
    }
    
    public static void main(String[] args) {
        DAO dao = new DAO(DataSourceFactory.getDataSource());
    }

}


