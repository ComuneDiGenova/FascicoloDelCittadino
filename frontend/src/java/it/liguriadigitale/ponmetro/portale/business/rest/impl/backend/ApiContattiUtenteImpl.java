package it.liguriadigitale.ponmetro.portale.business.rest.impl.backend;

import it.liguriadigitale.ponmetro.contattiutente.apiclient.ContattiUtenteApi;
import it.liguriadigitale.ponmetro.contattiutente.model.ContattiUtente;
import java.util.List;

public class ApiContattiUtenteImpl implements ContattiUtenteApi {

  private ContattiUtenteApi instance;

  public ApiContattiUtenteImpl(ContattiUtenteApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public List<ContattiUtente> getContatti(Long idFcitt) {
    return instance.getContatti(idFcitt);
  }

  @Override
  public void upsertContatti(Long idFcitt, String tipo, ContattiUtente contatti) {
    instance.upsertContatti(idFcitt, tipo, contatti);
  }

  @Override
  public void deleteContatti(Long idFcitt, String tipo) {
    instance.deleteContatti(idFcitt, tipo);
  }
}
