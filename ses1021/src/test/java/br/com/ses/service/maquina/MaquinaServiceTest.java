package br.com.ses.service.maquina;

import br.com.ses.domain.maquina.Maquina;
import br.com.ses.repository.maquina.MaquinaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static br.com.ses.common.MaquinaConstants.maquinaParaAlterarNome;
import static br.com.ses.common.MaquinaConstants.maquinaValida;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/*
 * Testes do service. verifica orquestração e delegação ao repository.
 * o service não tem regras de negócio próprias: apenas coordena.
 */
@ExtendWith(MockitoExtension.class)
class MaquinaServiceTest {

    @Mock
    private MaquinaRepository repository;

    @InjectMocks
    private MaquinaService service;

    // adicionar
    @Test
    void deveAdicionarMaquinaRecebidaPorRepository_RetornaMesmaInstancia() {
        Maquina maquina = maquinaValida();

        Maquina resultado = service.adicionar(maquina);

        verify(repository, times(1)).adicionar(maquina);
        assertSame(maquina, resultado); // service deve devolver a mesma instância, sem transformar
    }

    // buscar
    @Test
    void deveBuscarMaquinaExistente_RetornaOptionalComMaquina() {
        Maquina maquina = maquinaValida();
        when(repository.buscar(983)).thenReturn(Optional.of(maquina));

        Optional<Maquina> resultado = service.buscar(983);

        assertTrue(resultado.isPresent());
        assertSame(maquina, resultado.get());
        verify(repository, times(1)).buscar(983);
    }

    @Test
    void deveBuscarMaquinaInexistente_RetornaOptionalVazio() {
        when(repository.buscar(999)).thenReturn(Optional.empty());

        Optional<Maquina> resultado = service.buscar(999);

        assertFalse(resultado.isPresent());
        verify(repository, times(1)).buscar(999);
    }

    // atualizar
    @Test
    void deveAtualizarMaquinaRecebidaPorRepository_RetornaMesmaInstancia() {
        Maquina maquina = maquinaValida();

        when(repository.buscar(983)).thenReturn(Optional.of(maquina));

        Maquina resultado = service.atualizar(maquina);

        verify(repository, times(1)).atualizar(maquina);
        assertSame(maquina, resultado);
    }

    // listar
    @Test
    void deveListarMaquinas_RetornaListaDoRepository() {
        List<Maquina> lista = List.of(maquinaValida(), maquinaParaAlterarNome());
        when(repository.listarTodos(1, 999)). thenReturn(lista);

        List<Maquina> resultado = service.listarTodos(1, 999);

        assertSame(lista, resultado);
        verify(repository, times(1)).listarTodos(1, 999);
    }

    @Test
    void deveListarMaquinas_SemResultado_RetornaVazia() {
        when(repository.listarTodos(1, 10)).thenReturn(List.of());

        List<Maquina> resultado = service.listarTodos(1, 10);

        assertTrue(resultado.isEmpty());
        verify(repository, times(1)).listarTodos(1, 10);
    }

    // remover
    @Test
    void deveRemoverMaquina_Retorna() {
        Maquina maquina = maquinaValida();

        when(repository.buscar(983)).thenReturn(Optional.of(maquina));

        service.remover(983);

        verify(repository, times(1)).remover(983);
    }
}