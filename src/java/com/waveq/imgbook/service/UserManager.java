/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waveq.imgbook.service;

import com.waveq.imgbook.entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Szymon
 */
@Stateless
public class UserManager {
    @PersistenceContext
    EntityManager em;
    
    public void create(User user) {
        user.setId(null);
        em.persist(user);
    }
    
    public boolean uniqueLogin(String login) {
        if(em.createNamedQuery("User.findByLogin").setParameter("login", login).getResultList().size() > 0) {
            return false;
        }
        else {
            return true;
        }
    }
    
    public List<User> findAll() {
        List<User> userList = em.createNamedQuery("User.findAll").getResultList();
        return userList;
    }
    
    public User find(Integer i) {
        return em.find(User.class, i);
    }
    
    public User findByLogin(String login) {
        return (User)em.createNamedQuery("User.findByLogin").setParameter("login", login).getSingleResult();
    }
    
}
