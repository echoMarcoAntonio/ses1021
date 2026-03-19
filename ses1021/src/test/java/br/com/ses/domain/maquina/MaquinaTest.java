package br.com.ses.domain.maquina;

import br.com.ses.common.exception.maquina.CodigoInvalidoException;
import br.com.ses.common.exception.maquina.NomeInvalidoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static br.com.ses.common.MaquinaConstants.*;
import static br.com.ses.common.exception.ExceptionMessages.*;
import static org.junit.jupiter.api.Assertions.*;

/*
 * Classe de domínio: núcleo
 * testes de unidade puros e excecoes. Sem mocks nem complexidade.
 */
public class MaquinaTest {

    // criação valida

    @Test
    public void deveCriarMaquinaValida_RetornaMaquina() {
        Maquina maquina = maquinaValida();

        Assertions.assertEquals(983, maquina.getCdMaquina());
        Assertions.assertNotNull(maquina);
        Assertions.assertEquals("Valtra S416 The Boss", maquina.getNome()); // verifica estado inicial
        Assertions.assertFalse(maquina.isInativo());
    }

    // excecoes: codigo

    @Test
    public void deveCriarMaquinaInvalida_CodigoMenorQueUm_RetornaExcecaoDominio() {
        Exception exception = assertThrows(CodigoInvalidoException.class,
                () -> new Maquina(CODIGO_ZERO, NOME_VALIDO));

        Assertions.assertEquals(CD_MIN_TAM, exception.getMessage());
    }

    @Test
    public void deveCriarMaquinaInvalida_CodigoMaiorQue999_RetornaExcecaoDominio() {
        Exception exception = assertThrows(CodigoInvalidoException.class,
                () -> new Maquina(CODIGO_ACIMA_MAXIMO, NOME_VALIDO));

        Assertions.assertEquals(CD_MAX_TAM, exception.getMessage());
    }

    // excecoes: nome

    @Test
    public void deveCriarMaquinaInvalida_NomeNulo_RetornaExcecaoDominio() {
        Exception exception = assertThrows(NomeInvalidoException.class,
                () -> new Maquina(CODIGO_VALIDO, NOME_NULO));

        Assertions.assertEquals(NOME_MAQ_OBRGT, exception.getMessage());
    }

    @Test
    public void deveCriarMaquinaInvalida_NomeVazio_RetornaExcecaoDominio() {
        Exception exception = assertThrows(NomeInvalidoException.class,
                () -> new Maquina(CODIGO_VALIDO, NOME_VAZIO));

        Assertions.assertEquals(NOME_MAQ_OBRGT, exception.getMessage());
    }

    @Test
    public void deveCriarMaquinaInvalida_NomeSomenteEspacos_RetornaExcecaoDominio() {
        Exception exception = assertThrows(NomeInvalidoException.class,
                () -> new Maquina(CODIGO_VALIDO, NOME_SO_ESPACOS));

        Assertions.assertEquals(NOME_MAQ_OBRGT, exception.getMessage());
    }

    @Test
    public void deveCriarMaquinaInvalida_NomeMenorDoQueMinimo_RetornaExcecaoDominio() {
        Exception exception = assertThrows(NomeInvalidoException.class,
                () -> new Maquina(CODIGO_VALIDO, NOME_MENOR_QUE_MINIMO));

        Assertions.assertEquals(NOME_MIN_TAM, exception.getMessage());
    }

    @Test
    public void deveCriarMaquinaInvalida_NomeMaiorDoQueLimite_RetornaExcecaoDominio() {
        Exception exception = assertThrows(NomeInvalidoException.class,
                () -> new Maquina(CODIGO_VALIDO, NOME_MAIOR_QUE_MAXIMO));

        Assertions.assertEquals(NOME_MAX_TAM, exception.getMessage());
    }

    // comportamento: status

    @Test
    public void deveAlterarStatusDeMaquinaAtivaParaInativa() {
        Maquina maquina = maquinaParaInativar();

        maquina.inativar();

        assertTrue(maquina.isInativo());
    }

    @Test
    public void deveAlterarStatusDeMaquinaInativaParaAtiva() {
        Maquina maquina = maquinaParaInativar();

        maquina.reativar();

        assertFalse(maquina.isInativo());
    }

    // comportamento: nome

    @Test
    public void deveAlterarNomeMaquina_ViaAlterarNome() {
        Maquina maquina = maquinaParaAlterarNome();

        maquina.alterarNome("John Deere 9RX 830");

        assertEquals("John Deere 9RX 830", maquina.getNome());
    }

    @Test
    public void deveAlterarNomeMaquina_ViaAlterarNome_RetornaNovoNome() {
        Maquina maquina = maquinaParaAlterarNome();

        maquina.alterarNome("John Deere 9RX 830");

        assertEquals("John Deere 9RX 830", maquina.getNome());
    }
}