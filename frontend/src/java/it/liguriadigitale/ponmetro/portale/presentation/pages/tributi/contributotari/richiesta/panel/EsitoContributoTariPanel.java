package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.contributotari.richiesta.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiFileAllegatoNetribe;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiRichiestaContributoTariNetribeResult;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.tarinetribe.model.FileAllegato;
import java.io.IOException;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.StringResourceModel;

public class EsitoContributoTariPanel extends BasePanel {

  private static final long serialVersionUID = -5862142505199939107L;

  private int index;

  public EsitoContributoTariPanel(String id, int index) {
    super(id);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.setIndex(index);
  }

  @Override
  public void fillDati(Object dati) {
    DatiRichiestaContributoTariNetribeResult esito =
        (DatiRichiestaContributoTariNetribeResult) dati;

    WebMarkupContainer containerMessaggioOK = new WebMarkupContainer("containerMessaggioOK");
    containerMessaggioOK.setOutputMarkupId(true);
    containerMessaggioOK.setOutputMarkupPlaceholderTag(true);
    containerMessaggioOK.setVisible(esito.isEsito());

    Label messaggioOK =
        new Label(
            "messaggioOK",
            new StringResourceModel("EsitoContributoTariPanel.messaggioOK", this)
                .setParameters(
                    String.valueOf(esito.getIdRichiesta()),
                    esito.getNumeroProtocollo(),
                    esito.getAnnoProtocollo()));

    messaggioOK.setOutputMarkupId(true);
    messaggioOK.setOutputMarkupPlaceholderTag(true);
    containerMessaggioOK.addOrReplace(messaggioOK);

    @SuppressWarnings("rawtypes")
    BottoneAJAXDownloadWithError btnDocumento = creaDownloadDocumento(esito.getIdFileProtocollo());
    containerMessaggioOK.addOrReplace(btnDocumento);

    addOrReplace(containerMessaggioOK);

    WebMarkupContainer containerMessaggioKO = new WebMarkupContainer("containerMessaggioKO");
    containerMessaggioKO.setOutputMarkupId(true);
    containerMessaggioKO.setOutputMarkupPlaceholderTag(true);
    containerMessaggioKO.setVisible(!esito.isEsito());

    String messaggioErrore = "";
    if (!esito.isEsito()) {
      messaggioErrore = esito.getMessaggioErrore();
    }

    Label messaggioKO =
        new Label(
            "messaggioKO",
            new StringResourceModel("EsitoContributoTariPanel.messaggioKO", this)
                .setParameters(messaggioErrore));

    messaggioKO.setOutputMarkupId(true);
    messaggioKO.setOutputMarkupPlaceholderTag(true);
    containerMessaggioKO.addOrReplace(messaggioKO);
    addOrReplace(containerMessaggioKO);
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaDownloadDocumento(String identificativoDocumento) {
    BottoneAJAXDownloadWithError btnDocumento =
        new BottoneAJAXDownloadWithError("btnDocumento", EsitoContributoTariPanel.this) {

          private static final long serialVersionUID = 3865398269104537881L;

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {

            try {

              DatiFileAllegatoNetribe datiFileAllegato =
                  ServiceLocator.getInstance()
                      .getServiziTariNetribe()
                      .datiFileAllegatoNetribe(identificativoDocumento);

              FileDaScaricare fileDaScaricare = new FileDaScaricare();

              if (datiFileAllegato.isScaricato()) {
                FileAllegato documento = datiFileAllegato.getFileAllegatoNetribe();

                fileDaScaricare.setFileBytes(documento.getFile());
                fileDaScaricare.setFileName(documento.getNomeFile());
                fileDaScaricare.setEsitoOK(datiFileAllegato.isScaricato());
              } else {
                fileDaScaricare.setMessaggioErrore(datiFileAllegato.getMessaggioErrore());
                fileDaScaricare.setEsitoOK(datiFileAllegato.isScaricato());
              }

              return fileDaScaricare;

            } catch (ApiException | BusinessException | IOException e) {
              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              fileDaScaricare.setMessaggioErrore("Errore durante download file");
              fileDaScaricare.setEsitoOK(false);
              return fileDaScaricare;
            }
          }
        };

    btnDocumento.setVisibileDopoDownload(true);

    return btnDocumento;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
