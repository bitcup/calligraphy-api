package com.bitcup.calligraphy.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * @author bitcup
 */
@EqualsAndHashCode
@ToString
@Document(indexName = "lawha", type = "wall", refreshInterval = "-1")
public class Lawha implements Serializable {
    @Getter
    @Setter
    @Id
    private String id;

    @Getter
    @Setter
    @Field(type = FieldType.String)
    private String name;

    @Getter
    @Setter
    @Field(type = FieldType.String)
    private String nameAr;

    @Getter
    @Setter
    @Field(type = FieldType.String)
    private String imgSrc;

    @Getter
    @Setter
    @Field(type = FieldType.Date)
    private Date dateAdded;

    @Getter
    @Setter
    @Field(type = FieldType.String)
    private Collection<String> tags;
}
