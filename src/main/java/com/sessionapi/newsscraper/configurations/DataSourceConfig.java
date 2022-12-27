package com.sessionapi.newsscraper.configurations;

import com.sessionapi.newsscraper.utils.ConfigUtility;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;


@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
@EnableJpaRepositories(
        basePackages = {"com.sessionapi.newsscraper.repository"}
)
public class DataSourceConfig {
    @Bean
    @Primary
    DataSource dataSource(DataSourceProperties dataSourceProperties) {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.type(HikariDataSource.class);
        ConfigUtility.parseDataSourceProperties(dataSourceBuilder, dataSourceProperties);
        dataSourceBuilder.driverClassName(dataSourceProperties.getDriverClassName());
        return dataSourceBuilder.build();
    }
}
