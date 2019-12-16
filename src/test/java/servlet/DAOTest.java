/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;
import model.CategorieEntity;
import model.ClientEntity;
import model.CommandeEntity;
import model.DAO;
import static model.DataSourceFactory.getDataSource;
import model.ProduitEntity;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author lauriecoumes
 */
public class DAOTest {

    private DAO dao; // L'objet à tester
    private Connection connection;
    private DataSource DataSource; // La source de données à utiliser
    private ClientEntity client;
    String saisie_le = "1994-08-04";
    String envoyee_le = "1994-10-19";

    @Before
    public void setUp() throws SQLException, IOException, SqlToolError {
        DataSource = getDataSource();
        connection = DataSource.getConnection();
        // On crée le schema de la base de test
        executeSQLScript(connection, "comptoirs_schema_derby.sql");
        // On y met des données
        executeSQLScript(connection, "comptoirs_data.sql");

        // On crée l'objet à tester
        dao = new DAO(DataSource);

        client = new ClientEntity("ALFKI", "Alfreds Futterkiste", "Maria Anders",
                "Représentant(e)", "Obere Str. 57", "Berlin", null, "12209",
                "Allemagne", "030-0074321", "030-0076545");
    }

    private void executeSQLScript(Connection connexion, String filename) throws IOException, SqlToolError, SQLException {
        // On initialise la base avec le contenu d'un fichier de test
        String sqlFilePath = this.getClass().getResource(filename).getFile();
        SqlFile sqlFile = new SqlFile(new File(sqlFilePath));

        sqlFile.setConnection(connexion);
        sqlFile.execute();
        sqlFile.closeReader();
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
        dao = null; // Pas vraiment utile
    }

    /**
     * Tester la méthode allCategories
     *
     * @throws Exception
     */
    @Test
    public void testAllCategories() throws Exception {
        List<CategorieEntity> categories = dao.allCategories();
        assertEquals(8, categories.size());
    }

    /**
     *
     * Tester la méthode allProduct
     *
     * @throws Exception
     */
    @Test
    public void testAllProduct() throws Exception {
        List<ProduitEntity> produits = dao.allProducts();
        assertEquals(77, produits.size());
    }

    /**
     * Tester la méthode ProductByCategorie
     *
     * @throws Exception
     *
     */
    @Test
    public void testProductByCategorie() throws Exception {
        ProduitEntity produit1 = dao.showProduit(7);
        ProduitEntity produit2 = dao.showProduit(14);
        ProduitEntity produit3 = dao.showProduit(28);
        ProduitEntity produit4 = dao.showProduit(51);
        ProduitEntity produit5 = dao.showProduit(74);

        List<ProduitEntity> produit = new LinkedList<>();
        produit.add(produit1);
        produit.add(produit2);
        produit.add(produit3);
        produit.add(produit4);
        produit.add(produit5);

        produit.equals(dao.ProductByCategorie(7));
    }

    /**
     * Tester la méthode showProduit
     *
     * @throws Exception
     */
    @Test
    public void testShowProduit() throws Exception {
        ProduitEntity produit = new ProduitEntity(1, "Chai", 1, 1, "10 boîtes x 20 sacs", 90.00, 39, 0, 10, 0);
        produit.equals(dao.showProduit(1));
    }

    /**
     * Tester la méthode showPays
     *
     * @throws Exception
     */
    @Test
    public void testShowPays() throws Exception {
        List<String> pays = dao.showPays();
        assertEquals(21, pays.size());
    }

    /**
     * Tester la méthode getPassword
     *
     * @throws Exception
     */
    @Test
    public void testGetPassword() throws Exception {
        String code = "ALFKI";
        assertEquals(code, dao.getPassword("Maria Anders"));
    }

    /**
     * Tester la méthode showClient
     *
     * @throws Exception
     *
     */
    @Test
    public void testShowClient() throws Exception {
        ClientEntity cli = new ClientEntity("ALFKI", "Alfreds Futterkiste",
                "Maria Anders", "Représentant(e)", "Obere Str. 57", "Berlin",
                null, "12209", "Allemagne", "030-0074321", "030-0076545");

        cli.equals(dao.showClient("ALFKI"));
    }

    /**
     * Tester la méthode showCommandeByClient
     *
     * @throws Exception
     */
    @Test
    public void testShowCommandeByClient() throws Exception {
        List<CommandeEntity> commandes = new LinkedList<>();
        int[] numero_commandes = new int[]{10702, 10835, 10952, 11011};

        commandes = dao.showCommandeByClient("ALFKI");

        assertEquals(numero_commandes.length, commandes.size());

        for (int i = 0; i < commandes.size(); i++) {
            assertEquals(numero_commandes[i], commandes.get(i).getNumero());
        }
    }

    /**
     * Tester la méthode getUnitesCommandees
     *
     * @throws Exception
     */
    @Test
    public void testGetUnitesCommandees() throws Exception {
        int unites_commandees = 40;
        assertEquals(unites_commandees, dao.getUnitesCommandees(2));
    }

    /**
     * Tester la méthode numberOfCommandes
     *
     * @throws Exception
     */
    @Test
    public void testNumberOfCommandes() throws Exception {
        int nb_commandes = 4;
        assertEquals(nb_commandes, dao.numberOfCommandes("ALFKI"));
    }

    /**
     * Tester la méthode showCAByCategorie
     *
     * @throws Exception
     */
    @Test
    public void testShowCAByCategorie() throws Exception {
        double chiffre_daffaires = 150.00 * 15
                + 116.00 * 9
                + 228.00 * 20
                + 265.00 * 40
                + 265.00 * 35
                + 265.00 * 2
                + 50.00 * 21
                + 50.00 * 36;
        assertEquals(chiffre_daffaires, dao.showCAByCategorie(7, saisie_le, envoyee_le), 0);
    }

    /**
     * Tester la méthode showCaForAllCategories
     *
     * @throws Exception
     */
    @Test
    public void testShowCaForAllCategories() throws Exception {
        List<Double> chiffre_daffaires = new ArrayList<>();

        List<CategorieEntity> categories = dao.allCategories();

        for (int i = 0; i < categories.size(); i++) {
            chiffre_daffaires.add(dao.showCAByCategorie(i + 1, saisie_le, envoyee_le));
        }

        for (int i = 0; i < chiffre_daffaires.size(); i++) {
            assertTrue(dao.showCaForAllCategories(saisie_le, envoyee_le).values().contains(chiffre_daffaires.get(i)));
        }
    }

    /**
     * Tester la méthode showCAByCountry
     *
     * @throws Exception
     */
    @Test
    public void testShowCAByCountry() throws Exception {
        double chiffre_daffaires = 105.00 * 12
                + 70.00 * 10
                + 174.00 * 5
                + 105.00 * 6
                + 97.00 * 15
                + 105.00 * 20
                + 195.00 * 30
                + 75.00 * 20
                + 107.00 * 20
                + 174.00 * 7
                + 190.00 * 4
                + 90.00 * 60
                + 174.00 * 20;

        assertEquals(chiffre_daffaires, dao.showCAByCountry("France", saisie_le, envoyee_le), 0);
    }

    /**
     * Tester la méthode showCaForAllCountries
     *
     * @throws Exception
     */
    @Test
    public void testShowCaForAllCountries() throws Exception {
        List<String> pays = dao.showPays();
        List<Double> chiffre_daffaires = new ArrayList<>();

        for (int i = 0; i < pays.size(); i++) {
            chiffre_daffaires.add(dao.showCAByCountry(pays.get(i), saisie_le, envoyee_le));
        }

        for (int i = 0; i < chiffre_daffaires.size(); i++) {
            assertTrue(dao.showCaForAllCountries(saisie_le, envoyee_le).values().contains(chiffre_daffaires.get(i)));
        }
    }

    /**
     * Tester la méthode showCAByClient
     *
     * @throws Exception
     */
    @Test
    public void testShowCAByClient() throws Exception {
        double chiffre_daffaires = 105.00 * 12
                + 70.00 * 10
                + 174.00 * 5
                + 107.00 * 20
                + 174.00 * 7
                + 190.00 * 4;

        assertEquals(chiffre_daffaires, dao.showCAByClient("VINET", saisie_le, envoyee_le), 0);
    }

    /**
     * Tester la méthode addProduit
     *
     * @throws Exception
     */
    @Test
    public void testAddProduit() throws Exception {
        ProduitEntity produit = new ProduitEntity(78, "testNom", 1, 2, "testQuantiteParUnite", 90.00, 10, 2, 12, 0);

        dao.addProduit(produit.getNom(), produit.getFournisseur(),
                produit.getCategorie(), produit.getQuantiteParUnite(),
                produit.getPrixUnitaire(), produit.getUnitesEnStock(),
                produit.getUnitesCommandees(), produit.getNiveauDeReappro());

        produit.equals(dao.showProduit(78));
    }

    /**
     * Tester la méthode editProduit
     *
     * @throws Exception
     */
    @Test
    public void testEditProduit() throws Exception {
        ProduitEntity produit_before = new ProduitEntity(78, "testNom", 1, 2, "testQuantiteParUnite", 90.00, 10, 2, 12, 0);

        dao.addProduit(produit_before.getNom(), produit_before.getFournisseur(),
                produit_before.getCategorie(), produit_before.getQuantiteParUnite(),
                produit_before.getPrixUnitaire(), produit_before.getUnitesEnStock(),
                produit_before.getUnitesCommandees(), produit_before.getNiveauDeReappro());
        
        String nom_before = produit_before.getNom();

        dao.editProduit(78, "Syrup", 1, 2, "testQuantiteParUnite", 90.00, 10, 2, 12);

        ProduitEntity produit_after = dao.showProduit(78);
        String nom_after = produit_after.getNom();

        assertNotEquals(nom_after, nom_before);
        assertEquals(nom_after, "Syrup");
    }

    /**
     * Tester la méthode deleteProduit
     *
     * @throws Exception
     */
    @Test
    public void testDeleteProduit() throws Exception {
        ProduitEntity produit = new ProduitEntity(78, "testNom", 1, 2, "testQuantiteParUnite", 90.00, 10, 2, 12, 0);

        dao.addProduit(produit.getNom(), produit.getFournisseur(),
                produit.getCategorie(), produit.getQuantiteParUnite(),
                produit.getPrixUnitaire(), produit.getUnitesEnStock(),
                produit.getUnitesCommandees(), produit.getNiveauDeReappro());

        dao.deleteProduit(78);

        assertFalse(dao.ProductByCategorie(78).contains(produit));
    }

    /**
     * Tester la méthode editClientPersonalData
     *
     * @throws Exception
     */
    @Test
    public void testEditClientPersonalData() throws Exception {
        String code = "ALFKI";
        String societe = "Alfreds Futterkiste";
        String contact = "Maria Anders";
        String fonction = "Représentant(e)";
        String adresse = "Obere Str. 57";
        String ville = "Berlin";
        String region = null;
        String code_postal = "12209";
        String pays = "Allemagne";
        String telephone = "030-0074321";
        String fax = "030-0076545";

        ClientEntity cli = dao.showClient(code);
        assertEquals(adresse, cli.getAdresse());

        String new_adresse = "Graer Str. 43";
        dao.editClientPersonalData(code, societe, contact, fonction,
                new_adresse, ville, region, code_postal, pays, telephone, fax);
        ClientEntity new_cli = dao.showClient(code);
        assertEquals(new_adresse, new_cli.getAdresse());
    }

    /**
     * Tester la méthode confirmCart
     *
     * @throws SQLException
     */
    @Test
    public void testConfirmCart() throws Exception {
        // On calcule combien le client a de commandes
        String code = client.getCode();
        int before = dao.numberOfCommandes(code);

        // Un tableau de 3 productID
        int[] productID = new int[]{2, 16, 57};

        //On calcule les unités commandées de chaque produit
        int before_q1 = dao.getUnitesCommandees(productID[0]);
        int before_q2 = dao.getUnitesCommandees(productID[1]);
        int before_q3 = dao.getUnitesCommandees(productID[2]);

        // Un tableau de 3 quantite
        int[] quantite = new int[]{10, 3, 8};

        // On exécute la transaction
        String destinataire = client.getSociete();
        String adresse_livraison = client.getAdresse();
        String ville_livraison = client.getVille();
        String region_livraison = client.getRegion();
        String code_postal_livrais = client.getCodePostal();
        String pays_livraison = client.getPays();

        dao.confirmCart(code, destinataire, adresse_livraison,
                ville_livraison, region_livraison, code_postal_livrais,
                pays_livraison, productID, quantite);

        int after = dao.numberOfCommandes(client.getCode());

        int after_q1 = dao.getUnitesCommandees(productID[0]);
        int after_q2 = dao.getUnitesCommandees(productID[1]);
        int after_q3 = dao.getUnitesCommandees(productID[2]);

        // Le client a maintenant une commande de plus
        assertEquals(before + 1, after);

        //Les unités commentées de chaque produit ont augmenté
        assertEquals(before_q1 + quantite[0], after_q1);
        assertEquals(before_q2 + quantite[1], after_q2);
        assertEquals(before_q3 + quantite[2], after_q3);
    }
}
