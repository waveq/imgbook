/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waveq.imgbook.service;

import com.waveq.imgbook.entity.Image;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Szymon
 */
@Stateless
public class ImageManager {

    @PersistenceContext
    EntityManager em;

    public Image find(Integer i) {
        return em.find(Image.class, i);
    }

    public void update(Image image) {
        em.merge(image);
    }

    public void remove(Image image) {
        em.remove(em.merge(image));
    }

    public void submit(Image image) {
        image.setId(null);
        em.persist(image);
    }

    /**
     * LISTS
     *
     * @return list of Images that is needed for specific page
     */
    public List mainFirstPageList() {
        List<Image> list = em
                .createNamedQuery("Image.findAllOrderByMainDateDESC")
                .setFirstResult(0).setMaxResults(5).getResultList();
        return list;
    }

    public List mainNotFirstPageList(int page) {
        List<Image> list = em
                .createNamedQuery("Image.findAllOrderByMainDateDESC")
                .setFirstResult((page - 1) * 5).setMaxResults(5).getResultList();
        return list;
    }

    public List queueFirstPageList() {
        List<Image> list = em.createNamedQuery("Image.findAllOrderByDateDESC").
                setFirstResult(0).setMaxResults(5).getResultList();
        return list;
    }

    public List queueNotFirstPageList(int page) {
        List<Image> list = em.createNamedQuery("Image.findAllOrderByDateDESC")
                .setFirstResult((page - 1) * 5).setMaxResults(5).getResultList();
        return list;
    }

    public List simpleQueueList() {
        List<Image> list = em.createNamedQuery("Image.findAllQueue")
                .getResultList();
        return list;
    }

    public List simpleMainList() {
        List<Image> list = em.createNamedQuery("Image.findAllMain").getResultList();
        return list;
    }
}
