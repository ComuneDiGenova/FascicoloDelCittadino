package it.liguriadigitale.ponmetro.portale.business.impiantitermici.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.catastoimpianti.model.Documento;
import it.liguriadigitale.ponmetro.catastoimpianti.model.ImpiantiTermici;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import java.io.IOException;
import java.util.List;

public interface ServiziImpiantiTermici {

  List<BreadcrumbFdC> getListaBreadcrumb();

  List<BreadcrumbFdC> getListaBreadcrumbDettagli();

  List<MessaggiInformativi> popolaListaMessaggi();

  List<MessaggiInformativi> popolaListaMessaggiDettagli();

  ImpiantiTermici getImpianti(String codiceFiscale)
      throws BusinessException, ApiException, IOException;

  Documento getDocumento(
      String idImpianto, String idGruppo, String idRapporto, String tipoDocumento)
      throws BusinessException, ApiException, IOException;
}
