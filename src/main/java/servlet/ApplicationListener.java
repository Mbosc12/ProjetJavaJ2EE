package servlet;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import model.CategorieEntity;
import model.DAO;
import model.DataSourceFactory;
import org.apache.derby.tools.ij;

/**
 * Web application lifecycle listener, initialise la base de données au démarrage de l'application si nécessaire
 */
@WebListener()
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (!databaseExists()) {
            initializeDatabase();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    private boolean databaseExists() {
        boolean result = false;

        DAO dao = new DAO(DataSourceFactory.getDataSource());
        try {
            List<CategorieEntity> categories = dao.allCategories();
            Logger.getLogger("AjaxMaven").log(Level.INFO, "Database already exists");
            result = true;
        } catch (SQLException ex) {
            Logger.getLogger("AjaxMaven").log(Level.INFO, "Database does not exist");
        }
        return result;
    }

    private void initializeDatabase() {
        OutputStream nowhere = new OutputStream() {
            @Override
            public void write(int b) {
            }
        };

        Logger.getLogger("AjaxMaven").log(Level.INFO, "Creating databse from SQL script");
        try {
            Connection connection = DataSourceFactory.getDataSource().getConnection();
            int schema = ij.runScript(connection, this.getClass().getResourceAsStream("comptoirs_schema_derby.sql"), "UTF-8", System.out, "UTF-8");
            if (schema == 0) {
                Logger.getLogger("AjaxMaven").log(Level.INFO, "Database succesfully created");
            } else {
                Logger.getLogger("AjaxMaven").log(Level.SEVERE, "Errors creating database");
            }

            int script = ij.runScript(connection, this.getClass().getResourceAsStream("comptoirs_data.sql"), "UTF-8", System.out, "UTF-8");
            if (script == 0) {
                Logger.getLogger("AjaxMaven").log(Level.INFO, "Database succesfully created");
            } else {
                Logger.getLogger("AjaxMaven").log(Level.SEVERE, "Errors creating database");
            }

        } catch (UnsupportedEncodingException | SQLException e) {
            Logger.getLogger("AjaxMaven").log(Level.SEVERE, null, e);
        }

    }
}
