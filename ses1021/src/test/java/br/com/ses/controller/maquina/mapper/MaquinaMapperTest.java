package br.com.ses.controller.maquina.mapper;

import br.com.ses.controller.maquina.mapper.MaquinaMapper;
import br.com.ses.domain.maquina.Maquina;
import br.com.ses.controller.maquina.dto.MaquinaResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.ses.common.MaquinaConstants.*;
import static org.junit.jupiter.api.Assertions.*;

/*
 * Classe de mapeamento: mapper
 * métodos de RequestDTO para entidade e de entidade para ResponseDTO
 * testes de unidade. Sem mocks nem complexidade.
 */
@ExtendWith(MockitoExtension.class)
class MaquinaMapperTest {

    // comportamento: toEntity

    @Test
    public void deveConverterDTOParaEntidade_RetornaEntidadeValida() {
        Maquina maquina = MaquinaMapper.toEntity(MAQUINA_VALIDA);

        Assertions.assertNotNull(maquina);
        Assertions.assertEquals("Massey Ferguson 5M", maquina.getNome()); // verifica estado inicial
        Assertions.assertFalse(maquina.isInativo()); // nasce sempre ativo
    }


    @Test
    public void deveConverterDTO_MesmoComStatusInativoNoDTO_RetornaEntidadeAtiva() {
        // a entidade sempre nasce ativa, regra do construtor
        // mas o DTO pode chegar com inativo = true e ser ignorado pelo domínio
        Maquina maquina = MaquinaMapper.toEntity(MAQUINA_JA_INATIVA);

        Assertions.assertFalse(maquina.isInativo()); // deve nascer ativa
    }

    // comportamento: toResponse

    @Test
    public void deveConverterEntidadeParaResponseDTO_RetornaResponseDTOValido() {
        Maquina maquina = maquinaValida();

        MaquinaResponseDTO response = MaquinaMapper.toResponse(maquina);

        assertNotNull(response);
        assertEquals(983, response.getCodigo());
        assertEquals("Valtra S416 The Boss", response.getNome());
        assertFalse(response.isInativo());
    }

    @Test
    void deveConverterEntidade_CamposAuditoriaNulos_ResponseRefleteNulos() {
        // auditoria é preenchida pelo DAO — nasce nula no domínio.
        // o mapper deve repassar os nulos sem explodir.
        Maquina maquina = maquinaValida();

        MaquinaResponseDTO response = MaquinaMapper.toResponse(maquina);

        assertNull(response.getNmUsuarioCriacao());
        assertNull(response.getDtHrCriacao());
        assertNull(response.getNmUsuarioAlteracao());
        assertNull(response.getDtHrAlteracao());
    }
}