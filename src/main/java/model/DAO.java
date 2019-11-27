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

public class DAO {

    private final DataSource myDataSource;

    /**
     * Construit le DAO avec sa source de données
     *
     * @param dataSource la source de données à utiliser
     */
    public DAO(DataSource dataSource) {
        this.myDataSource = dataSource;
    }

    /**
     * @return Liste des catégories
     */
    public List<CategorieEntity> allCategories() throws SQLException {

        List<CategorieEntity> result = new LinkedList<>();

        String sql = "SELECT * FROM APP.CATEGORIE";

        try (Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
             Statement stmt = connection.createStatement(); // On crée un statement pour exécuter une requête
             ResultSet rs = stmt.executeQuery(sql) // Un ResultSet pour parcourir les enregistrements du résultat{
        ) {
            while (rs.next()) {
                String lib = rs.getString("LIBELLE");
                String desc = rs.getString("DESCRIPTION");
                CategorieEntity categorie = new CategorieEntity(lib, desc);
                result.add(categorie);
            }

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, error);
            throw new SQLException(error.getMessage());
        }

        return result;
    }

    /**
     *
     * @param enterpriseName Nom de l'entreprise
     * @return Liste des articles vendus par l'entreprise
     */
    public List<ProduitEntity> itemSold(String enterpriseName) throws SQLException {
        List<ProduitEntity> result = new LinkedList<>();
        String sql = "SELECT DISTINCT PRIX_UNITAIRE, NOM " +
                "FROM APP.PRODUIT, APP.LIGNE, APP.COMMANDE " +
                "WHERE APP.PRODUIT.REFERENCE=APP.LIGNE.PRODUIT " +
                "AND APP.LIGNE.COMMANDE=APP.COMMANDE.NUMERO " +
                "AND APP.COMMANDE.CLIENT=?";

        try (Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
             PreparedStatement stmt = connection.prepareStatement(sql) // On crée un statement pour exécuter une requête
        ) {
            stmt.setString(1, enterpriseName);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                String nom = rs.getString("NOM");
                Float prix = rs.getFloat("PRIX_UNITAIRE");
                ProduitEntity produit = new ProduitEntity(nom, prix);
                result.add(produit);
            }

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, error);
            throw new SQLException(error.getMessage());
        }

        return result;
    }

    /**
     *
     * @param Client Nom du visiteur qui souhaite se connecter
     * @return Mot de passe du client
     * @throws SQLException
     */
    public String getPassword(String Client) throws SQLException {

        String result = "";
        String sql = "SELECT CODE FROM APP.CLIENT WHERE APP.CLIENT.CONTACT = ?";

        try (Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
             PreparedStatement stmt = connection.prepareStatement(sql); // On crée un statement pour exécuter une requête
        ) {
            stmt.setString(1, Client);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                result = rs.getString("CODE");
            }

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, (String)null, error);
            throw new SQLException(error.getMessage());
        }

        return result;
    }

    public void editPersonalData() throws SQLException {

    }

    public void addPurchaseOrders() throws SQLException {

    }

    public void editPurchaseOrders() throws SQLException {

    }

    public void deletePurchaseOrders() throws SQLException {

    }

    /**
     *
     * @param panier Panier du client
     * @param article Article à ajouter au panier
     * @param quantite Quantité de l'article à ajouter
     */
    public void addToCart(HashMap<String, Integer> panier, String article, int quantite) {
        panier.put(article, quantite);
    }

    /**
     *
     * @param panier Panier du client
     * @param article Article à supprimer du panier
     */
    public void deleteFromCart(HashMap<String, Integer> panier, String article) {
        panier.remove(article);
    }

    /**
     *
     * @param panier Panier du client
     * @param article Article dont il faut changer la quantité
     * @param newQuantite La nouvelle quantité qui va remplacer la précédente
     * @throws SQLException
     */
    public void editQuantityOrdered(HashMap<String, Integer> panier, String article, int newQuantite) throws SQLException {
        panier.put(article, newQuantite);
    }

    public List<String> confirmCart(HashMap<String, Integer> panier, String client) throws SQLException {

        List<String> result = new LinkedList<>();

        String infoClient = "SELECT CODE, SOCIETE, ADRESSE, VILLE, REGION, CODE_POSTAL, PAYS\n" +
                "FROM APP.CLIENT\n" +
                "WHERE APP.CLIENT.CONTACT = ?";
        String ajoutCommande = "INSERT INTO APP.COMMANDE (CLIENT, DESTINATAIRE, ADRESSE_LIVRAISON, VILLE_LIVRAISON, " +
                "REGION_LIVRAISON, CODE_POSTAL_LIVRAIS, PAYS_LIVRAISON)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
             PreparedStatement stmt = connection.prepareStatement(infoClient, Statement.RETURN_GENERATED_KEYS) // On crée un statement pour exécuter une requête
        ) {
            stmt.setString(1, client);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                String codeClient = rs.getString("CODE");
                String destinataire = rs.getString("SOCIETE");
                String adresseLivraison = rs.getString("ADRESSE");
                String villeLivraison = rs.getString("VILLE");
                String region = rs.getString("REGION");
                String codePostal = rs.getString("CODE_POSTAL");
                String paysLivraison = rs.getString("PAYS");

                result.add(codeClient);
                result.add(destinataire);
                result.add(adresseLivraison);
                result.add(villeLivraison);
                result.add(region);
                result.add(codePostal);
                result.add(paysLivraison);
            }

            // Génération du numéro de commande
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            generatedKeys.next();
            int numeroCommande = generatedKeys.getInt("NUMERO");

            // Créer les commandes
            try (PreparedStatement addCommand = connection.prepareStatement(ajoutCommande)) {
                for(int i = 0; i<result.size(); i++) {
                    addCommand.clearParameters();
                    addCommand.setString(1, result.get(0));
                    addCommand.setString(2, result.get(1));
                    addCommand.setString(3, result.get(2));
                    addCommand.setString(4, result.get(3));
                    addCommand.setString(5, result.get(4));
                    addCommand.setString(6, result.get(5));
                    addCommand.setString(7, result.get(6));

                    int n = addCommand.executeUpdate();
                }
            }


        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, error);
            throw new SQLException(error.getMessage());
        }
        return result;
    }

    public static void main(String[] args) throws SQLException {
        DAO dao = new DAO(DataSourceFactory.getDataSource());

        // HashMap<Integer, Categorie> result = dao.allCategories();
        // List<ProduitEntity> result = dao.itemSold("VINET");


        HashMap<String, Integer> panier = new HashMap<>();

        List<String> result = dao.confirmCart(panier, "Maria Anders");
        for (String s : result) {
            System.out.println(s);
        }
        /*dao.addToCart(panier, "Chang", 10);
        dao.addToCart(panier, "Ikura", 5);
        System.out.println(panier);
        dao.deleteFromCart(panier, "Chang");
        System.out.println(panier);
        dao.editQuantityOrdered(panier, "Ikura", 10);
        System.out.println(panier);*/
    }

}


