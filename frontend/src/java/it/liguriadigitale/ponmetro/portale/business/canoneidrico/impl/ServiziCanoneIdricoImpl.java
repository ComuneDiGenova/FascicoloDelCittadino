package it.liguriadigitale.ponmetro.portale.business.canoneidrico.impl;

import it.liguriadigitale.ponmetro.portale.business.canoneidrico.service.ServiziCanoneIdricoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServiziCanoneIdricoImpl implements ServiziCanoneIdricoService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_CANONE_IDRICO =
      "Errore di connessione alle API canone idrico";
}
