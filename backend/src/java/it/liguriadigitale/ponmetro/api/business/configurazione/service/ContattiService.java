package it.liguriadigitale.ponmetro.api.business.configurazione.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.contatti.CfgTContatti;
import it.liguriadigitale.ponmetro.contattiutente.model.ContattiUtente;
import java.util.List;

public interface ContattiService {

  public List<CfgTContatti> selectContatti(Long idFcitt) throws BusinessException;

  public void inserisciContatti(Long idFcitt, String contatto, String tipo)
      throws BusinessException;

  public void aggiornaContatti(Long idFcitt, String contatto, String tipo) throws BusinessException;

  public void inserisciAggiornaContatto(Long idFcitt, String tipo, ContattiUtente contatti)
      throws BusinessException;

  public List<ContattiUtente> getContattiUtente(Long idFcitt) throws BusinessException;

  public void cancellaContatto(Long idFcitt, String tipo) throws BusinessException;
}
