# MOSIP ID Authentication Assessment

Assessment submission for Senior Java Developer role at Identiko Solutions.

## What I Built

Two REST endpoints for MOSIP's ID Authentication service:

1. **Health Details API** - `GET /api/v1/health/details`
   - Shows service status, version, and configuration
   - Can simulate DOWN status for testing

2. **Audit Log API** - `POST /api/v1/audit/log`
   - Logs events with validation
   - Returns unique event ID

## Quick Start
```bash
mvn clean install
mvn spring-boot:run
Visit: http://localhost:8080/swagger-ui/index.html
Testing
bashmvn test
All tests pass with coverage for controllers and service layer.
Tech Stack

Java 17, Spring Boot 3.5.6
In-memory storage (ConcurrentHashMap)
Bean Validation, Swagger docs
JUnit 5 for testing

Design Notes
Why in-memory storage?
Simpler for assessment, thread-safe, easy to swap for database later.
Why service interface?
Matches MOSIP patterns I saw in their codebase, makes testing easier.
Project Structure
Standard Spring Boot layout with controller/service/dto packages under io.mosip.authentication.
Integration with MOSIP
Files are structured to drop into id-authentication/authentication-service with minimal changes. Package names and patterns match what I found in their repo.

David Onyeka 
October 2025