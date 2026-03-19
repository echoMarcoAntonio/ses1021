package br.com.ses.common.exception;

public final class ExceptionMessages {

    /**
     * Maquinas
     */
    public static final String CD_NULO = "Código não pode ser nulo.";
    public static final String CD_ATRIBUIDO = "Este código já foi atribuído a outra máquina.";
    public static final String CD_MAX_TAM = "Código deve ser menor do que 9999.";
    public static final String CD_MIN_TAM = "Código deve ser maior ou igual a 1.";

    public static final String NOME_MAQ_OBRGT = "Nome da maquina é obrigatório.";
    public static final String NOME_MAX_TAM = "Nome não pode exceder 20 caracteres.";
    public static final String NOME_MIN_TAM = "Nome não pode ser menor do que 3 caracteres.";

    public static final String STTS_MAQ_OBRGT = "Uma máquina não pode ser criada inativa.";
    public static final String MAQ_INEXISTENTE = "Não foi possível buscar pela máquina. ID não encontrado";

    private ExceptionMessages() {
        throw new UnsupportedOperationException("Classe utilitária.");
    }
}