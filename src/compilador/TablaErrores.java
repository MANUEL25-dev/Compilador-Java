package compilador;

import java.util.ArrayList;

public class TablaErrores {

    private ArrayList<ErrorSemantico> errores;

    public TablaErrores() {
        errores = new ArrayList<>();
    }

    public void agregar(String clave, int linea) {
        for (ErrorSemantico e : errores) {
            if (e.clave.equals(clave) && e.linea == linea) {
                return;
            }
        }
        errores.add(new ErrorSemantico(clave, linea));
    }

    public boolean hayErrores() {
        return !errores.isEmpty();
    }

    public void imprimir(CatalogodeErrores catalogo) {
        for (ErrorSemantico e : errores) {
            String descripcion = catalogo.getDescripcion(e.clave);
            if (e.linea == 0) {
                System.out.println(e.clave + " / " + descripcion);
            } else {
                System.out.println(e.clave + " en linea " + e.linea + " / " + descripcion);
            }
        }
    }
}