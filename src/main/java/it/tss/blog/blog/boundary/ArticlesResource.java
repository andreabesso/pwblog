/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.blog.boundary;

import it.tss.blog.blog.control.ArticleStore;
import it.tss.blog.blog.control.CommentStore;
import it.tss.blog.blog.entity.Article;
import it.tss.blog.blog.entity.Comment;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import static javax.ws.rs.client.Entity.json;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author andre
 */
@Path("/articles")
public class ArticlesResource {
    
    @Inject
    ArticleStore store;
    
    @Context
    private ResourceContext resource;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonObject> search(){
        return store.search()
                .stream()
                .map(v -> v.toJson())
                .collect(Collectors.toList());
    }
  
 
  @Path("{id}")
  public ArticleResource find(@PathParam("id") Long id){
      ArticleResource sub = resource.getResource(ArticleResource.class);
              sub.setArticleId(id);
              return sub;
  }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject create(JsonObject json){
        String title=json.getString("title");
        String content=json.getString("content");
        String tag=json.getString("tag");
        Article article=new Article(title, content, tag);
        Article art= store.create(article);
        return art.toJson();
    }

  
    
    
    
}