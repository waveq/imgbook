package com.waveq.imgbook.controllers;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import com.waveq.imgbook.config.DBManager;
import com.waveq.imgbook.entity.User;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
/**
 *
 * @author Szymon
 */

@ManagedBean(name="userBean")
@SessionScoped
public class UserBean implements Serializable {
    
    private User user = new User();
    private boolean loggedIn = false;
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public String create() {
        EntityManager em = DBManager.getManager().createEntityManager();
        user.setLogin(user.getLogin().toLowerCase());
        em.getTransaction().begin();
        user.setId(null);
        em.persist(user);
        em.getTransaction().commit();
        this.addInformation("Dodano użytkownika");
        em.close();
        this.user = new User();
        return null;
    }
    
    public String doLogin() {
        EntityManager em = DBManager.getManager().createEntityManager();
        List<User> userList = em.createNamedQuery("User.findAll").getResultList();
        // Set input login to lower case and delete whitespaces.
        user.setLogin(user.getLogin().toLowerCase().replaceAll("\\s", ""));

        for (int  i = 0; i < userList.size(); i++) {
            if (userList.get(i).getLogin().equals(user.getLogin()) && userList.get(i).getPassword().equals(user.getPassword())) { 
                this.user.setId(userList.get(i).getId());
                this.user = em.find(User.class, user.getId());
                em.close();
                loggedIn = true;
                addInformation("Zalogowano");
                return "login-success";
            } else {}
        }
        // Set login ERROR
        em.close();
        FacesMessage msg = new FacesMessage("Błąd logowania!", "");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        // To to index page
        return null;
    }
    
    public String doLogout() {
        loggedIn = false;
        addInformation("Wylogowano");
        // clearing session
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "logout-success";
    }
    
    public void addInformation(String s) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, s, ""));
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
