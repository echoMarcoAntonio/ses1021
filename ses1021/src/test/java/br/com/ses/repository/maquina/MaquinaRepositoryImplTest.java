package br.com.ses.repository.maquina;

import br.com.ses.domain.maquina.Maquina;
import br.com.ses.repository.maquina.dao.MaquinaDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static br.com.ses.common.MaquinaConstants.maquinaValida;

// teste de integracao
@ExtendWith(MockitoExtension.class)
class MaquinaRepositoryImplTest {

    @Mock
    MaquinaDao dao;

    @InjectMocks
    MaquinaRepositoryImpl repository;

    @Test
    void deveAdicionarMaquina_DelegaAoDao() {
        Maquina maquina = maquinaValida();
        repository.adicionar(maquina);
        verify(dao, times(1)).criar(maquina);
    }

    @Test
    void deveBuscarMaquina_DelegaAoDao() {
        repository.buscar(983);
        verify(dao, times(1)).buscar(983);
    }

    @Test
    void deveAtualizarMaquina_DelegaAoDao() {
        Maquina maquina = maquinaValida();
        repository.atualizar(maquina);
        verify(dao, times(1)).atualizar(maquina);
    }

    @Test
    void deveRemoverMaquina_DelegaAoDao() {
        repository.remover(983);
        verify(dao, times(1)).deletar(983);
    }

    @Test
    void deveListarMaquinas_DelegaAoDao() {
        repository.listarTodos(1, 999);
        verify(dao, times(1)).listar(1, 999);
    }
}
