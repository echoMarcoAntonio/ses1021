package br.com.ses.common.audit;

public class UsuarioContexto {
    private static final ThreadLocal<String> userContext = new ThreadLocal<>();

    public static void set(String nmUsuario) {
        userContext.set(nmUsuario != null ? nmUsuario.trim() : null);
    }

    public static String get() {
        return userContext.get(); // retorna null se nada chamar set()
    }

    public static void limpar() {
        userContext.remove(); // chamar sempre no finally do ponto de entrada
    }

    private UsuarioContexto() {} // impede instancia
}
