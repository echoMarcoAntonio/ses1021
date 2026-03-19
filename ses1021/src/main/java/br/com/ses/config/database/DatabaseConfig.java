package br.com.ses.config.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DatabaseConfig {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("ses-pu");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void fechar() {
        if (emf != null && emf.isOpen())
            emf.close();
    }
}
