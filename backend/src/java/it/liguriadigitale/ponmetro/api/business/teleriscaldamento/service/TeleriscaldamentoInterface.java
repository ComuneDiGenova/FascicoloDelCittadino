package it.liguriadigitale.ponmetro.api.business.teleriscaldamento.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.teleriscaldamento.TrClienti;
import it.liguriadigitale.ponmetro.api.pojo.teleriscaldamento.TrContratti;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.Cliente;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.Contratto;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento;
import java.math.BigDecimal;
import java.util.List;

public interface TeleriscaldamentoInterface {

  public List<DomandaTeleriscaldamento> getDatiCittadino(String codiceFiscale)
      throws BusinessException;

  public BigDecimal getProtocollo() throws BusinessException;

  public void setDatiCittadino(DomandaTeleriscaldamento domanda) throws BusinessException;

  public List<Contratto> getListaContratti() throws BusinessException;

  public List<Cliente> getListaClienti() throws BusinessException;

  public TrContratti getContratto(String numeroContratto) throws BusinessException;

  public TrClienti getCliente(String numeroCliente) throws BusinessException;
}
