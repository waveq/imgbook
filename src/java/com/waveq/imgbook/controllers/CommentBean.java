package com.waveq.imgbook.controllers;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import com.waveq.imgbook.entity.Comment;
import com.waveq.imgbook.entity.Image;
import com.waveq.imgbook.entity.User;
import com.waveq.imgbook.service.CommentManager;
import com.waveq.imgbook.service.ImageManager;
import com.waveq.imgbook.service.UserManager;
import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;

/**
 *
 * @author Szymon
 */
@ManagedBean(name = "commentBean")
@SessionScoped
public class CommentBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    UserManager um;
    
    @Inject
    CommentManager cm;
    
    @Inject
    ImageManager im;
    
    @ManagedProperty(value = "#{userBean.user}")
    private User injectedUser;
    private Comment comment = new Comment();
    private Image image = new Image();
    private int passedImageId;
    private int userID;

    public CommentBean() {
        this.injectedUser = new User();
    }

    public List<Comment> getList() {
        return cm.findByImageId(passedImageId);
    }

    public String addComment() {
        Date date = new Date();
        image = im.find(passedImageId);
        // injectedUser without it is null (?)
        injectedUser = um.findByLogin(injectedUser.getLogin());
        comment.setUser(injectedUser);
        comment.setImage(image);
        comment.setAddDate(date);

        cm.addComment(comment);
        this.addGrowl("Dodano komentarz", "");
        comment = new Comment();
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
