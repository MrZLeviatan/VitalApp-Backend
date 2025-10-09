plugins {
    java
    // Plugin para Spring Boot, necesario para construir y ejecutar la aplicación.
    // Se utiliza la versión de Spring Boot 3.4.1
    id("org.springframework.boot") version "3.4.1"
    // Plugin para la gestión de dependencias en proyectos Spring.
    id("io.spring.dependency-management") version "1.1.7"
    // Plugin de SonarQube para conectarse con SonarCloud
    jacoco
    // Plugin para conectar Gradle a SonarCloud
    id("org.sonarqube") version "4.4.1.3373"
}

group = "co.edu.uniquindio"
version = "1.0-SNAPSHOT"
description = "Proyecto basado en el desarrollo para el sistema VitalApp, " +
        "siguiendo las normas del negocio. " +
        "Autor: MrZ Leviatán"

java{
    toolchain{
        // Se define la versión de Java a utilizar (Java 22).
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}


configurations {
    compileOnly {
        //  Asegura que los procesadores de anotaciones solo se incluyan en tiempo de compilación.
        extendsFrom(configurations.annotationProcessor.get())
    }
}

// Configuración de JaCoCo
configure<JacocoPluginExtension> {
    toolVersion = "0.8.9"
}

// Configuración del reporte JaCoCo para SonarCloud
tasks.jacocoTestReport {
    reports {
        xml.required.set(true)  // SonarCloud requiere el XML
        html.required.set(true) // Reporte visual opcional
    }
}

// Configuración de SonarQube / SonarCloud
sonarqube {
    properties {
        // Se usan variables de entorno (provenientes de GitHub Secrets)
        property("sonar.projectKey", System.getenv("SONAR_PROJECT_KEY"))
        property("sonar.organization", System.getenv("SONAR_ORG"))
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.token", System.getenv("SONAR_TOKEN"))

        // Cobertura de código y análisis de rutas
        property("sonar.java.coveragePlugin", "jacoco")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")

        // Directorio de fuente y de pruebas
        //property("sonar.sources", "src/main/java")
        
    }
}


repositories {
    // Define Maven Central como el repositorio para las dependencias
    mavenCentral()
}

// Bloque donde definiremos las dependencias a usar en el Backend del proyecto.
dependencies {

    // ==== Core Spring Boot ====
    // Starter Web de Spring Boot: Proporciona funcionalidades web básicas (REST, Tomcat embebido, JSON).
    implementation("org.springframework.boot:spring-boot-starter-web")
    // Starter de validación: Anotaciones como @Email, @NotNull, etc.
    implementation("org.springframework.boot:spring-boot-starter-validation")
    // Agrega el Actuator para exponer endpoints de monitoreo
    implementation("org.springframework.boot:spring-boot-starter-actuator")


    // ==== Utilidades ====
    // Lombok: Reduce el código repetitivo (Getters, Setters, etc.).
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    // MapStruct: Mapeo entre DTOs y entidades.
    implementation("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")




    // ==== Testing ====
    // Starter de pruebas de Spring Boot (JUnit 5, Mockito, etc.).
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}