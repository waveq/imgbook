package com.waveq.imgbook.controllers;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import com.waveq.imgbook.config.DBManager;
import com.waveq.imgbook.entity.User;
import com.waveq.imgbook.entity.Image;
import com.waveq.imgbook.entity.Rating;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
/**
 *
 * @author Szymon
 */

@ManagedBean(name="ratingBean")
@RequestScoped
public class RatingBean implements Serializable {
    @ManagedProperty(value="#{userBean.user}")
    private User injectedUser;
    
    private Rating rating = new Rating();

    
    private Image image = new Image();
    private boolean voted = false;
    String plusOrMinus;

    public List<Rating> getList() {
        EntityManager em = DBManager.getManager().createEntityManager();
        List<Rating> list = em.createNamedQuery("Rating.findAll").getResultList();
        em.close();
        return list;
    }
    
     public void ratingListener(ActionEvent ae) {
        String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("imageID").toString();
        int id = Integer.parseInt(ids);
        this.image.setId(id);
        plusOrMinus = FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap().get("plusOrMinus").toString();
    }
    
    public String rate() {
        EntityManager em = DBManager.getManager().createEntityManager();
        
        if(injectedUser.getId() == null) {
            this.addGrowl("Najpierw musisz się zalogować","");
            em.close();
            return null;
        }
        
        this.image = em.find(Image.class, image.getId());
        List<Rating> myList = em.createNamedQuery("Rating.findAll").getResultList();
        for(int i=0;i<myList.size(); i++) {
            if(myList.get(i).getImage().getId() == image.getId() && myList.get(i).getUser().getId() == injectedUser.getId()) {
                voted = true;
                break;
            }
            voted = false;
        }
        if(!voted) {
            if(plusOrMinus.equals("-")) {
                this.image.setRating(image.getRating()-1);
            }
            else {
                this.image.setRating(image.getRating()+1);
            }
            em.getTransaction().begin();
            em.merge(this.image);
            em.getTransaction().commit();
            
            this.rating.setUser(this.injectedUser);
            this.rating.setImage(this.image);
        
            em.getTransaction().begin();
            rating.setId(null);
            em.persist(rating);
            em.getTransaction().commit();
            em.close();
            this.rating = new Rating();
            this.image = new Image();    
            this.addGrowl("Dzięki","");
            
            return null;
        } else {
            this.addGrowl("Już głosowałeś na ten obrazek","");
        }
        em.close();
        return null;
    }
    
     public void addGrowl(String title, String content) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(title, content));
    }
    
    public void addInformation(String s) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, s, ""));
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public User getInjectedUser() {
        return injectedUser;
    }

    public void setInjectedUser(User injectedUser) {
        this.injectedUser = injectedUser;
    }

}
