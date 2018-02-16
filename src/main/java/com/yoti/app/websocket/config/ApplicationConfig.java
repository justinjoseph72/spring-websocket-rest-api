package com.yoti.app.websocket.config;

import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfig {

    /*@Value("${database.server.name}")
    private String serverName;
    @Value("${database.username}")
    private String databaseUserName;
    @Value("${database.password}")
    private String databasePassword;
    @Value("${database.name}")
    private String databaseName;*/

    /*@Bean
    public JdbcTemplate getTemplate(@Qualifier(value = "postgresDS") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "postgresDS")
    public DataSource getDatasource() {
        PGPoolingDataSource source = new PGPoolingDataSource();
        source.setServerName(serverName);
        source.setUser(databaseUserName);
        source.setPassword(databasePassword);
        source.setDatabaseName(databaseName);
        source.setCurrentSchema("public");
        source.setMaxConnections(10);
        return source;
    }*/


}
