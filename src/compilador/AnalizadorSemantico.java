package compilador;

import java.io.File;
import java.util.Scanner;

public class AnalizadorSemantico {

    private TablaSimbolos tabla;
    private TablaErrores errores;

    private boolean modoDeclaracion = false;

    public AnalizadorSemantico(TablaSimbolos tabla, TablaErrores errores, CatalogodeErrores catalogo) {
        this.tabla = tabla;
        this.errores = errores;
    }

    public void analizar(String archivo) {

        try {
            Scanner leer = new Scanner(new File(archivo));
            int linea = 1;
            while (leer.hasNextLine()) {
                String texto = leer.nextLine().trim();

                if (texto.isEmpty()) {
                    linea++;
                    continue;
                }
                if (texto.equals("INI_DECL:")) modoDeclaracion = true;
                else if (texto.equals("FIN_DECL:")) modoDeclaracion = false;

                else if (modoDeclaracion && texto.matches(ExpresionesRegulares.DECLARACION)) {
                    procesarDeclaracion(texto, linea);
                }
                else if (texto.matches(ExpresionesRegulares.CAPTURAR)) {
                    procesarCapturar(texto, linea);
                }
                else if (texto.matches(ExpresionesRegulares.MOSTRAR)) {
                    procesarMostrar(texto, linea);
                }
                else if (texto.matches(ExpresionesRegulares.ASIGNACION) ||
                         texto.matches(ExpresionesRegulares.ARITM)) {
                    procesarAsignacion(texto, linea);
                }
                else if (texto.matches(ExpresionesRegulares.SI) ||
                         texto.matches(ExpresionesRegulares.MIENTRAS) ||
                         texto.matches(ExpresionesRegulares.HASTA)) {
                    procesarCondicion(texto, linea);
                }
                else if (texto.matches(ExpresionesRegulares.PARA)) {
                    procesarPara(texto, linea);
                }
                linea++;
            }
            leer.close();
        } catch (Exception e) {
            System.out.println("Error al leer archivo");
        }
    }

    private void procesarDeclaracion(String linea, int numLinea) {
        String[] partes = linea.split(":");
        String id = partes[0].trim();
        String tipo = partes[1].replace(";", "").trim();
        if (tabla.existe(id)) {
            errores.agregar("M01", numLinea);
            return;
        }
        for (String r : AnalizadorLexico.reservadas) {
            if (id.equals(r)) {
                errores.agregar("M02", numLinea);
                return;
            }
        }

        tabla.agregar(id, tipo);
    }

    private void procesarCapturar(String linea, int numLinea) {
        String id = linea.substring(linea.indexOf("(") + 1, linea.indexOf(")")).trim();
        if (!tabla.existe(id)) {
            errores.agregar("M03", numLinea);
        }
    }

    private void procesarMostrar(String linea, int numLinea) {
        String contenido = linea.substring(linea.indexOf("(") + 1, linea.indexOf(")")).trim();
        if (esID(contenido) && !tabla.existe(contenido)) {
            errores.agregar("M03", numLinea);
        }
    }

    private void procesarAsignacion(String linea, int numLinea) {
        linea = linea.replace(";", "");
        String[] partes = linea.split("=");

        String idIzq = partes[0].trim();
        String operacion = partes[1].trim();

        if (!tabla.existe(idIzq)) {
            errores.agregar("M03", numLinea);
            return;
        }

        String tipoIzq = tabla.obtenerTipo(idIzq);

        operacion = operacion.replaceAll("[()]", " ");
        String[] tokens = operacion.split("[^A-Z0-9\\.\\\"]+");

        String tipoOperacion = null;

        for (String t : tokens) {

            if (t.isEmpty()) continue;
            if (t.equals("AND") || t.equals("OR")) continue;

            if (esID(t)) {

                if (!tabla.existe(t)) {
                    errores.agregar("M03", numLinea);
                    continue;
                }

                tipoOperacion = combinarTipos(tipoOperacion, tabla.obtenerTipo(t), numLinea);
            }
            else if (t.matches(ExpresionesRegulares.ENTERO)) {
                tipoOperacion = combinarTipos(tipoOperacion, "ENT", numLinea);
            }

            else if (t.matches(ExpresionesRegulares.FLOTANTE)) {
                tipoOperacion = combinarTipos(tipoOperacion, "FLOT", numLinea);
            }

            else if (t.matches(ExpresionesRegulares.CADENA)) {
                tipoOperacion = combinarTipos(tipoOperacion, "CAD", numLinea);
            }
        }
        if (tipoOperacion != null && !tipoOperacion.equals(tipoIzq)) {
            errores.agregar("M04", numLinea);
        }
    }

    private void procesarCondicion(String linea, int numLinea) {
        linea = linea.replaceAll("[()]", " ");
        String[] tokens = linea.split("[^A-Z0-9\\.\\\"]+");

        String tipo1 = null;
        String tipo2 = null;

        for (String t : tokens) {

            if (t.isEmpty()) continue;
            if (t.equals("AND") || t.equals("OR")) continue;

            if (esID(t)) {

                if (!tabla.existe(t)) {
                    errores.agregar("M03", numLinea);
                    continue;
                }

                if (tipo1 == null) tipo1 = tabla.obtenerTipo(t);
                else tipo2 = tabla.obtenerTipo(t);
            }

            else if (t.matches(ExpresionesRegulares.ENTERO)) {
                if (tipo1 == null) tipo1 = "ENT";
                else tipo2 = "ENT";
            }

            else if (t.matches(ExpresionesRegulares.FLOTANTE)) {
                if (tipo1 == null) tipo1 = "FLOT";
                else tipo2 = "FLOT";
            }

            else if (t.matches(ExpresionesRegulares.CADENA)) {
                if (tipo1 == null) tipo1 = "CAD";
                else tipo2 = "CAD";
            }
        }

        if (tipo1 != null && tipo2 != null && !tipo1.equals(tipo2)) {
            errores.agregar("M05", numLinea);
        }
    }

    private void procesarPara(String linea, int numLinea) {
        String[] tokens = linea.split("\\s+");
        for (String t : tokens) {
            if (esID(t) && !tabla.existe(t)) {
                errores.agregar("M03", numLinea);
            }
        }
    }

    private boolean esID(String token) {
        if (!token.matches(ExpresionesRegulares.ID)) return false;
        for (String r : AnalizadorLexico.reservadas) {
            if (token.equals(r)) return false;
        }
        return true;
    }

    private String combinarTipos(String t1, String t2, int linea) {
        if (t1 == null) return t2;
        if (!t1.equals(t2)) {
            errores.agregar("M05", linea);
        }
        return t1;
    }
}