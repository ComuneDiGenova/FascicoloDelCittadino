package it.liguriadigitale.ponmetro.portale.presentation.pages.messaggi;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.messaggi.utente.model.DatiMessaggioUtente;
import it.liguriadigitale.ponmetro.messaggi.utente.model.EnumAzione;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.messaggi.impl.ServiziMessaggiImpl;
import it.liguriadigitale.ponmetro.portale.business.messaggi.service.ServiziMessaggiService;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.messaggi.panel.MessaggiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.messaggi.panel.SingoloMessaggioPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.messaggi.panel.breadcrumb.BreadcrumbMessaggiPanel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessaggiPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 8428611431468821575L;

  private Log log = LogFactory.getLog(getClass());

  public MessaggiPage() {
    this(LocalDate.now());
  }

  public MessaggiPage(LocalDate day) {
    boolean valorizzaDataVisualizzazioneDopoGet = true;
    // List<DatiMessaggioUtente> lista =
    // getListaMessaggi(valorizzaDataVisualizzazioneDopoGet);
    List<DatiMessaggioUtente> lista =
        getListaMessaggiInOrdineCronologico(valorizzaDataVisualizzazioneDopoGet);
    creaPanelMessaggi(day, lista);
    creaPanelSingoloMessaggio();
    BreadcrumbMessaggiPanel breadcrumbMessaggiPanel =
        new BreadcrumbMessaggiPanel("breadcrumbPanel");
    add(breadcrumbMessaggiPanel);
  }

  private void creaPanelMessaggi(LocalDate day, List<DatiMessaggioUtente> lista) {
    MessaggiPanel messaggiPanel = new MessaggiPanel(day, lista);
    add(messaggiPanel);
  }

  private void creaPanelSingoloMessaggio() {
    SingoloMessaggioPanel singoloMessaggioPanel = new SingoloMessaggioPanel();
    singoloMessaggioPanel.setVisible(false);
    add(singoloMessaggioPanel);
  }

  private List<DatiMessaggioUtente> getListaMessaggi(boolean valorizzaDataVisualizzazioneDopoGet) {
    List<DatiMessaggioUtente> listaMessaggi = new ArrayList<DatiMessaggioUtente>();

    ServiziMessaggiService messaggiService = new ServiziMessaggiImpl();
    try {
      listaMessaggi = messaggiService.getListaTuttiMessaggi(getUtente());
      if (valorizzaDataVisualizzazioneDopoGet) {
        messaggiService.setAzioneMessaggi(getUtente(), listaMessaggi, EnumAzione.SEGNA_COME_LETTO);
      }
    } catch (BusinessException e) {
      log.error(" -- errore getListaMessaggi:", e);
    } catch (ApiException e) {
      log.error(" -- errore getListaMessaggi:", e);
    }
    return listaMessaggi;
  }

  private List<DatiMessaggioUtente> getListaMessaggiInOrdineCronologico(
      boolean valorizzaDataVisualizzazioneDopoGet) {
    List<DatiMessaggioUtente> listaMessaggi = getListaMessaggi(valorizzaDataVisualizzazioneDopoGet);
    List<DatiMessaggioUtente> listaMessaggiOrdineCronologico =
        listaMessaggi.stream()
            .sorted(Comparator.comparing(DatiMessaggioUtente::getNotificaData).reversed())
            .collect(Collectors.toList());
    return listaMessaggiOrdineCronologico;
  }
}
