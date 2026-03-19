package br.com.ses.common;

import br.com.ses.domain.maquina.Maquina;
import br.com.ses.controller.maquina.dto.MaquinaRequestDTO;

public class MaquinaConstants {

    /*
     * DOMÍNIO
     */

    // válidas
    public static Maquina maquinaValida() {
        return new Maquina(983, "Valtra S416 The Boss");
    }

    public static Maquina maquinaParaAlterarNome() {
        return new Maquina(333, "jon dir9rx 830");
    }

    public static Maquina maquinaParaInativar() {
        return new Maquina(767, "Valtra BH 194 HiTech");
    }

    // inválidas — nome
    public static final int CODIGO_VALIDO = 1;
    public static final String NOME_NULO = null;
    public static final String NOME_VAZIO = "";
    public static final String NOME_SO_ESPACOS = "   ";
    public static final String NOME_MENOR_QUE_MINIMO = "M1";
    public static final String NOME_MAIOR_QUE_MAXIMO =
            "Case IH Steiger 715 Quadtra Staphylococcus Aureus";

    // inválidas — código
    public static final String NOME_VALIDO = "John Deere 9RX 830";
    public static final int CODIGO_ZERO = 0;
    public static final int CODIGO_ACIMA_MAXIMO = 1520;

    /*
     * DTOs
     */

    // sem código: banco gera via sequence
    public static final MaquinaRequestDTO MAQUINA_VALIDA =
            new MaquinaRequestDTO("Massey Ferguson 5M", false);

    // código inválido para testar validações
    public static final MaquinaRequestDTO MAQUINA_CODIGO_ZERO =
            new MaquinaRequestDTO("New Holland T5.110", false);

    public static final MaquinaRequestDTO MAQUINA_CODIGO_ACIMA_LIMITE =
            new MaquinaRequestDTO("Valtra S416 The Boss", false);

    // com nome inválido
    public static final MaquinaRequestDTO MAQUINA_NOME_VAZIO =
            new MaquinaRequestDTO("", false);

    public static final MaquinaRequestDTO MAQUINA_NOME_EXCEDE_TAMANHO =
            new MaquinaRequestDTO("Case IH Steiger 715 Quadtra Staphylococcus Aureus", false);

    // com status inválido
    public static final MaquinaRequestDTO MAQUINA_JA_INATIVA =
            new MaquinaRequestDTO("New Holland T9", true);

    private MaquinaConstants() {
    }
}