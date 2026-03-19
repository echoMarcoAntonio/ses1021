package br.com.ses.repository.maquina;

import br.com.ses.domain.maquina.Maquina;
import br.com.ses.repository.maquina.dao.MaquinaDao;

import java.util.List;
import java.util.Optional;

public class MaquinaRepositoryImpl implements MaquinaRepository {

    private final MaquinaDao dao;

    public MaquinaRepositoryImpl(
            MaquinaDao dao
    ){
        this.dao = dao;
    }

    @Override
    public void adicionar(Maquina maquina) {
        dao.criar(maquina);
    }

    @Override
    public Optional<Maquina> buscar(int codigo) {
        return dao.buscar(codigo);
    }

    @Override
    public void atualizar(Maquina maquina) {
        dao.atualizar(maquina);
    }

    @Override
    public void remover(int codigo) {
        dao.deletar(codigo);
    }

    @Override
    public List<Maquina> listarTodos(int cdDe, int cdAte) {
        return dao.listar(cdDe, cdAte);
    }
}