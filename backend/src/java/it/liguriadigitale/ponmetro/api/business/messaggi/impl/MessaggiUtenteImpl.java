package it.liguriadigitale.ponmetro.api.business.messaggi.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroBusinessHelper;
import it.liguriadigitale.ponmetro.api.business.messaggi.service.MessaggiUtenteInterface;
import it.liguriadigitale.ponmetro.api.integration.dao.VCfgRFcittNotificheDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.update.CfgRFcittNotificheUpdateDAO;
import it.liguriadigitale.ponmetro.api.pojo.messaggiutente.CfgRFcittNotifiche;
import it.liguriadigitale.ponmetro.api.pojo.messaggiutente.VCfgRFcittNotifiche;
import it.liguriadigitale.ponmetro.messaggi.utente.model.BodyCambiaStato;
import it.liguriadigitale.ponmetro.messaggi.utente.model.EnumAzione;
import it.liguriadigitale.ponmetro.messaggi.utente.model.EnumFiltroTipologia;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessaggiUtenteImpl implements MessaggiUtenteInterface {

  private static Log log = LogFactory.getLog(MessaggiUtenteImpl.class);

  @SuppressWarnings("unchecked")
  @Override
  public List<VCfgRFcittNotifiche> getListaMessaggi(
      Long idFCitt, EnumFiltroTipologia tipoMessaggi, Long idComparto) {
    List<VCfgRFcittNotifiche> toRet = new ArrayList<>();
    try {
      PonMetroBusinessHelper helper = buildHelper(idFCitt, tipoMessaggi, idComparto, false);
      toRet = helper.cercaOggetti();
    } catch (Exception e) {
      log.error(e);
    }
    return toRet;
  }

  @Override
  public Long messaggiUtenteNumeroGet(
      Long idFCitt, EnumFiltroTipologia tipoMessaggi, Long idComparto) {
    Long toRet = new Long(0);
    try {
      PonMetroBusinessHelper helper = buildHelper(idFCitt, tipoMessaggi, idComparto, true);
      toRet = (Long) helper.cercaOggetti().get(0);
    } catch (Exception e) {
      log.error(e);
    }
    return toRet;
  }

  private PonMetroBusinessHelper buildHelper(
      Long idFCitt, EnumFiltroTipologia tipoMessaggi, Long idComparto, boolean voglioNumero)
      throws BusinessException {
    log.debug("buildHelper: idFCitt:" + idFCitt);
    if (idFCitt == null || idFCitt == 0) {
      throw new BusinessException("id citt non puo essere nullo");
    }
    VCfgRFcittNotifiche vCfgRFcittNotifiche = new VCfgRFcittNotifiche();
    vCfgRFcittNotifiche.setIdFcitt(idFCitt);
    vCfgRFcittNotifiche.setIdComp(idComparto != null ? idComparto : null);

    VCfgRFcittNotificheDAO vCfgRFcittNotificheDAO = new VCfgRFcittNotificheDAO(vCfgRFcittNotifiche);
    vCfgRFcittNotificheDAO.setTipoMessaggi(
        tipoMessaggi != null ? tipoMessaggi : EnumFiltroTipologia.TUTTI);

    vCfgRFcittNotificheDAO.setVoglioNumero(voglioNumero);
    return new PonMetroBusinessHelper(vCfgRFcittNotificheDAO);
  }

  @Override
  public void setStatoMessaggi(Long idFCitt, BodyCambiaStato body) throws BusinessException {
    if (idFCitt == null || idFCitt == 0) {
      throw new BusinessException("id citt non puo essere nullo");
    }
    if (body == null) {
      throw new BusinessException("body non puo essere nullo");
    }
    if (StringUtils.isEmpty(body.getUtenteAgg())) {
      throw new BusinessException(" l'utente aggiornamento non puo' essere nullo o vuota");
    }
    if (body.getListaMessaggi() == null || body.getListaMessaggi().isEmpty()) {
      throw new BusinessException("la lista degli id non puo' essere nulla o vuota");
    }
    log.debug("setStatoMessaggi: idFCitt:" + idFCitt);
    log.debug("setStatoMessaggi: body:" + body.toString());
    CfgRFcittNotifiche cfgRFcittNotifiche = new CfgRFcittNotifiche();
    cfgRFcittNotifiche.setIdFcitt(idFCitt);
    cfgRFcittNotifiche.setUtenteAgg(body.getUtenteAgg());
    log.debug("setStatoMessaggi: cfgRFcittNotifiche:" + cfgRFcittNotifiche.toString());
    CfgRFcittNotificheUpdateDAO cfgRFcittNotificheDAO =
        new CfgRFcittNotificheUpdateDAO(cfgRFcittNotifiche);
    cfgRFcittNotificheDAO.setAzione(
        body.getAzione() != null ? body.getAzione() : EnumAzione.SEGNA_COME_LETTO);
    cfgRFcittNotificheDAO.setListaIdFcittNotifiche(body.getListaMessaggi());
    log.debug("setStatoMessaggi: cfgRFcittNotificheDAO:" + cfgRFcittNotificheDAO.toString());
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(cfgRFcittNotificheDAO);
    helper.aggiornaOggetto();
  }
}
