package com.waveq.imgbook.controllers.imageController;

//import com.waveq.imgbook.entity.Image;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@ApplicationScoped
public class ImageStreamer {

    @EJB
    private ImageBean imageBean;

 /*   public StreamedContent getImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Get ID value from actual request param.
            String id = context.getExternalContext().getRequestParameterMap().get("id");
   //         Image image = imageBean.find(Long.valueOf(id));
    //        return new DefaultStreamedContent(new ByteArrayInputStream(image.getBytes()));
        }
    } */
}