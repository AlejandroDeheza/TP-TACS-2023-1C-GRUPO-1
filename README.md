# tp-tacs-1C-2023
## Instrucción de pruebas
- mvn clean install(en directorio backend)
- mvn clean package(en directorio backend)
- Swagger: http://localhost:8091/swagger-ui/index.html#/
- Registrar un nuevo usuario(si no existe, si ya existe, con el usuario y password hacer Authentication)
- Obtener el access token
- Loguear el access token en [Authorize]

# Utilizando Makefile for usage with docker
- make first (First init of the project)
- make build (Build the project)
- make start (Start the project)
- make clean (Clean the project)
- make stop (Stop the project)
- make help (Show the help)

## Seguridad
- Spring Security agrega clases para filtrar antes de que la petición HTTP llegue al DispatcherServlet, 
comprueba los estados de Autenticación y Autorización antes de que la petición llegue al 
DispatcherServlet y luego a los controladores.

    ![Image text](https://github.com/tp-tacs-2023/tp-tacs-1C-2023/blob/entrega-1/img/seguridad.png?raw=true)
