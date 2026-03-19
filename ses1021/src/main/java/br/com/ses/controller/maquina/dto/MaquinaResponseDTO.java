package br.com.ses.controller.maquina.dto;

import java.time.LocalDateTime;

public class MaquinaResponseDTO {
    private final int codigo;
    private final String nome;
    private final boolean inativo;
    private final String nmUsuarioCriacao;
    private final LocalDateTime dtHrCriacao;
    private final String nmUsuarioAlteracao;
    private final LocalDateTime dtHrAlteracao;

    public MaquinaResponseDTO(
            int codigo,
            String nome,
            boolean inativo,
            String nmUsuarioCriacao,
            LocalDateTime dtHrCriacao,
            String nmUsuarioAlteracao,
            LocalDateTime dtHrAlteracao
    ) {
        this.codigo             = codigo;
        this.nome               = nome;
        this.inativo            = inativo;
        this.nmUsuarioCriacao   = nmUsuarioCriacao;
        this.dtHrCriacao        = dtHrCriacao;
        this.nmUsuarioAlteracao = nmUsuarioAlteracao;
        this.dtHrAlteracao      = dtHrAlteracao;
    }

    public int getCodigo() {
        return codigo;
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
}