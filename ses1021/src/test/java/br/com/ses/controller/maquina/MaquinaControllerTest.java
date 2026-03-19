package br.com.ses.controller.maquina;

import br.com.ses.common.exception.maquina.CodigoInvalidoException;
import br.com.ses.common.exception.maquina.NomeInvalidoException;
import br.com.ses.common.exception.maquina.StatusInativoException;
import br.com.ses.domain.maquina.Maquina;
import br.com.ses.controller.maquina.dto.MaquinaResponseDTO;
import br.com.ses.service.maquina.MaquinaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static br.com.ses.common.MaquinaConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MaquinaControllerTest {

    @Mock
    private MaquinaService service;

    @InjectMocks
    private MaquinaController controller;

    // adicionar

    @Test
    public void deveAdicionarMaquinaValida_RetornaResponse() {
        Maquina maquina = mock(Maquina.class);
        when(maquina.getCdMaquina()).thenReturn(800);
        when(maquina.getNome()).thenReturn("Massey Ferguson 5M");
        when(maquina.isInativo()).thenReturn(false);
        when(service.adicionar(any())).thenReturn(maquina);

        MaquinaResponseDTO response = controller.adicionar(MAQUINA_VALIDA);

        assertNotNull(response);
        assertEquals(800, response.getCodigo());
        assertEquals("Massey Ferguson 5M", response.getNome());
        assertFalse(response.isInativo());
        verify(service, times(1)).adicionar(any());
    }

    @Test
    public void deveAdicionarMaquinaInvalida_CodigoZero_RetornaExcecao() {
        assertThrows(CodigoInvalidoException.class,
                () -> controller.adicionar(MAQUINA_CODIGO_ZERO));

        verify(service, never()).adicionar(any());
    }

    @Test
    public void deveAdicionarMaquinaInvalida_CodigoAcimaLimite_RetornaExcecao() {
        assertThrows(CodigoInvalidoException.class,
                () -> controller.adicionar(MAQUINA_CODIGO_ACIMA_LIMITE));

        verify(service, never()).adicionar(any());
    }

    @Test
    public void deveAdicionarMaquinaInvalida_NomeVazio_RetornaExcecao() {
        assertThrows(NomeInvalidoException.class,
                () -> controller.adicionar(MAQUINA_NOME_VAZIO));

        verify(service, never()).adicionar(any());
    }

    @Test
    public void deveAdicionarMaquinaInvalida_NomeExcedeTamanho_RetornaExcecao() {
        assertThrows(NomeInvalidoException.class,
                () -> controller.adicionar(MAQUINA_NOME_EXCEDE_TAMANHO));

        verify(service, never()).adicionar(any());
    }

    @Test
    public void deveAdicionarMaquinaInvalida_JaInativa_RetornaExcecao() {
        assertThrows(StatusInativoException.class,
                () -> controller.adicionar(MAQUINA_JA_INATIVA));

        verify(service, never()).adicionar(any());
    }

    // buscar

    @Test
    public void deveBuscarMaquinaExistente_RetornaResponse() {
        Maquina maquinaMock = mock(Maquina.class);
        when(maquinaMock.getCdMaquina()).thenReturn(800);
        when(maquinaMock.getNome()).thenReturn("Massey Ferguson MF 5M");
        when(service.buscar(800)).thenReturn(Optional.of(maquinaMock));

        Optional<MaquinaResponseDTO> response = controller.buscar(800);

        assertTrue(response.isPresent());
        assertEquals(800, response.get().getCodigo());
        assertEquals("Massey Ferguson MF 5M", response.get().getNome());
        verify(service, times(1)).buscar(800);
    }

    @Test
    public void deveBuscarMaquinaInexistente_RetornaVazio() {
        when(service.buscar(999)).thenReturn(Optional.empty());

        Optional<MaquinaResponseDTO> response = controller.buscar(999);

        assertFalse(response.isPresent());
        verify(service, times(1)).buscar(999);
    }

    // atualizar

    @Test
    public void deveAtualizarMaquinaValida_RetornaResponse() {
        Maquina maquina = mock(Maquina.class);
        when(maquina.getCdMaquina()).thenReturn(800);
        when(maquina.getNome()).thenReturn("Massey Ferguson 5M");
        when(maquina.isInativo()).thenReturn(false);
        when(service.atualizar(any())).thenReturn(maquina); // ← isso

        MaquinaResponseDTO response = controller.atualizar(MAQUINA_VALIDA);

        assertNotNull(response);
        assertEquals(800, response.getCodigo());
        assertEquals("Massey Ferguson 5M", response.getNome());
        verify(service, times(1)).atualizar(any());
    }

    @Test
    public void deveAtualizarMaquinaInvalida_CodigoZero_RetornaExcecao() {
        assertThrows(CodigoInvalidoException.class,
                () -> controller.atualizar(MAQUINA_CODIGO_ZERO));

        verify(service, never()).atualizar(any());
    }

    @Test
    public void deveAtualizarMaquinaInvalida_NomeVazio_RetornaExcecao() {
        assertThrows(NomeInvalidoException.class,
                () -> controller.atualizar(MAQUINA_NOME_VAZIO));

        verify(service, never()).atualizar(any());
    }

    // remover

    @Test
    public void deveRemoverMaquina_DelegaAoService() {
        controller.remover(800);

        verify(service, times(1)).remover(800);
    }

    // listar

    @Test
    public void deveListarMaquinas_RetornaLista() {
        Maquina mock1 = mock(Maquina.class);
        Maquina mock2 = mock(Maquina.class);
        when(mock1.getCdMaquina()).thenReturn(800);
        when(mock2.getCdMaquina()).thenReturn(801);
        when(service.listarTodos(800, 801)).thenReturn(List.of(mock1, mock2));

        List<MaquinaResponseDTO> response = controller.listar(800, 801);

        assertEquals(2, response.size());
        assertEquals(800, response.get(0).getCodigo());
        assertEquals(801, response.get(1).getCodigo());
        verify(service, times(1)).listarTodos(800, 801);
    }

    @Test
    public void deveListarMaquinas_SemResultado_RetornaListaVazia() {
        when(service.listarTodos(1, 10)).thenReturn(List.of());

        List<MaquinaResponseDTO> response = controller.listar(1, 10);

        assertTrue(response.isEmpty());
        verify(service, times(1)).listarTodos(1, 10);
    }
}