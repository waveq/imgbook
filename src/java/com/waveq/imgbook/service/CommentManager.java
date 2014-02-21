/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waveq.imgbook.service;

import com.waveq.imgbook.entity.Comment;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Szymon
 */
@Stateless
public class CommentManager {
    
    @PersistenceContext
    EntityManager em;
    
     public Comment find(Integer i) {
        return em.find(Comment.class, i);
    }
     
     public List findByImageId(Integer id) {
         return em.createNamedQuery("Comment.findByImageId").setParameter("id", id).getResultList();
     }
     
     public void addComment(Comment comment) {
        comment.setId(null);
        em.persist(comment);
     }
}
