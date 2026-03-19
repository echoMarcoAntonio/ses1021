package br.com.ses.service.maquina;

import br.com.ses.common.exception.maquina.MaquinaNaoEncontradaException;
import br.com.ses.domain.maquina.Maquina;
import br.com.ses.repository.maquina.MaquinaRepository;

import java.util.List;
import java.util.Optional;

import static br.com.ses.common.exception.ExceptionMessages.MAQ_INEXISTENTE;

public class MaquinaService {

    private final MaquinaRepository maquinaRepository;

    public MaquinaService(MaquinaRepository maquinaRepository) {
        this.maquinaRepository = maquinaRepository;
    }

    public Maquina adicionar(Maquina maquina) {
        maquinaRepository.adicionar(maquina);
        return maquina;
    }

    public Optional<Maquina> buscar(int codigo) {
        return maquinaRepository.buscar(codigo);
    }

    public List<Maquina> listarTodos(int cdDe, int cdAte) {
        return maquinaRepository.listarTodos(cdDe, cdAte);
    }

    public Maquina atualizar(Maquina maquina) {
        maquinaRepository.buscar(maquina.getCdMaquina())
                .orElseThrow(() -> new MaquinaNaoEncontradaException(MAQ_INEXISTENTE));
        maquinaRepository.atualizar(maquina);
        return maquina;
    }

    public void remover(int codigo) {
        maquinaRepository.remover(codigo);
    }
}