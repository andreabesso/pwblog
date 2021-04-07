/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.blog.boundary;

import it.tss.blog.blog.control.ArticleStore;
import it.tss.blog.blog.entity.Article;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author andre
 */
public class ArticleResource {

    @Inject
    private ArticleStore store;

    @Context
    ResourceContext resource;

    private Long articleId;

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject update(JsonObject json) {
        String title = json.getString("title");
        String content = json.getString("content");
        String tag = json.getString("tag");
        Article article = store.find(articleId).orElseThrow(() -> new NotFoundException());
        Article art=new Article(title, content, tag);
        Article artupdate = store.update(art, json);
        return artupdate.toJson();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject find(@PathParam("id") Long id) {
        Article article = store.find(id).orElseThrow(() -> new NotFoundException());
        return article.toJson();
    }

    @Path("comments")
    public CommentsResource comments() {
        CommentsResource sub = resource.getResource(CommentsResource.class);
        sub.setArticleId(articleId);
        return sub;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

}