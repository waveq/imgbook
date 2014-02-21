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
public class PageManager {
    @PersistenceContext
    EntityManager em;

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
