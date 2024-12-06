package it.liguriadigitale.ponmetro.portale.presentation.pages.configurazione.avatar.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.configurazione.avatar.util.UtilConfigurazioneAvatar;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.util.lang.Bytes;

public class ConfigurazioneAvatarPanel extends BasePanel {

  private static final long serialVersionUID = 3014101184669099579L;

  private FileUploadField fileUploadField;

  public ConfigurazioneAvatarPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    createFeedBackPanel();

    addForm();
  }

  private void addForm() {

    Form<?> form =
        new Form<Void>("form") {

          private static final long serialVersionUID = -2534801652745695910L;
        };
    form.setMultiPart(false);
    form.setMaxSize(Bytes.kilobytes(1000));
    fileUploadField = new FileUploadField("fileInput");
    add(form);

    fileUploadField.add(
        new AjaxFormSubmitBehavior("change") {

          private static final long serialVersionUID = 1509035538086304756L;

          @Override
          protected void onError(AjaxRequestTarget target) {

            super.onError(target);
            target.add(getPage());
          }

          @Override
          protected void onSubmit(AjaxRequestTarget target) {

            FileUpload fileUpload = fileUploadField.getFileUpload();
            File temp = null;

            try {
              String mimeType =
                  UtilConfigurazioneAvatar.getMimeTypeFileAvatar(fileUpload.getBytes());

              if (fileUpload != null
                  && (mimeType.equalsIgnoreCase("image/png")
                      || mimeType.equalsIgnoreCase("image/jpeg"))) {
                try {

                  temp = File.createTempFile(fileUpload.getClientFileName(), ".png");
                  FileUtils.writeByteArrayToFile(temp, fileUpload.getBytes());
                  ServiceLocator.getInstance()
                      .getServiziConfigurazione()
                      .uploadAvatar(getUtente(), temp);
                  success("Immagine caricata con successo.");

                } catch (BusinessException | IOException e) {
                  error("Attenzione, errore nel caricamento immagine.");
                  log.error("Errore durante il caricamento del file: " + e.getMessage());

                } finally {
                  if (temp != null)
                    try {
                      Files.delete(Paths.get(temp.getPath()));
                    } catch (IOException e) {
                      log.error("errore cancellazione file avatar fallita: " + e.getMessage());
                    }
                }
              } else {
                error(
                    "Attenzione, errore nel formato dell'immagine. Formati validi .png opppure .jpg");
              }

            } catch (BusinessException e) {
              log.error("errore cancellazione file avatar fallita " + e.getMessage());
            }

            target.add(getPage());
          }
          ;
        });
    form.add(fileUploadField);
    this.add(new NonCachingImage("imageUpload", getAvatar()));
  }

  @Override
  public void fillDati(Object dati) {}

  private IResource getAvatar() {

    return new DynamicImageResource() {

      private static final long serialVersionUID = -5718780936386736508L;

      @Override
      protected byte[] getImageData(IResource.Attributes attributes) {
        try {
          return ServiceLocator.getInstance()
              .getServiziConfigurazione()
              .getImagineCaricata(getUtente());
        } catch (BusinessException | ApiException | IOException e) {
          log.error("Errore durante il caricamento dell'avatar: ", e);
          return new byte[1];
        }
      }
    };
  }
}
