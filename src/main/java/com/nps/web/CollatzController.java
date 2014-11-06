package com.nps.web;

import com.nps.domain.CollatzNumber;
import com.nps.repository.CollatzNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class CollatzController {

    @Autowired
    CollatzNumberRepository collatzNumberRepository;

    @RequestMapping("/number/{value}")
    CollatzNumber getNumber(@PathVariable int value){
        return collatzNumberRepository.getByValue(value);
    }

    @RequestMapping("/multiple-incoming")
    Set<CollatzNumber> multipleIncoming(){
        return collatzNumberRepository.findMoreThanOneIncomingRelationship();
    }

    @RequestMapping("/longest-path")
    List<CollatzNumber> longestPath(){
        CollatzNumberRepository.CollatzPathData path = collatzNumberRepository.longestPath();
        return path.getPath();
    }


    @RequestMapping("/longest-resolved-path")
    List<CollatzNumber> longestResolvedPath(){
        CollatzNumberRepository.CollatzPathData path = collatzNumberRepository.longestResolvedPath();
        return path.getPath();
    }

    @RequestMapping("/circular-paths")
    Set<CollatzNumber> circularPaths(){
        Set<CollatzNumber> collatzNumbers = collatzNumberRepository.numberInCircularPath();

        return collatzNumbers;
    }
}
