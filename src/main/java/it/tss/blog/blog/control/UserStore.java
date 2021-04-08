/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.blog.control;

import it.tss.blog.blog.entity.User;
import it.tss.blog.security.control.SecurityEncoding;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author andre
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class UserStore {

    @PersistenceContext
    private EntityManager em;

    public List<User> search() {
        return em.createQuery("select e from User e where e.ban=false order by e.id", User.class)
                .getResultList();
    }

    public User create(User u) {
        u.setPwd(SecurityEncoding.shaHash(u.getPwd()));
        return em.merge(u);
    }

    public Optional<User> find(Long id) {
        User found = em.find(User.class, id);
        return found == null ? Optional.empty() : Optional.of(found);
    }

    public User update(User user, JsonObject json) {
        if (json.getString("fname") != null) {
            user.setFname(json.getJsonString("fname").getString());
        }
        if (json.getString("lname") != null) {
            user.setLname(json.getJsonString("lname").getString());
        }
        if (json.getString("pwd") != null) {
            user.setPwd(json.getJsonString("pwd").getString());
        }
        return em.merge(user);
    }

    public User ban(User user, JsonObject json) {
        if (json.getString("ban") != null) {
            user.setBan(true);
        }
        return em.merge(user);
    }

    public Optional<User> findByEmilAndPwd(String email, String pwd) {
        try {
            User found = em.createQuery("select e from User e where e.email= :email and e.pwd= :pwd", User.class)
                    .setParameter("email", email)
                    .setParameter("pwd", SecurityEncoding.shaHash(pwd))
                    .getSingleResult();
            return Optional.of(found);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public User coAdmin(User user, JsonObject json) {
        if (json.getString("role") != null) {
            user.setRole(User.Role.ADMIN);
        }
        return em.merge(user);
    }

    public void delete(Long userId) {
        em.remove(em.find(User.class, userId));
    }

}
