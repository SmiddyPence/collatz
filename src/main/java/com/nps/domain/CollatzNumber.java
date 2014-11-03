package com.nps.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class CollatzNumber {

    @GraphId
    @JsonIgnore
    Long id;

    int value;

    @RelatedTo(type="NEXT", direction = Direction.OUTGOING)
    @JsonIgnore
    CollatzNumber nextNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public CollatzNumber getNextNumber() {
        return nextNumber;
    }

    public void setNextNumber(CollatzNumber nextNumber) {
        this.nextNumber = nextNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollatzNumber that = (CollatzNumber) o;

        if (value != that.value) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
