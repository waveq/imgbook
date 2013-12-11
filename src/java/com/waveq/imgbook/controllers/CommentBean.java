package com.waveq.imgbook.controllers;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import com.waveq.imgbook.config.DBManager;
import com.waveq.imgbook.entity.Comment;
import com.waveq.imgbook.entity.Image;
import com.waveq.imgbook.entity.User;
import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;


/**
 *
 * @author Szymon
 */
@ManagedBean(name="commentBean")
@SessionScoped
public class CommentBean implements Serializable {

    @ManagedProperty(value="#{userBean.user}")
    private User injectedUser;
    
    private Comment comment = new Comment();
    private Image image = new Image();
    

   
    private int passedImageId;
    private int userID;

    public CommentBean() {
        this.injectedUser = new User();
    }

    public List<Comment> getList() {
        EntityManager em = DBManager.getManager().createEntityManager();
        List list = em.createQuery("from Comment c WHERE c.image.id=:id").setParameter("id", passedImageId).getResultList();
        em.close();
        return list;
    }

    public String addComment() {
        
        Date date = new Date();
        
        EntityManager em = DBManager.getManager().createEntityManager();
        
        this.image = em.find(Image.class, passedImageId);

        this.comment.setUser(injectedUser);
        this.comment.setImage(this.image);
        this.comment.setAddDate(date);

        em.getTransaction().begin();
        comment.setId(null);
        em.persist(comment);
        em.getTransaction().commit();
        this.addInformation("Dodano komentarz");
        em.close();
        this.comment = new Comment();
        return null;
    }
/*
    public String delete() {
        EntityManager em = DBManager.getManager().createEntityManager();
        em.getTransaction().begin();
        this.sign = em.find(Sign.class, sign.getId());
        em.remove(this.sign);
        this.sign = new Sign();
        em.getTransaction().commit();
        em.close();
        this.addGrowl("Sukces!", "Wypisano użytkownika.");
        return null;
    }

    public void signListener(ActionEvent ae) {
        String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("signID").toString();
        int id = Integer.parseInt(ids);
        this.sign.setId(id);
    }
*/
    public void addInformation(String s) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, s, ""));
    }

    public void addGrowl(String title, String content) {
        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage(title, content));
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Comment getComment() {
        return comment;
    }

    public void setSign(Comment comment) {
        this.comment = comment;
    }

    public int getPassedImageId() {
        return passedImageId;
    }

    public void setPassedImageId(int passedImageId) {
        this.passedImageId = passedImageId;
    }

    public User getInjectedUser() {
        return injectedUser;
    }

    public void setInjectedUser(User injectedUser) {
        this.injectedUser = injectedUser;
    }
}
