/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waveq.imgbook.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import com.waveq.imgbook.def.Page;
import com.waveq.imgbook.service.PageManager;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Szymon
 * Class used to create page buttons that are at bottom of the site.
 */
@ManagedBean(name = "pageBean")
@RequestScoped
public class PageBean {

    private static final long serialVersionUID = 1L;
    @Inject
    PageManager pm;
    private Page page = new Page();

    public List<Page> getMainPageList() {
        int number = pm.simpleMainList().size();
        List<Page> pageList = new ArrayList<Page>();
        int counter = 0;
        for (int i = 0; i < number; i++) {
            if (i % 5 == 0) {
                counter++;
                page.setNumber(counter);
                pageList.add(page);
                page = new Page();
            }
        }
        return pageList;
    }

    public List<Page> getQueuePageList() {
        int number = pm.simpleQueueList().size();
        List<Page> pageList = new ArrayList<Page>();
        int counter = 0;
        for (int i = 0; i < number; i++) {
            if (i % 5 == 0) {
                counter++;
                page.setNumber(counter);
                pageList.add(page);
                page = new Page();
            }
        }
        return pageList;
    }
}
