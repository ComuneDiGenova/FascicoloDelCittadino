package it.liguriadigitale.ponmetro.portale.business.edilizia.pratiche.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.edilizia.pratiche.model.Pratica;
import it.liguriadigitale.ponmetro.edilizia.pratiche.model.Pratiche;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import java.util.List;

public interface ServiziEdiliziaPratiche {

  List<BreadcrumbFdC> getListaBreadcrumbPratiche();

  List<MessaggiInformativi> popolaListaMessaggiPratiche();

  Pratiche getPratiche(String codiceFiscale) throws BusinessException, ApiException;

  Pratica getDettaglioPratica(String codicePratica) throws BusinessException, ApiException;
}
