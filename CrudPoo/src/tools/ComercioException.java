package tools;

public class ComercioException extends Exception{
    
    // Construtor com mensagem personalizada
    public ComercioException(String message) {
        super(message);
    }

    // Construtor padrão
    public ComercioException() {
        super();
    }

    // Construtor com causa
    public ComercioException(Throwable t) {
        super(t);
    }

}