# tp-tacs-1C-2023
- application.yml host: mongo
- mvn clean install(in backend)
- mvn clean package(in backend)
- docker image build -t backend:latest . 
- docker-compose up -d
- Swagger: http://localhost:8091/swagger-ui/index.html#/
- Register a new user
- Authenticate the user(get a access token)
- Put the access token in Authorize

## Seguridad
- Spring Security agrega clases para filtrar antes de que la petición HTTP llegue al DispatcherServlet, 
comprueba los estados de Autenticación y Autorización antes de que la petición llegue al 
DispatcherServlet y luego a los controladores.

