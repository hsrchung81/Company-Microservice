/*
package com.tng.common.config;

import com.github.jasync.r2dbc.mysql.JasyncConnectionFactory;
import com.github.jasync.sql.db.mysql.pool.MySQLConnectionFactory;
import com.github.jasync.sql.db.mysql.util.URLParser;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import java.nio.charset.StandardCharsets;

@Configuration
public class PostgresqlConfiguration extends AbstractR2dbcConfiguration {

    @Autowired
    private Environment env;

    @Override
    public ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration
                .builder()
                .
                .host(env.getProperty(""))
                .database(env.getProperty(""))
                .username(env.getProperty(""))
                .password(env.getProperty(""))
                .port(env.getProperty(""))
                .build());
        return connectionFactory;
    }
}
*/
