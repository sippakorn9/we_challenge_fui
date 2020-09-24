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
import javax.persistence.Version;

@Data
@Entity
@Indexed
@NoArgsConstructor
@AnalyzerDef(
        name = "thaianalyzer",
        tokenizer = @TokenizerDef(factory = ThaiTokenizerFactory.class)
)
@Table(name = "review")
public class Review {
    @Id
    private Integer id;

    @Field(index = Index.YES, analyze = Analyze.YES)
    private String content;

    @Version
    private Integer version;

    public Review(Integer id) {
        this.id = id;
    }

    public Review(Integer id, String content) {
        this.id = id;
        this.content = content;
    }

    public Review(Integer id, String content, Integer version) {
        this.id = id;
        this.content = content;
        this.version = version;
    }
}
