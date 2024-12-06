package it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.verbali;

import it.liguriadigitale.ponmetro.verbaliContravvenzioni.apiclient.IstanzeRicorsiApi;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AllegatoIstanza;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DatiISEE;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DatiIstanza;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.EsitoOperazione;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.FileAllegato;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Istanza;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.IstanzeCollection;
import java.util.List;

public class ApiIstanzeRicorsiImpl implements IstanzeRicorsiApi {

  private IstanzeRicorsiApi instance;

  public ApiIstanzeRicorsiImpl(IstanzeRicorsiApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public EsitoOperazione addResumeInstance(String arg0, String arg1, Integer arg2) {
    return instance.addResumeInstance(arg0, arg1, arg2);
  }

  @Override
  public EsitoOperazione deleteCancellationRequestInFine(Integer arg0, Integer arg1, Integer arg2) {
    return instance.deleteCancellationRequestInFine(arg0, arg1, arg2);
  }

  @Override
  public IstanzeCollection getCancellationsRequest(String arg0) {
    return instance.getCancellationsRequest(arg0);
  }

  @Override
  public FileAllegato getDocumentoIstanza(String arg0, String arg1, String arg2, Integer arg3) {
    return instance.getDocumentoIstanza(arg0, arg1, arg2, arg3);
  }

  @Override
  public EsitoOperazione getFineCancellationAllegato(
      String arg0, String arg1, String arg2, Integer arg3) {
    return instance.getFineCancellationAllegato(arg0, arg1, arg2, arg3);
  }

  @Override
  public Istanza getFineCancellationRequest(String arg0, String arg1, Integer arg2) {
    return instance.getFineCancellationRequest(arg0, arg1, arg2);
  }

  @Override
  public IstanzeCollection getFineCancellationsRequest(String arg0) {
    return instance.getFineCancellationsRequest(arg0);
  }

  @Override
  public EsitoOperazione inserimentoAllegato(
      String arg0, String arg1, Integer arg2, AllegatoIstanza arg3) {
    return instance.inserimentoAllegato(arg0, arg1, arg2, arg3);
  }

  @Override
  public Istanza setCancellationRequestToVerbaleId(String arg0, DatiIstanza arg1) {
    return instance.setCancellationRequestToVerbaleId(arg0, arg1);
  }

  @Override
  public List<String> getListaMarcheVeicoli() {
    return instance.getListaMarcheVeicoli();
  }

  @Override
  public EsitoOperazione inserimentoISEE(String arg0, String arg1, Integer arg2, DatiISEE arg3) {
    return instance.inserimentoISEE(arg0, arg1, arg2, arg3);
  }
}
