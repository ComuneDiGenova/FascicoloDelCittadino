package it.liguriadigitale.ponmetro.portale.business.rest.impl.backend;

import it.liguriadigitale.ponmetro.messaggi.utente.apiclient.MessaggiUtenteApi;
import it.liguriadigitale.ponmetro.messaggi.utente.model.BodyCambiaStato;
import it.liguriadigitale.ponmetro.messaggi.utente.model.DatiMessaggioUtente;
import it.liguriadigitale.ponmetro.messaggi.utente.model.EnumFiltroTipologia;
import it.liguriadigitale.ponmetro.messaggi.utente.model.EnumMessaggiUtente;
import it.liguriadigitale.ponmetro.messaggi.utente.model.NumeroMessaggiUtente;
import java.util.List;

public class ApiServiziMessaggiUtentePortaleImpl implements MessaggiUtenteApi {

  private MessaggiUtenteApi instance;

  public ApiServiziMessaggiUtentePortaleImpl(MessaggiUtenteApi instance) {
    this.instance = instance;
  }

  @Override
  public void messaggiUtenteCambiaStatoPut(Long arg0, BodyCambiaStato arg1) {
    this.instance.messaggiUtenteCambiaStatoPut(arg0, arg1);
  }

  @Override
  public EnumMessaggiUtente messaggiUtenteEnumsGet() {
    return this.instance.messaggiUtenteEnumsGet();
  }

  @Override
  public List<DatiMessaggioUtente> messaggiUtenteListaGet(
      Long arg0, EnumFiltroTipologia arg1, Long arg2) {
    return this.instance.messaggiUtenteListaGet(arg0, arg1, arg2);
  }

  @Override
  public NumeroMessaggiUtente messaggiUtenteNumeroGet(
      Long arg0, EnumFiltroTipologia arg1, Long arg2) {
    return this.instance.messaggiUtenteNumeroGet(arg0, arg1, arg2);
  }
}
