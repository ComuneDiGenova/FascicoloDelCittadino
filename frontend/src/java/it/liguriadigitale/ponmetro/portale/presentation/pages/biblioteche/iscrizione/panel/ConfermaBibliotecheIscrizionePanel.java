package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.biblioteche.BibliotecheIscrizione;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import java.io.IOException;

public class ConfermaBibliotecheIscrizionePanel extends BasePanel {

  private static final long serialVersionUID = -1739476939275159213L;

  public ConfermaBibliotecheIscrizionePanel(BibliotecheIscrizione bibliotecheIscrizione) {
    super("iscrizioneBibliotechePanel");

    createFeedBackPanel();
    fillDati(bibliotecheIscrizione);
  }

  @Override
  public void fillDati(Object dati) {

    BibliotecheIscrizione bibliotecheIscrizione = (BibliotecheIscrizione) dati;

    try {
      //			UtenteFull utenteFullSebina = new UtenteFull();
      //
      //			utenteFullSebina.setSn(bibliotecheIscrizione.getCognome());
      //			utenteFullSebina.setGivenName(bibliotecheIscrizione.getNome());
      //			utenteFullSebina.setPlaceOfBirth(bibliotecheIscrizione.getLuogoNascita());
      //			utenteFullSebina.setDtNasc(bibliotecheIscrizione.getDataNascita());
      //			utenteFullSebina.setCf(bibliotecheIscrizione.getCodiceFiscale());
      //
      //			GenderEnum genderEnum;
      //			if (bibliotecheIscrizione.getSesso().equalsIgnoreCase(GenderEnum.M.toString())) {
      //				genderEnum = GenderEnum.M;
      //			} else {
      //				genderEnum = GenderEnum.F;
      //			}
      //			utenteFullSebina.setGender(genderEnum);
      //
      //			IndirizzoUtenteFull indirizzoUtenteFull = new IndirizzoUtenteFull();
      //			indirizzoUtenteFull.setVia(bibliotecheIscrizione.getIndirizzoResidenza());
      //			indirizzoUtenteFull.setCap(bibliotecheIscrizione.getCapResidenza());
      //			indirizzoUtenteFull.setCitta(bibliotecheIscrizione.getCittaResidenza());
      //			indirizzoUtenteFull.setProv(bibliotecheIscrizione.getProvinciaResidenza());
      //			indirizzoUtenteFull.setPaese(bibliotecheIscrizione.getStatoResidenza());
      //
      //			utenteFullSebina.setResidenza(indirizzoUtenteFull);
      //
      //			utenteFullSebina.setIndPrinc(bibliotecheIscrizione.getIndirizzoPrincipale());
      //			utenteFullSebina.setMail(bibliotecheIscrizione.getEmail());
      //			utenteFullSebina.setMobile(bibliotecheIscrizione.getCellulare());
      //
      //			utenteFullSebina.setRecPref(bibliotecheIscrizione.getRecapitoPreferenziale());
      //
      //			utenteFullSebina.setTut(bibliotecheIscrizione.getCodiceTipoUtente());
      //			utenteFullSebina.setAutTratt(bibliotecheIscrizione.isAutorizzazioneTrattamentoDati());
      //
      //			String codiceBerio = "BE";
      //			utenteFullSebina.setBiblIscriz(codiceBerio);
      //
      //			utenteFullSebina.setLinCom(LinComEnum.IT);
      //
      //			DocIdentita utenteFullDocIdent = new DocIdentita();
      //			utenteFullDocIdent.setNum(bibliotecheIscrizione.getNumeroCI());
      //			String tipoCI = "1";
      //			utenteFullDocIdent.setTipo(tipoCI);
      //			String ente = "Comune";
      //			utenteFullDocIdent.setEnte(ente);
      //			utenteFullDocIdent.setLuogo(bibliotecheIscrizione.getLuogoCI());
      //			utenteFullDocIdent.setDtRil(bibliotecheIscrizione.getDataRilascioCI());
      //			utenteFullDocIdent.setDtScad(bibliotecheIscrizione.getDataScadenzaCI());
      //			utenteFullSebina.setDocIdent(utenteFullDocIdent);

      // log.debug("CP utenteFullSebina prima di chiamare servizio iscrizione = " +
      // utenteFullSebina.toString());

      ServiceLocator.getInstance()
          .getServiziBiblioteche()
          .iscriviBibliotecheSebina(getUtente(), bibliotecheIscrizione);

      log.debug("CP iscrizione Sebina fatta correttamente");

      aggiornaUtenteSebina();

      success("Iscrizione completata correttamente");

    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore durante iscrizione biblioteche Sebina: " + e.getMessage());
      this.error(
          "Attenzione, errore nella richiesta di iscrizione. Si prega di riprovare più tardi. Grazie.");
    }
  }

  private void aggiornaUtenteSebina() throws BusinessException, ApiException {
    Utente.inizializzaBiblioteche(getUtente());
    Utente.inizializzaScadenzeMovimentiBiblioteca(getUtente());
  }
}
