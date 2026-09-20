package com.sriram.srirammart.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public final class DBUtil {

    private DBUtil() {
        // Utility class - no objects
    }

    public static HikariDataSource createDataSource() {

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(env("DB_URL", "jdbc:h2:./data/srirammart"));
        config.setDriverClassName("org.h2.Driver");
        config.setUsername(env("DB_USERNAME", "sa"));
        config.setPassword(env("DB_PASSWORD", ""));

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);

        config.setPoolName("SriramMartHikariPool");

        System.out.println("H2 JDBC URL = " + config.getJdbcUrl());

        return new HikariDataSource(config);
    }

    /** Returns the environment variable's value, or the default if it's unset/blank. */
    private static String env(String name, String defaultValue) {
        String value = System.getenv(name);
        return (value != null && !value.isBlank()) ? value : defaultValue;
    }
}