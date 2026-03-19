package br.com.ses.controller.maquina.mapper;

import br.com.ses.domain.maquina.Maquina;
import br.com.ses.controller.maquina.dto.MaquinaRequestDTO;
import br.com.ses.controller.maquina.dto.MaquinaResponseDTO;

/*
 * O Mapper vai receber a requisição do Controller e transformar estes dados antes de passa-los ao Service
 */
public class MaquinaMapper {

    private MaquinaMapper() {
    } // classe utilitária não deve ser instanciada

    /*
     * se o código for null, usa o construtor sem código:
     * o DAO atribuirá o próximo valor da sequence antes do persist.
     *
     * se o código for preenchido, vai direto pro construtor que valida e atribui
     * o código informado para a máquina.
     */
    public static Maquina toEntity(MaquinaRequestDTO dto) {
        if (dto.getCdMaquina() == null) {
            return new Maquina(dto.getNome());
        }
        return new Maquina(dto.getCdMaquina(), dto.getNome());
    }

    public static MaquinaResponseDTO toResponse(Maquina maquina) {
        return new MaquinaResponseDTO(
                maquina.getCdMaquina(),
                maquina.getNome(),
                maquina.isInativo(),
                maquina.getNmUsuarioCriacao(),
                maquina.getDtHrCriacao(),
                maquina.getNmUsuarioAlteracao(),
                maquina.getDtHrAlteracao()
        );
    }
}