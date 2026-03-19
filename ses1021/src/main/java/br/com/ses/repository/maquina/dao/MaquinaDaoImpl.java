package br.com.ses.repository.maquina.dao;

import br.com.ses.common.audit.UsuarioContexto;
import br.com.ses.config.database.DatabaseConfig;
import br.com.ses.domain.maquina.Maquina;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class MaquinaDaoImpl implements MaquinaDao {

    /**
     * consulta o próximo valor da sequence no banco. nextval() avança a sequence permanentemente
     * mesmo quando transações são revertidas. design intencional, gaps evitam deadlocks em inserções concorrentes
     */
    @Override
    public Integer proximoCdMaquina() {
        try (EntityManager em = DatabaseConfig.getEntityManager()) {
            return ((Number) em
                    .createNativeQuery("SELECT nextval('ses_maquinas_seq')")
                    .getSingleResult()
            ).intValue();
        }
    }

    /**
     * persiste a máquina. caso cdMaquina = null (usuário não informou), busca o próximo valor da sequence e o atribui
     * antes do persist.
     * caso o código já tenha sido definido (usuário informou), persiste diretamente
     */
    @Override
    public void criar(Maquina maquina) {
        if (maquina.getCdMaquina() == null) {
            {
                maquina.atribuirCodigo(proximoCdMaquina());
            }
        }

        EntityManager em = DatabaseConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            maquina.setNmUsuarioCriacao(UsuarioContexto.get());
            maquina.setNmUsuarioAlteracao(UsuarioContexto.get());
            em.persist(maquina);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao criar maquina", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Maquina> buscar(Integer cdMaquina) {
        try (EntityManager em = DatabaseConfig.getEntityManager()) {
            return Optional.ofNullable(em.find(Maquina.class, cdMaquina));
        }
    }

    @Override
    public void atualizar(Maquina maquina) {
        EntityManager em = DatabaseConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            maquina.setNmUsuarioAlteracao(UsuarioContexto.get());
            em.merge(maquina);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar maquina", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void deletar(Integer cdMaquina) {
        EntityManager em = DatabaseConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            Maquina maquina = em.find(Maquina.class, cdMaquina);
            if (maquina != null) em.remove(maquina);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao deletar maquina", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Maquina> listar(int cdDe, int cdAte) {
        try (EntityManager em = DatabaseConfig.getEntityManager()) {
            return em.createQuery(
                            "SELECT m FROM Maquina m " +
                                    "WHERE m.cdMaquina BETWEEN :de AND :ate " +
                                    "ORDER BY m.cdMaquina",
                            Maquina.class)
                    .setParameter("de", cdDe)
                    .setParameter("ate", cdAte)
                    .getResultList();
        }
    }
}