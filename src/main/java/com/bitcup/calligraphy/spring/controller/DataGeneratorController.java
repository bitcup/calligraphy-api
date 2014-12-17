package com.bitcup.calligraphy.spring.controller;

import com.bitcup.calligraphy.LawhaDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bitcup
 */
@RestController
@RequestMapping(value = {"api/v1.0"})
public class DataGeneratorController {

    @Autowired
    private LawhaDataGenerator lawhaDataGenerator;

    @RequestMapping("/generate")
    public void generate() {
        lawhaDataGenerator.generateSampleData();
    }

    @RequestMapping("/delete")
    public void delete() {
        lawhaDataGenerator.deleteAllData();
    }
}
