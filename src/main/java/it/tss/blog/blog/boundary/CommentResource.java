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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

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

    @Inject
    @Claim(standard = Claims.sub)
    String userId;

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject find(@PathParam("commentId") Long id) {
        Comment comment = commentStore.find(id).orElseThrow(() -> new NotFoundException());
        return comment.toJson();
    }

  
    @GET//ritorna tutti i commenti di un commento
    @Path("/answers")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonObject> search() {
         return commentStore.searchByComment(commentId)
                .stream()
                .map(v -> v.toJson())
                .collect(Collectors.toList());
    }
   
    
    @DELETE
    @RolesAllowed({"ADMIN"})
    public Response delete(@PathParam("commentId") Long id) {
        Comment comment = commentStore.find(id).orElseThrow(() -> new NotFoundException());
        commentStore.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

   
    @POST//crea commento ad un altro commento
    @Path("/answer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public JsonObject answerTo(@PathParam("commentId") Long id, JsonObject json) {
        Comment commentAnswer = commentStore.find(id).orElseThrow(() -> new NotFoundException());
        String testo = json.getString("testo");
        String rating = json.getString("rating");
        Article article = articleStore.find(commentAnswer.getArticle().getId()).orElseThrow(() -> new NotFoundException());
        User user = userStore.find(Long.parseLong(userId)).orElseThrow(() -> new NotFoundException());
        Comment comment = new Comment(testo, article, user, Integer.parseInt(rating));
        comment.setAnswersTo(commentAnswer);
        Comment comm = commentStore.create(comment);
        return comm.toJson();
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

}
