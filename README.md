 # Microservices Project # 
Projekt oparty na architekturze mikroserwisowej zbudowany przy użyciu Spring Boot.
Zawiera komunikację synchroniczną (Feign) i asynchroniczną (Kafka/RabbitMQ), rejestrację usług przez Eureka oraz monitorowanie za pomocą Zipkina.
# Moduły #
customer - Obsługuje rejestrację klienta, komunikuje się z `fraud` i `notification` 
fraud - Weryfikuje, czy klient jest oszustem                                                        
apigw - Spring Cloud Gateway jako API Gateway
clients - Zawiera wspólne klasy DTO i FeignClienty dla komunikacji między usługami 
eureka-server - Rejestracja i odkrywanie usług                                     
notification - Wysyła powiadomienia przez Kafka lub RabbitMQ 
# Technologie #
- Spring Boot 3
- Spring Cloud Eureka
- Spring Cloud Gateway
- RabbitMQ (broker wiadomości)
- Apache Kafka (event streaming)
- Spring Cloud OpenFeign
- Spring Boot Test
- Zipkin (trace'owanie)
- PostgreSQL (baza danych)
# Uruchamianie projektu#
1. Upewnij się, że działają usługi:
   - PostgreSQL
   - RabbitMQ
   - Kafka
   - Zipkin
2. Uruchom `eureka-server`
3. Uruchom `apigw`
4. Uruchom `customer`, `fraud`, `notification`
