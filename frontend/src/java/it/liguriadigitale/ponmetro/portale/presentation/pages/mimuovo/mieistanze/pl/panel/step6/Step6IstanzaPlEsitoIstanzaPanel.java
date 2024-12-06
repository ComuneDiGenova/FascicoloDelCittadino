package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.panel.step6;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.panel.StepBaseIstanzaPlPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.EsitoOperazione;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.FileAllegato;
import java.io.IOException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.StringResourceModel;

public class Step6IstanzaPlEsitoIstanzaPanel extends StepBaseIstanzaPlPanel {

  private static final long serialVersionUID = 2781955826041290508L;

  private Integer index;

  private DatiRichiestaIstanzaPl datiRichiestaIstanzaPl;

  public Step6IstanzaPlEsitoIstanzaPanel(
      String id, Integer index, DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {

    super(id, index, datiRichiestaIstanzaPl);

    this.datiRichiestaIstanzaPl = datiRichiestaIstanzaPl;

    fillDati(null);
  }

  @Override
  public void fillDati(Object dati) {

    log.debug(
        "Step6IstanzaPlEsitoIstanzaPanelStep6IstanzaPlEsitoIstanzaPanel: datiRichiestaIstanzaPl "
            + datiRichiestaIstanzaPl);

    EsitoOperazione esitoOperazione = datiRichiestaIstanzaPl.getEsitoInvioIstanza();
    log.debug("CP esito operazione in step 6 = " + esitoOperazione);

    String istanza = "";
    if (LabelFdCUtil.checkIfNotNull(datiRichiestaIstanzaPl.getIstanza())
        && LabelFdCUtil.checkIfNotNull(datiRichiestaIstanzaPl.getIstanza().getId())) {
      istanza = String.valueOf(datiRichiestaIstanzaPl.getIstanza().getId());
    }

    Label esitoOk =
        new Label(
            "esitoOk",
            new StringResourceModel("Step6IstanzaPlEsitoIstanzaPanel.esitoOk", this)
                .setParameters(istanza));
    addOrReplace(esitoOk);

    @SuppressWarnings("rawtypes")
    BottoneAJAXDownloadWithError btnScaricaRicevuta = creaDownloadRicevuta(esitoOperazione);
    addOrReplace(btnScaricaRicevuta);
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaDownloadRicevuta(EsitoOperazione esitoOperazione) {
    BottoneAJAXDownloadWithError btnScaricaRicevuta =
        new BottoneAJAXDownloadWithError(
            "btnScaricaRicevuta", Step6IstanzaPlEsitoIstanzaPanel.this) {

          private static final long serialVersionUID = -8998456801171600346L;

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {
            FileAllegato response;
            try {

              String numeroProtocollo = "";
              if (LabelFdCUtil.checkIfNotNull(
                  datiRichiestaIstanzaPl.getDettaglioVerbaleDiPartenza())) {
                numeroProtocollo =
                    datiRichiestaIstanzaPl.getDettaglioVerbaleDiPartenza().getNumeroProtocollo();
              }

              String identificativoEsito = esitoOperazione.getIdentificativo();

              response =
                  ServiceLocator.getInstance()
                      .getServiziMieiVerbali()
                      .getFileAllegatoAlVerbaleByAllegatoId(
                          getUtente(), numeroProtocollo, identificativoEsito);

              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              fileDaScaricare.setFileBytes(response.getFile());
              fileDaScaricare.setFileName(response.getNomeFile());
              fileDaScaricare.setEsitoOK(true);
              return fileDaScaricare;
            } catch (ApiException | BusinessException | IOException e) {

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
          }
        };

    btnScaricaRicevuta.setVisibileDopoDownload(true);

    return btnScaricaRicevuta;
  }
}
