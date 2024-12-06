package it.liguriadigitale.ponmetro.portale.presentation.pages.domandalocazione;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.bandirealgim.model.BandoAttivoFullDOMValidationResult;
import it.liguriadigitale.ponmetro.bandirealgim.model.BooleanValidationResult;
import it.liguriadigitale.ponmetro.bandirealgim.model.V1BandoDomandeViewFullDOM;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.domandalocazione.panel.DomandeLocazionePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class DomandeLocazionePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -2128299649443398857L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public DomandeLocazionePage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    String bandoPiuRecente = getCodiceBandoPiuRecente();

    LinkDinamicoLaddaFunzione<Object> btnDomandaLocazione =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnDomandaLocazione",
            new LinkDinamicoFunzioneData(
                "DomandeLocazionePage.btnDomandaLocazione",
                "RichiestaLocazionePage",
                "DomandeLocazionePage.btnDomandaLocazione"),
            null,
            DomandeLocazionePage.this,
            "color-fc-secondary col-auto icon-contratti-immobili",
            bandoAperto(bandoPiuRecente));
    addOrReplace(btnDomandaLocazione);

    //		List<String> listaPratiche = popolaPratiche(getUtente().getCodiceFiscaleOperatore());
    //		DomandeLocazionePanel domandeLocazionePanel = new
    // DomandeLocazionePanel("domandeLocazionePanel", listaPratiche);
    //		addOrReplace(domandeLocazionePanel);

    V1BandoDomandeViewFullDOM dettaglioDomanda = getDettaglioDomanda(bandoPiuRecente);

    DomandeLocazionePanel domandeAlloggioPanel =
        new DomandeLocazionePanel("domandeLocazionePanel", dettaglioDomanda);
    addOrReplace(domandeAlloggioPanel);

    setOutputMarkupId(true);
  }

  private List<String> popolaPratiche(String codiceFiscale) {
    // TODO quando avremo servizio get
    //		List<Pratica> listaPratiche = new ArrayList<>();
    //		try {
    //			listaPratiche = ServiceLocator.getInstance().getServiziBorseDiStudio()
    //					.listaPraticheBorseDiStudio(codiceFiscale);
    //		} catch (BusinessException | ApiException | IOException e) {
    //			log.error("Errore popolaPratiche: " + e.getMessage(), e);
    //		}
    //		return listaPratiche;

    List<String> lista = new ArrayList<String>();
    lista.add("A");
    return lista;
  }

  private String getCodiceBandoPiuRecente() {
    String codiceBandoPiuRecente = "";
    try {
      BandoAttivoFullDOMValidationResult activennouncemnt =
          ServiceLocator.getInstance()
              .getServiziDomandeAlloggio()
              .getActiveAnnouncement(OffsetDateTime.now(), "02");

      if (LabelFdCUtil.checkIfNotNull(activennouncemnt)
          && LabelFdCUtil.checkIfNotNull(activennouncemnt.getElementDOM())) {
        codiceBandoPiuRecente = activennouncemnt.getElementDOM().getCodBando();
      }
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore getCodiceBandoPiuRecente = " + e.getMessage());
    }

    log.debug("CP codiceBandoPiuRecente = " + codiceBandoPiuRecente);

    return codiceBandoPiuRecente;
  }

  private V1BandoDomandeViewFullDOM getDettaglioDomanda(String codiceBandoPiuRecente) {
    try {
      return ServiceLocator.getInstance()
          .getServiziDomandeAlloggio()
          .getAnnouncementQuestionDetail(
              true, getUtente().getCodiceFiscaleOperatore(), codiceBandoPiuRecente);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore getDettaglioDomanda = " + e.getMessage());
      return null;
    }
  }

  private boolean bandoAperto(String bandoPiuRecente) {
    boolean bandoAperto = false;

    try {
      BooleanValidationResult checkAnnouncementStatus =
          ServiceLocator.getInstance()
              .getServiziDomandeAlloggio()
              .checkAnnouncementStatus(bandoPiuRecente);
      bandoAperto =
          LabelFdCUtil.checkIfNotNull(checkAnnouncementStatus)
                  && LabelFdCUtil.checkIfNotNull(checkAnnouncementStatus.getValidationSucceded())
                  && checkAnnouncementStatus.getValidationSucceded()
              ? true
              : false;
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore bandoAperto = " + e.getMessage());
    }

    log.debug("CP bandoAperto = " + bandoAperto);

    return bandoAperto;
  }
}
