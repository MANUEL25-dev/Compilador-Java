package compilador;
/*Compilador 
  Juan Manuel Lambaren Torres
  23630167
  Lenguajes y Automatas
*/
public class Compilador {

    public static void main(String[] args) {

        String archivo = "archivo.txt";

        TablaSimbolos tablaSimbolos = new TablaSimbolos();
        TablaErrores tablaErrores = new TablaErrores();
        CatalogodeErrores catalogo = new CatalogodeErrores();

        AnalizadorLexico lexico = new AnalizadorLexico(tablaSimbolos, tablaErrores);
        AnalicadorSintactico sintactico = new AnalicadorSintactico(tablaErrores);
        AnalizadorSemantico semantico = new AnalizadorSemantico(tablaSimbolos, tablaErrores, catalogo);
        
        lexico.analizar(archivo);
        sintactico.analizar(archivo);
        semantico.analizar(archivo);

        if (tablaErrores.hayErrores()) {
            tablaErrores.imprimir(catalogo);
        }
    }
}