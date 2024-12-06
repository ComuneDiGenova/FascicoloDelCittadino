package it.liguriadigitale.ponmetro.portale.business.supportoistanzeverbalipl.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiDocumento;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiMotivo;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiMotivoSummary;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public interface ServiziSupportoIstanzeVerbaliPLInterface {

  public List<DatiMotivoSummary> getElencoMotiviUgualiCodiceASummary(String codiceHermes)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException;

  public List<DatiMotivoSummary> getElencoMotiviUgualiASummary(String serie, List<String> articoli)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException;

  public List<DatiMotivo> getElencoMotiviUgualiA(
      String serie, List<String> articoli, String codiceHermes)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException;

  public List<DatiMotivoSummary> getElencoMotiviDiversiDaSummary(
      String serie, List<String> articoli)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException;

  public List<DatiMotivo> getElencoMotiviDiversiDa(
      String serie, List<String> articoli, String codiceHermes)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException;

  public List<DatiDocumento> getElencoDocumenti(String codiceHermes)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException;
}
