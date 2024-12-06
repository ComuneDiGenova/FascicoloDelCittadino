package it.liguriadigitale.ponmetro.api.presentation.rest.home;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.common.builder.EsitoResponseBuilder;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgTCatFunz;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioneChiaveValore;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.SottopagineDisponibili;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ChiaveValore;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ListaFunzioni;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ListaPagine;
import it.liguriadigitale.ponmetro.api.pojo.links.CfgTCatFunzLink;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.api.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.api.presentation.rest.home.service.HomeRestInterface;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Path("/homepage")
public class HomeServiziResource implements HomeRestInterface {

  private static Log log = LogFactory.getLog(HomeServiziResource.class);

  @Override
  public ChiaveValore getValore(String chiave) {

    try {
      String valore = ServiceLocator.getInstance().getServiziHomePage().getValoreByChiave(chiave);
      ChiaveValore chiaveValore = new ChiaveValore();
      chiaveValore.setChiave(chiave);
      chiaveValore.setValore(valore);
      return chiaveValore;

    } catch (BusinessException e) {
      log.error("e");
      throw new NotFoundException("Nessuno valore trovato per la chiave: " + chiave);
    }
  }

  @Override
  public ListaFunzioni getListaFunzioni() {

    try {
      List<FunzioniDisponibili> lista =
          ServiceLocator.getInstance().getConfigurazioneUtenteFdC().getListaFunzioni();
      ListaFunzioni listaFunzioni = new ListaFunzioni();
      listaFunzioni.setEsito(new EsitoResponseBuilder().setEsito(true).build());
      listaFunzioni.setListaFunzioni(lista);
      return listaFunzioni;
    } catch (BusinessException e) {
      log.error("e");
      throw new NotFoundException("Nessuna funzione trovata");
    }
  }

  @Override
  public List<FunzioneChiaveValore> getListaFunzioniInEvidenza(String chiave) {
    log.debug("CP getListaFunzioniInEvidenza in home servizi = " + chiave);
    try {
      List<FunzioneChiaveValore> lista = new ArrayList<FunzioneChiaveValore>();
      if (chiave != null && !chiave.isEmpty()) {
        lista =
            ServiceLocator.getInstance()
                .getServiziHomePage()
                .getListaFunzioniInEvidenza(chiave.concat("%"));
      }
      return lista;
    } catch (BusinessException e) {
      log.error("Errore getListaFunzioniInEvidenza: " + e.getMessage());
      throw new NotFoundException("Nessuna funzione trovata per la chiave: " + chiave);
    }
  }

  @Override
  public List<ChiaveValore> getListaFunzioniInRealizzazione(String chiave) {
    log.debug("CP getListaFunzioniInRealizzazione in home servizi = " + chiave);
    try {
      List<ChiaveValore> lista = new ArrayList<ChiaveValore>();
      if (chiave != null && !chiave.isEmpty()) {
        lista =
            ServiceLocator.getInstance()
                .getServiziHomePage()
                .getListaFunzioniInRealizzazione(chiave.concat("%"));
      }
      return lista;
    } catch (BusinessException e) {
      log.error("Errore getListaFunzioniInEvidenza: " + e.getMessage());
      throw new NotFoundException("Nessuna funzione trovata per la chiave: " + chiave);
    }
  }

  @Override
  public Boolean getAbilitazioneIngresso(String codiceFiscale) {
    log.debug("getAbilitazioneIngresso in home servizi = " + codiceFiscale);
    try {
      if (StringUtils.isNotEmpty(codiceFiscale)) {
        return ServiceLocator.getInstance()
            .getConfigurazioneUtenteFdC()
            .isAccessoAutorizzato(codiceFiscale);
      } else {
        log.debug("codiceFiscale nullo o vuoto");
        return false;
      }
    } catch (BusinessException e) {
      log.error("Errore getAbilitazioneIngresso: " + e.getMessage());
      throw new NotAuthorizedException("Errore durente la verifica dell'autorizzazione");
    }
  }

  @Override
  public List<MessaggiInformativi> getMessaggiInformativi(String classe) {
    log.debug("getMessaggiInformativi in home servizi = " + classe);
    try {
      if (StringUtils.isNotEmpty(classe)) {
        return ServiceLocator.getInstance().getServiziMessaggiI().getMessaggiInformativi(classe);
      } else {
        log.debug("nome classe nulla o vuota");
        return new ArrayList<>();
      }
    } catch (BusinessException e) {
      log.error("Errore getMessaggiInformativi: " + e.getMessage());
      throw new BadRequestException("Errore durante l'accesso al database");
    }
  }

  @Override
  public ListaPagine getListaPagine() {
    try {
      List<SottopagineDisponibili> lista =
          ServiceLocator.getInstance().getServiziHomePage().getElencoSottoPagine();
      ListaPagine listaPagine = new ListaPagine();
      listaPagine.setEsito(new EsitoResponseBuilder().setEsito(true).build());
      listaPagine.setListaPagine(lista);
      return listaPagine;
    } catch (BusinessException e) {
      log.error("e");
      throw new NotFoundException("Nessuna funzione trovata");
    }
  }

  @Override
  public List<CfgTCatFunzLink> getLinkEsterni(String var1) {

    Long idSez = Long.parseLong(var1);
    List<CfgTCatFunzLink> lista = new ArrayList<CfgTCatFunzLink>();
    VCfgTCatFunz pojo = new VCfgTCatFunz();
    pojo.setIdSez(idSez);
    try {
      lista = ServiceLocator.getInstance().getServiziHomePage().getElencoLinkEsterni(pojo);
      log.debug("lista link esterni ritrovati = " + lista);
      return lista;
    } catch (BusinessException e) {
      log.error("e");
      throw new NotFoundException("Nessuna funzione trovata");
    }
  }

  @Override
  public List<CfgTCatFunzLink> getLinkEsterniPerIdFunz(String idFunz) {

    // info o video
    // se idFunz risulta "all" restituisce tutti i record
    List<CfgTCatFunzLink> lista = new ArrayList<CfgTCatFunzLink>();

    try {
      lista = ServiceLocator.getInstance().getServiziHomePage().getLinkEsterniPerIdFunz(idFunz);
    } catch (BusinessException e) {
      log.error("getLinkEsterniPerIdFunz =" + e);
    }
    return lista;
  }
}
