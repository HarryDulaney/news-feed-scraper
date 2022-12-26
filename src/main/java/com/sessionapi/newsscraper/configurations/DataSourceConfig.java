package com.sessionapi.newsscraper.configurations;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;


@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
@EnableJpaRepositories(
        basePackages = {"com.sessionapi.newsscraper.repositories"}
)
public class DataSourceConfig {

    @Bean(name = "pgDataSource")
    DataSource datasource(DataSourceProperties dataSourceProperties) {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.type(HikariDataSource.class);
        ConfigUtility.parseDataSourceProperties(dataSourceBuilder, dataSourceProperties);
        return dataSourceBuilder.build();
    }
}
