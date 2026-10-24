package compilador;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;

public class AnalicadorSintactico {

    private TablaErrores errores;

    public AnalicadorSintactico(TablaErrores errores) {
        this.errores = errores;
    }

    private boolean validar(String regex, String linea) {
        return linea.matches(regex);
    }

    public void analizar(String nombreArchivo) {
        try {
            File archivo = new File(nombreArchivo);
            Scanner leer = new Scanner(archivo);
            int linea = 1;
            boolean inicio = false;
            boolean fin = false;
            boolean ini_decl = false;
            boolean fin_decl = false;

            boolean dentroDeDecl = false;

            GeneradorCodigo gen = new GeneradorCodigo();

            Stack<String> pila = new Stack<>();

            while (leer.hasNextLine()) {

                String texto = leer.nextLine().trim();

                if (texto.isEmpty()) {
                    linea++;
                    continue;
                }

                if (texto.equals("INICIO:")) {
                    inicio = true;
                    gen.abrirArchivo();
                } else if (texto.equals("FIN:")) {
                    fin = true;
                    gen.cerrarArchivo();
                } else if (texto.equals("INI_DECL:")) {
                    ini_decl = true;
                    dentroDeDecl = true;
                } else if (texto.equals("FIN_DECL:")) {
                    fin_decl = true;
                    dentroDeDecl = false;
                }

                else if (validar(ExpresionesRegulares.DECLARACION, texto)) {
                    if (dentroDeDecl) {
                        gen.acumularVariable(texto);
                    }
                } else if (validar(ExpresionesRegulares.CAPTURAR, texto)) {
                    String id = texto.substring(texto.indexOf("(") + 1, texto.indexOf(")")).trim();
                    gen.capturarVariable(id);
                } else if (validar(ExpresionesRegulares.MOSTRAR, texto)) {
                    gen.imprimirMensaje(texto.substring(texto.indexOf("("), texto.lastIndexOf(")") + 1));
                } else if (validar(ExpresionesRegulares.ARITM, texto)) {
                    gen.realizarOperacion(texto);
                } else if (validar(ExpresionesRegulares.ASIGNACION, texto)) {
                    gen.realizarOperacion(texto);
                } else if (validar(ExpresionesRegulares.SI, texto)) {
                    pila.push("SI");
                    String limpia = texto.replace("(", " ( ").replace(")", " ) ");
                    String[] partesSI = limpia.split("\\s+");
                    ArrayList<String> tokensSI = new ArrayList<>(Arrays.asList(partesSI));
                    gen.generarCondicion(tokensSI);
                } else if (validar(ExpresionesRegulares.SINO, texto)) {
                    if (pila.isEmpty() || !pila.peek().equals("FIN_SI")) {
                        errores.agregar("S10", linea);
                    } else {
                        pila.pop(); 
                        pila.push("SINO");
                        gen.generarSino();
                    }
                } else if (validar(ExpresionesRegulares.FIN_SI, texto)) {
                    if (pila.isEmpty() || !pila.peek().equals("SI")) {
                        errores.agregar("S04", linea);
                    } else {
                        pila.pop(); 
                        pila.push("FIN_SI");
                        gen.cerrarCondicion();
                    }
                } else if (validar(ExpresionesRegulares.SINO_FIN, texto)) {
                    if (pila.isEmpty() || !pila.peek().equals("SINO")) {
                        errores.agregar("S12", linea);
                    } else {
                        pila.pop(); 
                        gen.cerrarCondicion();
                    }
                } else if (validar(ExpresionesRegulares.PARA, texto)) {
                    pila.push("PARA");
                    String[] partes = texto.split("\\s+");
                    
                    String variable = partes[1];
                    String desde = partes[3];
                    String hasta = partes[5];
                    gen.generarPara(variable, desde, hasta);
                } else if (validar(ExpresionesRegulares.FIN_PARA, texto)) {

                    if (pila.isEmpty() || !pila.peek().equals("PARA")) {
                        errores.agregar("S06", linea);
                    } else {
                        pila.pop();
                        gen.cerrarPara();
                    }
                } else if (validar(ExpresionesRegulares.MIENTRAS, texto)) {
                    pila.push("MIENTRAS");
                    String limpia = texto.replace("(", " ( ").replace(")", " ) ");
                    String[] partesMI = limpia.split("\\s+");
                    ArrayList<String> tokensMI = new ArrayList<>(Arrays.asList(partesMI));
                    gen.generarMientras(tokensMI);
                }

                else if (validar(ExpresionesRegulares.FIN_MIENTRAS, texto)) {

                    if (pila.isEmpty() || !pila.peek().equals("MIENTRAS")) {
                        errores.agregar("S07", linea);
                    } else {
                        pila.pop();
                        gen.cerrarMientras();
                    }
                }

                else if (validar(ExpresionesRegulares.REPETIR, texto)) {
                    pila.push("REPETIR");
                }

                else if (validar(ExpresionesRegulares.HASTA, texto)) {

                    if (pila.isEmpty() || !pila.peek().equals("REPETIR")) {
                        errores.agregar("S13", linea);
                    } else {
                        pila.pop();
                    }
                }

                else {
                    analizarErroresEspecificos(texto, linea);
                }

                linea++;
            }

            leer.close();

            
            while (!pila.isEmpty()) {
                String tipo = pila.pop();
                switch (tipo) {
                    case "SI":
                        errores.agregar("S04", 0);
                        break;
                    case "SINO":
                        errores.agregar("S12", 0);
                        break;
                    case "PARA":
                        errores.agregar("S06", 0);
                        break;
                    case "MIENTRAS":
                        errores.agregar("S07", 0);
                        break;
                    case "REPETIR":
                        errores.agregar("S13", 0);
                        break;
                }
            }

            if (!inicio)
                errores.agregar("L05", 0);
            if (!fin)
                errores.agregar("L06", 0);
            if (!ini_decl)
                errores.agregar("S20", 0);
            if (!fin_decl)
                errores.agregar("S21", 0);

        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        }
    }

    private void analizarErroresEspecificos(String texto, int linea) {
        boolean errorDetectado = false;
        if (texto.startsWith("MOSTRAR") || texto.startsWith("CAPTURAR")) {
            if (!texto.contains("(")) {
                errores.agregar("S03", linea);
                errorDetectado = true;
            }
            if (!texto.contains(")")) {
                errores.agregar("S05", linea);
                errorDetectado = true;
            }
            if (!texto.endsWith(";")) {
                errores.agregar("S02", linea);
                errorDetectado = true;
            }
            if (texto.startsWith("MOSTRAR")
                    && (texto.indexOf("\"") == -1 || texto.indexOf("\"", texto.indexOf("\"") + 1) == -1)) {
                errores.agregar("S01", linea);
                errorDetectado = true;
            }
        }
        if (!errorDetectado) {
            errores.agregar("S00", linea);
        }
    }
}