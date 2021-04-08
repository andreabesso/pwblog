/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.blog.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author andre
 */
@Entity
@Table(name = "article")
public class Article extends AbstractEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "article_sequence", sequenceName = "article_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "article_sequence")
    protected Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2048)
    private String content;

    @Column(nullable = false)
    private String tag;

    @Column(name = "dateArt", insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateArt;

    public Article() {
    }

    public Article(String title, String content, String tag) {
        this.title = title;
        this.content = content;
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tags) {
        this.tag = tags;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Article other = (Article) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("title", this.title)
                .add("content", this.content)
                .add("tags", this.tag)
                .build();

    }

}
