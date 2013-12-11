package com.waveq.imgbook.controllers.imageController;

//import com.waveq.imgbook.entity.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class ImageStreamer {
    
    private static final int BUFFER_SIZE = 10124;
    private String fileName;
    private boolean uploaded;
    
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
    /*
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
    }*/
    
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

}