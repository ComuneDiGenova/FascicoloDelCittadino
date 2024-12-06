package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizione.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizione.Iscrizione;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDomandaIscrizioneServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDomandaIscrizioneServiziRistorazione.SessoIscrivendoEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;

public class ConfermaIscrizionePanel extends BasePanel {

  private static final long serialVersionUID = -4652274627139372877L;

  public ConfermaIscrizionePanel(Iscrizione iscrizione) {
    super("iscrizionePanel");
    createFeedBackPanel();
    fillDati(iscrizione);
  }

  @Override
  public void fillDati(Object dati) {
    Iscrizione iscrizione = (Iscrizione) dati;
    UtenteServiziRistorazione iscritto = iscrizione.getUtente();

    try {
      DatiDomandaIscrizioneServiziRistorazione domanda =
          new DatiDomandaIscrizioneServiziRistorazione();
      domanda.setCodiceFiscaleImpegnato(getUtente().getCodiceFiscaleOperatore());
      domanda.setCodiceFiscaleIscrivendo(iscritto.getCodiceFiscale());
      domanda.setCognomeIscrivendo(iscritto.getCognome());
      domanda.setDataNascitaIscrivendo(iscritto.getDataNascita());

      // TODO da togliere 1.3.4
      /*
       * domanda.setDietaSpecialeIscrivendo(
       * iscrizione.isDietaEpisodiAllergici() ||
       * iscrizione.isDietaMotiviReligiosi() ||
       * iscrizione.isDietaMotiviSanitari());
       * domanda.setDietaSpecialeIscrivendoTrueEtico(iscrizione.
       * isDietaMotiviReligiosi());
       * domanda.setDietaSpecialeIscrivendoTrueSalute(iscrizione.
       * isDietaMotiviSanitari());
       * domanda.setDietaSpecialeIscrivendoTrueSaluteAllergie(iscrizione.
       * isDietaEpisodiAllergici());
       */

      domanda.setEmailIscrivendo(iscrizione.getEmailContatto());
      domanda.setIndirizzoDomicilioIscrivendo(iscrizione.getIndirizzo());
      domanda.setComuneDomicilioIscrivendo(iscrizione.getComune());
      domanda.setCapDomicilioIscrivendo(iscrizione.getCap());
      domanda.setNomeIscrivendo(iscritto.getNome());
      domanda.setNumTelefonoIscrivendo(getUtente().getMobile());
      // ddisr.setImpegnatoContoAltroAdulto(impegnatoContoAltroAdulto);
      domanda.setRicevimentoNotifichePresenzaMensa(true);
      domanda.setSessoIscrivendo(
          iscritto.getSesso().startsWith("M") ? SessoIscrivendoEnum.M : SessoIscrivendoEnum.F);
      ServiceLocator.getInstance()
          .getServiziRistorazione()
          .presentaDomandaIscrizione(domanda, getUtente());
      aggiornaUtente(iscritto.getCodiceFiscale());
      log.debug("ConfermaIscrizionePanel -- fillDati: iscrizione effettuata correttamente");
      success("Richiesta di iscrizione inviata correttamente.");
    } catch (BusinessException | ApiException e) {
      log.debug("ConfermaIscrizionePanel fillDati ERRORE: ", e);
      // FASCITT-135, non mostare "e" bensi' mostare messaggio qui sotto
      this.error("Attenzione, errore nella richiesta. Si prega di riprovare piu' tardi. ");
    }
  }

  private void aggiornaUtente(String cf) throws BusinessException, ApiException {

    Utente.inizializzaListaFigli(getUtente());

    // for (UtenteServiziRistorazione iscritto :
    // getUtente().getListaFigli()) {
    // if (iscritto.getCodiceFiscale().equalsIgnoreCase(cf)) {
    // log.debug("iscritto:" +
    // iscritto.getStatoIscrizioneServiziRistorazione());
    // }
    // }
  }
}
