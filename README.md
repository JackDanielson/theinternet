# The Internet
Test Suite de Playwright para https://the-internet.herokuapp.com/login

## Overview
Test automatizados para https://the-internet.herokuapp.com/login requerido, usando Playwright con Java.

## Tecnologias Usadas:
- Java 21
- Playwright
- Maven
- JUnit
- ExtentReports
- Gson

Explicación del Stack:

Utilicé Java, en su ultima versión, y para hacer web testing en Java si bien existen varios frameworks que permiten automatizar UI, decidí ir por uno en el que me permite realizar tests facilmente y que evite flaky tests: Playwright.
Usé Maven como gestor de repositorio por su facilidad, el pom.xml es un archivo de tamaño pequeño que me permite con pocas lineas traer todas las librerias necesarias con facilidad.
A la hora de hacer reportes, ExtentReports es una herramienta que me permite con pequeñas modificaciones mostrar un reporte de la ejecucion, y datos como cuanto tiempo taardó un test y permite facilmente mostrar los pasos.
Con JUnit se generó el archivo Runner que me permite agrupar una suite en particular, en este caso es sencilla al tener solo una suite de un Archivo, pero permite flexibilidad a la hora de definir que ejecutar.


## Instalación y Ejecución
### 1. Clonar Repo:
```bash
git clone https://github.com/JackDanielson/theinternet.git
cd theinternet
```
### 2. Instalar y correr:
```bash
mvn clean test -U -Dtest=LoginSuite
```

## Reportes

Los Reportes se generan en la carpeta /target/ReportePlaywright_*.html, donde * será la fecha y hora de la ejecución del reporte.

# Casos de prueba

En esta Test Suite se detectaron:

* Login Correcto*
* Login Incorrecto por Usuario inexistente*
* Login Incorrecto por Contraseña inexistente*
* Login Incorrecto por falta de Usuario*
* Login Incorrecto por falta de Contraseña*
* Login Incorrecto por falta de credenciales (Usuario Y Contraseña)*
* Login y Logout correcto*
- Vulnerabilidad de SQL Injection
- Logout por Tiempo Expirado

Marcadas con * las que se Automatizaron, por importantes y repetitivas. La Vulnerabilidad de SQL Injection no se automatizaría y sería mas una prueba de vulnerabilidad y no una prueba funcional, ademas que al ser una pantalla de prueba no existe riesgo de inyeccion, pero es importnate
Logout por tiempo fuera es importante testear, por eso lo considero, pero no hay indicios que haya alguna expiración por tiempo 

# Consideraciones

Se decidió usar Page Object Model para evitar hardcodear los locators en la parte de Test, entonces queda mas limpio
Se utilizaron dos clases Helper, una que "traduce" el color del Hexadecimal a RGB para la validacion del color del mensaje, y otra que permite leer Credenciales
En vez de hardcodear las credenciales, se crea un archivo credenciales.json para guardar todas las combinaciones de credenciales posibles. Las credenciales se leen desde este archivo en vez de hardcodearlas.
