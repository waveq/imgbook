package com.waveq.imgbook.controllers.imageController;

import com.google.common.collect.Lists;
import com.waveq.imgbook.config.DBManager;
import com.waveq.imgbook.entity.Image;
import com.waveq.imgbook.entity.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
/**
 *
 * @author Szymon
 */
@ManagedBean(name="imageBean")
@SessionScoped
public class ImageBean implements Serializable {

    @ManagedProperty(value="#{userBean.user}")
    private User injectedUser;
    
    
    private static final int BUFFER_SIZE = 10124;
    private StreamedContent uploadedImage = new DefaultStreamedContent();
    private boolean uploaded;
    private String fileName; 
    private Image image = new Image();
    private int page = 1;
    

    private StreamedContent oneImage = new DefaultStreamedContent();
    private int passedId;

    public ImageBean() {
    }

    public String submit() {
        EntityManager em = DBManager.getManager().createEntityManager();
        Date date = new Date();
        image.setImage(fileName);
        image.setAddDate(date);
        image.setRating(0);
        image.setUser(getInjectedUser());
        
        em.getTransaction().begin();
        image.setId(null);
        em.persist(image);
        em.getTransaction().commit();
        em.close();
        this.image = new Image();
        uploaded = false;
        return null;  
    }
    
     public void loadImage() {
        EntityManager em = DBManager.getManager().createEntityManager();
        this.image = em.find(Image.class, image.getId());
        em.close();
    }
     
    public List<Image> getIndexList() {
        EntityManager em = DBManager.getManager().createEntityManager();
        List<Image> list = em.createNamedQuery("Image.findAllOrderByDateDESC").setFirstResult(0).setMaxResults(5).getResultList(); 
        em.close();
        return list;
    } 
       
    public List<Image> getList() {
        EntityManager em = DBManager.getManager().createEntityManager();
        List<Image> list = em.createNamedQuery("Image.findAllOrderByDateDESC").setFirstResult((page-1)*5).setMaxResults(5).getResultList(); 
        em.close();
        return list;
    } 

    // Adding (0) (1) (2) substring to name of file
    // ext is extension of the file
    public StreamedContent prepareImageToDisplay(String fileName) {
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
        String path = extContext.getRealPath("//resources//uploaded//" + fileName);
        File file = new File(path);
            InputStream stream;
        try {
            stream = new FileInputStream(file);
            uploadedImage = new DefaultStreamedContent(stream, "image/jpeg");
            return uploadedImage;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return uploadedImage;
    }

    public void handleFileUpload(FileUploadEvent event) {
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
        File result = new File(extContext.getRealPath("//resources//uploaded//" + event.getFile().getFileName()));
        int i = 0;
        String newName;
        while (result.length() > 0) {
            newName = changeName(i, result.getName());
            result = new File(extContext.getRealPath("//resources//uploaded//" + newName));
            i += 1;
        }
        try {
            InputStream inputStream;
            try (FileOutputStream fileOutputStream = new FileOutputStream(result)) {
                byte[] buffer = new byte[BUFFER_SIZE];
                int bulk;
                inputStream = event.getFile().getInputstream();
                while (true) {
                    bulk = inputStream.read(buffer);
                    if (bulk < 0) {
                        break;
                    }
                    fileOutputStream.write(buffer, 0, bulk);
                    fileOutputStream.flush();
                }
            }
            inputStream.close();
            fileName = result.getName();
            uploaded = true;

        } catch (IOException e) {
            FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Plik nie został wysłany", "");
            FacesContext.getCurrentInstance().addMessage(null, error);
        }
    }
    private String changeName(int count, String name) {
        String ext = "";
        if (count == 0) {
            if (name.indexOf(".") > 0) {
                ext = name.substring(name.lastIndexOf("."), name.length());
                name = name.substring(0, name.lastIndexOf("."));
            }
            name += " (" + count + ")" + ext;
        } else {
            if (name.indexOf(".") > 0) {
                ext = name.substring(name.lastIndexOf("."), name.length());
                name = name.substring(0, name.lastIndexOf("."));
            }
            name = name.replace("(" + (count - 1) + ")", "(" + count + ")" + ext);
        }
        return name;
    }
 
      public StreamedContent getImgToRepeat() {    
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            HttpServletRequest myRequest = (HttpServletRequest) context.getExternalContext().getRequest();
            String imageID =  (String) myRequest.getParameter("imageID");
            
            EntityManager em = DBManager.getManager().createEntityManager();  
            
            image = em.find(Image.class, Integer.parseInt(imageID));
            em.close();
            return prepareImageToDisplay(image.getImage());
        }
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public StreamedContent getUploadedImage() {
        return prepareImageToDisplay(fileName);         
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public User getInjectedUser() {
        return injectedUser;
    }

    public void setInjectedUser(User injectedUser) {
        this.injectedUser = injectedUser;
    }

    public StreamedContent getOneImage() {
            EntityManager em = DBManager.getManager().createEntityManager();       
            this.image = em.find(Image.class, image.getId());
            em.close();
            return prepareImageToDisplay(image.getImage());
    }

    public void setOneImage(StreamedContent oneImage) {
        this.oneImage = oneImage;
    }

    public int getPassedId() {
        return passedId;
    }

    public void setPassedId(int passedId) {
        EntityManager em = DBManager.getManager().createEntityManager();
        this.image = em.find(Image.class, passedId);
        em.close();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }


}
