package br.com.ses.common.audit;

import org.hibernate.envers.RevisionListener;

public class SesRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        SesRevisionEntity revisao = (SesRevisionEntity) revisionEntity;
        revisao.setNmUsuario(UsuarioContexto.get());
    }
}