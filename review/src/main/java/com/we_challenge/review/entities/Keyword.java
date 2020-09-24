package com.we_challenge.review.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.lucene.analysis.th.ThaiTokenizerFactory;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TokenizerDef;

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

    @Field(index = Index.YES, analyze = Analyze.YES)
    private String keyword;
}
