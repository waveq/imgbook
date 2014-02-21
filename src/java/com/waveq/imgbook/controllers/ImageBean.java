package com.waveq.imgbook.controllers;

import com.waveq.imgbook.entity.Image;
import com.waveq.imgbook.entity.User;
import com.waveq.imgbook.service.ImageManager;
import com.waveq.imgbook.service.UserManager;
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
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Szymon
 */
@ManagedBean(name = "imageBean")
@SessionScoped
public class ImageBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String PATH_FOR_IMAGES = "//resources//uploaded//";
    @Inject
    ImageManager im;
    @Inject
    UserManager um;
    @ManagedProperty(value = "#{userBean.user}")
    private User injectedUser;
    private static final int BUFFER_SIZE = 10124;
    private StreamedContent uploadedImage = new DefaultStreamedContent();
    private boolean uploaded;
    private String fileName;
    private Image image = new Image();
    private int page = 1;
    private int passedId;
    private StreamedContent oneImage = new DefaultStreamedContent();

    public ImageBean() {
    }

    public void imageListener(ActionEvent ae) {
        String ids = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get("imageID").toString();
        int id = Integer.parseInt(ids);
        this.image.setId(id);
    }

    public String promoteToMain() {
        image = im.find(image.getId());
        Date date = new Date();
        image.setMainDate(date);
        im.update(image);
        return null;
    }

    public String downgradeToQueue() {
        image = im.find(image.getId());
        image.setMainDate(null);
        im.update(image);
        return null;
    }

    public String delete() {
        image = im.find(image.getId());
        im.remove(image);
        return null;
    }

    public String submit() {
        Date date = new Date();
        image.setImage(fileName);
        image.setAddDate(date);
        image.setRating(0);

        // injectedUser without it is null (?)
        injectedUser = um.findByLogin(injectedUser.getLogin());
        image.setUser(injectedUser);
        im.submit(image);
        image = new Image();
        uploaded = false;
        return null;
    }

    public void clearBean() {
        this.image = new Image();
    }

    // LISTS TO UI:REPEAT
    public List<Image> getMainFirstPageList() {
        return im.mainFirstPageList();
    }

    public List<Image> getMainNotFirstPageList() {
        return im.mainNotFirstPageList(page);
    }

    public List<Image> getQueueFirstPageList() {
        return im.queueFirstPageList();
    }

    public List<Image> getQueueNotFirstPageList() {
        return im.queueNotFirstPageList(page);
    }

    public List<Image> getSimpleQueueList() {
        return im.simpleQueueList();
    }

    public List<Image> getSimpleMainList() {
        return im.simpleMainList();
    }

    /**
     * Returns image of given name as streamedContent. StreamedContent is type
     * that is needed by primefaces component that is responsible for displaying
     * image.
     *
     * @param fileName
     */
    public StreamedContent prepareImageToDisplay(String fileName) {
        ExternalContext extContext = FacesContext.getCurrentInstance()
                .getExternalContext();
        String path = extContext.getRealPath(PATH_FOR_IMAGES + fileName);
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

    /**
     * Streams sent image to resources/uploaded/ (PATH_FOR_IMAGES) by the way it
     * changes name of the file, if name is already occupied.
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        ExternalContext extContext = FacesContext.getCurrentInstance()
                .getExternalContext();
        File streamDestination = new File(extContext.getRealPath(PATH_FOR_IMAGES
                + event.getFile().getFileName()));

        int i = 0;
        String newName;
        while (streamDestination.length() > 0) {
            newName = changeName(i, streamDestination.getName());
            streamDestination = new File(extContext.getRealPath(PATH_FOR_IMAGES + newName));
            i += 1;
        }
        stream(streamDestination, event.getFile());
    }

    /**
     * Streams fileToStream into streamDestination (/resources/uploaded/ + file
     * name)
     *
     * @param streamDestination
     * @param fileToStream
     */
    private void stream(File streamDestination, UploadedFile fileToStream) {
        try {
            InputStream inputStream;
            FileOutputStream fileOutputStream = new FileOutputStream(streamDestination);

            byte[] buffer = new byte[BUFFER_SIZE];
            int bulk;
            inputStream = fileToStream.getInputstream();
            while (true) {
                bulk = inputStream.read(buffer);
                if (bulk < 0) {
                    break;
                }
                fileOutputStream.write(buffer, 0, bulk);
                fileOutputStream.flush();
            }
            inputStream.close();
            fileName = streamDestination.getName();
            uploaded = true;

        } catch (IOException e) {
            FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Plik nie został wysłany", "");
            FacesContext.getCurrentInstance().addMessage(null, error);
        }
    }

    // Adding (0) (1) (2) substring to name of file
    // ext is extension of the file
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

    /**
     * Used in ui:repeat returns requested images, finds them thanks to passed
     * imageID
     */
    public StreamedContent getImgToRepeat() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            HttpServletRequest myRequest =
                    (HttpServletRequest) context.getExternalContext().getRequest();
            String imageID = (String) myRequest.getParameter("imageID");

            image = im.find(Integer.parseInt(imageID));
            return prepareImageToDisplay(image.getImage());
        }
    }

    /**
     * used for image.xhtml
     */
    public StreamedContent getOneImage() {
        this.image = im.find(image.getId());
        return prepareImageToDisplay(image.getImage());
    }

    /**
     * Used after uploading image in addimage.xhtml
     */
    public StreamedContent getUploadedImage() {
        return prepareImageToDisplay(fileName);
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
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

    public void setOneImage(StreamedContent oneImage) {
        this.oneImage = oneImage;
    }

    public int getPassedId() {
        return passedId;
    }

    public void setPassedId(int passedId) {
        this.image = im.find(passedId);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
