package compilador;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;

public class GeneradorCodigo {

    public static ArrayList<String> _ige = new ArrayList<>();
    public static ArrayList<String> codigo = new ArrayList<>();
    public static ArrayList<String> data = new ArrayList<>();
    public static Stack<Integer> PilaSi = new Stack<>();
    public static Stack<String> PilaPara = new Stack<>();
    public static Stack<Integer> PilaMientras = new Stack<>();
    public static int numsi = 0;
    public static int numPara = 0;
    public static int numMientras = 0;
    public static int contadorMensajes = 1;

    private String nombreArchivoAsm = "archivo.asm";

    public GeneradorCodigo() {
        codigo.clear();
        data.clear();
        _ige.clear();
        PilaSi.clear();
        PilaPara.clear();
        PilaMientras.clear();
        numsi = 0;
        numPara = 0;
        numMientras = 0;
        contadorMensajes = 1;

        codigo.add("IMPRIME Macro Mensaje");
        codigo.add("Mov Ah, 09h");
        codigo.add("mov dx, offset Mensaje");
        codigo.add("int 21h");
        codigo.add("EndM");

        codigo.add("LEER Macro Entrada");
        codigo.add("mov Ah,0Ah");
        codigo.add("mov dx, offset Entrada");
        codigo.add("int 21h");
        codigo.add("EndM");

        codigo.add(".MODEL SMALL");
        codigo.add(".CODE");
        codigo.add("Inicio:");
        codigo.add("mov Ax,@Data");
        codigo.add("mov Ds,Ax");
        codigo.add("");

        // Agregar Salto de línea a data
        data.add("Salto db 10,13,'$'");
    }

    public void abrirArchivo() {
        // Ya inicializado en el constructor
    }

    public void acumularVariable(String declaracion) {
        // A : ENT ; -> A db 6,?,6 dup(24h) and A_num dw 0
        String limpia = declaracion.replace(";", "").trim();
        String[] partes = limpia.split(":");
        if (partes.length >= 2) {
            String nombre = partes[0].trim();
            data.add(nombre + " db 6,?,6 dup(24h)");
            data.add(nombre + "_num dw 0");
        }
    }

    public void capturarVariable(String id) {
        codigo.add("IMPRIME Salto");
        codigo.add("LEER " + id);
        

        codigo.add("xor ax, ax");
        codigo.add("xor cx, cx");
        codigo.add("mov cl, [" + id + " + 1]");
        codigo.add("cmp cl, 0");
        codigo.add("je EtiParseEnd_" + id);
        codigo.add("mov si, offset " + id + " + 2");
        codigo.add("EtiParseLoop_" + id + ":");
        codigo.add("mov dx, 10");
        codigo.add("mul dx");
        codigo.add("xor bx, bx");
        codigo.add("mov bl, [si]");
        codigo.add("sub bl, 30h");
        codigo.add("add ax, bx");
        codigo.add("inc si");
        codigo.add("loop EtiParseLoop_" + id);
        codigo.add("EtiParseEnd_" + id + ":");
        codigo.add("mov " + id + "_num, ax");
        
        
        codigo.add("xor bx, bx");
        codigo.add("mov bl, [" + id + " + 1]");
        codigo.add("mov byte ptr [" + id + " + 2 + bx], '$'");
        
        
        codigo.add("mov Al, [" + id + " + 2]");
        codigo.add("mov " + id + ", Al");
        
        codigo.add("IMPRIME Salto");
        codigo.add("");
    }

    public void imprimirMensaje(String contenido) {
        contenido = contenido.trim();
        if (contenido.startsWith("(") && contenido.endsWith(")")) {
            contenido = contenido.substring(1, contenido.length() - 1).trim();
        }

        if (contenido.startsWith("\"") && contenido.endsWith("\"")) {
            String texto = contenido.substring(1, contenido.length() - 1);
            String idMensaje = "Msg" + contadorMensajes++;
            data.add(idMensaje + " db '" + texto + "','$'");
            codigo.add("IMPRIME " + idMensaje);
        } else {
            codigo.add("IMPRIME " + contenido);
        }
        codigo.add("IMPRIME Salto");
        codigo.add("");
    }

    public void realizarOperacion(String linea) {
        linea = linea.replace(";", "").trim();

        String[] partes = linea.split("=");
        if (partes.length < 2)
            return;

        String destino = partes[0].trim();
        String expresion = partes[1].trim();

        String op = "";

        if (expresion.contains("+"))
            op = "+";
        else if (expresion.contains("-"))
            op = "-";
        else if (expresion.contains("*"))
            op = "*";
        else if (expresion.contains("/"))
            op = "/";
        else if (expresion.contains("\\"))
            op = "\\";
        else if (expresion.contains("%"))
            op = "%";

        if (!op.isEmpty()) {

            String[] operandos = expresion.split(java.util.regex.Pattern.quote(op));

            if (operandos.length < 2)
                return;

            String op1 = operandos[0].trim();
            String op2 = operandos[1].trim();

            codigo.add("; OPERACION " + op);

            codigo.add("xor ax, ax");

           
            if (op1.matches("\\d+")) {
                codigo.add("mov ax, " + op1);
            } else {
                codigo.add("mov ax, " + op1 + "_num");
            }

            
            if (op2.matches("\\d+")) {
                codigo.add("mov bx, " + op2);
            } else {
                codigo.add("mov bx, " + op2 + "_num");
            }

            switch (op) {

                case "+":
                    codigo.add("add ax, bx");
                    break;

                case "-":
                    codigo.add("sub ax, bx");
                    break;

                case "*":
                    codigo.add("mul bx");
                    break;

                case "/":
                    codigo.add("xor dx, dx");
                    codigo.add("div bx");
                    break;

                case "\\":
                    codigo.add("xor dx, dx");
                    codigo.add("div bx");
                    break;

                case "%":
                    codigo.add("xor dx, dx");
                    codigo.add("div bx");
                    codigo.add("mov ax, dx");
                    break;
            }

            codigo.add("mov " + destino + "_num, ax");

            
            codigo.add("mov si, offset " + destino);
            codigo.add("mov byte ptr [si], '$'");
            codigo.add("cmp ax, 0");
            int localLabelNum = contadorMensajes++;
            codigo.add("jne dest_conv_non_zero_" + localLabelNum);
            codigo.add("mov byte ptr [si], '0'");
            codigo.add("mov byte ptr [si + 1], '$'");
            codigo.add("jmp dest_conv_end_" + localLabelNum);
            
            codigo.add("dest_conv_non_zero_" + localLabelNum + ":");
            codigo.add("xor cx, cx");
            codigo.add("dest_div_loop_" + localLabelNum + ":");
            codigo.add("cmp ax, 0");
            codigo.add("je dest_pop_loop_" + localLabelNum);
            codigo.add("xor dx, dx");
            codigo.add("mov bx, 10");
            codigo.add("div bx");
            codigo.add("add dl, 30h");
            codigo.add("push dx");
            codigo.add("inc cx");
            codigo.add("jmp dest_div_loop_" + localLabelNum);
            
            codigo.add("dest_pop_loop_" + localLabelNum + ":");
            codigo.add("pop dx");
            codigo.add("mov [si], dl");
            codigo.add("inc si");
            codigo.add("loop dest_pop_loop_" + localLabelNum);
            codigo.add("mov byte ptr [si], '$'");
            codigo.add("dest_conv_end_" + localLabelNum + ":");

        } else {
            if (expresion.matches("\\d+")) {
                codigo.add("mov ax, " + expresion);
            } else {
                codigo.add("mov ax, " + expresion + "_num");
            }

            codigo.add("mov " + destino + "_num, ax");
            codigo.add("mov si, offset " + destino);
            codigo.add("mov byte ptr [si], '$'");
            codigo.add("cmp ax, 0");
            int localLabelNum = contadorMensajes++;
            codigo.add("jne dest_conv_non_zero_" + localLabelNum);
            codigo.add("mov byte ptr [si], '0'");
            codigo.add("mov byte ptr [si + 1], '$'");
            codigo.add("jmp dest_conv_end_" + localLabelNum);
            
            codigo.add("dest_conv_non_zero_" + localLabelNum + ":");
            codigo.add("xor cx, cx");
            codigo.add("dest_div_loop_" + localLabelNum + ":");
            codigo.add("cmp ax, 0");
            codigo.add("je dest_pop_loop_" + localLabelNum);
            codigo.add("xor dx, dx");
            codigo.add("mov bx, 10");
            codigo.add("div bx");
            codigo.add("add dl, 30h");
            codigo.add("push dx");
            codigo.add("inc cx");
            codigo.add("jmp dest_div_loop_" + localLabelNum);
            
            codigo.add("dest_pop_loop_" + localLabelNum + ":");
            codigo.add("pop dx");
            codigo.add("mov [si], dl");
            codigo.add("inc si");
            codigo.add("loop dest_pop_loop_" + localLabelNum);
            codigo.add("mov byte ptr [si], '$'");
            codigo.add("dest_conv_end_" + localLabelNum + ":");
            
            codigo.add("");
        }
    }

    public void generarCondicion(ArrayList<String> tokens) {
        numsi++;
        PilaSi.push(numsi);

        String op1 = tokens.get(2);
        String operador = tokens.get(3);
        String op2 = tokens.get(4);

        codigo.add("; CONDICION SI " + op1 + " " + operador + " " + op2);
        
        if (op1.matches("\\d+")) {
            codigo.add("mov ax, " + op1);
        } else {
            codigo.add("mov ax, " + op1 + "_num");
        }
        
        if (op2.matches("\\d+")) {
            codigo.add("mov bx, " + op2);
        } else {
            codigo.add("mov bx, " + op2 + "_num");
        }
        
        codigo.add("cmp ax, bx");

        switch (operador) {
            case "==":
                codigo.add("jne EtiSi" + numsi);
                break;
            case ">":
                codigo.add("jle EtiSi" + numsi);
                break;
            case "<":
                codigo.add("jge EtiSi" + numsi);
                break;
            case ">=":
                codigo.add("jl EtiSi" + numsi);
                break;
            case "<=":
                codigo.add("jg EtiSi" + numsi);
                break;
            case "<>":
                codigo.add("je EtiSi" + numsi);
                break;
        }
    }

    public void generarSino() {
        if (!PilaSi.isEmpty()) {
            int n = PilaSi.pop();
            int index = codigo.lastIndexOf("EtiSi" + n + ":");
            if (index != -1) {
                codigo.add(index, "jmp EtiFin" + n);
            } else {
                codigo.add("jmp EtiFin" + n);
            }
            PilaSi.push(-n);
        }
    }

    public void cerrarCondicion() {
        if (!PilaSi.isEmpty()) {
            int n = PilaSi.pop();
            if (n >= 0) {
                codigo.add("EtiSi" + n + ":");
                PilaSi.push(n);
            } else {
                codigo.add("EtiFin" + (-n) + ":");
            }
            codigo.add("");
        }
    }

    public void generarPara(String variable, String desde, String hasta) {
        numPara++;
        PilaPara.push(variable);
        PilaPara.push(String.valueOf(numPara));

        codigo.add("; CICLO PARA " + variable + " INICIA_EN " + desde + " TERMINA_EN " + hasta);
        codigo.add("xor ax, ax");

        if (desde.matches("\\d+")) {
            codigo.add("mov al, " + desde);
        } else {
            codigo.add("mov al, " + desde);
            codigo.add("sub al, 30h");
        }

        if (hasta.matches("\\d+")) {
            codigo.add("mov ah, " + hasta);
        } else {
            codigo.add("mov ah, " + hasta);
            codigo.add("sub ah, 30h");
        }

        codigo.add("push cx");
        codigo.add("xor cx, cx");
        codigo.add("mov cl, ah");
        codigo.add("sub cl, al");
        codigo.add("add cl, 1");

        codigo.add("mov " + variable + ", al");
        codigo.add("add byte ptr [" + variable + "], 30h");
        codigo.add("mov byte ptr [" + variable + " + 1], '$'");

        codigo.add("Etipara" + (numPara - 1) + ":");
    }

    public void cerrarPara() {
        if (PilaPara.size() >= 2) {
            String nStr = PilaPara.pop();
            String variable = PilaPara.pop();
            int n = Integer.parseInt(nStr);

            codigo.add("inc byte ptr [" + variable + "]");
            codigo.add("loop Etipara" + (n - 1));
            codigo.add("pop cx");
            codigo.add("");
        }
    }

    public void generarMientras(ArrayList<String> tokens) {
        numMientras++;
        PilaMientras.push(numMientras);

        String op1 = tokens.get(2);
        String operador = tokens.get(3);
        String op2 = tokens.get(4);

        codigo.add("EtiMientras" + numMientras + ":");
        codigo.add("; CICLO MIENTRAS " + op1 + " " + operador + " " + op2);

        if (op1.matches("\\d+")) {
            codigo.add("mov ax, " + op1);
        } else {
            codigo.add("mov ax, " + op1 + "_num");
        }
        if (op2.matches("\\d+")) {
            codigo.add("mov bx, " + op2);
        } else {
            codigo.add("mov bx, " + op2 + "_num");
        }

        codigo.add("cmp ax, bx");

        switch (operador) {
            case "==":
                codigo.add("jne EtiFinMientras" + numMientras);
                break;
            case ">":
                codigo.add("jbe EtiFinMientras" + numMientras);
                break;
            case "<":
                codigo.add("jae EtiFinMientras" + numMientras);
                break;
            case ">=":
                codigo.add("jb EtiFinMientras" + numMientras);
                break;
            case "<=":
                codigo.add("ja EtiFinMientras" + numMientras);
                break;
            case "<>":
                codigo.add("je EtiFinMientras" + numMientras);
                break;
        }
    }

    public void cerrarMientras() {
        if (!PilaMientras.isEmpty()) {
            int n = PilaMientras.pop();
            codigo.add("jmp EtiMientras" + n);
            codigo.add("EtiFinMientras" + n + ":");
            codigo.add("");
        }
    }

    public void cerrarArchivo() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivoAsm))) {
            for (String s : codigo)
                writer.println(s);
            writer.println("mov Ax,4C00h");
            writer.println("int 21h");
            writer.println("");
            writer.println(".DATA");
            for (String d : data)
                writer.println(d);
            writer.println(".STACK");
            writer.println("END Inicio");
        } catch (IOException e) {
            System.err.println("Error al cerrar ASM: " + e.getMessage());
        }
    }
}