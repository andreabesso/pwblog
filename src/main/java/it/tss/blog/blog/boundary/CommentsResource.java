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
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

/**
 *
 * @author andre
 */
@Path("/comments")
@DenyAll
public class CommentsResource {

    @Inject
    CommentStore commentStore;
    @Inject
    ArticleStore articleStore;
    @Inject
    UserStore userStore;
    @Context
    private ResourceContext resource;

    private Long articleId;
    
    @Inject
    @Claim(standard = Claims.sub)
    String userId;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public List<JsonObject> search() {
        return commentStore.searchByArticle(articleId)
                .stream()
                .map(v -> v.toJson())
                .collect(Collectors.toList());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public JsonObject create(JsonObject json) {
        String testo = json.getString("testo");
        String rating = json.getString("rating");
        Article article = articleStore.find(articleId).orElseThrow(() -> new NotFoundException());
        User user = userStore.find(Long.parseLong(userId)).orElseThrow(() -> new NotFoundException());
        Comment comment = new Comment(testo, article, user, Integer.parseInt(rating));
        Comment comm = commentStore.create(comment);
        return comm.toJson();
    }

    @Path("{commentId}")
    public CommentResource find(@PathParam("commentId") Long id) {
        CommentResource sub = resource.getResource(CommentResource.class);
        sub.setCommentId(id);
        return sub;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

}
