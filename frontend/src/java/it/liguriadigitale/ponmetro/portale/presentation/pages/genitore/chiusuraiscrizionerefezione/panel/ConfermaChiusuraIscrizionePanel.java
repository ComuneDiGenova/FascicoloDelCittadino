package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.chiusuraiscrizionerefezione.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.chiusuraiscrizionerefezione.Chiusuraiscrizionerefezione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDomandaChiusuraServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.time.LocalDate;

public class ConfermaChiusuraIscrizionePanel extends BasePanel {
  private static final long serialVersionUID = 9133902432796893258L;

  UtenteServiziRistorazione iscritto;
  Chiusuraiscrizionerefezione chiusuraiscrizione;

  public ConfermaChiusuraIscrizionePanel(
      String id,
      Chiusuraiscrizionerefezione chiusuraiscrizione,
      UtenteServiziRistorazione iscritto) {
    super(id);
    createFeedBackPanel();
    this.iscritto = iscritto;
    this.chiusuraiscrizione = chiusuraiscrizione;
    fillDati(chiusuraiscrizione);
  }

  @Override
  public void fillDati(Object dati) {
    Chiusuraiscrizionerefezione chiusura = (Chiusuraiscrizionerefezione) dati;

    try {
      DatiDomandaChiusuraServiziRistorazione domanda = new DatiDomandaChiusuraServiziRistorazione();
      domanda.setCodiceFiscaleIscritto(iscritto.getCodiceFiscale());
      domanda.setCodiceFiscaleRichiedente(getUtente().getCodiceFiscaleOperatore());
      domanda.setCognomeIscritto(iscritto.getCognome());
      domanda.setCognomeRichiedente(getUtente().getCognome());
      domanda.setDataInizioChiusura(LocalDate.now());
      domanda.setEmailRichiedente(chiusura.getEmailContatto());
      domanda.setNomeIscritto(iscritto.getNome());
      domanda.setNomeRichiedente(getUtente().getNome());
      domanda.setNote(chiusura.getNote());
      domanda.setNumeroTelefonoRichiedente(chiusura.getTelefonoContatto());
      ServiceLocator.getInstance()
          .getServiziRistorazione()
          .presentaDomandaChiusuraIscrizione(domanda);
      log.debug("ConfermaChiusuraIscrizionePanel: chiusura iscrizione effettuata correttamente");
      success("Richiesta di chiusura iscrizione inviata correttamente.");
    } catch (BusinessException | ApiException e) {
      log.debug("ConfermaChiusuraIscrizionePanel fillDati ERRORE: ", e);
      this.error("Attenzione, errore nella richiesta. Si prega di riprovare piu' tardi. ");
    }
  }
}
