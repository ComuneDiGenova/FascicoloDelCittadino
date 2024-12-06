package it.liguriadigitale.ponmetro.portale.presentation.pages.teleriscaldamento.panel.domanda;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.configurazione.service.ConfigurazioneInterface;
import it.liguriadigitale.ponmetro.portale.pojo.teleriscaldamento.DatiDomandaTeleriscaldamento;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.SiNoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class ConsensiPanel extends BasePanel {

  private static final long serialVersionUID = 8915550575710320751L;

  private int index;

  public ConsensiPanel(String id, DatiDomandaTeleriscaldamento datiDomanda, int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = index;

    fillDati(datiDomanda);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void fillDati(Object dati) {
    DatiDomandaTeleriscaldamento datiDomanda = (DatiDomandaTeleriscaldamento) dati;

    log.debug("CP dati utenza");

    addOrReplace(new FdCTitoloPanel("titolo", getString("ConsensiPanel.titolo")));

    addOrReplace(new FdCTitoloPanel("ioSottoscritto", getString("ConsensiPanel.ioSottoscritto")));

    SiNoDropDownChoice art43 =
        new SiNoDropDownChoice("art43", new PropertyModel<>(datiDomanda, "art43"));
    art43.setLabel(Model.of("Dichiarazione ai sensi dell'art 43"));
    art43.setRequired(true);
    addOrReplace(art43);

    SiNoDropDownChoice privacy =
        new SiNoDropDownChoice("privacy", new PropertyModel<>(datiDomanda, "privacy"));
    privacy.setLabel(Model.of("Dichiarazione privacy"));
    privacy.setRequired(true);
    addOrReplace(privacy);

    @SuppressWarnings("rawtypes")
    BottoneAJAXDownloadWithError btnScaricaInformativa = creaDownloadInformativa();
    addOrReplace(btnScaricaInformativa);
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaDownloadInformativa() {
    BottoneAJAXDownloadWithError btnScaricaInformativa =
        new BottoneAJAXDownloadWithError("btnScaricaInformativa", ConsensiPanel.this) {

          private static final long serialVersionUID = -9027367179380860864L;

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {
            try {

              String nomeFile = "";
              byte[] file = null;

              nomeFile = "Informativa.pdf";

              ConfigurazioneInterface stampa =
                  ServiceLocator.getInstance().getServiziConfigurazione();
              file = stampa.getInformativaServiziTeleriscaldamento(getUtente());

              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              fileDaScaricare.setFileBytes(file);
              fileDaScaricare.setFileName(nomeFile);
              fileDaScaricare.setEsitoOK(true);
              return fileDaScaricare;
            } catch (BusinessException e) {

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

    btnScaricaInformativa.setVisibileDopoDownload(true);

    return btnScaricaInformativa;
  }
}
