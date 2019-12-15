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
     * @throws SQLException
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
                int code = rs.getInt("CODE");
                CategorieEntity categorie = new CategorieEntity(lib, desc, code);
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
     * @return Liste des produits
     * @throws SQLException
     */
    public List<ProduitEntity> allProducts() throws SQLException {

        List<ProduitEntity> result = new LinkedList<>();

        String sql = "SELECT * FROM APP.PRODUIT";

        try (Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
                Statement stmt = connection.createStatement(); // On crée un statement pour exécuter une requête
                ResultSet rs = stmt.executeQuery(sql) // Un ResultSet pour parcourir les enregistrements du résultat{
                ) {
            while (rs.next()) {
                int reference = rs.getInt("REFERENCE");
                String nom = rs.getString("NOM");
                double prix = rs.getFloat("PRIX_UNITAIRE");
                int stock = rs.getInt("Unites_en_stock");

                ProduitEntity product = new ProduitEntity(reference, nom, prix, stock);
                result.add(product);
            }

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, error);
            throw new SQLException(error.getMessage());
        }

        return result;
    }

    public List<ProduitEntity> ProductByCategorie(String code) throws SQLException {
        List<ProduitEntity> result = new LinkedList<>();
        String sql = "SELECT * FROM APP.PRODUIT WHERE CATEGORIE = ?";

        try (Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
                PreparedStatement stmt = connection.prepareStatement(sql)) { // On crée un statement pour exécuter une requête
            stmt.setInt(1, Integer.parseInt(code));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int reference = rs.getInt("REFERENCE");
                    String nom = rs.getString("NOM");
                    double prix = rs.getFloat("PRIX_UNITAIRE");
                    int stock = rs.getInt("Unites_en_stock");

                    ProduitEntity product = new ProduitEntity(reference, nom, prix, stock);
                    result.add(product);
                }
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

        String result = "null";
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
            Logger.getLogger("DAO").log(Level.SEVERE, (String) null, error);
            throw new SQLException(error.getMessage());
        }

        return result;
    }

    /**
     *
     * @param nom Nom du client
     * @return Entité client avec toutes ses informations
     * @throws SQLException
     */
    public String getCodeByName(String nom) throws SQLException {
        String result = "";

        String sql = "SELECT CODE FROM APP.CLIENT WHERE CONTACT = ?";

        try (Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
                PreparedStatement stmt = connection.prepareStatement(sql) // On crée un statement pour exécuter une requête
                ) {
            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                result = rs.getString("CODE");
            }

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, error);
            throw new SQLException(error.getMessage());
        }

        return result;
    }

    public List<ClientEntity> showClient(String code) throws SQLException {

        List<ClientEntity> client = new LinkedList();
        String sql = "SELECT * FROM APP.CLIENT WHERE CODE = ?";

        try (Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
                PreparedStatement stmt = connection.prepareStatement(sql) // On crée un statement pour exécuter une requête
                ) {
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nom = rs.getString("CODE");
                String societe = rs.getString("SOCIETE");
                String contact = rs.getString("CONTACT");
                String fonction = rs.getString("FONCTION");
                String adresse = rs.getString("ADRESSE");
                String ville = rs.getString("VILLE");
                String region = rs.getString("REGION");
                String code_postal = rs.getString("CODE_POSTAL");
                String pays = rs.getString("PAYS");
                String telephone = rs.getString("TELEPHONE");
                String fax = rs.getString("FAX");
                client.add(new ClientEntity(nom, societe, contact,
                        fonction, adresse, ville, region, code_postal, pays,
                        telephone, fax));
            }

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, error);
            throw new SQLException(error.getMessage());
        }

        return client;
    }

    /**
     * Edite les données personnels d'un client
     *
     * @param code Code du client
     * @param societe Société du client
     * @param contact Nom et prénom du client
     * @param fonction Métier du client
     * @param adresse Adresse du client
     * @param ville Ville du client
     * @param region Région du client
     * @param code_postal Code postal du client
     * @param pays Pays du client
     * @param telephone Téléphone du client
     * @param fax Fax du client
     * @throws SQLException
     */
    public void editClientPersonalData(String code, String societe,
            String contact, String fonction, String adresse, String ville,
            String region, String code_postal, String pays, String telephone,
            String fax)
            throws SQLException {

        String sql = "UPDATE APP.CLIENT SET SOCIETE = ?, CONTACT = ?, "
                + "FONCTION = ?, ADRESSE = ?, VILLE = ?, REGION = ?,"
                + "CODE_POSTAL = ?, PAYS = ?, TELEPHONE = ?, FAX = ?"
                + "WHERE CODE = ?";

        try (Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
                PreparedStatement stmt = connection.prepareStatement(sql) // On crée un statement pour exécuter une requête
                ) {
            stmt.setString(1, societe);
            stmt.setString(2, contact);
            stmt.setString(3, fonction);
            stmt.setString(4, adresse);
            stmt.setString(5, ville);
            stmt.setString(6, region);
            stmt.setString(7, code_postal);
            stmt.setString(8, pays);
            stmt.setString(9, telephone);
            stmt.setString(10, fax);
            stmt.setString(11, code);

            stmt.executeUpdate();

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, error);
            throw new SQLException(error.getMessage());
        }
    }

    /**
     *
     * Ajoute une commande dans la table COMMANDE et les lignes correspondantes
     * dans la table LIGNE
     *
     * @param client Client passant la commande
     * @param destinataire Societe du client
     * @param adresse_livraison Adresse de livraison du client
     * @param ville_livraison Ville de livraison du client
     * @param region_livraison Region de livraison du client
     * @param code_postal_livrais Code postale de livraison du client
     * @param pays_livraison Pays de livraison du client
     * @param produitID Produits à ajouter à la table LIGNE
     * @param quantite Quantite des articles du panier
     * @throws SQLException
     */
    public void confirmCart(String client, String destinataire,
            String adresse_livraison, String ville_livraison,
            String region_livraison, String code_postal_livrais,
            String pays_livraison, int[] produitID, int[] quantite)
            throws SQLException {

        String ajoutCommande = "INSERT INTO APP.COMMANDE (CLIENT, "
                + "DESTINATAIRE, ADRESSE_LIVRAISON, VILLE_LIVRAISON,"
                + "REGION_LIVRAISON, CODE_POSTAL_LIVRAIS, PAYS_LIVRAISON)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String ajoutLigne = "INSERT INTO APP.LIGNE VALUES (?, ?, ?)";
        try (Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
                PreparedStatement stmt = connection.prepareStatement(ajoutCommande, Statement.RETURN_GENERATED_KEYS) // On crée un statement pour exécuter une requête
                ) {
            connection.setAutoCommit(false);
            stmt.setString(1, client);
            stmt.setString(2, destinataire);
            stmt.setString(3, adresse_livraison);
            stmt.setString(4, ville_livraison);
            stmt.setString(5, region_livraison);
            stmt.setString(6, code_postal_livrais);
            stmt.setString(7, pays_livraison);

            stmt.executeUpdate();

            // Génération du numéro de commande
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            generatedKeys.next();
            int numeroCommande = generatedKeys.getInt(1);
            Logger.getLogger("DAO").log(Level.INFO,
                    "Nouvelle clé générée pour INVOICE : {0}", numeroCommande);

            try (PreparedStatement addLigne = connection.prepareStatement(ajoutLigne)) {
                for (int produit = 0; produit < produitID.length; produit++) {
                    addLigne.setInt(1, numeroCommande);
                    addLigne.setInt(2, produitID[produit]);
                    addLigne.setInt(3, quantite[produit]);

                    int n = addLigne.executeUpdate();
                }
            }

            String produit = "SELECT UNITES_COMMANDEES "
                    + "FROM PRODUIT "
                    + "WHERE REFERENCE = ?";
            String updateProduit = "UPDATE PRODUIT "
                    + "SET UNITES_COMMANDEES = ? "
                    + "WHERE REFERENCE = ?";

            try (PreparedStatement affProduit = connection.prepareStatement(produit);) {
                for (int reference = 0; reference < produitID.length; reference++) {
                    affProduit.setInt(1, produitID[reference]);

                    ResultSet rs = affProduit.executeQuery();
                    rs.next();
                    String unites_commandees = rs.getString("UNITES_COMMANDEES");

                    try (PreparedStatement upProduit = connection.prepareStatement(updateProduit);) {

                        upProduit.setInt(1,
                                Integer.parseInt(unites_commandees)
                                + quantite[reference]
                        );
                        upProduit.setInt(2, produitID[reference]);

                        upProduit.executeUpdate();
                    }
                }
            }

            connection.commit();
            
        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, error);
            throw new SQLException(error.getMessage());
        }

    }

    public List<CommandeEntity> showCommandeByClient(String code) throws SQLException {

        String sql = "SELECT * FROM APP.COMMANDE WHERE CLIENT = ?";
        List<CommandeEntity> result = new LinkedList<>();

        try (Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
                PreparedStatement stmt = connection.prepareStatement(sql); // On crée un statement pour exécuter une requête
                ) {
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int numero = rs.getInt("NUMERO");
                String client = rs.getString("CLIENT");
                String saise_le = rs.getString("SAISIE_LE");
                String envoye_le = rs.getString("ENVOYEE_LE");
                String port = rs.getString("PORT");
                String destinataire = rs.getString("DESTINATAIRE");
                String adresse_livraison = rs.getString("ADRESSE_LIVRAISON");
                String ville_livraison = rs.getString("VILLE_LIVRAISON");
                String region_livraison = rs.getString("REGION_LIVRAISON");
                String code_postal_livrais = rs.getString("CODE_POSTAL_LIVRAIS");
                String pays_livraison = rs.getString("PAYS_LIVRAISON");
                String remise = rs.getString("REMISE");

                result.add(new CommandeEntity(numero, client, destinataire, adresse_livraison, ville_livraison, region_livraison, code_postal_livrais, pays_livraison));
            }

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, (String) null, error);
            throw new SQLException(error.getMessage());
        }

        return result;
    }

    /**
     *
     * Donne le nombre de commande(s) passée(s) par un client
     *
     * @param client Code du client
     * @return Nombre de commande pour un client
     * @throws SQLException
     */
    public int numberOfCommandes(String client) throws SQLException {
        String sql = "SELECT COUNT(*) AS NUMBER_OF_COMMANDE FROM APP.COMMANDE "
                + "WHERE CLIENT=?";
        int result = 0;

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, client);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("NUMBER_OF_COMMANDE");
            }
        }
        return result;
    }

    /**
     *
     * Donne le chiffre d'affaires en fonction d'une catégorie de produits
     *
     * @param categorie Catégorie des produits
     * @param saisie_le A partir de quelle date afficher le chiffre d'affaires
     * @param envoyee_le Jusqu'à quelle date afficher le chiffre d'affaires
     * @return Chiffre d'affaires
     * @throws SQLException
     */
    public double showCAByCategorie(int categorie, String saisie_le,
            String envoyee_le) throws SQLException {
        double result = 0;

        String sql = "SELECT PRIX_UNITAIRE*QUANTITE AS CHIFFRE_DAFFAIRES "
                + "FROM PRODUIT, LIGNE, COMMANDE "
                + "WHERE PRODUIT.REFERENCE = LIGNE.PRODUIT "
                + "AND LIGNE.COMMANDE = COMMANDE.NUMERO "
                + "AND PRODUIT.CATEGORIE = ? "
                + "AND COMMANDE.SAISIE_LE >= ? "
                + "AND COMMANDE.ENVOYEE_LE <= ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setInt(1, categorie);
            stmt.setString(2, saisie_le);
            stmt.setString(3, envoyee_le);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                double chiffre_daffaires = rs.getInt("CHIFFRE_DAFFAIRES");
                result += chiffre_daffaires;
            }

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, (String) null, error);
            throw new SQLException(error.getMessage());
        }
        return result;
    }

    /**
     *
     * Donne le chiffre d'affaires en fonction d'un pays
     *
     * @param pays Pays
     * @param saisie_le A partir de quelle date afficher le chiffre d'affaires
     * @param envoyee_le Jusqu'à quelle date afficher le chiffre d'affaires
     * @return Chiffre d'affaires
     * @throws SQLException
     */
    public double showCAByCountry(String pays, String saisie_le,
            String envoyee_le) throws SQLException {
        double result = 0;

        String sql = "SELECT PRIX_UNITAIRE*QUANTITE AS CHIFFRE_DAFFAIRES "
                + "FROM LIGNE, COMMANDE, PRODUIT "
                + "WHERE LIGNE.COMMANDE = COMMANDE.NUMERO "
                + "AND PRODUIT.REFERENCE = LIGNE.PRODUIT "
                + "AND COMMANDE.PAYS_LIVRAISON = ? "
                + "AND COMMANDE.SAISIE_LE >= ? "
                + "AND COMMANDE.ENVOYEE_LE <= ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, pays);
            stmt.setString(2, saisie_le);
            stmt.setString(3, envoyee_le);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                double chiffre_daffaires = rs.getInt("CHIFFRE_DAFFAIRES");
                result += chiffre_daffaires;
            }

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, (String) null, error);
            throw new SQLException(error.getMessage());
        }
        return result;
    }

    /**
     *
     * Donne le chiffres d'affaires en fonction d'un client
     *
     * @param code Code du client
     * @param saisie_le A partir de quelle date afficher le chiffre d'affaires
     * @param envoyee_le Jusqu'à quelle date afficher le chiffre d'affaires
     * @return Chiffre d'affaires
     * @throws SQLException
     */
    public double showCAByClient(String code, String saisie_le,
            String envoyee_le) throws SQLException {
        double result = 0;

        String sql = "SELECT PRIX_UNITAIRE*QUANTITE AS CHIFFRE_DAFFAIRES "
                + "FROM LIGNE, COMMANDE, PRODUIT "
                + "WHERE LIGNE.COMMANDE = COMMANDE.NUMERO "
                + "AND PRODUIT.REFERENCE = LIGNE.PRODUIT "
                + "AND COMMANDE.CLIENT = ? "
                + "AND COMMANDE.SAISIE_LE >= ? "
                + "AND COMMANDE.ENVOYEE_LE <= ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, code);
            stmt.setString(2, saisie_le);
            stmt.setString(3, envoyee_le);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                double chiffre_daffaires = rs.getInt("CHIFFRE_DAFFAIRES");
                result += chiffre_daffaires;
            }

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, (String) null, error);
            throw new SQLException(error.getMessage());
        }
        return result;
    }

    /**
     *
     * Ajouter un produit dans la table PRODUIT
     *
     * @param nom Nom du produit
     * @param fournisseur Numéro du fournisseur du produit
     * @param categorie Numéro de la catégorie du produit
     * @param quantite_par_unite Description de la quantité par unité du produit
     * @param prix_unitaire Prix unitaire du produit
     * @param unites_en_stock Unités en stock du produit
     * @param unites_commandees Unités commandées du produit
     * @param niveau_de_reappro Niveau de réapprovisionnement du produit
     * @throws SQLException
     */
    public void addProduit(String nom, int fournisseur, int categorie,
            String quantite_par_unite, double prix_unitaire, int unites_en_stock,
            int unites_commandees, int niveau_de_reappro)
            throws SQLException {
        int result;
        String reference_max = "SELECT MAX(REFERENCE) AS REFERENCE_MAX FROM APP.PRODUIT";

        try (Connection connection = myDataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(reference_max);) {

            rs.next();
            result = rs.getInt("REFERENCE_MAX") + 1;

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, (String) null, error);
            throw new SQLException(error.getMessage());
        }

        int indisponible;
        String insert = "INSERT INTO PRODUIT "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(insert);) {
            System.out.print("RESULT " + result);
            stmt.setInt(1, result);
            stmt.setString(2, nom);
            stmt.setInt(3, fournisseur);
            stmt.setInt(4, categorie);
            stmt.setString(5, quantite_par_unite);
            stmt.setDouble(6, prix_unitaire);
            stmt.setInt(7, unites_en_stock);
            stmt.setInt(8, unites_commandees);
            stmt.setInt(9, niveau_de_reappro);

            if (unites_commandees == 0 & niveau_de_reappro == 0) {
                indisponible = 1;
            } else {
                indisponible = 0;
            }

            stmt.setInt(10, indisponible);

            stmt.executeUpdate();

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, (String) null, error);
            throw new SQLException(error.getMessage());
        }
    }

    /**
     *
     * Supprimer un produit de la table PRODUIT
     *
     * @param reference Reference du produit
     * @throws SQLException
     */
    public void deleteProduit(int reference) throws SQLException {

        String sql = "DELETE FROM PRODUIT WHERE REFERENCE = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setInt(1, reference);

            stmt.executeUpdate();
        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, (String) null, error);
            throw new SQLException(error.getMessage());
        }
    }

    /**
     *
     * Editer les informations d'un produit
     *
     * @param reference Reference du produit
     * @param nom Nom du produit
     * @param fournisseur Numéro du fournisseur du produit
     * @param categorie Numéro de la catégorie du produit
     * @param quantite_par_unite Description de la quantité par unité du produit
     * @param prix_unitaire Prix unitaire du produit
     * @param unites_en_stock Unités en stock du produit
     * @param unites_commandees Unités commandées du produit
     * @param niveau_de_reappro Niveau de réapprovisionnement du produit
     * @throws SQLException
     */
    public void editProduit(int reference, String nom, int fournisseur,
            int categorie, String quantite_par_unite, double prix_unitaire,
            int unites_en_stock, int unites_commandees, int niveau_de_reappro)
            throws SQLException {

        int indisponible;
        String sql = "UPDATE PRODUIT SET NOM = ?, FOURNISSEUR = ?, "
                + "CATEGORIE = ?, QUANTITE_PAR_UNITE = ?, "
                + "PRIX_UNITAIRE = ?, UNITES_EN_STOCK = ?, "
                + "UNITES_COMMANDEES = ?, NIVEAU_DE_REAPPRO = ?, "
                + "INDISPONIBLE = ? "
                + "WHERE REFERENCE = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);) {

            stmt.setString(1, nom);
            stmt.setInt(2, fournisseur);
            stmt.setInt(3, categorie);
            stmt.setString(4, quantite_par_unite);
            stmt.setDouble(5, prix_unitaire);
            stmt.setInt(6, unites_en_stock);
            stmt.setInt(7, unites_commandees);
            stmt.setInt(8, niveau_de_reappro);

            if (unites_commandees == 0 & niveau_de_reappro == 0) {
                indisponible = 1;
            } else {
                indisponible = 0;
            }

            stmt.setInt(9, indisponible);

            stmt.setInt(10, reference);

            stmt.executeUpdate();

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, (String) null, error);
            throw new SQLException(error.getMessage());
        }

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

    public void addPurchaseOrders() throws SQLException {

    }

    public void editPurchaseOrders() throws SQLException {

    }

    public void deletePurchaseOrders() throws SQLException {

    }

    public void addToCart(ProduitEntity p, int qte) {
    }

    public void deleteFromCart(ProduitEntity p) {
    }

    public static void main(String[] args) throws SQLException {
        DAO dao = new DAO(DataSourceFactory.getDataSource());

        ClientEntity client = new ClientEntity("ALFKI", "Alfreds Futterkiste",
                "Maria Anders", "Représentant(e)", "Obere Str. 57", "Berlin",
                null, "12209", "Allemagne", "030_0074321", "030-0076545");

        int[] produitID = new int[]{2};
        int[] quantite = new int[]{4};

        dao.confirmCart(client.getCode(), client.getSociete(), client.getAdresse(),
                client.getVille(), client.getRegion(), client.getCodePostal(),
                client.getPays(), produitID, quantite);

    }

}
