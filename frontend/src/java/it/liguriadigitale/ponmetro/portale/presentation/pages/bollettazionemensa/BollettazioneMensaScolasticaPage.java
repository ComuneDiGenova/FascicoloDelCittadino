package it.liguriadigitale.ponmetro.portale.presentation.pages.bollettazionemensa;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.bollettazionemensa.panel.BollettazioneMensaScolasticaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiInfoBollettazione;
import java.io.IOException;

public class BollettazioneMensaScolasticaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 2336460917446136391L;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public BollettazioneMensaScolasticaPage() {

    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    DatiInfoBollettazione datiBollettazione = getDatiInfoBollettazione();

    boolean isRichiestaVisibile = LabelFdCUtil.checkIfNotNull(datiBollettazione);

    LinkDinamicoLaddaFunzione<Object> btnAggiornamentoBollettazione =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnAggiornamentoBollettazione",
            new LinkDinamicoFunzioneData(
                "BollettazioneMensaScolasticaPage.btnAggiornamentoBollettazione",
                "RichiestaBollettazioneMensaScolasticaPage",
                "BollettazioneMensaScolasticaPage.btnAggiornamentoBollettazione"),
            null,
            BollettazioneMensaScolasticaPage.this,
            "color-orange col-auto icon-studenti",
            isRichiestaVisibile);
    addOrReplace(btnAggiornamentoBollettazione);

    BollettazioneMensaScolasticaPanel bollettazionePanel =
        new BollettazioneMensaScolasticaPanel("bollettazionePanel", datiBollettazione);
    addOrReplace(bollettazionePanel);
  }

  private DatiInfoBollettazione getDatiInfoBollettazione() {
    DatiInfoBollettazione datiBollettazione = null;
    try {
      datiBollettazione =
          ServiceLocator.getInstance()
              .getServiziRistorazione()
              .getDatiInfoBollettazione(getUtente().getCodiceFiscaleOperatore());
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore getDatiInfoBollettazione = " + e.getMessage());
    }
    return datiBollettazione;
  }
}
