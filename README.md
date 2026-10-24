# Compilador en Java

Compilador desarrollado en Java utilizando NetBeans, como proyecto académico enfocado en los fundamentos del diseño y funcionamiento de los compiladores. El proyecto implementa diferentes etapas de análisis del código fuente y la generación de código ensamblador para trabajar con el emulador emu8086.

## Características

- **Análisis léxico:** identifica los componentes y elementos del código fuente.
- **Análisis sintáctico:** verifica la estructura y organización de las expresiones e instrucciones.
- **Análisis semántico:** revisa aspectos semánticos del código fuente.
- **Detección y manejo de errores:** identifica errores durante las etapas de análisis.
- **Tabla de símbolos:** organiza información relacionada con los elementos identificados durante el análisis.
- **Generación de código:** produce código relacionado con lenguaje ensamblador.
- **Catálogo de errores:** permite organizar los errores identificados por el compilador.
- **Pruebas con emu8086:** permite trabajar con el código ensamblador generado y comprobar su comportamiento en el emulador, según la compatibilidad del código producido.

## Tecnologías utilizadas

- **Java:** lenguaje de programación utilizado para desarrollar el compilador.
- **NetBeans:** entorno de desarrollo integrado (IDE).
- **Apache Ant:** herramienta de construcción y gestión del proyecto.
- **emu8086:** emulador utilizado para trabajar con código ensamblador del procesador 8086.

## Estructura del proyecto

```text
compilador-java/
├── src/
│   └── compilador/
│       ├── AnalizadorLexico.java
│       ├── AnalizadorSintactico.java
│       ├── AnalizadorSemantico.java
│       ├── CatalogodeErrores.java
│       ├── Compilador.java
│       ├── ErrorSemantico.java
│       ├── ExpresionesRegulares.java
│       ├── GeneradorCodigo.java
│       ├── Tabla.java
│       ├── TablaErrores.java
│       └── TablaSimbolos.java
├── nbproject/
├── archivo.txt
├── archivo.asm
├── build.xml
├── manifest.mf
└── .gitignore
```

## Descripción de los archivos principales

- `AnalizadorLexico.java`: procesa los elementos léxicos del código fuente.
- `AnalizadorSintactico.java`: analiza la estructura sintáctica del código.
- `AnalizadorSemantico.java`: realiza las comprobaciones semánticas implementadas.
- `CatalogodeErrores.java`: contiene el catálogo de errores del compilador.
- `Compilador.java`: contiene la lógica principal del programa.
- `GeneradorCodigo.java`: se encarga de generar el código de salida.
- `TablaSimbolos.java`: gestiona la tabla de símbolos.
- `TablaErrores.java`: gestiona la información de los errores.
- `archivo.txt`: archivo utilizado como entrada o prueba.
- `archivo.asm`: archivo relacionado con el código ensamblador generado.

## Cómo abrir el proyecto

1. Clona o descarga este repositorio desde GitHub.
2. Abre NetBeans.
3. Selecciona **File - Open Project**.
4. Busca y selecciona la carpeta del proyecto.
5. Espera a que NetBeans cargue la configuración.
6. Ejecuta la clase principal del compilador.

## Cómo trabajar con emu8086

1. Ejecuta el compilador desde NetBeans.
2. Utiliza un archivo de entrada compatible con las reglas implementadas en el proyecto.
3. Genera el código ensamblador mediante el compilador.
4. Abre el archivo `.asm` resultante con emu8086, si su sintaxis es compatible.
5. Ensambla y ejecuta el programa dentro del emulador para comprobar su funcionamiento.

## Objetivo del proyecto

Aplicar los conocimientos de teoría de compiladores mediante la implementación de las etapas de análisis léxico, sintáctico y semántico, así como la generación de código ensamblador.

## Propósito académico

Este proyecto permite reforzar los conocimientos de programación en Java, diseño de compiladores, manejo de errores, análisis del código fuente y fundamentos del lenguaje ensamblador.

-Proyecto académico desarrollado con fines educativos.
Lambaren Torres Juan Manuel
Materia: Lenguajes y Automatas


<img width="1537" height="777" alt="image" src="https://github.com/user-attachments/assets/acfd8f29-8e0f-4d30-a21c-290a4b5b464d" />
<img width="1446" height="892" alt="image" src="https://github.com/user-attachments/assets/7c091696-4f21-47b2-a3a1-02fdf10f7703" />
<img width="1487" height="897" alt="image" src="https://github.com/user-attachments/assets/8e52d2d4-2dbd-43d8-8769-c0023d25ef5d" />
<img width="1237" height="852" alt="image" src="https://github.com/user-attachments/assets/4adba7d8-e64a-4300-9f37-c596acf3a6c9" />
<img width="1167" height="831" alt="image" src="https://github.com/user-attachments/assets/b2849f00-5f83-4831-a4a5-80ce0f58c1d2" />
<img width="1616" height="817" alt="image" src="https://github.com/user-attachments/assets/d05fcda3-8f6b-42f5-ad71-2d7f948cd27d" />
