package com.nps;

import org.neo4j.graphdb.GraphDatabaseService;
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

        //return new EmbeddedGraphDatabase("target/graph.db");
//        return new SpringRestGraphDatabase("http://collatz.sb02.stations.graphenedb.com:24789/db/data/",
//                "collatz", "xz5nMZiICRjIm6DYPjMo");
        return new SpringRestGraphDatabase("http://localhost:7474/db/data/");
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
