package com.waveq.imgbook.controllers;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import com.waveq.imgbook.entity.User;
import com.waveq.imgbook.service.UserManager;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
/**
 *
 * @author Szymon
 */

@ManagedBean(name="userBean")
@SessionScoped
public class UserBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Inject
    private UserManager um;
    
    private User user = new User();
    private boolean loggedIn = false;
    private boolean admin = false;
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public String create() {
        user.setLogin(user.getLogin().toLowerCase());
        if(um.uniqueLogin(user.getLogin())) {
            um.create(user);
            this.addGrowl("Witaj w serwisie "+user.getLogin(),"");
        } else {
            this.addGrowl("Użytkownik o takim loginie już istnieje", "");
        }
        return null;
    }
    
    public String doLogin() {
        List<User> userList = um.findAll();       
        // Set input login to lower case and delete whitespaces.
        user.setLogin(user.getLogin().toLowerCase().replaceAll("\\s", ""));

        for (int  i = 0; i < userList.size(); i++) {
            if (userList.get(i).getLogin().equals(user.getLogin())&& 
                    userList.get(i).getPassword().equals(user.getPassword())) { 
                this.user = um.find(userList.get(i).getId());
                loggedIn = true;
                System.out.println("id zalogowanego: "+user.getId());
                this.addGrowl("Zalogowano", "");
                if(user.getId() == 11)
                    admin = true;
                return null;
            }
        }
        // Set login ERROR
        FacesMessage msg = new FacesMessage("Błąd logowania!", "");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        // To to index page
        return null;
    }
    
    public String doLogout() {
        loggedIn = false;
        addGrowl("Wylogowano","");
        // clearing session
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "logout-success";
    }
    
    public void addInformation(String s) {
        FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, s, ""));
    }
    
    public void addGrowl(String title, String content) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(title, content));
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
