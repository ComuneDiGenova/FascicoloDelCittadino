package it.liguriadigitale.ponmetro.portale.presentation.components.download;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.resource.IResourceStream;

public abstract class BottoneAJAXDownloadWithError<T extends Component> extends Panel {

  private static final String WICKET_ID_BOTTONE_DOWNLOAD = "download";
  private static final long serialVersionUID = -6136291330764426401L;
  protected static Log log = LogFactory.getLog(BottoneAJAXDownloadWithError.class);

  protected BottoneAJAXDownload download;

  private boolean visibileDopoDownload = false;
  private Component panel;

  protected abstract FileDaScaricare eseguiBusinessPerGenerazionePDF();

  public BottoneAJAXDownloadWithError(String id, Component panel, boolean visibileDopoDownload) {
    this(id, panel);
    this.setVisibileDopoDownload(visibileDopoDownload);
  }

  public BottoneAJAXDownloadWithError(String id, Component panel) {
    super(id);
    this.panel = panel;
    setOutputMarkupId(true);
    NotificationPanel feedBackPulsante = createFeedBackPulsante();
    add(feedBackPulsante);
    add(createDownloadLink(feedBackPulsante));
  }

  private NotificationPanel createFeedBackPulsante() {
    NotificationPanel feedback =
        new NotificationPanel("feedbackPulsante") {

          private static final long serialVersionUID = -883302032153540620L;

          @Override
          protected boolean isCloseButtonVisible() {
            /* Feedback senza X per la chiusura */
            return false;
          }
        };
    feedback.setOutputMarkupId(true);
    return feedback;
  }

  private AjaxLink<Page> createDownloadLink(NotificationPanel feedBackPulsante) {
    download =
        new BottoneAJAXDownload(feedBackPulsante) {

          private static final long serialVersionUID = 1L;

          private byte[] pdfBytes;
          private String filename;
          private String messaggioErrore;

          public byte[] getPdfBytes() {
            return pdfBytes;
          }

          @Override
          public void setPdfBytes(byte[] pdfBytes) {
            this.pdfBytes = pdfBytes;
          }

          @Override
          protected String getFileName() {
            return filename;
          }

          @Override
          public void setFilename(String filename) {
            this.filename = filename;
          }

          @Override
          public void setMessaggioErrore(String messaggioErrore) {
            this.messaggioErrore = messaggioErrore;
          }

          @Override
          public String getMessaggioErrore() {
            return messaggioErrore;
          }

          @Override
          protected IResourceStream getResourceStream() {
            return PageUtil.createResourceStream(getPdfBytes());
          }

          @Override
          public boolean onBeforeDownload(AjaxRequestTarget target) {
            FileDaScaricare fileDaScaricare = eseguiBusinessPerGenerazionePDF();
            if (fileDaScaricare.isEsitoOK()) {
              download.setPdfBytes(fileDaScaricare.getFileBytes());
              download.setFilename(fileDaScaricare.getFileName());
              return true;
            } else {
              download.setMessaggioErrore(fileDaScaricare.getMessaggioErrore());
              return false;
            }
          }
        };
    add(download);

    final LaddaAjaxLink<Page> linkDownload =
        new LaddaAjaxLink<Page>(WICKET_ID_BOTTONE_DOWNLOAD, Buttons.Type.Primary) {

          private static final long serialVersionUID = 4503382426855985520L;

          @Override
          public void onClick(final AjaxRequestTarget target) {
            if (download.onBeforeDownload(target)) {
              download.initiate(target);
              setVisible(isVisibileDopoDownload());
            } else {
              setVisible(false);
              feedBackPulsante.error(download.getMessaggioErrore());
            }
            target.add(BottoneAJAXDownloadWithError.this);
          }

          @Override
          public <L extends Serializable> BootstrapAjaxLink<Page> setLabel(IModel<L> label) {
            return super.setLabel(label);
          }
        };
    linkDownload.setLabel(Model.of(creaLabelEtichetta(panel, WICKET_ID_BOTTONE_DOWNLOAD)));

    IconType icon =
        new IconType(WICKET_ID_BOTTONE_DOWNLOAD) {

          private static final long serialVersionUID = 4478767979072257607L;

          @Override
          public String cssClassName() {
            return "icon-download";
          }
        };
    linkDownload.setIconType(icon);

    return (linkDownload);
  }

  protected String creaLabelEtichetta(Component pannello, String id) {
    String nomePannello = pannello.getClass().getSimpleName();
    String resourceId = nomePannello + "." + id;
    String etichetta = getLocalizer().getString(resourceId, pannello);
    return etichetta;
  }

  public boolean isVisibileDopoDownload() {
    return visibileDopoDownload;
  }

  public void setVisibileDopoDownload(boolean visibileDopoDownload) {
    this.visibileDopoDownload = visibileDopoDownload;
  }
}
