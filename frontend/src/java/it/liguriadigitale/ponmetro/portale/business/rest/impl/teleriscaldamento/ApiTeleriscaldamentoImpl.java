package it.liguriadigitale.ponmetro.portale.business.rest.impl.teleriscaldamento;

import it.liguriadigitale.ponmetro.teleriscaldamento.apiclient.TeleriscaldamentoApi;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.ClientiTeleriscaldamento;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.ContrattiTeleriscaldamento;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandeTeleriscaldamento;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.Esito;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.Protocollo;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.RicercaDato;

public class ApiTeleriscaldamentoImpl implements TeleriscaldamentoApi {

  private TeleriscaldamentoApi instance;

  public ApiTeleriscaldamentoImpl(TeleriscaldamentoApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public DomandeTeleriscaldamento getDatiCittadino(String arg0) {
    return instance.getDatiCittadino(arg0);
  }

  @Override
  public Protocollo getProtocollo() {
    return instance.getProtocollo();
  }

  @Override
  public Esito setDatiCittadino(DomandaTeleriscaldamento arg0) {
    return instance.setDatiCittadino(arg0);
  }

  @Override
  public ClientiTeleriscaldamento getClienti() {
    return instance.getClienti();
  }

  @Override
  public ContrattiTeleriscaldamento getContratti() {
    return instance.getContratti();
  }

  @Override
  public RicercaDato isClientePresenteInLista(String arg0) {
    return instance.isClientePresenteInLista(arg0);
  }

  @Override
  public RicercaDato isContrattoPresenteInLista(String arg0) {
    return instance.isContrattoPresenteInLista(arg0);
  }
}
