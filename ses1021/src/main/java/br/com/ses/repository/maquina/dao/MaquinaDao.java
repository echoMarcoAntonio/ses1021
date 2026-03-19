package br.com.ses.repository.maquina.dao;

import br.com.ses.domain.maquina.Maquina;

import java.util.List;
import java.util.Optional;

/*
 * Retorna o próximo valor da sequence (ses_maquinas_sequence)
 * chamado pelo criar() quando a máquina não tiver código definido
 */
public interface MaquinaDao {

    Integer proximoCdMaquina();
    
    void criar(Maquina maquina);

    Optional<Maquina> buscar(Integer cdMaquina);

    void atualizar(Maquina maquina);

    void deletar(Integer cdMaquina);

    List<Maquina> listar(int cdDe, int cdAte);
}
