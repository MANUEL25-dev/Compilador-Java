package compilador;

import java.util.ArrayList;

public class TablaSimbolos {

    private ArrayList<Tabla> tabla;

    public TablaSimbolos() {
        tabla = new ArrayList<>();
    }

    public boolean agregar(String nombre, String tipo) {
        if (existe(nombre)) return false;
        tabla.add(new Tabla(nombre, tipo));
        return true;
    }

    public boolean existe(String nombre) {
        for (Tabla t : tabla) {
            if (t.getClave().equals(nombre)) return true;
        }
        return false;
    }

    public String obtenerTipo(String nombre) {
        for (Tabla t : tabla) {
            if (t.getClave().equals(nombre)) return t.getDescripcion();
        }
        return null;
    }

    public void imprimir() {
        for (Tabla t : tabla) {
            System.out.println(t.getClave() + " : " + t.getDescripcion());
        }
    }
}