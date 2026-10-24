package compilador;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class AnalizadorLexico {

    private TablaErrores errores;

    public AnalizadorLexico(TablaSimbolos tabla, TablaErrores errores) {
        this.errores = errores;
    }

    static String[] reservadas = {
            "INICIO", "FIN", "INI_DECL", "FIN_DECL",
            "CAPTURAR", "MOSTRAR",
            "SI", "ENTONCES", "FIN_SI",
            "SINO", "SINO_FIN",
            "PARA", "INICIA_EN", "TERMINA_EN", "HACER", "FIN_PARA",
            "MIENTRAS", "FIN_MIENTRAS","REPETIR", "HASTA",
            "ENT", "FLOT", "CAD",
            "AND", "OR"
    };

    public void analizar(String nombreArchivo) {
        boolean hayInicio = false;
        boolean hayFin = false;

        try {

            File archivo = new File(nombreArchivo);
            Scanner leer = new Scanner(archivo);

            int linea = 1;

            while (leer.hasNextLine()) {

                String texto = leer.nextLine();
                String lineaTrim = texto.trim();

                if (lineaTrim.equals("INICIO:")) {
                    if (hayInicio) {
                        errores.agregar("L03", linea);
                    }
                    hayInicio = true;
                }

                if (lineaTrim.equals("FIN:")) {
                    if (!hayInicio) {
                        errores.agregar("L04", linea);
                    }
                    hayFin = true;
                }

                while (texto.contains("\"")) {
                    int inicio = texto.indexOf("\"");
                    int fin = texto.indexOf("\"", inicio + 1);

                    if (fin != -1) {
                        String cadena = texto.substring(inicio, fin + 1);
                        texto = texto.replace(cadena, " ");
                    } else {
                        errores.agregar("L07", linea);
                        break;
                    }
                }

                for (int i = 0; i < texto.length(); i++) {

                    char c = texto.charAt(i);

                    if (!Character.isLetterOrDigit(c) &&
                            ".+-*/\\%^><=():; _\"".indexOf(c) == -1 &&
                            !Character.isWhitespace(c)) {

                        errores.agregar("L02", linea);
                    }
                }

                String[] tokens = texto.split("(?=[()+\\-*/\\\\%^><=;:])|\\s+|(?<=[()+\\-*/\\\\%^><=;:])");

                for (String token : tokens) {

                    if (token.isEmpty())
                        continue;

                    analizarToken(token, linea);
                }

                linea++;
            }
            leer.close();

            if (!hayInicio) {
                errores.agregar("L05", 0);
            }

            if (!hayFin) {
                errores.agregar("L06", 0);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        }
    }

    public void analizarToken(String token, int linea) {

        for (String r : reservadas) {
            if (token.equals(r))
                return;
        }

        if (token.matches(ExpresionesRegulares.ID))
            return;

        if (token.matches(ExpresionesRegulares.ENTERO))
            return;

        if (token.matches(ExpresionesRegulares.FLOTANTE))
            return;

        if (token.matches(ExpresionesRegulares.CADENA))
            return;

        if (token.equals("+") || token.equals("-") ||
                token.equals("*") || token.equals("/") ||
                token.equals("\\") || token.equals("%") || token.equals("^") ||
                token.equals(">") || token.equals("<") ||
                token.equals("=") || token.equals("."))
            return;

        if (token.equals("(") || token.equals(")") ||
                token.equals(":") || token.equals(";"))
            return;

        errores.agregar("L01", linea);
    }
}