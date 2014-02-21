/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waveq.imgbook.service;

import com.waveq.imgbook.entity.Rating;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Szymon
 */
@Stateless
public class RatingManager {

    @PersistenceContext
    EntityManager em;

    public List<Rating> findAll() {
        List<Rating> ratingList = em.createNamedQuery("Rating.findAll").getResultList();
        return ratingList;
    }

    public void rate(Rating rating) {
        rating.setId(null);
        em.persist(rating);
    }

    public Rating find(Integer i) {
        return em.find(Rating.class, i);
    }
}
