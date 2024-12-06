package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.chiusuraiscrizionerefezione;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.chiusuraiscrizionerefezione.Chiusuraiscrizionerefezione;
import it.liguriadigitale.ponmetro.portale.presentation.common.autorizzazione.UtenteNonAutorizzatoPage;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.chiusuraiscrizionerefezione.panel.RichiestaChiusuraIscrizioneRefezionePanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiStatoDomandaChiusuraServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class RichiestaChiusuraIscrizioneRefezionePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -1607952561006213777L;

  public RichiestaChiusuraIscrizioneRefezionePage() {
    this(null);
  }

  public RichiestaChiusuraIscrizioneRefezionePage(UtenteServiziRistorazione iscritto) {
    super();

    if (iscritto == null) {
      if (getSession().getAttribute("iscrittoServiziRistorazione") != null) {
        iscritto =
            (UtenteServiziRistorazione) getSession().getAttribute("iscrittoServiziRistorazione");
      }
    } else {
      getSession().setAttribute("iscrittoServiziRistorazione", iscritto);
    }

    initPage(iscritto, new Chiusuraiscrizionerefezione());

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);
  }

  public RichiestaChiusuraIscrizioneRefezionePage(
      UtenteServiziRistorazione iscritto, Chiusuraiscrizionerefezione chiusuraiscrizionerefezione) {
    super();

    if (iscritto == null) {
      if (getSession().getAttribute("iscrittoServiziRistorazione") != null) {
        iscritto =
            (UtenteServiziRistorazione) getSession().getAttribute("iscrittoServiziRistorazione");
      }
    } else {
      getSession().setAttribute("iscrittoServiziRistorazione", iscritto);
    }

    initPage(iscritto, chiusuraiscrizionerefezione);
    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);
  }

  private void initPage(
      final UtenteServiziRistorazione iscritto,
      Chiusuraiscrizionerefezione chiusuraiscrizionerefezione) {

    if (getUtente() == null || utenteNonPuoEssereDisiscritto(iscritto)) {
      throw new RestartResponseAtInterceptPageException(UtenteNonAutorizzatoPage.class);
    }

    DatiStatoDomandaChiusuraServiziRistorazione datiChiusura = getDatiChiusura(iscritto);

    RichiestaChiusuraIscrizioneRefezionePanel panel =
        new RichiestaChiusuraIscrizioneRefezionePanel(
            "chiusuraiscrizionePanel", iscritto, datiChiusura, chiusuraiscrizionerefezione);
    this.add(panel);
  }

  private DatiStatoDomandaChiusuraServiziRistorazione getDatiChiusura(
      UtenteServiziRistorazione iscritto) {
    try {
      return ServiceLocator.getInstance()
          .getServiziRistorazione()
          .getDatiChiusuraIscritto(iscritto.getCodiceFiscale());
    } catch (BusinessException | ApiException e) {
      log.error("Errore getDatiChiusura:", e);
      error("Errore getDatiChiusura");
    }
    return null;
  }

  private boolean utenteNonPuoEssereDisiscritto(final UtenteServiziRistorazione iscritto) {
    return iscritto
            .getStatoIscrizioneServiziRistorazione()
            .equalsIgnoreCase(
                UtenteServiziRistorazione.StatoIscrizioneServiziRistorazioneEnum.NON_ISCRITTO
                    .value())
        || iscritto
            .getStatoIscrizioneServiziRistorazione()
            .equalsIgnoreCase(
                UtenteServiziRistorazione.StatoIscrizioneServiziRistorazioneEnum.IN_ELABORAZIONE
                    .value())
        || iscritto
            .getStatoIscrizioneServiziRistorazione()
            .equalsIgnoreCase(
                UtenteServiziRistorazione.StatoIscrizioneServiziRistorazioneEnum.INVIATA.value());
  }
}
