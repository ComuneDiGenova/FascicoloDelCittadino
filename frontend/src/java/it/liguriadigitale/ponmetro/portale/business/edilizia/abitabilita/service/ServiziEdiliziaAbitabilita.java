package it.liguriadigitale.ponmetro.portale.business.edilizia.abitabilita.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.edilizia.abitabilita.model.AbitabilitaResponse;
import it.liguriadigitale.ponmetro.edilizia.abitabilita.model.AllegatoBody;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.edilizia.abitabilita.Abitabilita;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import java.io.IOException;
import java.util.List;

public interface ServiziEdiliziaAbitabilita {

  List<BreadcrumbFdC> getListaBreadcrumbAbitabilita();

  List<MessaggiInformativi> popolaListaMessaggiAbitabilita();

  List<AbitabilitaResponse> getDecretiAbitabilita(Abitabilita abitabilita)
      throws BusinessException, IOException, ApiException;

  AllegatoBody getdecretoIdUdAllegatoNomeFile(Integer idUd, String nomeFile)
      throws BusinessException, IOException, ApiException;
}
