package com.bitcup.calligraphy.spring.controller;

import com.bitcup.calligraphy.util.AcDataParser;
import com.bitcup.calligraphy.util.LawhaDataGenerator;
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

    @Autowired
    private AcDataParser dataParser;

    @RequestMapping("/generate")
    public void generate() throws Exception {
        dataParser.parse();
        lawhaDataGenerator.generateGalleryData();
    }

    @RequestMapping("/delete")
    public void delete() {
        dataParser.deleteAllData();
    }
}
