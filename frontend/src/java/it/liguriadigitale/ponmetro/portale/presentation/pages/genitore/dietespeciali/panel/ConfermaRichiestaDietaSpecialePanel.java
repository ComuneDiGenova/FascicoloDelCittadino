package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.dietespeciali.DietaSpeciale;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;

public class ConfermaRichiestaDietaSpecialePanel extends BasePanel {

  private static final long serialVersionUID = 221719635757182078L;

  public ConfermaRichiestaDietaSpecialePanel(String id) {
    super(id);
  }

  public ConfermaRichiestaDietaSpecialePanel(DietaSpeciale dietaSpeciale) {
    super("richiestaDietaPanel");

    createFeedBackPanel();
    fillDati(dietaSpeciale);
  }

  @Override
  public void fillDati(Object dati) {
    DietaSpeciale dietaSpeciale = (DietaSpeciale) dati;

    try {

      ServiceLocator.getInstance().getServiziRistorazione().richiediDietaSpeciale(dietaSpeciale);

      success("Richiesta dieta speciale inviata correttamente.");

    } catch (ApiException e) {

      String messaggioAttenzione = "Attenzione, errore nella richiesta di dieta speciale: ";

      log.error("Errore durante richiesta dieta speciale: " + e);
      String myMessage = e.getMyMessage();
      String eccezione = "javax.ws.rs.WebApplicationException:";
      String messaggioRicevuto = myMessage;
      if (myMessage.contains(eccezione)) {
        messaggioRicevuto = myMessage.substring(eccezione.length(), myMessage.length());
      }
      log.debug(
          "Errore gestito durante la chiamata delle API Ristorazione dieta speciale: " + myMessage,
          e);

      Integer indexOf = messaggioRicevuto.indexOf(":");
      String messaggioDaVisualizzare =
          messaggioRicevuto.substring(indexOf + 1, messaggioRicevuto.length());

      messaggioDaVisualizzare = messaggioAttenzione.concat(messaggioDaVisualizzare);

      log.debug("CP messaggio dieta errore = " + messaggioDaVisualizzare);

      error(messaggioDaVisualizzare);

    } catch (BusinessException e) {
      log.error(
          "BusinessException gestito durante la chiamata delle API Ristorazione dieta speciale: ",
          e);
      error(
          "Attenzione, errore nella richiesta di dieta speciale. Si prega di riprovare più tardi. Grazie.");
    }
  }
}
