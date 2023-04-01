# Spring-boot-rest-api

Servizio avanzato per l'esposizione di CRUD rest documentate con Swagger.

Tra gli aspetti indirizzati:
 - 01
   - Servizio rest Base
 - 02
   - Utilizzo di Lombok
   - ObjectMapper
   - Gestione errori (Error Handler)
   - Logging
   - Unit Test
 - 03  
   - Correlation ID
   - Access Log
   - Actuator
 - 04
   - Swagger
 - **05**
   - **Gestione Sicurezza tramite JWT**
   - **Swagger definizione multipla (document/security)**
  - 06
    - Approccio contract first
 - 07
   - JPA
   - Caching - Caffeine
 - 08
   - Integration test


## Link principali
- **Swagger UI** - http://localhost:8082/swagger-ui.html
- **Actuator** - http://localhost:8082/actuator
- **Api doc**
    - http://localhost:8082/v3/api-docs
    - http://localhost:8082/v3/api-docs.yaml

## Curl per provare il servizio (su windows usare gitbash)

```shell
# getDocuments 
curl "http://localhost:8082/api/v1/document" -s

# getDocuments 
curl "http://localhost:8082/api/v1/document/1" -s

# delete 
curl -X DELETE "http://localhost:8082/api/v1/document/1" -s

# post
curl -X POST "http://localhost:8082/api/v1/document" -d "{\"id\":4,\"name\":\"Appendice\",\"description\":\"Appendice 2\"}" -H "accept: application/json" -H "Content-Type: application/json" -s 

# put
curl -X PUT "http://localhost:8082/api/v1/document" -d "{\"id\":4,\"name\":\"Appendice\",\"description\":\"Appendice 2 - correzione\"}" -H "accept: application/json" -H "Content-Type: application/json" -s
```

## Code quality

```shell
# Report Jacoco + SonarQube
mvn org.jacoco:jacoco-maven-plugin:prepare-agent test org.jacoco:jacoco-maven-plugin:report
mvn sonar:sonar "-Dsonar.projectKey=allitude-spring-boot-restapi" "-Dsonar.host.url=http://localhost:9000" "-Dsonar.login=67c3ece48b0c72e568899e4cb4fd5be3d61416da"
```

## Note operative

### Jwt

- Generazione le chiavi (pubblica e privata) di cifratura

  ```shell
    # Generazione delle chiavi (pubblica e privata)
    openssl req -nodes -x509 -newkey rsa:4096 -keyout private_key_jwt.pem -out public_key_jwt.pem -days 7300 -subj '/CN=localhost'
  ```

- Aggiungere i certificati al progetto.

- Aggiornare `application.properties`

  - main
  
    ```properties
    server.port=8082
    
    app.version=@project.version@
    
    cookbook.public_key_jwt=classpath:public_key_jwt.pem
    ```
  - test
  
    ```properties
    server.port=8082
    
    app.version=@project.version@
    
    cookbook.public_key_jwt=classpath:public_key_jwt.pem
    cookbook.private_key_jwt=classpath:private_key_jwt.pem
    ```
  
- Aggiornare il pom:

  ```xml
  <!-- Jwt -->
  <dependency>
  	<groupId>org.springframework.boot</groupId>
  	<artifactId>spring-boot-starter-security</artifactId>
  </dependency>
  <dependency>
      <groupId>io.jsonwebtoken</groupId>
      <artifactId>jjwt</artifactId>
      <version>0.9.1</version>
  </dependency>
  ```

- Codice:
  - Aggiungere `SecurityConfig`
  - Aggiornare `SwaggerConfiguration`
  - Aggiungere il filtro `JwtAuthenticationTokenFilter`
  - Aggiornare il model: `Document` con l'attributo updateBy
  
- Aggiornare i test