# Spring-boot-rest-api

Servizio avanzato per l'esposizione di CRUD rest documentate con Swagger.

Tra gli aspetti indirizzati:
 - 01
   - Servizio rest Base
 - **02**
   - **Utilizzo di Lombok**
   - **ObjectMapper**
   - **Gestione errori (Error Handler)**
   - **Logging**
   - **Unit Test**
 - 03  
   - Correlation ID
   - Access Log
   - Actuator 
- 04
    - Swagger
- 05
    - Gestione Sicurezza tramite JWT
    - Swagger definizione multipla (document/security)
- 06
    - Approccio contract first
- 07
    - JPA

## Link principali

- **Swagger UI** - http://localhost:8080/swagger-ui.html
- **Actuator** - http://localhost:8080/actuator
- **Api doc**
    - http://localhost:8080/v3/api-docs
    - http://localhost:8080/v3/api-docs.yaml

## Curl per provare il servizio (su windows usare gitbash)

```shell
# getDocuments 
curl -X GET 'http://localhost:8080/documento' -s

# getDocuments 
curl -X GET "http://localhost:8080/documento/1" -s

# delete 
curl -X DELETE "http://localhost:8080/documento/1" -s

# post
curl -X POST "http://localhost:8080/documento" -d "{\"nome\":\"Appendice\",\"descrizione\":\"Appendice 2\"}" -H "accept: application/json" -H "Content-Type: application/json" -s 

# put
curl -X PUT "http://localhost:8080/documento/4" -d "{\"id\":4,\"nome\":\"Appendice\",\"descrizione\":\"Appendice 2 - correzione\"}" -H "accept: application/json" -H "Content-Type: application/json" -s
```
