/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.blog.boundary;

import it.tss.blog.blog.control.ArticleStore;
import it.tss.blog.blog.control.CommentStore;
import it.tss.blog.blog.control.UserStore;
import it.tss.blog.blog.entity.Article;
import it.tss.blog.blog.entity.Comment;
import it.tss.blog.blog.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author andre
 */
public class CommentsResource {

    @Inject
    CommentStore commentStore;
    @Inject
    ArticleStore articleStore;
    @Inject
    UserStore userStore;

    private Long articleId;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonObject> search() {
        return commentStore.searchByArticle(articleId)
                .stream()
                .map(v -> v.toJson())
                .collect(Collectors.toList());
    }

    @GET
    @Path("{commentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject find(@PathParam("commentId") Long id) {
        Comment comment = commentStore.find(id).orElseThrow(() -> new NotFoundException());
        return comment.toJson();
    }

   
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject create(JsonObject json) {
        String testo = json.getString("testo");
        String userId = json.getString("user");
        String rating = json.getString("rating");
        Article article = articleStore.find(articleId).orElseThrow(() -> new NotFoundException());
        User user = userStore.find(Long.parseLong(userId)).orElseThrow(() -> new NotFoundException());
        Comment comment = new Comment(testo, article, user, Integer.parseInt(rating));
        Comment comm = commentStore.create(comment);
        return comm.toJson();
    }

    @DELETE
    @Path("{commentId}")
    public Response delete(@PathParam("commentId") Long id) {
        Comment comment = commentStore.find(id).orElseThrow(() -> new NotFoundException());
        commentStore.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

}