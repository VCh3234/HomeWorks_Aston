package configuration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;

/**
 * Class for simple access to session factory
 */
public class EntityManagerBuilder {

    private static final SessionFactory sf = new Configuration()
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();

    public static EntityManager getEntityManager() {
        return sf.createEntityManager();
    }

    public static Session getSessionFactory() {
        return sf.openSession();
    }
}
