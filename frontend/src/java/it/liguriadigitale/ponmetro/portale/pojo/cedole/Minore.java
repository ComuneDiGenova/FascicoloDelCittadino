package it.liguriadigitale.ponmetro.portale.pojo.cedole;

import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;

public class Minore extends UtenteServiziRistorazione {
  private static final long serialVersionUID = 1L;

  public Minore(UtenteServiziRistorazione utente) {
    setNome(utente.getNome());
    setCognome(utente.getCognome());
    setDataNascita(utente.getDataNascita());
    setLuogoNascita(utente.getLuogoNascita());
    setSesso(SessoEnum.fromValue(utente.getSesso()));
    setCodiceFiscale(utente.getCodiceFiscale());
    setIndirizzoResidenza(utente.getIndirizzoResidenza());
    setStatoIscrizioneServiziRistorazione(
        StatoIscrizioneServiziRistorazioneEnum.fromValue(
            utente.getStatoIscrizioneServiziRistorazione()));
    setDataPresentazioneDomandaIscrizioneServiziRistorazione(
        utente.getDataPresentazioneDomandaIscrizioneServiziRistorazione());
    setDataIscrizioneServiziRistorazione(utente.getDataIscrizioneServiziRistorazione());
    setCategoriaStrutturaScolastica(utente.getCategoriaStrutturaScolastica());
    setStrutturaScolastica(utente.getCategoriaStrutturaScolastica());
    setClasse(utente.getClasse());
    setSezione(utente.getSezione());
    setNomeImpegnato(utente.getNomeImpegnato());
    setCognomeImpegnato(utente.getCognomeImpegnato());
    setCodiceFiscaleImpegnato(utente.getCodiceFiscaleImpegnato());
    setCodicePdfDomanda(utente.getCodicePdfDomanda());
    setBambinoMangia(utente.getBambinoMangia());
    setScuolaConMensa(utente.getScuolaConMensa());
    setContattoRefezioneNominativo(utente.getContattoRefezioneNominativo());
    setContattoRefezioneEmail(utente.getContattoRefezioneEmail());
    setCodiceScuola(utente.getCodiceScuola());
    setScuolaPrimaria(utente.getScuolaPrimaria());

    // return minore;
  }
}
