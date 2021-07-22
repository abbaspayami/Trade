# Trade

## How to Run
This coding challenge includes a runnable JAR (the partnerService) that provides the above mentioned Websockets.
* [Firstly, you should start run partner-service-1.0-all.jar] 
* Execute tests:

  **mvn clean install**

* starting Application:

  **mvn spring-boot:run**

### Tools & Technologies

* [Java 8]
* [Spring boot]
* [Maven]
* [Thymeleaf]
* [Lombok]
* [Swagger]
* [JUnit 5]
* [Mockito]
* [H2]

### Notes :
* For getting Instrument Price, you can call this API in postman
  [ curl -X GET \http://localhost:8081/api/instrumentPrice ]

*  For getting Instrument Price History, you can call this API in postman

   [ curl -X GET \http://localhost:8081/api/instrumentPriceHistory ]