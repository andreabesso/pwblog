/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.blog.control;

import it.tss.blog.blog.entity.Article;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author andre
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class ArticleStore {
    
    @PersistenceContext
    private EntityManager em;

    public List<Article> search() {
        return em.createQuery("select e from Article e order by e.id ", Article.class)
                .getResultList();
    }

    public Optional<Article> find(Long id) {
        Article found=em.find(Article.class, id);
        return found == null ? Optional.empty() : Optional.of(found);
    
    }

    public Article create(Article a) {
        return em.merge(a);
    }

    public Article update(Article article, JsonObject json) {
    if(json.getString("title")!= null){
        article.setTitle(json.getJsonString("title").getString());
    }
    if(json.getString("content")!= null){
        article.setContent(json.getJsonString("content").getString());
          }
    if(json.getString("tag")!= null){
        article.setTag(json.getJsonString("tag").getString());
    }
    return em.merge(article);
    }

    public void delete(Long articleId) {
        em.remove(em.find(Article.class, articleId));
    }
    
    
}