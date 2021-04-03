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
import javax.persistence.Table;

/**
 *
 * @author andre
 */
@Entity
@Table(name = "article")
public class Article extends AbstractEntity implements Serializable {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2048)
    private String content;

    @Column(nullable =false)
    private String tag;
    

    public Article() {
    }

    public Article(String title, String content, String tag) {
        this.title = title;
        this.content = content;
        this.tag = tag;
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

    public JsonObject toJson(){
        return Json.createObjectBuilder()
                .add("title", this.title)
                .add("content", this.content)
                .add("tags", this.tag)
                .build();
              
    }
    
}