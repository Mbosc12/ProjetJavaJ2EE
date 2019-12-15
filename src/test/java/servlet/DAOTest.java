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
import javax.sql.DataSource;
import model.ClientEntity;
import model.DAO;
import static model.DataSourceFactory.getDataSource;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lauriecoumes
 */
public class DAOTest {

    private DAO dao; // L'objet à tester
    private static Connection connection;
    private DataSource DataSource; // La source de données à utiliser
    private ClientEntity client;

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
     * Teste la méthode toutesLesCategories
     *
     * @throws SQLException
     */
    @Test
    public void canConfirmCart() throws Exception {
        // On calcule combien le client a de factures
        String code = client.getCode();
        int before = dao.numberOfCommandes(code);
        System.out.println("BEFORE " + before);

        // Un tableau de 3 productID
        int[] productID = new int[]{2, 16, 57};

        // Un tableau de 3 quantites
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

        // Le client a maintenant une facture de plus
        assertEquals(before + 1, after);
    }

}
