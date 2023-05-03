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
#### body: formato json

- Authentication(Sign In): /authentication {body}

- Sign Up: /register {body}

- Get All Events: /all_events {token}

- Get Event By Id: /event_by_id {token} {eventId}

- Register To Event: /register_events {token} {eventId}

- Create New Event: /new_event {token} {body}

- Change Event Status: /change_event_status {token} {eventId} {status}

- Vote Event: /vote_event_option {token} {eventId} {optionId}

- Get Marketing Report: /event_marketing_report {token}
-
- Get Options Report: /options_report {token}

### Ejemplos:
- Authentication(Sign In): /authentication {
  "username": "juan.perez",
  "password": "mksiug_865K"
  }

- Sign Up: /register {
  "first_name": "Juan",
  "last_name": "Perez",
  "username": "juan.perez",
  "password": "mksiug_865K",
  "password_confirmation": "mksiug_865K"
  }

- Get All Events: /all_events eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuLnBlcmV6IiwiaWF0IjoxNjgzMDUxNzE0LCJleHAiOjE2ODMxMzgxMTQsInJvbGUiOlt7ImF1dGhvcml0eSI6IlVTRVIifV19.rhoIMOli0ZNhNtDRB6FYm6SVSY7sH3ej1KdC0gZ4rwo

- Get Event By Id: /event_by_id eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuLnBlcmV6IiwiaWF0IjoxNjgzMDUxNzE0LCJleHAiOjE2ODMxMzgxMTQsInJvbGUiOlt7ImF1dGhvcml0eSI6IlVTRVIifV19.rhoIMOli0ZNhNtDRB6FYm6SVSY7sH3ej1KdC0gZ4rwo 644f591f2a0a680ea28bd448

- Register To Event: /register_events eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuLnBlcmV6IiwiaWF0IjoxNjgzMDUxNzE0LCJleHAiOjE2ODMxMzgxMTQsInJvbGUiOlt7ImF1dGhvcml0eSI6IlVTRVIifV19.rhoIMOli0ZNhNtDRB6FYm6SVSY7sH3ej1KdC0gZ4rwo 644f591f2a0a680ea28bd448

- Create New Event: /new_event eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuLnBlcmV6IiwiaWF0IjoxNjgzMDUxNzE0LCJleHAiOjE2ODMxMzgxMTQsInJvbGUiOlt7ImF1dGhvcml0eSI6IlVTRVIifV19.rhoIMOli0ZNhNtDRB6FYm6SVSY7sH3ej1KdC0gZ4rwo {
  "name": "TACSSSSSS",
  "description": "TACSSSS",
  "event_options": [
  {
  "date_time": "2023-05-02T18:25:18.410Z"
  }
  ]
  }

- Change Event Status: /change_event_status eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuLnBlcmV6IiwiaWF0IjoxNjgzMDUxNzE0LCJleHAiOjE2ODMxMzgxMTQsInJvbGUiOlt7ImF1dGhvcml0eSI6IlVTRVIifV19.rhoIMOli0ZNhNtDRB6FYm6SVSY7sH3ej1KdC0gZ4rwo 644f591f2a0a680ea28bd448 VOTE_PENDING

- Vote Event: /vote_event_option eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuLnBlcmV6IiwiaWF0IjoxNjgzMDUxNzE0LCJleHAiOjE2ODMxMzgxMTQsInJvbGUiOlt7ImF1dGhvcml0eSI6IlVTRVIifV19.rhoIMOli0ZNhNtDRB6FYm6SVSY7sH3ej1KdC0gZ4rwo 644f591f2a0a680ea28bd448 644f591f2a0a680ea28bd446

- Get Marketing Report: /event_marketing_report eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuLnBlcmV6IiwiaWF0IjoxNjgzMDUxNzE0LCJleHAiOjE2ODMxMzgxMTQsInJvbGUiOlt7ImF1dGhvcml0eSI6IlVTRVIifV19.rhoIMOli0ZNhNtDRB6FYm6SVSY7sH3ej1KdC0gZ4rwo
- 
- Get Options Report: /options_report eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuLnBlcmV6IiwiaWF0IjoxNjgzMDUxNzE0LCJleHAiOjE2ODMxMzgxMTQsInJvbGUiOlt7ImF1dGhvcml0eSI6IlVTRVIifV19.rhoIMOli0ZNhNtDRB6FYm6SVSY7sH3ej1KdC0gZ4rwo
