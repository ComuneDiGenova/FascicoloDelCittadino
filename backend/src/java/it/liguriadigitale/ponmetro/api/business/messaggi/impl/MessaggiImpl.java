package it.liguriadigitale.ponmetro.api.business.messaggi.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroBusinessHelper;
import it.liguriadigitale.ponmetro.api.business.messaggi.service.MessaggiInterface;
import it.liguriadigitale.ponmetro.api.integration.dao.messaggi.CfgTTestiSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.CfgTTesti;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.CfgTTestiBuilder;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessaggiImpl implements MessaggiInterface {

  private static Log log = LogFactory.getLog(MessaggiImpl.class);

  @SuppressWarnings("unchecked")
  @Override
  public List<MessaggiInformativi> getMessaggiInformativi(String classeWicket)
      throws BusinessException {

    CfgTTesti ricerca =
        new CfgTTestiBuilder()
            .addClasseJava(classeWicket)
            .addDataValFine(LocalDateTime.now())
            .build();
    CfgTTestiSearchDAO dao = new CfgTTestiSearchDAO(ricerca);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    List<CfgTTesti> listaDB = helper.cercaOggetti();
    List<MessaggiInformativi> listaRisposte = new ArrayList();
    for (CfgTTesti cfgTTesti : listaDB) {
      MessaggiInformativi messaggio = new MessaggiInformativi();
      messaggio.setMessaggio(cfgTTesti.getCfgValue());
      messaggio.setType(cfgTTesti.getTipoTesto());
      log.debug("MessaggiImpl: " + messaggio);
      listaRisposte.add(messaggio);
    }
    log.debug("listaRisposte: " + listaRisposte.size());
    return listaRisposte;
  }
}
