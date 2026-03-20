package br.com.ses.repository.maquina.dao;

import br.com.ses.common.audit.UsuarioContexto;
import br.com.ses.config.database.DatabaseConfig;
import br.com.ses.domain.maquina.Maquina;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Testcontainers
class MaquinaDaoImplTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    static MaquinaDaoImpl dao;

    @BeforeAll
    static void configurar() {
        try {
            Map<String, Object> props = new HashMap<>();
            dao = new MaquinaDaoImpl();
            props.put("jakarta.persistence.jdbc.url", postgres.getJdbcUrl());
            props.put("jakarta.persistence.jdbc.user", postgres.getUsername());
            props.put("jakarta.persistence.jdbc.password", postgres.getPassword());
            props.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
            DatabaseConfig.init(props);
        } catch (Exception e) {
            // e.printStackTrace();
            throw e;
        }
    }

    @AfterAll
    static void encerrar() {
        postgres.stop();
    }

    @Test
    void deveCriar_PesisteMaquinaNoContainer() {
        UsuarioContexto.set("teste");
        try {
            Maquina maquina = new Maquina(100, "John Deere 9RX");

            dao.criar(maquina);

            Optional<Maquina> resultado = dao.buscar(100);
            Assertions.assertTrue(resultado.isPresent());
            Assertions.assertEquals("John Deere 9RX", resultado.get().getNome());
        } finally {
            UsuarioContexto.limpar();
        }
    }
}
