package br.com.ses.repository.maquina;

import br.com.ses.domain.maquina.Maquina;

import java.util.List;
import java.util.Optional;

public interface MaquinaRepository {

        void adicionar(Maquina maquina);

        Optional<Maquina> buscar(int codigo);

        void atualizar(Maquina maquina);

        void remover(int codigo);

        List<Maquina> listarTodos(int cdDe, int cdAte);
}
