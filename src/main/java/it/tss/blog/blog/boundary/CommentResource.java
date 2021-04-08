/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.blog.boundary;

import it.tss.blog.blog.control.ArticleStore;
import it.tss.blog.blog.control.CommentStore;
import it.tss.blog.blog.control.UserStore;
import it.tss.blog.blog.entity.Comment;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author andre
 */
@DenyAll
public class CommentResource {

    @Inject
    CommentStore commentStore;
    @Inject
    ArticleStore articleStore;
    @Inject
    UserStore userStore;

    private Long commentId;
    private Long articleId;

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject find(@PathParam("commentId") Long id) {
        Comment comment = commentStore.find(id).orElseThrow(() -> new NotFoundException());
        return comment.toJson();
    }

    @DELETE
    @RolesAllowed("ADMIN")
    public Response delete(@PathParam("commentId") Long id) {
        Comment comment = commentStore.find(id).orElseThrow(() -> new NotFoundException());
        commentStore.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

}
