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

        try ( Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
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

        try ( Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
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

        try ( Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
                  PreparedStatement stmt = connection.prepareStatement(sql)) { // On crée un statement pour exécuter une requête
            stmt.setInt(1, Integer.parseInt(code));
            try ( ResultSet rs = stmt.executeQuery()) {
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

        try ( Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
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

        try ( Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
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

    public List<ClientEntity> getPersonalData(String code) throws SQLException {

        List<ClientEntity> client = new LinkedList();
        String sql = "SELECT * FROM APP.CLIENT WHERE CODE = ?";

        try ( Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
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
     *
     * @param code Code du client
     * @param societe Societe du client
     * @throws SQLException
     */
    public void editClientSociete(String code, String societe)
            throws SQLException {

        String sql = "UPDATE APP.CLIENT SET SOCIETE = ? WHERE CODE = ?";

        try ( Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
                  PreparedStatement stmt = connection.prepareStatement(sql) // On crée un statement pour exécuter une requête
                ) {
            stmt.setString(1, societe);
            stmt.setString(2, code);

            stmt.executeQuery();

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, error);
            throw new SQLException(error.getMessage());
        }
    }
    
    /**
     * 
     * @param code Code du client
     * @param contact Nom et prénom du client
     * @throws SQLException 
     */
    public void editClientContact(String code, String contact)
            throws SQLException {

        String sql = "UPDATE APP.CLIENT SET CONTACT = ? WHERE CODE = ?";

        try ( Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
                  PreparedStatement stmt = connection.prepareStatement(sql) // On crée un statement pour exécuter une requête
                ) {
            stmt.setString(1, contact);
            stmt.setString(2, code);

            stmt.executeQuery();

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, error);
            throw new SQLException(error.getMessage());
        }
    }
    
    /**
     * 
     * @param code Code du client
     * @param fonction Fonction du client
     * @throws SQLException 
     */
    public void editClientFonction(String code, String fonction)
            throws SQLException {

        String sql = "UPDATE APP.CLIENT SET FONCTION = ? WHERE CODE = ?";

        try ( Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
                  PreparedStatement stmt = connection.prepareStatement(sql) // On crée un statement pour exécuter une requête
                ) {
            stmt.setString(1, fonction);
            stmt.setString(2, code);

            stmt.executeQuery();

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, error);
            throw new SQLException(error.getMessage());
        }
    }
    
    /**
     * 
     * @param code Code du client
     * @param adresse Adresse du client
     * @throws SQLException 
     */
    public void editClientAdresse(String code, String adresse)
            throws SQLException {

        String sql = "UPDATE APP.CLIENT SET ADRESSE = ? WHERE CODE = ?";

        try ( Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
                  PreparedStatement stmt = connection.prepareStatement(sql) // On crée un statement pour exécuter une requête
                ) {
            stmt.setString(1, adresse);
            stmt.setString(2, code);

            stmt.executeQuery();

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, error);
            throw new SQLException(error.getMessage());
        }
    }
    
    /**
     * 
     * @param code Code du client
     * @param ville Ville du client
     * @throws SQLException 
     */
    public void editClientVille(String code, String ville)
            throws SQLException {

        String sql = "UPDATE APP.CLIENT SET VILLE = ? WHERE CODE = ?";

        try ( Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
                  PreparedStatement stmt = connection.prepareStatement(sql) // On crée un statement pour exécuter une requête
                ) {
            stmt.setString(1, ville);
            stmt.setString(2, code);

            stmt.executeQuery();

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, error);
            throw new SQLException(error.getMessage());
        }
    }
    
    /**
     * 
     * @param code Code du client
     * @param region Region du client
     * @throws SQLException 
     */
    public void editClientRegion(String code, String region)
            throws SQLException {

        String sql = "UPDATE APP.CLIENT SET REGION = ? WHERE CODE = ?";

        try ( Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
                  PreparedStatement stmt = connection.prepareStatement(sql) // On crée un statement pour exécuter une requête
                ) {
            stmt.setString(1, region);
            stmt.setString(2, code);

            stmt.executeQuery();

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, error);
            throw new SQLException(error.getMessage());
        }
    }
    
    /**
     * 
     * @param code Code du client
     * @param code_postal Code postal du client
     * @throws SQLException 
     */
    public void editClientCodePostal(String code, String code_postal)
            throws SQLException {

        String sql = "UPDATE APP.CLIENT SET CODE_POSTAL = ? WHERE CODE = ?";

        try ( Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
                  PreparedStatement stmt = connection.prepareStatement(sql) // On crée un statement pour exécuter une requête
                ) {
            stmt.setString(1, code_postal);
            stmt.setString(2, code);

            stmt.executeQuery();

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, error);
            throw new SQLException(error.getMessage());
        }
    }
    
    /**
     * 
     * @param code Code du client
     * @param pays Pays du client
     * @throws SQLException 
     */
    public void editClientPays(String code, String pays)
            throws SQLException {

        String sql = "UPDATE APP.CLIENT SET PAYS = ? WHERE CODE = ?";

        try ( Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
                  PreparedStatement stmt = connection.prepareStatement(sql) // On crée un statement pour exécuter une requête
                ) {
            stmt.setString(1, pays);
            stmt.setString(2, code);

            stmt.executeQuery();

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, error);
            throw new SQLException(error.getMessage());
        }
    }
    
    /**
     * 
     * @param code Code du client
     * @param telephone Telephone du client
     * @throws SQLException 
     */
    public void editClientTelephone(String code, String telephone)
            throws SQLException {

        String sql = "UPDATE APP.CLIENT SET TELEPHONE = ? WHERE CODE = ?";

        try ( Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
                  PreparedStatement stmt = connection.prepareStatement(sql) // On crée un statement pour exécuter une requête
                ) {
            stmt.setString(1, telephone);
            stmt.setString(2, code);

            stmt.executeQuery();

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, error);
            throw new SQLException(error.getMessage());
        }
    }
    
    /**
     * 
     * @param code Code du client
     * @param fax Fax du client
     * @throws SQLException 
     */
    public void editClientFax(String code, String fax)
            throws SQLException {

        String sql = "UPDATE APP.CLIENT SET FAX = ? WHERE CODE = ?";

        try ( Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
                  PreparedStatement stmt = connection.prepareStatement(sql) // On crée un statement pour exécuter une requête
                ) {
            stmt.setString(1, fax);
            stmt.setString(2, code);

            stmt.executeQuery();

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, error);
            throw new SQLException(error.getMessage());
        }
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

    /**
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

        List<String> result = new LinkedList<>();

        String ajoutCommande = "INSERT INTO APP.COMMANDE (CLIENT, "
                + "DESTINATAIRE, ADRESSE_LIVRAISON, VILLE_LIVRAISON,"
                + "REGION_LIVRAISON, CODE_POSTAL_LIVRAIS, PAYS_LIVRAISON)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String ajoutLigne = "INSERT INTO APP.LIGNE VALUES (?, ?, ?)";
        try ( Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
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

            try ( PreparedStatement addLigne = connection.prepareStatement(ajoutLigne)) {
                for (int produit = 0; produit < produitID.length; produit++) {
                    addLigne.setInt(1, numeroCommande);
                    addLigne.setInt(2, produitID[produit]);
                    addLigne.setInt(3, quantite[produit]);

                    int n = addLigne.executeUpdate();
                }
            }

            connection.commit();

        } catch (SQLException error) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, error);
            throw new SQLException(error.getMessage());
        }
    }

    public List<CommandeEntity> ClientCommande(String code) throws SQLException {

        String sql = "SELECT * FROM APP.COMMANDE WHERE CLIENT = ?";
        List<CommandeEntity> result = new LinkedList<>();

        try ( Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
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

    public static void main(String[] args) throws SQLException {
        DAO dao = new DAO(DataSourceFactory.getDataSource());
        //Afficher toutes les catégories
        // HashMap<Integer, Categorie> result = dao.allCategories();
        
        // Ajouter + supprimer du panier
        /*HashMap<ProduitEntity, Integer> panier = new HashMap<>();

        ProduitEntity chang = new ProduitEntity(2, "Chang", 95.00, 17);
        dao.addToCart(panier, chang, 3);
        
        ProduitEntity ikura = new ProduitEntity(10, "Ikura", 155.00, 31);
        dao.addToCart(panier, ikura, 5);
        System.out.print(panier);
        
        dao.deleteFromCart(panier, chang);
        System.out.print(panier);
        
        int[] productID = new int[]{2, 10};
        int[] quantite = new int[]{3, 5};
        
        dao.confirmCart("ALFKI", "Alfreds Futterkiste",
                "Obere Str. 57", "Berlin", null, "12209", "Allemange", 
                productID, quantite);
        
        System.out.println(panier);
        
        System.out.println(panier);
        dao.editQuantityOrdered(panier, "Ikura", 10);
        System.out.println(panier);
        System.out.println(dao.ClientCommande("ALFKI"));
        System.out.println(dao.getPersonalData("ALFKI"));*/
        
    }

}
