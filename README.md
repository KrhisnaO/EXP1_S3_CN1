# Sistema de Alertas Médicas en Tiempo Real

## Desarrollo Cloud Native I - DSY2206

---

# Descripción del Proyecto

Sistema de alertas médicas en tiempo real para la gestión de pacientes críticos en un entorno hospitalario.

La solución permite registrar pacientes, almacenar signos vitales, generar alertas médicas y administrar la información mediante una arquitectura Cloud Native basada en microservicios.

---

# Arquitectura de la Solución

```text
Frontend Angular
        │
        ▼
BFF (Spring Boot)
        │
        ▼
Microservicio Alertas
        │
        ▼
Oracle Cloud Database
```

La aplicación fue desarrollada utilizando una arquitectura desacoplada compuesta por:

* Frontend Angular
* Backend For Frontend (BFF)
* Microservicio de Alertas
* Oracle Cloud Database
* Docker Compose para despliegue local

---

# Tecnologías Utilizadas

## Frontend

* Angular
* TypeScript
* Angular Forms
* Bootstrap

## Backend

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* Maven

---

# Funcionalidades Implementadas

## Gestión de Pacientes

* Crear paciente
* Obtener paciente por ID
* Listar pacientes
* Actualizar paciente
* Eliminar paciente

## Gestión de Signos Vitales

* Registrar signos vitales
* Consultar historial de signos vitales
* Asociar signos vitales a pacientes

## Gestión de Alertas

* Generación de alertas médicas
* Consulta de alertas activas
* Atención y cierre de alertas

## Seguridad

* Spring Security configurado
* Protección de endpoints REST
* Arquitectura preparada para autenticación mediante Azure AD B2C y JWT

---

# Estructura del Proyecto

```text
alertas_medicas_cn1
│
├── frontend-alertas
│
├── alertas-bff
│
├── alertas-ms
│
├── scripts
│
├── docker-compose.yml
│
└── README.md
```

---

# Base de Datos

La aplicación utiliza Oracle Cloud Database para el almacenamiento persistente de la información.

Entidades principales:

* Paciente
* SignoVital
* Alerta

---

# Ejecución del Proyecto

## Requisitos

* Java 21
* Maven
* Docker Desktop
* Node.js
* Angular CLI

## Levantar con Docker

```bash
docker compose up --build
```

## Frontend

```bash
cd frontend-alertas
npm install
ng serve
```

Aplicación disponible en:

```text
http://localhost:4200
```

## Backend

BFF:

```text
http://localhost:8080
```

Microservicio:

```text
http://localhost:8081
```

---

# Seguridad e Integración Cloud

La arquitectura contempla:

* Identity as a Service (IDaaS)
* API Manager
* OAuth 2.0
* JSON Web Tokens (JWT)


---
