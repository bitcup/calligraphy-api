package com.bitcup.calligraphy.repository;

import com.bitcup.calligraphy.domain.Lawha;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bitcup
 */
@Repository
public interface LawhaRepository extends ElasticsearchCrudRepository<Lawha, String> {

    Page<Lawha> findByTypeAndTagsContaining(String type, String tag, Pageable page);

    Page<Lawha> findByType(String type, Pageable page);
}
