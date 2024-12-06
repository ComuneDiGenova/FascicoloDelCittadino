package it.liguriadigitale.ponmetro.portale.business.domandelocazione.impl;

import it.liguriadigitale.ponmetro.portale.business.domandelocazione.service.ServiziDomandeLocazioneService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServiziDomandeLocazioneImpl implements ServiziDomandeLocazioneService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_DOMANDE_LOCAZIONE =
      "Errore di connessione alle API Domande locazione";
}
