package compilador;

public class ErrorSemantico {

    String clave;
    int linea;

    public ErrorSemantico(String clave, int linea) {
        this.clave = clave;
        this.linea = linea;
    }

    public String getClave() {
        return clave;
    }

    public int getLinea() {
        return linea;
    }
}