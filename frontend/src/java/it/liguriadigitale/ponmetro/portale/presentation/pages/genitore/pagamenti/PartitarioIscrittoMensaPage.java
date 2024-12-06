package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.pagamenti;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.pagamentiristorazione.PartitarioMensa;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.pagamenti.panel.PartitarioIscrittoMensaRisultatiRicercaPanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiPagamentiPartitario;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiStatoPagamenti;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PartitarioIscrittoMensaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 2207028052103630094L;

  private Log log = LogFactory.getLog(getClass());

  private PartitarioIscrittoMensaRisultatiRicercaPanel panelRisultati;

  public PartitarioIscrittoMensaPage(final UtenteServiziRistorazione iscritto) {
    super();

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    initPage(iscritto);
  }

  private void initPage(final UtenteServiziRistorazione iscritto) {

    PartitarioMensa partitarioCompleto = getPartitarioIscritto(iscritto);

    panelRisultati =
        new PartitarioIscrittoMensaRisultatiRicercaPanel(
            "partitarioRisultatiRicercaAnno", partitarioCompleto);

    this.add(panelRisultati);
  }

  private PartitarioMensa getPartitarioIscritto(final UtenteServiziRistorazione iscritto) {
    PartitarioMensa toRet = new PartitarioMensa(iscritto);
    try {
      List<DatiPagamentiPartitario> listDatiPagamentiPartitario =
          ServiceLocator.getInstance()
              .getServiziRistorazione()
              .getPartitarioIscritto(iscritto.getCodiceFiscale());
      toRet.addAllToListDatiPagamentiPartitario(listDatiPagamentiPartitario);

      List<DatiStatoPagamenti> listaDatiStatoPagamentiPartitario =
          ServiceLocator.getInstance()
              .getServiziRistorazione()
              .getListaSituazioneAnniPartitario(iscritto.getCodiceFiscale());
      toRet.addAllToListaDatiStatoPagamentiPartitario(listaDatiStatoPagamentiPartitario);

    } catch (BusinessException | ApiException e) {
      log.error("Errore getPartitarioIscritto", e);
      error("Errore getPartitarioIscritto");
    }

    log.debug("CP getPartitarioIscritto = " + toRet);

    return toRet;
  }

  private boolean utenteCanReadPartitarioIScritto(final UtenteServiziRistorazione iscritto) {
    Utente utente = getUtente();
    if (utente == null) {
      return false;
    }
    List<UtenteServiziRistorazione> listaIscrittiUtente = utente.getListaFigli();
    if (listaIscrittiUtente == null || listaIscrittiUtente.isEmpty()) {
      return false;
    }
    return listaIscrittiUtente.contains(iscritto);
  }

  public PartitarioIscrittoMensaRisultatiRicercaPanel getPanelRisultati() {
    return panelRisultati;
  }

  public void setPanelRisultati(PartitarioIscrittoMensaRisultatiRicercaPanel panelRisultati) {
    this.panelRisultati = panelRisultati;
  }
}
