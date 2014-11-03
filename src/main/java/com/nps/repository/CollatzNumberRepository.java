package com.nps.repository;


import com.nps.domain.CollatzNumber;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


public interface CollatzNumberRepository extends GraphRepository<CollatzNumber> {

    CollatzNumber getByValue(int value);

    @Query("MATCH ()-[r]->(n) " +
            "WITH n, count(r) as rCount " +
            "WHERE rCount > 1 " +
            "RETURN n;")
    Set<CollatzNumber> findMoreThanOneIncomingRelationship();

    @Query("MATCH p = (n)-[r:NEXT*]->(m) " +
            "RETURN nodes(p) " +
            "ORDER BY length(p) desc " +
            "LIMIT 1;")
    CollatzPathData longestPath();


    @Query("MATCH p = (n)-[r:NEXT*]->(m) " +
            "WHERE m.value = 1 " +
            "RETURN nodes(p) " +
            "ORDER BY length(p) desc " +
            "LIMIT 1;")
    CollatzPathData longestResolvedPath();


    @Query("MATCH p = (n)-[r:NEXT*]->(m) " +
            "WHERE n = m " +
            "RETURN nodes(p);")
    CollatzPathData circularPaths();


    @QueryResult
    public interface CollatzPathData {

        @ResultColumn("nodes(p)")
        List<CollatzNumber> getNumbers();
    }
}
