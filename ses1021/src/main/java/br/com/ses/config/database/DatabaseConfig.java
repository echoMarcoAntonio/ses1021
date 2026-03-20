package br.com.ses.config.database;

import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DatabaseConfig {
    private static EntityManagerFactory emf;

    public static void init(Map<String, Object> props) {
        emf = Persistence.createEntityManagerFactory("ses-pu",props);
    }

    // producao
    public static EntityManager getEntityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("ses-pu");
        }
        return emf.createEntityManager();
    }

    // testes
    public static EntityManager getEntityManager(Map<String, Object> props) {
        EntityManagerFactory emfTeste = Persistence
                .createEntityManagerFactory("ses-pu", props);
        return emfTeste.createEntityManager();
    }

    public static void fechar() {
        if (emf != null && emf.isOpen())
            emf.close();
    }
}
