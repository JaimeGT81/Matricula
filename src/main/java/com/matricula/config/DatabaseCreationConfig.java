package com.matricula.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Configuración para crear la base de datos SME_DB si no existe.
 * Se habilita solo en el perfil 'dev'.
 */
@Configuration
@Profile("dev")
public class DatabaseCreationConfig {

    private final DataSourceProperties dataSourceProperties;

    public DatabaseCreationConfig(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    @Bean
    public ApplicationRunner createDatabaseRunner() {
        return args -> {
            String url = dataSourceProperties.getUrl();
            // Reemplaza el catalogo por 'master'
            String masterUrl = url.replaceFirst("(?i)databaseName=[^;]+", "databaseName=master");

            try (Connection conn = DriverManager.getConnection(
                    masterUrl,
                    dataSourceProperties.getUsername(),
                    dataSourceProperties.getPassword());
                 Statement stmt = conn.createStatement()) {

                String sql = "IF DB_ID('SME_DB') IS NULL CREATE DATABASE SME_DB";
                stmt.executeUpdate(sql);
                System.out.println("✅ SME_DB database verified/created");

            } catch (SQLException e) {
                throw new IllegalStateException("Error creating or verifying SME_DB database", e);
            }
        };
    }
}