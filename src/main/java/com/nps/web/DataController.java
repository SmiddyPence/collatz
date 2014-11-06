package com.nps.web;

import com.nps.domain.CollatzNumber;
import com.nps.repository.CollatzNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DataController {

    @Autowired
    CollatzNumberRepository collatzNumberRepository;

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
