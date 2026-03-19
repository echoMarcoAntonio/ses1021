package br.com.ses.domain.maquina;

import br.com.ses.common.exception.ExceptionMessages;
import br.com.ses.common.exception.maquina.CodigoInvalidoException;
import br.com.ses.common.exception.maquina.NomeInvalidoException;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "ses_maquinas")
@Audited
@AuditTable(value = "ses_maquinas_audit")
public class Maquina {

    private static final int NOME_MIN = 3;
    private static final int NOME_MAX = 20;
    private static final int CD_MIN = 1;
    private static final int CD_MAX = 9999;

    // identidade do domínio
    @Id
    @Column(name = "cd_maquina", nullable = false)
    private Integer cdMaquina;

    @Column(name = "nm_maquina", nullable = false, length = 20)
    private String nome;

    @Column(name = "fl_inativo", nullable = false)
    private boolean inativo;

    /**
     * Campos de auditoria: quem gravou e quando. Preenchido pelo DAO no INSERT/UPDATE, jamais pelo construtor.
     * Criação: nmUsuarioCriacao + dtHrCriacao. Alteração: nmUsuarioAlteracao + dtHrAlteracao
     */
    @Column(name = "nm_usuario_criacao", length = 15)
    private String nmUsuarioCriacao;

    @CreationTimestamp
    @Column(name = "dt_hr_criacao", nullable = false, updatable = false)
    private LocalDateTime dtHrCriacao;

    @Column(name = "nm_usuario_alteracao", length = 15)
    private String nmUsuarioAlteracao;

    @UpdateTimestamp
    @Column(name = "dt_hr_alteracao")
    private LocalDateTime dtHrAlteracao;

    // JPA exige construtor sem argumentos. protected impede criação fora do ORM
    protected Maquina() {
    }

    // construtores públicos

    /**
     * (sequence) código gerado pelo banco, quando o usuário não informa.
     */
    public Maquina(
            final String nome
    ) {
        this.nome = validarNome(nome);
        this.inativo = false; // máquina sempre nasce ativa
        // cdMaquina permanece null até o DAO atribuir via sequence
    }

    /**
     * código informado pelo usuário.
     */
    public Maquina(
            final Integer cdMaquina,
            final String nome
    ) {
        this.cdMaquina = validarCdMaquina(cdMaquina); // valida identidade primeiro
        this.nome = validarNome(nome);
        this.inativo = false; // máquina sempre nasce ativa
    }

    /**
     * atibuição de código gerado pela sequence do banco
     * só pode ser instanciado uma vez: se o código já tiver sido cadastrado resulta em exceção
     */
    public void atribuirCodigo(Integer cdMaquina) {
        if (cdMaquina == null) {
            throw new CodigoInvalidoException(ExceptionMessages.CD_ATRIBUIDO);
        }
        this.cdMaquina = validarCdMaquina(cdMaquina);
    }

    // validações

    private Integer validarCdMaquina(Integer cdMaquina) {
        if (cdMaquina == null) throw new CodigoInvalidoException(ExceptionMessages.CD_NULO);
        if (cdMaquina < CD_MIN) throw new CodigoInvalidoException(ExceptionMessages.CD_MIN_TAM);
        if (cdMaquina > CD_MAX) throw new CodigoInvalidoException(ExceptionMessages.CD_MAX_TAM);

        return cdMaquina;
    }

    private String validarNome(String nome) {
        if (nome == null || nome.isBlank()) throw new NomeInvalidoException(ExceptionMessages.NOME_MAQ_OBRGT);

        // executa trim e length uma única vez para evitar chamadas repetidas
        String nomeTrimado = nome.trim();
        int tamanhoNome = nomeTrimado.length();

        if (tamanhoNome < NOME_MIN) throw new NomeInvalidoException(ExceptionMessages.NOME_MIN_TAM);
        if (tamanhoNome > NOME_MAX) throw new NomeInvalidoException(ExceptionMessages.NOME_MAX_TAM);

        return nomeTrimado;
    }

    // comportamentos do domínio

    /**
     * altera com a validação e retorna o nome normalizado
     */
    public void alterarNome(String novoNome) {
        this.nome = validarNome(novoNome);
    }

    /**
     * permite inativar a máquina. as regras mais complexas pertencem ao service
     */
    public void inativar() {
        this.inativo = true;
    }

    /**
     * reativa a máquina
     */
    public void reativar() {
        this.inativo = false;
    }

    // getters

    public Integer getCdMaquina() {
        return cdMaquina;
    }

    public String getNome() {
        return nome;
    }

    public boolean isInativo() {
        return inativo;
    }

    public String getNmUsuarioCriacao() {
        return nmUsuarioCriacao;
    }

    public LocalDateTime getDtHrCriacao() {
        return dtHrCriacao;
    }

    public String getNmUsuarioAlteracao() {
        return nmUsuarioAlteracao;
    }

    public LocalDateTime getDtHrAlteracao() {
        return dtHrAlteracao;
    }

    // setters auditoria

    public void setNmUsuarioCriacao(String nmUsuarioCriacao) {
        // preenchido pelo DAO. nasce nulo no construtor do domínio. trim só roda quando tem valor
        this.nmUsuarioCriacao = nmUsuarioCriacao != null ? nmUsuarioCriacao.trim() : null;
    }

    public void setNmUsuarioAlteracao(String nmUsuarioAlteracao) {
        this.nmUsuarioAlteracao = nmUsuarioAlteracao != null ? nmUsuarioAlteracao.trim() : null;
    }


    // identidade

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Maquina other)) return false;
        return Objects.equals(this.cdMaquina, other.cdMaquina);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cdMaquina);
    }

    @Override
    public String toString() {
        return "Maquina{cdMaquina=" + cdMaquina + ", nome=" + nome + ", inativo=" + inativo + "}";
    }
}
