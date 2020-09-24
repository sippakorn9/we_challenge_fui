package com.we_challenge.review.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Indexed
@NoArgsConstructor
@Table(name = "keyword")
public class Keyword {
    @Id
    private Integer id;

    @Field
    private String keyword;
}
