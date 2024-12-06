package it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.traffico;

import it.liguriadigitale.ponmetro.segnalazionitraffico.apiclient.RetrieveSegnalazioniTrafficoApi;
import it.liguriadigitale.ponmetro.segnalazionitraffico.model.DatiSegnalazioneTrafficoItem;
import java.math.BigDecimal;
import java.util.List;

public class ApiServiziSegnalazioniTrafficoPortaleImpl implements RetrieveSegnalazioniTrafficoApi {

  private RetrieveSegnalazioniTrafficoApi instance;

  public ApiServiziSegnalazioniTrafficoPortaleImpl(RetrieveSegnalazioniTrafficoApi instance) {
    this.instance = instance;
  }

  @Override
  public List<DatiSegnalazioneTrafficoItem> serviziTrafficoTrafficGet(
      String arg0,
      String arg1,
      String arg2,
      String arg3,
      Boolean arg4,
      Boolean arg5,
      Integer arg6,
      String arg7,
      String arg8,
      BigDecimal arg9,
      String arg10) {
    return instance.serviziTrafficoTrafficGet(
        arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10);
  }
}
