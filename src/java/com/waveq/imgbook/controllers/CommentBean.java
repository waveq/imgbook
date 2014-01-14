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
        System.out.println("Passed image: "+ passedImageId);
        System.out.println("Passed user: "+ injectedUser);
        System.out.println("Date: " + date);
        System.out.println("Comment content" + comment.getContent());
        
        this.image = em.find(Image.class, passedImageId);

        this.comment.setUser(injectedUser);
        this.comment.setImage(this.image);
        this.comment.setAddDate(date);

        em.getTransaction().begin();
        comment.setId(null);
        em.persist(comment);
        em.getTransaction().commit();
        this.addGrowl("Dodano komentarz", "");
        em.close();
        this.comment = new Comment();
        return null;
    }

    
    
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
