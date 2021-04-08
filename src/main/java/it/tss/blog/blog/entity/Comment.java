/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.blog.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author andre
 */
@Entity
@Table(name = "comment")
public class Comment extends AbstractEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "comment_sequence", sequenceName = "comment_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "comment_sequence")
    protected Long id;

    @Column(nullable = false)
    private String testo;
    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id")
    private Article article;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;
    private int rating;
    @ManyToOne
    @JoinColumn(name = "answersto_id")
    private Comment answersTo;
    @Column(name = "dateCom", insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateCom;
    
    public Comment() {
    }

    public Comment(String testo, Article article, User user, int rating) {
        this.testo = testo;
        this.article = article;
        this.user = user;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Comment getAnswersTo() {
        return answersTo;
    }

    public void setAnswersTo(Comment answersTo) {
        this.answersTo = answersTo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final Comment other = (Comment) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public JsonObject toJson() {

        return Json.createObjectBuilder()
                .add("fname", this.user.getFname())
                .add("email", this.user.getEmail())
                .add("article", this.article.getTitle())
                .add("testo", this.testo)
                .add("rating", this.rating)
                .build();
    }


}
