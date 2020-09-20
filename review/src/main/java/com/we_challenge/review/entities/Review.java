package com.we_challenge.review.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Data
@Entity
@Indexed
@NoArgsConstructor
@Table(name = "review")
public class Review {
    @Id
    private Integer id;

    @Field(termVector = TermVector.YES)
    private String content;

    @Version
    private Integer version;

    public Review(Integer id) {
        this.id = id;
    }

    public Review(Integer id, String content, Integer version) {
        this.id = id;
        this.content = content;
        this.version = version;
    }
}
