package it.liguriadigitale.ponmetro.portale.business.messaggi.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.messaggi.utente.model.DatiMessaggioUtente;
import it.liguriadigitale.ponmetro.messaggi.utente.model.EnumAzione;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import java.util.List;

public interface ServiziMessaggiService {

  public List<DatiMessaggioUtente> getListaTuttiMessaggi(Utente utente)
      throws BusinessException, ApiException;

  public Long getNumeroMessaggiDaLeggere(Utente utente) throws BusinessException, ApiException;

  public void setAzioneMessaggio(Utente utente, DatiMessaggioUtente messaggio, EnumAzione azione)
      throws BusinessException, ApiException;

  public void setAzioneMessaggi(
      Utente utente, List<DatiMessaggioUtente> messaggi, EnumAzione azione)
      throws BusinessException, ApiException;

  public List<MessaggiInformativi> getMessaggiInformativi(String classeWicket)
      throws BusinessException;
}
