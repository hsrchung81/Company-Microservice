package com.tng.company;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class FlywayConfiguration {

	@Value("${spring.datasource.host}")
	private String host;

	@Value("${spring.datasource.port}")
	private int port;

	@Value("${spring.datasource.databaseName}")
	private String databaseName;

	@Value("${spring.datasource.schema}")
    private String defaultSchema;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.flyway.baselineOnMigrate}")
    private boolean baselineOnMigrate;

//	@Value("${spring.flyway.url}")
//	private String url;

    @PostConstruct
    public void updateDatabase() {
        final Flyway flyway = new Flyway(
                new FluentConfiguration()
						.schemas(defaultSchema)
                        .defaultSchema(defaultSchema)
                        .dataSource(String.format("jdbc:postgresql://%s:%d/%s", host, port, databaseName),username, password)
                        .baselineOnMigrate(baselineOnMigrate)

        );
        flyway.repair();
        flyway.migrate();
    }
}
