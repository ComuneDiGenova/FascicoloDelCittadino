package it.liguriadigitale.ponmetro.api.business.messaggi.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import java.util.List;

public interface MessaggiInterface {

  public List<MessaggiInformativi> getMessaggiInformativi(String classeWicket)
      throws BusinessException;
}
