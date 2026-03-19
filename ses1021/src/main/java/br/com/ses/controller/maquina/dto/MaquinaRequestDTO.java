package br.com.ses.controller.maquina.dto;

public class MaquinaRequestDTO {
    private final Integer cdMaquina; // nulo até sua instanciação em DAO
    private final String nome;
    private final boolean inativo;

    /**
     * código gerado automáticamente pelo sistema: cadastro de máquina nova.
     */
    public MaquinaRequestDTO(String nome, boolean inativo) {
        this.cdMaquina = null;
        this.nome      = nome;
        this.inativo   = inativo;
    }

    /**
     * código especificado pelo usuário (patrimônio já existente).
     */
    public MaquinaRequestDTO(Integer cdMaquina, String nome, boolean inativo) {
        this.cdMaquina = cdMaquina;
        this.nome      = nome;
        this.inativo   = inativo;
    }

    public Integer getCdMaquina()  { return cdMaquina; }
    public String getNome() { return nome; }
    public boolean isInativo() { return inativo; }
}