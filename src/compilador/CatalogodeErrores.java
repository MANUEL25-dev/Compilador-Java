package compilador;

import java.util.ArrayList;

public class CatalogodeErrores {

    private ArrayList<Tabla> catalogo;

    public CatalogodeErrores() {

        catalogo = new ArrayList<>();

        // LEXICOS
        catalogo.add(new Tabla("L01", "Token no valido"));
        catalogo.add(new Tabla("L02", "Carácter no valido"));
        catalogo.add(new Tabla("L03", "INICIO duplicado"));
        catalogo.add(new Tabla("L04", "FIN sin INICIO"));
        catalogo.add(new Tabla("L05", "Falta INICIO:"));
        catalogo.add(new Tabla("L06", "Falta FIN:"));
        catalogo.add(new Tabla("L07", "Comillas sin cerrar"));

        // SINTACTICOS
        catalogo.add(new Tabla("S00", "Error de sintaxis"));
        catalogo.add(new Tabla("S01", "Faltan comillas"));
        catalogo.add(new Tabla("S02", "Falta punto y coma (;)"));
        catalogo.add(new Tabla("S03", "Falta parentesis de apertura ("));
        catalogo.add(new Tabla("S04", "FIN_SI sin SI o falta FIN_SI"));
        catalogo.add(new Tabla("S05", "Falta parentesis de cierre )"));
        catalogo.add(new Tabla("S06", "FIN_PARA sin PARA o falta FIN_PARA"));
        catalogo.add(new Tabla("S07", "FIN_MIENTRAS sin MIENTRAS o falta FIN_MIENTRAS"));
        catalogo.add(new Tabla("S10", "SINO sin FIN_SI previo"));
        catalogo.add(new Tabla("S12", "SINO_FIN sin SINO o falta SINO_FIN"));
        catalogo.add(new Tabla("S13", "HASTA sin REPETIR o falta HASTA"));
        catalogo.add(new Tabla("S20", "Falta INI_DECL:"));
        catalogo.add(new Tabla("S21", "Falta FIN_DECL:"));
        

        // SEMANTICOS
        catalogo.add(new Tabla("M01", "Nombre de ID repetido"));
        catalogo.add(new Tabla("M02", "Una palabra reservada no puede ser ID"));
        catalogo.add(new Tabla("M03", "ID no declarado"));
        catalogo.add(new Tabla("M04", "Tipo incompatible en asignacion"));
        catalogo.add(new Tabla("M05", "Error de tipos"));
    }

    public String getDescripcion(String clave) {
        for (Tabla t : catalogo) {
            if (t.getClave().equals(clave)) {
                return t.getDescripcion();
            }
        }
        return "Error desconocido";
    }

    public ArrayList<Tabla> getCatalogo() {
        return catalogo;
    }
}