package com.sessionapi.newsscraper.configurations;

import com.sessionapi.newsscraper.common.Constants;
import com.sessionapi.newsscraper.configurations.DataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;

@Slf4j
public class ConfigUtility {
    /**
     * Parse URL result: jdbc:postgresql://host:port/database
     *
     * @param dataSourceBuilder DataSourceBuilder<HikariDataSource>
     *                          to configure with database credentials
     */
    static void parseDataSourceProperties(DataSourceBuilder<?> dataSourceBuilder,
                                          DataSourceProperties dataSourceProperties) {
        char[] raw = dataSourceProperties.getUrl().toCharArray();
        StringBuilder unSb = new StringBuilder();
        StringBuilder pwSb = new StringBuilder();
        StringBuilder urlSb = new StringBuilder(Constants.JDBC_BASE_URL);
        if (raw.length < 16) {
            log.error("The DATABASE_URL environment variable is required.");
            return;
        }
        int i = 0;
        while (i < 13) {
            urlSb.append(raw[i++]);
        }
        while (raw[i] != ':') {
            unSb.append(raw[i++]);
        }
        i++; // Skip over ':'
        while (raw[i] != '@') {
            pwSb.append(raw[i++]);
        }
        i++; // Skip over '@'
        while (i < raw.length) {
            urlSb.append(raw[i++]);
        }
        dataSourceBuilder.url(urlSb.toString());
        dataSourceBuilder.username(unSb.toString());
        dataSourceBuilder.password(pwSb.toString());
    }
}
