package br.com.ses.controller.maquina;

import br.com.ses.common.audit.UsuarioContexto;
import br.com.ses.common.exception.ExceptionMessages;
import br.com.ses.common.exception.maquina.StatusInativoException;
import br.com.ses.controller.maquina.dto.MaquinaRequestDTO;
import br.com.ses.controller.maquina.dto.MaquinaResponseDTO;
import br.com.ses.controller.maquina.mapper.MaquinaMapper;
import br.com.ses.domain.maquina.Maquina;
import br.com.ses.service.maquina.MaquinaService;

import java.util.List;
import java.util.Optional;

public class MaquinaController {

    private final MaquinaService maquinaService;

    public MaquinaController(MaquinaService maquinaService) {
        this.maquinaService = maquinaService;
    }

    public MaquinaResponseDTO adicionar(MaquinaRequestDTO dto) {
        if (dto.isInativo()) {
            throw new StatusInativoException(ExceptionMessages.STTS_MAQ_OBRGT);
        }

        UsuarioContexto.set("sistema"); // usuário autenticado
        try {
            Maquina maquina = MaquinaMapper.toEntity(dto);
            Maquina salva = maquinaService.adicionar(maquina);
            return MaquinaMapper.toResponse(salva);
        } finally { // finally executa um bloco crucial. ideal para limpeza
            UsuarioContexto.limpar();
        }
    }

    public Optional<MaquinaResponseDTO> buscar(Integer codigo) {
        return maquinaService.buscar(codigo)
                .map(MaquinaMapper::toResponse);
    }

    public List<MaquinaResponseDTO> listar(Integer cdDe, Integer cdAte) {
        return maquinaService.listarTodos(cdDe, cdAte)
                .stream()
                .map(MaquinaMapper::toResponse)
                .toList();
    }

    public MaquinaResponseDTO atualizar(MaquinaRequestDTO dto) {
        if (dto.isInativo()) {
            throw new StatusInativoException(ExceptionMessages.STTS_MAQ_OBRGT);
        }

        UsuarioContexto.set("sistema");
        try {
            Maquina maquina = MaquinaMapper.toEntity(dto);
            Maquina atualizada = maquinaService.atualizar(maquina);
            return MaquinaMapper.toResponse(atualizada);
        } finally {
            UsuarioContexto.limpar(); // evita vazamento no pool de threads
        }
    }

    public void remover(Integer codigo) {
        maquinaService.remover(codigo);
    }
}