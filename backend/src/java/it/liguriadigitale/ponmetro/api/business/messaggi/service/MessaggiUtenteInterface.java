package it.liguriadigitale.ponmetro.api.business.messaggi.service;

import it.liguriadigitale.ponmetro.api.pojo.messaggiutente.VCfgRFcittNotifiche;
import it.liguriadigitale.ponmetro.messaggi.utente.model.BodyCambiaStato;
import it.liguriadigitale.ponmetro.messaggi.utente.model.EnumFiltroTipologia;
import java.util.List;

public interface MessaggiUtenteInterface {
  public List<VCfgRFcittNotifiche> getListaMessaggi(
      Long idFCitt, EnumFiltroTipologia tipoMessaggi, Long idComparto);

  public Long messaggiUtenteNumeroGet(
      Long idFCitt, EnumFiltroTipologia tipoMessaggi, Long idComparto);

  public void setStatoMessaggi(Long idFCitt, BodyCambiaStato body) throws Exception;
}
