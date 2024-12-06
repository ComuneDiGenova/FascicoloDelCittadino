package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.bravutils;

import it.liguriadigitale.ponmetro.genovaparcheggi.model.Permit;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitAllowedAction;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitAllowedAction.UrlTypeEnum;
import it.liguriadigitale.ponmetro.portale.business.geoworks.impl.util.GeoworksUtils;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.genovaparcheggi.buttonLaddaGeParc.AnnullaPermessoGeParkPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.genovaparcheggi.buttonLaddaGeParc.DueButtoniPagoPAGeparkPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.genovaparcheggi.buttonLaddaGeParc.RinnovaPermessoGeParkPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.genovaparcheggi.buttonLaddaGeParc.ScaricaQuietanzePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.genovaparcheggi.panel.extlink.FdCExternalLinkGePark;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.EmptyPanel;

public class BravAzioniUtils {

  private static Log log = LogFactory.getLog(GeoworksUtils.class);

  public static WebMarkupContainer creaBottoneDinamico(
      final Permit permesso, final PermitAllowedAction azione) {

    if (LabelFdCUtil.checkIfNull(azione.getUrlType())) {
      log.debug("azione.getUrlType()=" + azione.getUrlType());
      return new EmptyPanel("btnAzione");
    }

    UrlTypeEnum enumAzione = UrlTypeEnum.fromValue(azione.getUrlType());

    log.debug("creaBottoneDinamico enumAzione = " + enumAzione);

    switch (enumAzione) {
      case API:
        log.debug("API");
        return creaBottoneDinamicoPerAPI(permesso, azione);
      case BO:
        log.debug("BO");
        return creaBottoneDinamicoPerBO();
      case FO:
        log.debug("FO");
        return creaBottoneDinamicoLinkEsterno(permesso, azione);
      default:
        log.debug("default");
        return creaPannelloVuoto();
    }
  }

  public static WebMarkupContainer creaBottoneDinamicoPerAPI(
      Permit permesso, PermitAllowedAction azione) {

    String url = azione.getUrl();
    log.debug("creaBottoneDinamicoPerAPI url = " + url);

    if (StringUtils.containsIgnoreCase(url, "PermitPurchaseDocumentsPdf")) {
      log.debug("PermitPurchaseDocumentsPdf: " + url);
      return scaricaQuietanze(permesso, azione);
    } else if (StringUtils.containsIgnoreCase(url, "CancelRenewal")) {
      log.debug("CancelRenewal: " + url);
      return creaBottoneAnnullaPermesso(permesso, azione);
    } else if (StringUtils.containsIgnoreCase(url, "Model3Notice")) {
      log.debug("Model3Notice");
      return dueButtoniPagoPA(permesso, azione);
    } else if (StringUtils.containsIgnoreCase(url, "errore")) {
      log.debug("Model3Notice_test_buttone_paga");
      return new EmptyPanel("btnAzione");
    } else if (StringUtils.containsIgnoreCase(url, "Renewal")) {
      log.debug("CancelRenewal");
      return creaBottoneRinnovaPermesso(permesso, azione);
    } else {
      log.debug("path non trovato: " + url);
      return new EmptyPanel("btnAzione");
    }
  }

  private static WebMarkupContainer scaricaQuietanze(Permit permesso, PermitAllowedAction azione) {
    return new ScaricaQuietanzePanel("btnAzione", permesso, azione);
  }

  private static WebMarkupContainer creaBottoneAnnullaPermesso(
      Permit permesso, PermitAllowedAction azione) {
    return new AnnullaPermessoGeParkPanel("btnAzione", permesso, azione);
  }

  private static WebMarkupContainer dueButtoniPagoPA(Permit permesso, PermitAllowedAction azione) {
    return new DueButtoniPagoPAGeparkPanel("btnAzione", permesso, azione);
  }

  private static WebMarkupContainer creaBottoneRinnovaPermesso(
      Permit permesso, PermitAllowedAction azione) {
    return new RinnovaPermessoGeParkPanel("btnAzione", permesso, azione);
  }

  private static WebMarkupContainer creaBottoneDinamicoPerBO() {
    return new EmptyPanel("btnAzione");
  }

  private static WebMarkupContainer creaBottoneDinamicoLinkEsterno(
      Permit permesso, PermitAllowedAction azione) {
    log.debug("creaBottoneDinamicoLinkEsterno url_esterno " + azione.getUrl());
    FdCExternalLinkGePark btnAzione = new FdCExternalLinkGePark("btnAzione", permesso, azione);
    return btnAzione;
  }

  private static WebMarkupContainer creaPannelloVuoto() {
    return new EmptyPanel("btnAzione");
  }
}
