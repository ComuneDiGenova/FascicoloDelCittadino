package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.proprietaEdUtenze.dettaglio;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.proprieta.DettaglioProprietaUtenzaExt;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.proprieta.ProprietaUtenzeExt;
import it.liguriadigitale.ponmetro.portale.presentation.application.HomeWebApplication;
import it.liguriadigitale.ponmetro.portale.presentation.common.tributi.TributiErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.proprietaEdUtenze.dettaglio.panel.DatiCatastaliPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.proprietaEdUtenze.dettaglio.panel.DatiComproprietariPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.proprietaEdUtenze.dettaglio.panel.DatiGenericiProprietaPanel;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.behavior.AttributeAppender;

public class DettaglioProprietaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 1L;
  private Utente utente;

  public DettaglioProprietaPage(
      Utente utente,
      ProprietaUtenzeExt proprieta,
      AttributeAppender atIcon,
      Integer annoSelezionato) {
    super();
    this.utente = utente;
    initPage(
        HomeWebApplication.ID_DETTAGLIO_OFPROPRIETAEUTENZE_OFTRIBUTI_OFHOME,
        proprieta,
        atIcon,
        annoSelezionato);
  }

  private void initPage(
      final String idPage,
      final ProprietaUtenzeExt proprieta,
      AttributeAppender atIcon,
      Integer annoSelezionato) {
    DettaglioProprietaUtenzaExt dettaglioProprietaUtenzaExt = getProprietaUtenteExt(proprieta);

    // 1 - breadcrumb > path per navigare nel sito
    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    // Panel generico largo 6
    DatiGenericiProprietaPanel datiGenericiProprietaPanel =
        new DatiGenericiProprietaPanel("datiGenericiPanel", atIcon);
    datiGenericiProprietaPanel.fillDati(dettaglioProprietaUtenzaExt);
    add(datiGenericiProprietaPanel);

    // Panel dati catastali largo 6
    DatiCatastaliPanel datiCatastaliPanel = new DatiCatastaliPanel("datiCatastaliPanel");
    datiCatastaliPanel.fillDati(dettaglioProprietaUtenzaExt);
    add(datiCatastaliPanel);
    // Panel tutti i comproprietari largo 12 con singole card per ogni
    // comproprietario
    DatiComproprietariPanel datiComproprietariPanel =
        new DatiComproprietariPanel("datiComproprietariPanel");
    datiComproprietariPanel.fillDati(
        dettaglioProprietaUtenzaExt.getComproprietariExtFiltered(
            utente.getCodiceFiscaleOperatore(), annoSelezionato));
    add(datiComproprietariPanel);
  }

  private DettaglioProprietaUtenzaExt getProprietaUtenteExt(final ProprietaUtenzeExt proprieta) {
    try {
      return ServiceLocator.getInstance()
          .getServiziQuadroTributario()
          .getDettaglioProprietaUtenzaExt(proprieta);
    } catch (ApiException e) {
      throw new RestartResponseAtInterceptPageException(TributiErrorPage.class);
    } catch (BusinessException e) {
      return null;
    }
  }
}
