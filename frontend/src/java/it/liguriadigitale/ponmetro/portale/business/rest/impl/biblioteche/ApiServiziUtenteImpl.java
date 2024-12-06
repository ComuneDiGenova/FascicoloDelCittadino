package it.liguriadigitale.ponmetro.portale.business.rest.impl.biblioteche;

import it.liguriadigitale.ponmetro.sebinaBiblioteche.apiclient.ServiziUtenteApi;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Comunicazione;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.InlineObject2;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.InlineObject3;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Movimento;
import java.util.List;

public class ApiServiziUtenteImpl implements ServiziUtenteApi {

  private ServiziUtenteApi instance;

  public ApiServiziUtenteImpl(ServiziUtenteApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public void deleteAvvisoById(Long arg0, Long arg1) {
    instance.deleteAvvisoById(arg0, arg1);
  }

  @Override
  public void deleteMessaggioById(Long arg0, Long arg1) {
    instance.deleteMessaggioById(arg0, arg1);
  }

  @Override
  public void deleteMovimentoUtenteById(Long arg0, Long arg1) {
    instance.deleteMovimentoUtenteById(arg0, arg1);
  }

  @Override
  public List<Comunicazione> findAvvisiUtenteById(Long arg0, Boolean arg1, List<String> arg2) {
    return instance.findAvvisiUtenteById(arg0, arg1, arg2);
  }

  @Override
  public List<Comunicazione> findMessaggiUtenteById(Long arg0, Boolean arg1, List<String> arg2) {
    return instance.findMessaggiUtenteById(arg0, arg1, arg2);
  }

  @Override
  public List<Movimento> findMovimentiCorrentiUtenteById(Long arg0, List<String> arg1) {
    return instance.findMovimentiCorrentiUtenteById(arg0, arg1);
  }

  @Override
  public void patchAvvisoById(Long arg0, Long arg1, InlineObject2 arg2) {
    instance.patchAvvisoById(arg0, arg1, arg2);
  }

  @Override
  public void patchMessaggioById(Long arg0, Long arg1, InlineObject3 arg2) {
    instance.patchMessaggioById(arg0, arg1, arg2);
  }
}
