/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waveq.imgbook.controllers;

import com.waveq.imgbook.config.DBManager;
import com.waveq.imgbook.controllers.imageController.ImageBean;
import com.waveq.imgbook.entity.Image;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import com.waveq.imgbook.def.Page;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Szymon
 */

@ManagedBean(name="pageBean")
@RequestScoped
public class PageBean {
    
    private ImageBean imageBean = new ImageBean();
    private Page page = new Page();
    
    public List<Page> getMainPageList () {
        int number = imageBean.getSimpleMainList().size();
        System.out.println(number);
        List<Page> pageList = new ArrayList<Page>();
        int counter = 0;
        for(int i=0;i<number;i++) {
            
            if(i%5 == 0) {
                System.out.println(i%5);
                counter++;
                page.setNumber(counter);
                pageList.add(page);
                page = new Page();
            }
        }
        return pageList;
    }
    
     public List<Page> getQueuePageList () {
        int number = imageBean.getSimpleQueueList().size();
        List<Page> pageList = new ArrayList<Page>();
        int counter = 0;
        for(int i=0;i<number;i++) {
            
            if(i%5 == 0) {
                System.out.println(i%5);
                counter++;
                page.setNumber(counter);
                pageList.add(page);
                page = new Page();
            }
        }
        return pageList;
    }
            
}
