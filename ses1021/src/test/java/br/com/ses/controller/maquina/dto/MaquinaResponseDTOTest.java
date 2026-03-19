package br.com.ses.controller.maquina.dto;

import br.com.ses.controller.maquina.dto.MaquinaResponseDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/*
 * RESPONSE DTO
 * Representa o contrato de saída. dados que saem para o cliente.
 * inclui campos de auditoria que a entidade expõe mas o request não carrega.
 */
public class MaquinaResponseDTOTest {

    private static final MaquinaResponseDTO DTO_COMPLETO = new MaquinaResponseDTO(
            800,
            "Massey Ferguson 5M",
            false,
            "maria",
            LocalDateTime.of(2025, 1, 15, 14, 30, 0),
            "hoselito",
            LocalDateTime.of(2025, 6, 23, 8, 35, 9)
    );

    @Test
    void deveRetornarCodigo() {
        assertEquals(800, DTO_COMPLETO.getCodigo());
    }

    @Test
    void deveRetornarNome() {
        assertEquals("Massey Ferguson 5M", DTO_COMPLETO.getNome());
    }

    @Test
    void deveRetornarInativo() {
        assertFalse(DTO_COMPLETO.isInativo());
    }

    @Test
    void deveRetornarNmUsuarioCriacao() {
        assertEquals("maria", DTO_COMPLETO.getNmUsuarioCriacao());
    }

    @Test
    void deveRetornarDtHrCriacao() {
        assertEquals(LocalDateTime.of(2025, 1, 15, 14, 30, 0), DTO_COMPLETO.getDtHrCriacao());
    }

    @Test
    void deveRetornarNmUsuarioAlteracao() {
        assertEquals("hoselito", DTO_COMPLETO.getNmUsuarioAlteracao());
    }

    @Test
    void deveRetornarDtHrAlteracao() {
        assertEquals(LocalDateTime.of(2025, 6, 23, 8, 35, 9), DTO_COMPLETO.getDtHrAlteracao());
    }

    @Test
    void deveCriarResponse_CamposAuditoriaNulos_RetornaNulos() {
        MaquinaResponseDTO dto = new MaquinaResponseDTO(800, "Massey Ferguson 5M", false, null, null, null, null);

        assertNull(dto.getNmUsuarioCriacao());
        assertNull(dto.getDtHrAlteracao());
    }
}