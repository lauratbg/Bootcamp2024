# Bootcamp Capgemini 2024

<div>
      <img src='https://github.com/lauratbg/Bootcamp2024/blob/main/images/capgemini.jpg' align='left' style="width: 200px; margin-right: 20px;">
</div>
<br>
Este repositorio tiene como objetivo mostrar mi progreso y los proyectos desarrollados durante el bootcamp en Capgemini, demostrando mis habilidades en el uso de Spring Boot y Angular para el desarrollo de aplicaciones modernas y escalables.
<br>
Se pueden encontrar distintos proyectos con ejemplos de distintas herramientas así como funcionalidades.
<br>
<br>
<br>
<br>

## demo
> [!WARNING]
> Este proyecto es un proyecto demo de prueba que combina muchas cosas de manera desordenada y caótica. Es un "batiburrillo" experimental sin estructura definida.

Este proyecto es un espacio donde estoy aprendiendo y practicando varias tecnologías de Java, incluyendo:

- **Spring Framework**: Para el desarrollo de aplicaciones robustas y escalables.
- **Anotaciones JPA (Java Persistence API)**: Para la gestión de la persistencia de datos de manera eficiente.
- **Lombok**: Para reducir el código repetitivo a través de anotaciones.
- **Spring Data**: Para facilitar el acceso a los datos en aplicaciones Spring.
- **Podman**: Para gestionar contenedores, incluyendo la configuración de una base de datos MySQL con el esquema Sakila.
- **Proyecciones y Pageable**: Para optimizar y manejar grandes conjuntos de datos de manera eficiente.
- **Serialización y deserialización con Jackson**: Para JSON y XML.
- **Servicios REST**: Para la implementación y consumo de servicios web robustos y escalables.

### Tecnologías Utilizadas

- **Java**: Lenguaje de programación principal.
- **Spring Boot**: Framework para crear aplicaciones Spring autónomas y de producción de manera sencilla.
- **Spring Data JPA**: Simplificación del acceso a la base de datos mediante JPA.
- **Lombok**: Herramienta para eliminar el código repetitivo, como getters, setters, y constructores.
- **MySQL**: Base de datos utilizada para el esquema Sakila.
- **Podman**: Herramienta para gestionar contenedores.
- **Proyecciones JPA**: Para seleccionar y manipular solo los datos necesarios de las entidades.
- **Pageable**: Para manejar la paginación y ordenación de grandes conjuntos de datos.
- **Jackson**: Biblioteca para la serialización y deserialización de objetos en JSON y XML.

También se está usando como servidor del proyecto **demo-ws**

## demo-maven
En este proyecto estoy aprendiendo y practicando el uso de anotaciones en pruebas unitarias y de integración con JUnit 5. Entre las anotaciones se incluyen:

- **@DisplayName**: Para proporcionar nombres legibles y descriptivos a las pruebas.
- **@Nested**: Para agrupar pruebas relacionadas dentro de clases internas.
- **@RepeatedTest**: Para ejecutar una prueba varias veces.
- **@ParameterizedTest**: Para ejecutar una prueba con diferentes parámetros.
- **@CsvSource**: Para proporcionar datos a pruebas parametrizadas desde valores CSV.
- **@Retention**: Para especificar cuánto tiempo se retienen las anotaciones.
- **@Tag**: Para agrupar y filtrar pruebas según etiquetas específicas.
- **@Target**: Para indicar los elementos de programa a los que se aplica una anotación.
- **@Disabled**: Para deshabilitar pruebas temporalmente.

## gilded-rose-kata
Este proyecto contiene mi implementación y refactorización de la kata de refactorización "Gilded Rose", basada en la descripción y requisitos proporcionados en  [Gilded Rose Refactoring Kata](https://github.com/emilybache/GildedRose-Refactoring-Kata/blob/master/GildedRoseRequirements_es.md) de Emily Bache, siendo el objetivo la creación de tests.

## catalogo
> [!NOTE]
> Este proyecto de catálogo en el repositorio está bien organizado y estructurado. Aquí es donde aprendí a utilizar microservicios de manera efectiva.

Aquí se encuentra un sistema diseñado para gestionar un catálogo de películas. A través de este proyecto, se ha implementado una estructura completa de relaciones entre diferentes entidades que forman parte del catálogo. 

### Entidades del Proyecto
El proyecto incluye las siguientes entidades:

- **Actor**: Representa a los actores que participan en las películas.
- **Category**: Categoriza las películas en distintos géneros.
- **Film**: Representa a las películas del catálogo.
- **FilmActor**: Gestiona la relación entre las películas y los actores.
- **FilmCategory**: Gestiona la relación entre las películas y las categorías.
- **Language**: Define los idiomas disponibles para las películas.
- **FilmActorPK**: Llave primaria compuesta para la relación FilmActor.
- **FilmCategoryPK**: Llave primaria compuesta para la relación FilmCategory.

### Validaciones
Se han añadido diversas validaciones para asegurar la integridad y consistencia de los datos dentro del sistema. Cada atributo de las entidades ha sido cuidadosamente validado para cumplir con los requisitos específicos del proyecto.

### Repositorios y Servicios JPA
Para la persistencia y manipulación de datos, se han implementado repositorios y servicios utilizando JPA (Java Persistence API). Esto asegura un manejo eficiente y robusto de las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre las entidades del catálogo.

### Testing
El proyecto incluye una serie de tests para asegurar el correcto funcionamiento del sistema:

- **Tests de Validación**: Verifican que las validaciones de los atributos de las entidades funcionan correctamente.
- **Tests de Repositorios**: Aseguran que las operaciones de persistencia en los repositorios se realizan de manera adecuada.
- **Tests de Servicios**: Validan el funcionamiento de los servicios JPA, incluyendo las operaciones CRUD.
- **Tests de Servicios REST**: Verifican la correcta implementación y funcionamiento de los endpoints REST, asegurando que las APIs devuelvan los resultados esperados y gestionen adecuadamente las peticiones y respuestas.
  
Se han desarrollado suficientes tests para demostrar mi habilidad y conocimiento en la implementación de un sistema robusto y bien estructurado, aunque no se ha testeado completamente todo el proyecto.

Con este proyecto, he buscado implementar un sistema completo y funcional que demuestra mi capacidad para gestionar relaciones entre entidades, realizar validaciones adecuadas, y utilizar JPA para la persistencia de datos, todo ello complementado con un conjunto significativo de pruebas para garantizar la calidad del software.

## lotes-java 
Este proyecto es una implementación de Spring Batch diseñada para procesar datos de personas a partir de archivos `.csv`, bases de datos y archivos XML. Está orientado a aprender y comprender el uso de Spring Batch, incluyendo configuraciones de base de datos, DTOs y entidades, item processors, job listeners y configuraciones de jobs batch.
El objetivo principal del proyecto es demostrar cómo configurar y utilizar Spring Batch para realizar varias tareas de procesamiento de datos. 

## lotes-xml
Proyecto similar al anterior pero empleando configuraciones basadas en XML.

## demo-ws
### Get-Started
Para comprobar que funciona, hay que tener funcionando la base de datos en podman. Después, hay que tener ejecutando el servidor que es este proyecto y por último, ejecutar el cliente que está en el proyecto **demo**.

## Servidores
### ms.eureka.server  
El servidor Eureka actúa como un registro de servicios donde las aplicaciones cliente pueden registrar sus instancias y descubrir otros servicios registrados. Facilita la escalabilidad y resiliencia en arquitecturas de microservicios.
### ms.apigateway.server
El servidor API Gateway actúa como un punto de entrada unificado para los microservicios, gestionando el enrutamiento, autenticación y balanceo de carga. Facilita la comunicación segura y eficiente entre los clientes y los servicios internos.
### ms.autentication
El servicio de autenticación gestiona la verificación de identidades de usuarios y aplicaciones, asegurando que solo los usuarios autorizados accedan a los recursos. Proporciona tokens de seguridad y tokens de resfresco que se usan para la validación en otros servicios.
### ms.config.server
El servidor de configuración centraliza y gestiona las configuraciones de las aplicaciones distribuidas.

## curso
Este proyecto es una introducción a Angular. Se ha creado para familiarizarse con los conceptos básicos y las herramientas proporcionadas por el framework Angular. A continuación, se detallan los comandos más importantes utilizados en este proyecto. Contiene un ejercicio de la implementación de una calculadora.

### Comandos
> [!NOTE]
> Usar ng s -o para ver la aplicación en el navegador.

- **ng s -o**: para abrir automáticamente el navegador al ejecutar **ng serve**. 
- **ng new <nombre>**: para crear un nuevo proyecto angular
- **ng serve**: levantar servidor de desarollo
- **ng update**: actualizar las dependencias y herramientas del proyecto Angular a sus últimas versiones compatibles
- **ng build**: empaquetar y generar lo necesario, compilar la aplicación en una versión lista para producción
- 
<br>
Creación de módulos
<br>

- **ng g m security**
- **ng g m config**
- **ng g m ../lib/MyCore**

## sakila
Proyecto Angular en el que wse encuentra un frontend básico para el catálogo. Se puede ver la lista de categorías, actores y películas. Hay el suficiente contenido para demostrar mis conocimientos.
<br>akira
Se debe tener arrancado el **catalogo**, **ms.apigateway.server**, **ms.autentication** y **ms.eureka.server**, así como el contenedor mysql-sakila




