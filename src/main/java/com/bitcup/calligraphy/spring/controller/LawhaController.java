package com.bitcup.calligraphy.spring.controller;

import com.bitcup.calligraphy.domain.Lawha;
import com.bitcup.calligraphy.repository.LawhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bitcup
 */
@RestController
@RequestMapping(value = {"api/v1.0"})
public class LawhaController {

    @Autowired
    private LawhaRepository lawhaRepository;

    @RequestMapping("/lawhats/{id}")
    public Lawha getById(@PathVariable("id") String id) {
        return lawhaRepository.findOne(id);
    }

    @RequestMapping("/lawhats/type/{type}")
    public Page<Lawha> getByType(@PathVariable("type") String type, Pageable pageable) {
        return lawhaRepository.findByType(type, pageable);
    }

    @RequestMapping("/lawhats/type/{type}/tag/{tag}")
    public Page<Lawha> getByTypeAndTag(@PathVariable("type") String type, @PathVariable("tag") String tag, Pageable pageable) {
        return lawhaRepository.findByTypeAndTagsContaining(type, tag, pageable);
    }

    @RequestMapping(value = "/lawhats", method = RequestMethod.POST)
    public Lawha createLawha(Lawha lawha) {
        return lawhaRepository.save(lawha);
    }

}