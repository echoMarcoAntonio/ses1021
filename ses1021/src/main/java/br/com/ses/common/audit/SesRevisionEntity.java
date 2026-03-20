package br.com.ses.common.audit;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Entity
@Table(name = "ses_revisoes")
@RevisionEntity(SesRevisionListener.class)
public class SesRevisionEntity extends DefaultRevisionEntity {

    @jakarta.persistence.Column(name = "nm_usuario", length = 15)
    private String nmUsuario;

    public String getNmUsuario() { return nmUsuario; }
    public void setNmUsuario(String nmUsuario) { this.nmUsuario = nmUsuario; }
}