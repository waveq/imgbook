/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waveq.imgbook.controllers;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Szymon
 */

@ManagedBean(name="navigationBean")
@RequestScoped
public class NavigationBean implements Serializable {
    
    public String redirectToAddImage() {
        return "addimage";
    }
    public String redirectToIndex() {
        return "index";
    }
    public String redirectToOneImage() {
        return "index";
    }
     public String redirectToQueue() {
        return "queue";
    }
}
