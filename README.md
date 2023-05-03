# tp-tacs-1C-2023
## Build and Run
- docker-compose up -d

## Rebuild and Run
- docker-compose up --build -d

## Backend
- Swagger: http://localhost:8091/swagger-ui/index.html#/
- Registrar un nuevo usuario (si no existe, si ya existe, con el usuario y password hacer Authentication)
- Obtener el access token
- Loguear el access token en [Authorize]

## Frontend
- http://localhost:3000

## Telegram Bot
- Bot name: tacs_telegram_bot

### Formato del comando: 
- TYPE={SERVICE_TYPE}|TOKEN={TOKEN}|EVENT_ID={EVENT_ID}|OPTION_ID={OPTION_ID}|STATUS={STATUS}|BODY={BODY}
### SERVICE_TYPE: son los tipos de services a consumir
- AUTH_AUTHENTICATION
- AUTH_REGISTER
- EVENTS_ALL
- EVENTS_BY_ID,
- EVENTS_REGISTER
- EVENTS_CHANGE_STATUS
- EVENTS_VOTE
- MONITOR_MARKETING_REPORT
- MONITOR_OPTIONS_REPORT
### TOKEN: es el jwt que devuelve al consumir AUTH_AUTHENTICATION o AUTH_REGISTER
### EVENT_ID: id del evento
### OPTION_ID: id de la opcion de un evento
### STATUS: para cambiar el estado de un evento
### BODY: en formato JSON
### Ejemplos para consumir los servicios:
- AUTH_AUTHENTICATION: TYPE=AUTH_AUTHENTICATION|BODY={
  "username": "juan.perez",
  "password": "mksiug_865K"
  }
- AUTH_REGISTER: TYPE=AUTH_REGISTER|BODY={
  "first_name": "Juan",
  "last_name": "Perez",
  "username": "juan.perez",
  "password": "mksiug_865K",
  "password_confirmation": "mksiug_865K"
  }

- EVENTS_ALL: TYPE=EVENTS_ALL|TOKEN=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuLnBlcmV6IiwiaWF0IjoxNjgzMDUxNzE0LCJleHAiOjE2ODMxMzgxMTQsInJvbGUiOlt7ImF1dGhvcml0eSI6IlVTRVIifV19.rhoIMOli0ZNhNtDRB6FYm6SVSY7sH3ej1KdC0gZ4rwo
- EVENTS_BY_ID: TYPE=EVENTS_BY_ID|TOKEN=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuLnBlcmV6IiwiaWF0IjoxNjgzMDUxNzE0LCJleHAiOjE2ODMxMzgxMTQsInJvbGUiOlt7ImF1dGhvcml0eSI6IlVTRVIifV19.rhoIMOli0ZNhNtDRB6FYm6SVSY7sH3ej1KdC0gZ4rwo|EVENT_ID=644f591f2a0a680ea28bd448
- EVENTS_REGISTER: TYPE=EVENTS_REGISTER|TOKEN=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuLnBlcmV6IiwiaWF0IjoxNjgzMDUxNzE0LCJleHAiOjE2ODMxMzgxMTQsInJvbGUiOlt7ImF1dGhvcml0eSI6IlVTRVIifV19.rhoIMOli0ZNhNtDRB6FYm6SVSY7sH3ej1KdC0gZ4rwo|EVENT_ID=644f591f2a0a680ea28bd448
- EVENTS_CREATE: TYPE=EVENTS_CREATE|TOKEN=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuLnBlcmV6IiwiaWF0IjoxNjgzMDUxNzE0LCJleHAiOjE2ODMxMzgxMTQsInJvbGUiOlt7ImF1dGhvcml0eSI6IlVTRVIifV19.rhoIMOli0ZNhNtDRB6FYm6SVSY7sH3ej1KdC0gZ4rwo|BODY=
  {
  "name": "TACSSSSSS",
  "description": "TACSSSS",
  "event_options": [
  {
  "date_time": "2023-05-02T18:25:18.410Z"
  }
  ]
  }
- EVENTS_CHANGE_STATUS: TYPE=EVENTS_CHANGE_STATUS|TOKEN=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuLnBlcmV6IiwiaWF0IjoxNjgzMDUxNzE0LCJleHAiOjE2ODMxMzgxMTQsInJvbGUiOlt7ImF1dGhvcml0eSI6IlVTRVIifV19.rhoIMOli0ZNhNtDRB6FYm6SVSY7sH3ej1KdC0gZ4rwo|EVENT_ID=644f591f2a0a680ea28bd448|STATUS=VOTE_PENDING
- EVENTS_VOTE: TYPE=EVENTS_VOTE|TOKEN=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuLnBlcmV6IiwiaWF0IjoxNjgzMDUxNzE0LCJleHAiOjE2ODMxMzgxMTQsInJvbGUiOlt7ImF1dGhvcml0eSI6IlVTRVIifV19.rhoIMOli0ZNhNtDRB6FYm6SVSY7sH3ej1KdC0gZ4rwo|EVENT_ID=644f591f2a0a680ea28bd448|OPTION_ID=644f591f2a0a680ea28bd446

- MONITOR_MARKETING_REPORT: TYPE=MONITOR_MARKETING_REPORT|TOKEN=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuLnBlcmV6IiwiaWF0IjoxNjgzMDUxNzE0LCJleHAiOjE2ODMxMzgxMTQsInJvbGUiOlt7ImF1dGhvcml0eSI6IlVTRVIifV19.rhoIMOli0ZNhNtDRB6FYm6SVSY7sH3ej1KdC0gZ4rwo
- MONITOR_OPTIONS_REPORT: TYPE=MONITOR_OPTIONS_REPORT|TOKEN=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuLnBlcmV6IiwiaWF0IjoxNjgzMDUxNzE0LCJleHAiOjE2ODMxMzgxMTQsInJvbGUiOlt7ImF1dGhvcml0eSI6IlVTRVIifV19.rhoIMOli0ZNhNtDRB6FYm6SVSY7sH3ej1KdC0gZ4rwo
