package com.example.demo;

import com.zaxxer.hikari.HikariDataSource;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.MapDataSourceLookup;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ReproService {
    @Autowired
    private MeterRegistry registry;
    @Autowired
    private DataSourceLookup foo;

    @Bean
    public DataSourceLookup dataSourceLookup() {
        return new CustomDataSourceLookup();
    }

    @PostConstruct
    public void checkMetrics() {
        registry.forEachMeter(meter -> {
            if (meter.getId().getName().contains("hikari")) {
                if (meter.getId().getTag(CustomDataSourceLookup.DATA_SOURCE_1) == null &&
                        meter.getId().getTag(CustomDataSourceLookup.DATA_SOURCE_2) == null) {
                    System.out.println("Hikari meter found but tag isn't specified (https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-metrics.html#production-ready-metrics-jdbc)");
                } else {
                    System.out.println("It's working");
                }
            }
        });
    }

    public class CustomDataSourceLookup extends MapDataSourceLookup {
        public static final String DATA_SOURCE_1 = "dataSource1";
        public static final String DATA_SOURCE_2 = "dataSource2";

        public CustomDataSourceLookup() {
            addDataSource(DATA_SOURCE_1, new HikariDataSource());
            addDataSource(DATA_SOURCE_2, new HikariDataSource());
        }
    }
}
