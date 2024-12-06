package it.liguriadigitale.ponmetro.portale.business.tributi.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.proprieta.DettaglioProprietaUtenzaExt;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.proprieta.ProprietaUtenzeExt;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.quadrotributario.SchedaTributoExt;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.quadrotributario.TributiExt;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato.ElencoVersamentiExt;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato.TributiScadenzeExt;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public interface ServiziTributiInterface {

  public ElencoVersamentiExt getElencoVersamentiExt(Utente utente)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException;

  public ElencoVersamentiExt getElencoVersamentiExtFilteredByYear(
      ElencoVersamentiExt elencoVersamentiExt, Utente utente, Integer annoIesimo)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException;

  public ElencoVersamentiExt getElencoVersamentiExtFilteredByYear(Utente utente, Integer anno)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException;

  public TributiScadenzeExt getScadenzeExt(Utente utente)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException;

  public TributiScadenzeExt getScadenzeExtFilteredByYear(
      TributiScadenzeExt ScadenzeExt, Utente utente, Integer annoIesimo)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException;

  public TributiScadenzeExt getScadenzeExtFilteredByYear(Utente utente, Integer anno)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException;

  public List<SchedaTributoExt> getSchedaTributoExt(String uri)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException;

  public List<TributiExt> getTributiExt(Utente utente)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException;

  public List<TributiExt> getTributiExtFilteredByYear(
      List<TributiExt> listToFilter, Utente utente, Integer anno)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException;

  public List<TributiExt> getTributiExtFilteredByYear(Utente utente, Integer anno)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException;

  public List<ProprietaUtenzeExt> getProprietaUtenzeExt(Utente utente, String annoRiferimento)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException;

  public DettaglioProprietaUtenzaExt getDettaglioProprietaUtenzaExt(ProprietaUtenzeExt proprieta)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException;
}
