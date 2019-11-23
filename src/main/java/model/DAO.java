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
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
     * @throws SQLException
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

        } catch (SQLException var37) {
            Logger.getLogger("DAO").log(Level.SEVERE, (String)null, var37);
            throw new SQLException(var37.getMessage());
        }

        return result;
    }

    /**
     *
     * @param enterpriseName Nom de l'entreprise
     * @return Liste des articles vendus par l'entreprise
     * @throws SQLException
     */
    public List<String> itemSold(String enterpriseName) throws SQLException {
        List<String> result = new LinkedList<>();
        String sql = "SELECT DISTINCT REFERENCE, NOM " +
                "FROM APP.PRODUIT, APP.LIGNE, APP.COMMANDE " +
                "WHERE APP.PRODUIT.REFERENCE=APP.LIGNE.PRODUIT " +
                "AND APP.LIGNE.COMMANDE=APP.COMMANDE.NUMERO " +
                "AND APP.COMMANDE.CLIENT=?";

        try (Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
             PreparedStatement stmt = connection.prepareStatement(sql); // On crée un statement pour exécuter une requête
        ) {
            stmt.setString(1, enterpriseName);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                String nom = rs.getString("NOM");
                result.add(nom);
            }

        } catch (SQLException var37) {
            Logger.getLogger("DAO").log(Level.SEVERE, (String)null, var37);
            throw new SQLException(var37.getMessage());
        }

        return result;
    }

}


