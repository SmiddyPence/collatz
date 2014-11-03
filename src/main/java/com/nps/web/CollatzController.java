package com.nps.web;

import com.nps.domain.CollatzNumber;
import com.nps.repository.CollatzNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
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

    @RequestMapping("/multipleIncoming")
    Set<CollatzNumber> multipleIncoming(){
        return collatzNumberRepository.findMoreThanOneIncomingRelationship();
    }

    @RequestMapping("/longestPath")
    List<CollatzNumber> longestPath(){
        CollatzNumberRepository.CollatzPathData path = collatzNumberRepository.longestPath();
        return path.getNumbers();
    }


    @RequestMapping("/longestResolvedPath")
    List<CollatzNumber> longestResolvedPath(){
        CollatzNumberRepository.CollatzPathData path = collatzNumberRepository.longestResolvedPath();
        return path.getNumbers();
    }

    @RequestMapping("/populate-nodes")
    String populateNodes(){
        CollatzNumber cn = collatzNumberRepository.getByValue(1);

        if(cn != null){
            return "database already contains nodes";
        }

        List<CollatzNumber> numbers = new ArrayList<>();

        for(int n = -374; n<=375; n++){
            CollatzNumber collatzNumber = new CollatzNumber();
            collatzNumber.setValue(n);
            numbers.add(collatzNumber);
        }

        collatzNumberRepository.save(numbers);

        return "success";
    }

    @RequestMapping("/populate-rels")
    String populateRels(){

        CollatzNumber cn = collatzNumberRepository.getByValue(1);

        if(cn.getNextNumber() != null){
            return "database already contains relationships";
        }

        for(int n = -374; n<=375; n++){

            CollatzNumber currentNumber = collatzNumberRepository.getByValue(n);

            int nextValue;
            if(n % 2 != 0){
                nextValue = n * 3 + 1;
            } else {
                nextValue = n / 2;
            }
            CollatzNumber nextNumber = collatzNumberRepository.getByValue(nextValue);
            if(nextNumber == null){
                nextNumber = new CollatzNumber();
                nextNumber.setValue(nextValue);
                collatzNumberRepository.save(nextNumber);
            }

            if(currentNumber.equals(nextNumber)){
                currentNumber.setNextNumber(currentNumber);
            } else {
                currentNumber.setNextNumber(nextNumber);
            }

            collatzNumberRepository.save(currentNumber);

            System.out.println("(" + currentNumber.getValue() +")-->(" + nextNumber.getValue() + ")");
        }

        return "success";
    }
}
