package br.com.ses.controller.maquina.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/*
 * REQUEST DTO
 * representa um contrato de entrada que encapsula os dados externos. traz segurança
 * e evita expor a estrutura interna da entidade, enviando somente os dados necessários para a requisição.
 * Não tem regras de negócio. Testes só verificam que os valores são preservados.
 */

public class MaquinaRequestDTOTest {

    private static final MaquinaRequestDTO requestDTO = new MaquinaRequestDTO(800, "Massey Ferguson 5M", false);

    @Test
    public void deveRetornarCodigo() {
        assertEquals(800, requestDTO.getCdMaquina());
    }

    @Test
    public void deveRetornarNome() {
        assertEquals("Massey Ferguson 5M", requestDTO.getNome());
    }

    @Test
    public void deveRetornarFalsoQuandoAtivo() {
        assertFalse(requestDTO.isInativo());
    }
}
