# 📚 Colegio Bernardo O’Higgins — Plataforma de Gestión Escolar Distribuida

Sistema de gestión académica desarrollado bajo una arquitectura de microservicios utilizando Spring Boot, Spring Cloud y Docker.  
La plataforma busca modernizar y centralizar los procesos académicos, administrativos y comunicacionales del Colegio Bernardo O’Higgins mediante un ecosistema distribuido, escalable y seguro.

---

# 👥 Integrantes

- Martín Baza
- Mayckol Mardones
- Francisco Vera

---

# Objetivo del Proyecto

Desarrollar una plataforma web integral basada en microservicios que permita gestionar:

- información académica
- matrículas
- asistencia
- evaluaciones
- anotaciones
- comunicaciones institucionales
- notificaciones automáticas
- reportes académicos

Todo esto mediante una arquitectura desacoplada, escalable y preparada para ambientes distribuidos.

---

# Arquitectura del Sistema

El sistema fue diseñado utilizando una arquitectura basada en microservicios, implementando componentes especializados e independientes.

## Componentes principales

- API Gateway
- Eureka Server
- Config Server
- Auth Service (JWT)
- Backend For Frontend (BFF)
- Microservicios funcionales independientes
- Configuración centralizada
- Comunicación inter-servicio mediante OpenFeign

---

# Estructura del Proyecto

```txt
FULLSTACK-III/
│
├── academico-service
├── anotacion-service
├── api-gateway
├── asistencia-service
├── auth-service
├── comunicacion-service
├── config-repo
├── config-server
├── estudiante-service
├── eureka-server
├── evaluacion-service
├── notificacion-service
├── reporte-service
├── usuario-service
│
├── docker-compose.yml
└── README.md
```

---

# Microservicios Implementados

| Microservicio | Responsabilidad |
|---|---|
| auth-service | Autenticación y generación de JWT |
| usuario-service | Gestión de usuarios y roles |
| estudiante-service | Gestión de estudiantes |
| academico-service | Gestión académica |
| evaluacion-service | Registro y cálculo de evaluaciones |
| asistencia-service | Control de asistencia |
| anotacion-service | Gestión de anotaciones |
| comunicacion-service | Comunicaciones institucionales |
| notificacion-service | Envío de notificaciones |
| reporte-service | Backend For Frontend (BFF) |
| api-gateway | Punto de entrada centralizado |
| eureka-server | Service Discovery |
| config-server | Configuración centralizada |

---

# Arquitectura Interna de los Microservicios

Cada microservicio fue desarrollado siguiendo una estructura desacoplada basada en capas, permitiendo mantenibilidad, escalabilidad y separación clara de responsabilidades.

## Estructura implementada

```txt
controller/
service/
repository/
entity/
dto/
config/
exception/
client/
```

---

## Capas utilizadas

| Capa | Responsabilidad |
|---|---|
| Controller | Exposición de endpoints REST |
| Service | Lógica de negocio |
| Repository | Acceso a datos mediante Spring Data JPA |
| Entity | Modelado de datos persistentes |
| DTO | Transferencia segura de información |
| Config | Configuración de seguridad y componentes |
| Exception | Manejo global de excepciones |
| Client | Comunicación entre servicios mediante OpenFeign |

---

## Ejemplo de implementación real

```txt
academico-service/
├── controller/
├── service/
├── repository/
├── entity/
├── exception/
└── config/
```

Esta estructura se replica en los distintos microservicios para asegurar consistencia arquitectónica.

---

# Stack Tecnológico

## Backend

- Java 21
- Spring Boot 3.2.5
- Spring Cloud 2023.0.1
- Spring Security
- Spring Cloud Gateway
- Spring Cloud Config
- Eureka Netflix
- OpenFeign
- JWT (JSON Web Token)
- Maven

---

## Base de Datos

- MySQL 8

---

## DevOps e Infraestructura

- Docker
- Docker Compose

---

## Testing

- JUnit 5
- Mockito
- Spring Boot Test

---

## Control de Versiones

- Git
- GitHub

---

# Patrones de Diseño y Arquitectura

El proyecto implementa múltiples patrones arquitectónicos y de diseño enfocados en escalabilidad, mantenibilidad y desacoplamiento.

| Patrón | Implementación |
|---|---|
| Microservices Architecture | Separación funcional por dominios |
| Backend For Frontend (BFF) | `reporte-service` |
| API Gateway | `api-gateway` |
| Repository Pattern | Spring Data JPA |
| MVC | Controller + Service + Repository |
| DTO Pattern | Transferencia segura de datos |
| Dependency Injection | IoC Container de Spring |
| Service Discovery | Eureka Server |
| Centralized Configuration | Spring Cloud Config |
| Global Exception Handler | `GlobalExceptionHandler.java` |
| OpenFeign Client | Comunicación entre servicios |

---

## Ejemplos reales del proyecto

### DTO Pattern

```txt
AuthUserDto.java
TokenDto.java
ReporteGeneralDto.java
MensajeDto.java
```

---

### Repository Pattern

```txt
UsuarioRepository.java
EvaluacionRepository.java
AsignaturaRepository.java
```

---

### Global Exception Handler

```txt
GlobalExceptionHandler.java
```

Implementado en múltiples microservicios para manejo centralizado de errores.

---

# Comunicación Entre Microservicios

La comunicación entre servicios se implementó utilizando OpenFeign mediante clientes desacoplados.

## Implementaciones detectadas

### Auth Service

```txt
UsuarioFeignClient.java
```

Permite consultar información de usuarios desde el microservicio de autenticación.

---

### Reporte Service (BFF)

```txt
EstudianteClient.java
EvaluacionClient.java
```

El BFF consolida información académica proveniente de múltiples microservicios para entregar respuestas optimizadas al frontend.

---

## Beneficios de OpenFeign

- desacoplamiento
- comunicación declarativa
- integración simple con Eureka
- mantenibilidad
- balanceo de carga automático

---

# Configuración Centralizada

El ecosistema implementa Spring Cloud Config para centralizar la configuración de todos los microservicios.

## Componentes

### Config Server

Encargado de distribuir configuraciones dinámicamente a los servicios.

### Config Repo

Repositorio centralizado que contiene:

```txt
academico-service.yml
auth-service.yml
usuario-service.yml
reporte-service.yml
```

---

## Beneficios

- administración centralizada
- consistencia de configuración
- cambios dinámicos
- escalabilidad operativa

---

# Seguridad

La plataforma implementa autenticación basada en JWT (JSON Web Token).

## Características

- generación de tokens mediante `auth-service`
- validación desde `api-gateway`
- seguridad stateless
- control de acceso basado en roles
- filtros JWT centralizados

---

## Componentes de Seguridad Detectados

```txt
SecurityConfig.java
JwtProvider.java
AuthFilter.java
```

La autenticación y autorización se implementa mediante filtros JWT integrados en el API Gateway.

---

# Infraestructura Distribuida

## Eureka Server

Permite el registro y descubrimiento dinámico de servicios dentro del ecosistema.

### Puerto

```txt
8761
```

---

## API Gateway

Punto de entrada único para las solicitudes del cliente.

### Puerto

```txt
8080
```

---

## Auth Service

Servicio encargado de autenticación y emisión de JWT.

### Puerto

```txt
8090
```

---

## Usuario Service

Gestión de usuarios institucionales.

### Puerto

```txt
8092
```

---

# Docker

El proyecto utiliza Docker para facilitar el despliegue y la portabilidad de los servicios.

## docker-compose.yml

```yaml
services:

  mysql-db:
    image: mysql:8.0
    container_name: mysql-db
    environment:
      - MYSQL_ALLOW_EMPTY_PASSWORD=yes
    ports:
      - "3306:3306"
    networks:
      - microservices-net

  eureka-server:
    build: ./eureka-server
    container_name: eureka-server
    ports:
      - "8761:8761"
    networks:
      - microservices-net

networks:
  microservices-net:
    driver: bridge
```

---

# Ejecución del Proyecto

## 1. Clonar repositorio

```bash
git clone https://github.com/Mayckol2005/FullStack-III
```

---

## 2. Ingresar al proyecto

```bash
cd FULLSTACK-III
```

---

## 3. Levantar contenedores Docker

```bash
docker-compose up --build
```

---

## 4. Ejecutar microservicios

Desde cada microservicio:

```bash
mvn spring-boot:run
```

---

# Endpoints Base

| Servicio | Endpoint Base |
|---|---|
| Auth | `/api/auth` |
| Usuarios | `/api/usuarios` |
| Estudiantes | `/api/estudiantes` |
| Evaluaciones | `/api/evaluaciones` |
| Reportes | `/api/reportes` |
| Asistencia | `/api/asistencia` |
| Comunicación | `/api/comunicaciones` |
| Anotaciones | `/api/anotaciones` |

---

# Testing y Calidad de Software

El proyecto implementa pruebas automatizadas utilizando JUnit 5 y Mockito para garantizar estabilidad y confiabilidad.

## Tecnologías

- JUnit 5
- Mockito
- Spring Boot Test

---

## Tests implementados

### Reporte Service

```txt
ReporteServiceTest.java
```

### Usuario Service

```txt
UsuarioServiceTest.java
```

---

## Objetivos de las pruebas

- validar lógica de negocio
- verificar cálculo de métricas
- simular respuestas externas con mocks
- asegurar estabilidad del BFF
- validar integración entre capas

---

## Cobertura

Las pruebas fueron enfocadas principalmente en:
- servicios
- lógica de negocio
- consolidación de datos
- respuestas agregadas del BFF

---

# Estrategia de Branching

Se implementó la metodología GitHub Flow.

## Estructura de ramas

- `main`
- `feature/*`

---

## Ejemplos

```txt
feat/auth-config-setup
feat/unit-testing-report-service
feat/comms-service-refactor
```

---

## Flujo de trabajo

- desarrollo en ramas independientes
- integración mediante Pull Requests
- revisión colaborativa
- resolución manual de conflictos

---

# Características de Escalabilidad

- servicios desacoplados
- configuración centralizada
- descubrimiento dinámico con Eureka
- arquitectura distribuida
- separación de responsabilidades
- infraestructura preparada para escalamiento horizontal

---

# Estado del Proyecto

Proyecto académico correspondiente a la asignatura **Desarrollo Fullstack III**.

Actualmente cuenta con:

- arquitectura distribuida funcional
- microservicios operativos
- integración mediante Eureka
- seguridad JWT implementada
- pruebas unitarias funcionales
- configuración centralizada
- comunicación inter-servicio mediante OpenFeign

---
