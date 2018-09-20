# spring-boot-issue-14547-repro

If we create multiple datasources using the [DataSourceLookup](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/datasource/lookup/DataSourceLookup.html) API, they doesn't seems to be automatically registered with Micrometer.


The documentation specifies `Metrics are also tagged by the name of the DataSource computed based on the bean name.` (https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-metrics.html#production-ready-metrics-jdbc).


