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
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author andre
 */
@Entity
@Table(name = "comment")
public class Comment extends AbstractEntity implements Serializable {

    @Column(nullable = false)
    private String testo;
    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id")
    private Article article;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;
    private int rating;

    public Comment() {
    }

    public Comment(String testo, Article article, User user, int rating) {
        this.testo = testo;
        this.article = article;
        this.user = user;
        this.rating = rating;
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