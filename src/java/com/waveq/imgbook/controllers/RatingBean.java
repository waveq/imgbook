package com.waveq.imgbook.controllers;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import com.waveq.imgbook.entity.User;
import com.waveq.imgbook.entity.Image;
import com.waveq.imgbook.entity.Rating;
import com.waveq.imgbook.service.ImageManager;
import com.waveq.imgbook.service.RatingManager;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

/**
 *
 * @author Szymon
 */
@ManagedBean(name = "ratingBean")
@RequestScoped
public class RatingBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private RatingManager rm;
    @Inject
    private ImageManager im;
    @ManagedProperty(value = "#{userBean.user}")
    private User injectedUser;
    private Rating rating = new Rating();
    private Image image = new Image();
    private boolean voted = false;
    String plusOrMinus;

    public List<Rating> getList() {
        return rm.findAll();
    }

    public void ratingListener(ActionEvent ae) {
        String ids = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get("imageID").toString();
        int id = Integer.parseInt(ids);
        this.image.setId(id);
        plusOrMinus = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("plusOrMinus").toString();
    }

    public String rate() {
        if (injectedUser.getId() == null) {
            this.addGrowl("Najpierw musisz się zalogować", "");
            return null;
        }

        this.image = im.find(image.getId());
        List<Rating> myList = rm.findAll();
        for (int i = 0; i < myList.size(); i++) {
            if (myList.get(i).getImage().getId() == image.getId() 
                    && myList.get(i).getUser().getId() == injectedUser.getId()) {
                voted = true;
                break;
            }
            voted = false;
        }
        if (!voted) {
            if (plusOrMinus.equals("-")) {
                this.image.setRating(image.getRating() - 1);
            } else {
                this.image.setRating(image.getRating() + 1);
            }
            im.update(this.image);

            this.rating.setUser(this.injectedUser);
            this.rating.setImage(this.image);

            rm.rate(rating);
            this.rating = new Rating();
            this.image = new Image();
            this.addGrowl("Dzięki", "");

            return null;
        } else {
            this.addGrowl("Już głosowałeś na ten obrazek", "");
        }
        return null;
    }

    public void addGrowl(String title, String content) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(title, content));
    }

    public void addInformation(String s) {
        FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, s, ""));
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
