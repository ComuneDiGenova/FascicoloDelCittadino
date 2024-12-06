package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.verbalirate.panel;

import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.EsitoOperazione;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.StringResourceModel;

public class EsitoRichiestaRateizzazionePanel extends BasePanel {

  /** */
  private static final long serialVersionUID = 1L;

  DatiRichiestaIstanzaPl _datiRichiestaIstanzaPl;

  public EsitoRichiestaRateizzazionePanel(
      String id, DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
    super(id);
    // TODO Auto-generated constructor stub

    _datiRichiestaIstanzaPl = datiRichiestaIstanzaPl;
  }

  @Override
  public void fillDati(Object dati) {
    // TODO Auto-generated method stub
    EsitoOperazione esitoOperatione = (EsitoOperazione) dati;

    log.debug("CP esito operazione  = " + esitoOperatione);

    String istanza = "";
    if (LabelFdCUtil.checkIfNotNull(_datiRichiestaIstanzaPl.getIstanza())
        && LabelFdCUtil.checkIfNotNull(_datiRichiestaIstanzaPl.getIstanza().getId())) {
      istanza = String.valueOf(_datiRichiestaIstanzaPl.getIstanza().getId());
    }

    Label esitoOk =
        new Label(
            "esitoOk",
            new StringResourceModel("EsitoRichiestaRateizzazionePanel.esitoOk", this)
                .setParameters(istanza));
    addOrReplace(esitoOk);

    @SuppressWarnings("rawtypes")
    BottoneAJAXDownloadWithError btnScaricaRicevuta = creaDownloadRicevuta(esitoOperatione);
    addOrReplace(btnScaricaRicevuta);
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaDownloadRicevuta(EsitoOperazione esitoOperatione) {
    // TODO Auto-generated method stub
    return null;
  }
}
