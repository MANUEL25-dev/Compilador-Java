package compilador;

public class ExpresionesRegulares {

    public static final String ID = "[A-Z]+";
    public static final String TIPO = "(ENT|FLOT|CAD)";
    public static final String ENTERO = "[0-9]+";
    public static final String FLOTANTE = "[0-9]+\\.[0-9]+";
    public static final String CADENA = "\"[^\"]*\"";
    public static final String OPERANDO = "(" + ID + "|" + ENTERO + "|" + FLOTANTE + "|" + CADENA + ")";
    public static final String OPREL = "(==|>=|<=|<>|>|<)";
    public static final String OPLOG = "(AND|OR)";
    public static final String OPARITM = "(\\+|\\-|\\*|\\/|\\\\|\\%|\\^)";
    public static final String CONDSIMPLE = "\\(\\s*" + OPERANDO + "\\s*" + OPREL + "\\s*" + OPERANDO + "\\s*\\)";
    public static final String CONDCOMP = "\\(\\s*" + CONDSIMPLE + "\\s*" + OPLOG + "\\s*" + CONDSIMPLE + "\\s*\\)";
    public static final String CONDICION = "(" + CONDSIMPLE + "|" + CONDCOMP + ")";
    public static final String DECLARACION = "^" + ID + "\\s*:\\s*" + TIPO + "\\s*;$";
    public static final String CAPTURAR = "^CAPTURAR\\s*\\(\\s*" + ID + "\\s*\\)\\s*;$";
    public static final String MOSTRAR = "^MOSTRAR\\s*\\(\\s*(" + OPERANDO + ")\\s*\\)\\s*;$";
    public static final String ASIGNACION = "^" + ID + "\\s*=\\s*" + OPERANDO + "\\s*;$";
    public static final String ARITM = "^" + ID + "\\s*=\\s*" + OPERANDO + "\\s*" + OPARITM + "\\s*" + OPERANDO + "\\s*;$";
    public static final String SI = "^SI\\s+" + CONDICION + "\\s+ENTONCES$";
    public static final String FIN_SI = "^FIN_SI:?$";
    public static final String SINO = "^SINO$";
    public static final String SINO_FIN = "^SINO_FIN:?$";
    public static final String PARA = "^PARA\\s+" + ID + "\\s+INICIA_EN\\s+" + OPERANDO + "\\s+TERMINA_EN\\s+" + OPERANDO + "\\s+HACER$";
    public static final String FIN_PARA = "^FIN_PARA:?$";
    public static final String MIENTRAS = "^MIENTRAS\\s+" + CONDICION + "\\s+HACER$";
    public static final String FIN_MIENTRAS = "^FIN_MIENTRAS:?$";
    public static final String REPETIR = "^REPETIR$";
    public static final String HASTA = "^HASTA\\s*" + CONDICION + "$";

    public static boolean validar(String regex, String texto) {
        return texto.matches(regex);
    }
}