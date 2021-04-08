/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.blog.control;

import it.tss.blog.blog.entity.Comment;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author andre
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class CommentStore {

    @PersistenceContext
    EntityManager em;

    public List<Comment> searchByArticle(Long articleId) {
        return em.createQuery("select e from Comment e where e.article.id= :articleId  order by e.createdOn ", Comment.class)
                .setParameter("articleId", articleId)
                .getResultStream()
                .collect(Collectors.toList());
    }
    

   public Comment create(Comment c) {
        return em.merge(c);
    }

    public Optional<Comment> find(Long id) {
        Comment found = em.find(Comment.class, id);
        return found == null ? Optional.empty() : Optional.of(found);
    }

    public void delete(Long id) {
        em.remove(em.find(Comment.class, id));
    }

}