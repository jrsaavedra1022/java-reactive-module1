# Sistema de libros, series y peliculas Online

Este simula expone una cartelera de peliculas, series, libros y revistar online, para que cada usuario pueda ir consumiendo de manera gratuita.

## Endpoints Disponibles

server: http://localhost:8080

*_Libros_*

| Método | Ruta          | Descripción                            |
|--------|---------------|----------------------------------------|
| GET    | /books        | Obtener todos los recursos disponibles |
| POST   | /books        | Crear un nuevo recurso                 |
| GET    | /books/{id}   | Obtener detalles de un recurso por id  |
| PUT    | /books/{id}   | Actualizar un recurso existente        |
| DELETE | /books/{id}   | Eliminar un recurso por id             |
| POST   | /books/search | Buscar libro por título con like %%    |


*_Revistas_*

| Método  | Ruta          | Descripción                            |
| ------- |---------------|----------------------------------------|
| GET     | /magazines        | Obtener todos los recursos disponibles |
| POST    | /magazines        | Crear un nuevo recurso                 |
| GET     | /magazines/{id}   | Obtener detalles de un recurso por id  |
| PUT     | /magazines/{id}   | Actualizar un recurso existente        |
| DELETE  | /magazines/{id}   | Eliminar un recurso por id             |

*_Peliculas_*

| Método  | Ruta          | Descripción                            |
| ------- |---------------|----------------------------------------|
| GET     | /movies        | Obtener todos los recursos disponibles |
| POST    | /movies        | Crear un nuevo recurso                 |
| GET     | /movies/{id}   | Obtener detalles de un recurso por id  |
| PUT     | /movies/{id}   | Actualizar un recurso existente        |
| DELETE  | /movies/{id}   | Eliminar un recurso por id             |

*_Series_*

| Método  | Ruta          | Descripción                            |
| ------- |---------------|----------------------------------------|
| GET     | /series        | Obtener todos los recursos disponibles |
| POST    | /series        | Crear un nuevo recurso                 |
| GET     | /series/{id}   | Obtener detalles de un recurso por id  |
| PUT     | /series/{id}   | Actualizar un recurso existente        |
| DELETE  | /series/{id}   | Eliminar un recurso por id             |

*_Capítulos_*

| Método  | Ruta          | Descripción                            |
| ------- |---------------|----------------------------------------|
| GET     | /chapters        | Obtener todos los recursos disponibles |
| POST    | /chapters        | Crear un nuevo recurso                 |
| GET     | /chapters/{id}   | Obtener detalles de un recurso por id  |
| PUT     | /chapters/{id}   | Actualizar un recurso existente        |
| DELETE  | /chapters/{id}   | Eliminar un recurso por id             |

*_HealthCheck_*

| Método  | Ruta              | Descripción                               |
| ------- |-------------------|-------------------------------------------|
| GET     | /actuator/health  | Verificar el HealthCheck de la aplicación |

## Uso

### Obtener datos relacionados y usar endpoints
 Para usar los endpoints importe la colección de postman y utilice todas las funcionalidades
    ``` codesistemas-movies.postman_collection.json```
 
Recuerde para ello de configurar la variable de entorno:
```server: http://localhost:8080```