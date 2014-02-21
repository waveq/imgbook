/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waveq.imgbook.controllers;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Szymon
 */
@ManagedBean(name = "layoutBean")
@SessionScoped
public class LayoutBean implements Serializable {

    private static final long serialVersionUID = 1L;
    String cssDefault = "default.css";
    String cssLayout = "cssLayout.css";

    public void changeCss() {
        if (cssDefault.equals("default.css")) {
            cssDefault = "default2.css";
            cssLayout = "cssLayout2.css";
        } else {
            cssDefault = "default.css";
            cssLayout = "cssLayout.css";
        }
    }

    public String getCssDefault() {
        return cssDefault;
    }

    public String getCssLayout() {
        return cssLayout;
    }
}
