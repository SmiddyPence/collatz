package com.nps;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;


@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableNeo4jRepositories
public class Application extends Neo4jConfiguration {

    Application() {
        setBasePackage("com.nps.domain");
    }

    @Bean
    GraphDatabaseService graphDatabaseService() {

        String dbUrl = System.getenv("DB_URL");
        String userName = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if(dbUrl == null || userName == null || password == null ) {
            dbUrl = "http://localhost:7474/db/data";
            System.out.println("Database not configured defaulting to localhost");
        }
        return new SpringRestGraphDatabase(dbUrl, userName, password);

        //return new GraphDatabaseFactory().newEmbeddedDatabase("target/graph.db");
    }

    public static void main(String[] args) throws Exception {
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }
        System.setProperty("server.port", webPort);
        SpringApplication.run(Application.class, args);
    }
}
