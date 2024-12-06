package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.documentiallegati;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.AllegatoBody;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.AllegatoPermessiPersonalizzati;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.documentiallegati.form.FormDocumentoAllegatoPermessiPersonalizzati;
import java.io.IOException;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class AllegatoPermessiPersonalizzatiPanel extends BasePanel {

  private Label lblEtichettaAllegato;
  private FormDocumentoAllegatoPermessiPersonalizzati formDocumentoAllegatoPermessiPersonalizzati;
  private WebMarkupContainer wmkAllegatoInSolaLettura;
  FeedbackPanel feedbackPanel;
  String wicketId;
  CompoundPropertyModel<AllegatoPermessiPersonalizzati> allegatoPermessiPersonalizzatiModel;
  private int idDomanda;
  boolean enabled;

  public AllegatoPermessiPersonalizzatiPanel(
      String wicketId,
      CompoundPropertyModel<AllegatoPermessiPersonalizzati> allegatoPermessiPersonalizzatiModel,
      boolean enabled,
      int idDomanda) {
    super(wicketId);
    this.wicketId = wicketId;
    this.allegatoPermessiPersonalizzatiModel = allegatoPermessiPersonalizzatiModel;
    this.idDomanda = idDomanda;
    this.feedbackPanel = createFeedBackPPPanel();
    this.enabled = enabled;
    // this.setEnabled(enabled);

  }

  private static final long serialVersionUID = -3021289773196275267L;

  @Override
  public void fillDati(Object dati) {

    // CompoundPropertyModel<AllegatoPermessiPersonalizzati>
    // allegatoPermessiPersonalizzatiModel =
    // (CompoundPropertyModel<AllegatoPermessiPersonalizzati>) dati;

    addOrReplace(
        lblEtichettaAllegato =
            new Label(
                "lblEtichettaAllegato",
                Model.of(
                    allegatoPermessiPersonalizzatiModel.getObject().getDescrizioneAllegato())));

    addOrReplace(
        formDocumentoAllegatoPermessiPersonalizzati =
            new FormDocumentoAllegatoPermessiPersonalizzati(
                "formDocumentoAllegatoPermessiPersonalizzati",
                allegatoPermessiPersonalizzatiModel,
                feedbackPanel,
                getUtente(),
                wicketId,
                idDomanda));
    formDocumentoAllegatoPermessiPersonalizzati.setMultiPart(true);
    formDocumentoAllegatoPermessiPersonalizzati.setOutputMarkupId(true);

    formDocumentoAllegatoPermessiPersonalizzati.setVisible(enabled);

    wmkAllegatoInSolaLettura = new WebMarkupContainer("wmkAllegatoInSolaLettura");

    BottoneAJAXDownloadWithError downloadLinkInSolaLettura = createDownloadLink();

    //		Label nomeFileInsolaLettura = new Label("nomeFileInsolaLettura",
    //				allegatoPermessiPersonalizzatiModel.bind("nomeFile"));
    //		downloadLinkInSolaLettura.add(nomeFileInsolaLettura);

    wmkAllegatoInSolaLettura.add(downloadLinkInSolaLettura);

    // wmkAllegatoInSolaLettura.addOrReplace(nomeFileInsolaLettura);

    Label dimensioneFileInsolaLettura =
        new Label(
            "dimensioneFileInsolaLettura",
            Model.of(
                getFileSize(allegatoPermessiPersonalizzatiModel.getObject().getDimensioneFile())));
    wmkAllegatoInSolaLettura.addOrReplace(dimensioneFileInsolaLettura);

    wmkAllegatoInSolaLettura.setVisible(!enabled);

    addOrReplace(wmkAllegatoInSolaLettura);
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError createDownloadLink() {
    BottoneAJAXDownloadWithError download =
        new BottoneAJAXDownloadWithError(
            "btnDownloadInSolaLettura", AllegatoPermessiPersonalizzatiPanel.this) {

          private static final long serialVersionUID = 2717167982774170002L;

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {
            AllegatoBody response;

            if (allegatoPermessiPersonalizzatiModel.getObject().getByteFile() == null) {
              try {
                String nomeFile = allegatoPermessiPersonalizzatiModel.getObject().getNomeFile();
                nomeFile = nomeFile.substring(0, nomeFile.lastIndexOf('.'));
                response =
                    ServiceLocator.getInstance()
                        .getServiziPermessiPersonalizzati()
                        .getDomanda(idDomanda, nomeFile);
                FileDaScaricare fileDaScaricare = new FileDaScaricare();
                fileDaScaricare.setFileBytes(response.getAllegatoFile().getFile());
                fileDaScaricare.setFileName(
                    allegatoPermessiPersonalizzatiModel.getObject().getNomeFile());
                fileDaScaricare.setEsitoOK(true);
                return fileDaScaricare;
              } catch (IOException | ApiException | BusinessException e) {
                // ServiceLocator.getInstance().getCertificatiAnagrafe().inviaRichiestaPerEmail(request,
                // informazioni);
                String prefisso = "GNC-000-Server was unable to process request. --->";
                String message = e.getMessage();
                FileDaScaricare fileDaScaricare = new FileDaScaricare();
                if (message.contains(prefisso)) {
                  log.error("ERRORE API: " + e);
                  fileDaScaricare.setMessaggioErrore(message.replace(prefisso, ""));
                } else {
                  fileDaScaricare.setMessaggioErrore(message);
                }
                fileDaScaricare.setEsitoOK(false);
                return fileDaScaricare;
              }
            } else {
              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              fileDaScaricare.setFileBytes(
                  allegatoPermessiPersonalizzatiModel.getObject().getByteFile());
              fileDaScaricare.setFileName(
                  allegatoPermessiPersonalizzatiModel.getObject().getNomeFile());
              fileDaScaricare.setEsitoOK(true);
              return fileDaScaricare;
            }
          }
        };
    return download;
  }

  /*
  	private AjaxLink<Page> createDownloadLink() {
  		final AJAXDownload download = new AJAXDownload() {

  			private static final long serialVersionUID = 8189916125148718792L;

  			@Override
  			protected IResourceStream getResourceStream() {

  				ConfigurazioneInterface stampa = null;

  				if (allegatoPermessiPersonalizzatiModel.getObject().getByteFile() == null) {
  					try {

  //					AllegatoBody allegatoBody = ServiceLocator.getInstance().getServiziPermessiPersonalizzati()
  //							.getDomanda(41, "CID_41");

  						String nomeFile = allegatoPermessiPersonalizzatiModel.getObject().getNomeFile();
  						nomeFile = nomeFile.substring(0, nomeFile.lastIndexOf('.'));
  						AllegatoBody allegatoBody = ServiceLocator.getInstance().getServiziPermessiPersonalizzati()
  								.getDomanda(idDomanda, nomeFile);
  						allegatoPermessiPersonalizzatiModel.getObject()
  								.setByteFile(allegatoBody.getAllegatoFile().getFile());

  					} catch (BusinessException | ApiException | IOException e) {
  						log.debug("Errore durante la chiamata delle API", e);
  						throw new RestartResponseAtInterceptPageException(
  								new ErroreServiziPage("elenco permessi personalizzati"));
  					}
  				}
  				return PageUtil.createResourceStream(allegatoPermessiPersonalizzatiModel.getObject().getByteFile());
  			}

  			@Override
  			protected String getFileName() {
  				return getString("AllegatoPermessiPersonalizzatiPanel.nomeFile");
  			}
  		};
  		add(download);

  		final AjaxLink<Page> linkDownload = new AjaxLink<Page>("btnDownloadInSolaLettura") {

  			private static final long serialVersionUID = 562860070843086644L;

  			@Override
  			protected void onComponentTag(final ComponentTag tag) {
  				super.onComponentTag(tag);
  				tag.put("target", "_blank");
  			}

  			@Override
  			public void onClick(final AjaxRequestTarget target) {
  				download.initiate(target);
  			}
  		};

  		return linkDownload;
  	}
  	*/

  private String getFileSize(long size) {
    long n = 1000;
    String s = "";
    double kb = size / n;
    double mb = kb / n;
    double gb = mb / n;
    double tb = gb / n;
    if (size < n) {
      s = size + " Bytes";
    } else if (size >= n && size < (n * n)) {
      s = String.format("%.2f", kb) + " KB";
    } else if (size >= (n * n) && size < (n * n * n)) {
      s = String.format("%.2f", mb) + " MB";
    } else if (size >= (n * n * n) && size < (n * n * n * n)) {
      s = String.format("%.2f", gb) + " GB";
    } else if (size >= (n * n * n * n)) {
      s = String.format("%.2f", tb) + " TB";
    }
    return s;
  }

  protected FeedbackPanel createFeedBackPPPanel() {
    NotificationPanel feedback =
        new NotificationPanel("feedbackDocumentiAllegati") {

          private static final long serialVersionUID = -4144216848667276421L;

          @Override
          protected boolean isCloseButtonVisible() {
            return false;
          }
        };
    feedback.setOutputMarkupId(true);
    this.addOrReplace(feedback);
    return feedback;
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();
    fillDati(allegatoPermessiPersonalizzatiModel);
  }
}
