package it.liguriadigitale.ponmetro.api.business.entitaconfigurazioneutente.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroBusinessHelper;
import it.liguriadigitale.ponmetro.api.business.entitaconfigurazioneutente.service.ServiziHomePageInterface;
import it.liguriadigitale.ponmetro.api.integration.dao.CfgKeyValueDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.CfgTCatFunzLinkDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.query.ElencoCfgTCatFunzPagineDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.query.FunzioniInEvidenzaDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.query.FunzioniInRealizzazioneDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.query.RicercaLinkPanelRiepilogoDAO;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgTCatFunz;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioneChiaveValore;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.SottopagineDisponibili;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ChiaveValore;
import it.liguriadigitale.ponmetro.api.pojo.links.CfgTCatFunzLink;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.CfgKeyValue;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServiziHomePageImpl implements ServiziHomePageInterface {

  private static Log log = LogFactory.getLog(ServiziHomePageImpl.class);

  @Override
  public String getValoreByChiave(String chiave) {

    CfgKeyValue cfgkeyvalue = new CfgKeyValue();
    cfgkeyvalue.setCfgKey(chiave);
    CfgKeyValueDAO dao = new CfgKeyValueDAO(cfgkeyvalue);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    try {
      CfgKeyValue ritorno = (CfgKeyValue) helper.cercaOggetto();
      if (ritorno != null) {
        return ritorno.getCfgValue();
      } else {
        return null;
      }
    } catch (BusinessException e) {
      log.error("Errore", e);
      return "ERRORE durante la ricerca";
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<FunzioneChiaveValore> getListaFunzioniInEvidenza(String chiave) {
    log.debug("CP getListaFunzioniInEvidenza in servizi home page = " + chiave);

    List<FunzioneChiaveValore> lista = new ArrayList<>();

    FunzioneChiaveValore funzioneChiaveValore = new FunzioneChiaveValore();
    ChiaveValore chiaveValore = new ChiaveValore();
    chiaveValore.setChiave(chiave);
    funzioneChiaveValore.setChiaveValore(chiaveValore);

    FunzioniInEvidenzaDAO dao = new FunzioniInEvidenzaDAO(funzioneChiaveValore);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    try {
      lista = helper.cercaOggetti();
    } catch (BusinessException e) {
      log.error("Errore durante getListaFunzioniInEvidenza: ", e);
    }
    return lista;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ChiaveValore> getListaFunzioniInRealizzazione(String chiave) {
    log.debug("CP getListaFunzioniInRealizzazione in servizi home page = " + chiave);

    List<ChiaveValore> lista = new ArrayList<>();

    ChiaveValore chiaveValore = new ChiaveValore();
    chiaveValore.setChiave(chiave);

    FunzioniInRealizzazioneDAO dao = new FunzioniInRealizzazioneDAO(chiaveValore);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    try {
      lista = helper.cercaOggetti();
    } catch (BusinessException e) {
      log.error("Errore durante getListaFunzioniInRealizzazione: ", e);
    }
    return lista;
  }

  @Override
  public List<SottopagineDisponibili> getElencoSottoPagine() {
    log.debug("getElencoSottoPagine in servizi home page ");
    List<SottopagineDisponibili> lista = new ArrayList<>();
    ElencoCfgTCatFunzPagineDAO dao = new ElencoCfgTCatFunzPagineDAO();
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);

    try {
      lista = helper.cercaOggetti();
    } catch (BusinessException e) {
      log.error("Errore durante getListaFunzioniInRealizzazione: ", e);
    }
    return lista;
  }

  @Override
  public List<CfgTCatFunzLink> getElencoLinkEsterni(VCfgTCatFunz vcfgtcatfunz)
      throws BusinessException {

    RicercaLinkPanelRiepilogoDAO dao = new RicercaLinkPanelRiepilogoDAO(vcfgtcatfunz);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    return helper.cercaOggetti();
  }

  @Override
  public List<CfgTCatFunzLink> getLinkEsterniPerIdFunz(String idFunz) {
    log.debug("idFunz ricevuto =" + idFunz);
    List<CfgTCatFunzLink> lista = new ArrayList<CfgTCatFunzLink>();
    CfgTCatFunzLink pojo = new CfgTCatFunzLink();
    CfgTCatFunzLinkDAO dao = new CfgTCatFunzLinkDAO(pojo);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    try {
      lista = helper.cercaOggetti();
      log.debug("lista oggetti trovati = \n" + lista);
    } catch (BusinessException e) {
      log.error(e);
    }
    if (!idFunz.equalsIgnoreCase("all") && !lista.isEmpty()) {
      List<CfgTCatFunzLink> listaDaRestituire = new ArrayList<CfgTCatFunzLink>();
      for (CfgTCatFunzLink elemento : lista) {
        if (elemento.getIdFunz().equals(new BigDecimal(idFunz)) && elemento.getFlagAbilitazione()) {
          listaDaRestituire.add(elemento);
        }
      }
      return listaDaRestituire;
    }

    return lista;
  }
}
