# Proyecto de Matrícula - Sistema de Gestión Académica

**Matrícula** es una aplicación web desarrollada con **Spring Boot** para gestionar el proceso de matrícula en una institución educativa. Permite administrar alumnos, cursos, docentes, secciones, generación de cuotas y pagos, así como configuración de usuarios y perfiles.

---

## Tecnologías

- **Java 23** y **Spring Boot 3.4.4**
- **Thymeleaf** (plantillas HTML)
- **MS SQL Server** como base de datos
- **JPA / Hibernate** para persistencia
- **Maven** como gestor de proyectos
- **GitHub Actions** para CI/CD

---

## Estructura del Proyecto

```
com.matricula
├── config             # Configuración de seguridad y DataLoader
├── controller         # Controladores MVC (Alumnos, Cursos, Docentes, Secciones, Finanzas, Configuración)
├── dto                # Clases DTO para intercambio de datos
├── entity             # Entidades JPA / Hibernate
├── repository         # Repositorios Spring Data JPA
├── service            # Lógica de negocio (servicios, implementaciones)
└── templates          # Plantillas Thymeleaf (.html)
```

---

## Requisitos Previos

- JDK 17
- MS SQL Server (local o contenedor Docker)
- Maven 3.x

---

## Configuración

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/matricula.git
   cd matricula
   ```
2. Configurar conexión a BD en `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=SME_DB;encrypt=true;trustServerCertificate=true
   spring.datasource.username=sa
   spring.datasource.password=tu-password
   spring.jpa.hibernate.ddl-auto=update
   ```
3. El *DataLoader* crea automáticamente los roles (`ADMIN`, `GENERAL`, `USER`) y el usuario `admin`.

---

## Ejecución

1. Construir el proyecto:
   ```bash
   mvn clean package
   ```
2. Ejecutar la aplicación:
   ```bash
   java -jar target/matricula-0.0.1-SNAPSHOT.jar
   ```
3. Acceder en el navegador a `http://localhost:8080/login`.

---

## Características

- **Autenticación** y **Autorización** con Spring Security.
- **CRUD** de alumnos, cursos, docentes y secciones.
- **Generación de cuotas** (incluye matrícula como cuota 0) y **registro de pagos**.
- **Reportes** en PDF/Excel del estado de matrículas.
- **Soft delete** para mantener histórico de registros.
- **Módulo de Configuración**: gestión de usuarios y asignación de roles (`ADMIN`, `GENERAL`, `USER`).
- **MVP** implementado tras Sprint 4 con Scrum.

---

## CI/CD

El flujo de GitHub Actions realiza:

- Instalación de JDK 17 y cache de dependencias Maven.
- Levantamiento de contenedor SQL Server.
- Creación de la base de datos `SME_DB`.
- Ejecución de `mvn clean verify -Dspring.profiles.active=dev`.

Archivo de ejemplo: `.github/workflows/maven.yml`.

---

## Contribuciones

1. Hacer un _fork_ del repositorio.
2. Crear una rama para tu funcionalidad: `git checkout -b feature/nueva-funcionalidad`.
3. Hacer _commit_ de tus cambios: `git commit -m "Añade..."`.
4. Empujar la rama: `git push origin feature/nueva-funcionalidad`.
5. Crear un **Pull Request**.

---

## Licencia

Proyecto para fines académicos.


