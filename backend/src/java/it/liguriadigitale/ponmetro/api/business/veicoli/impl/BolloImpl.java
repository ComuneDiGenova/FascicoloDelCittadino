package it.liguriadigitale.ponmetro.api.business.veicoli.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.livello1.rest.delegate.ServiceLocatorLivelloUnoBollo;
import it.liguriadigitale.ponmetro.api.business.veicoli.service.BolloInterface;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.VeicoloDto;
import it.liguriadigitale.ponmetro.tassaauto.model.Bollo;
import it.liguriadigitale.ponmetro.tassaauto.model.DettagliBolloMezzi;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BolloImpl implements BolloInterface {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_VEICOLI = "Errore di connessione alle API Veicolo";

  @Override
  public List<Bollo> getListaDettagliBolli(VeicoloDto veicolo) throws BusinessException {
    log.debug("[BolloImpl] getListaDettagliBolli --- INIZIO");
    try {
      List<Bollo> listaBolli = new ArrayList<Bollo>();

      DettagliBolloMezzi dettagliBolloMezzi =
          ServiceLocatorLivelloUnoBollo.getInstance()
              .getApiTassaAuto()
              .getCalcolaSituazione(
                  veicolo
                      .getDataInizioProprieta()
                      .substring(
                          veicolo.getDataInizioProprieta().length() - 4,
                          veicolo.getDataInizioProprieta().length()),
                  String.valueOf(veicolo.getIdVeicolo()),
                  String.valueOf(veicolo.getIdProprietario()));

      listaBolli = dettagliBolloMezzi.getSituazionePagamenti();

      List<Bollo> sortedListaBolli =
          listaBolli.stream()
              .sorted(Comparator.comparing(Bollo::getAnno).reversed())
              .limit(4)
              .collect(Collectors.toList());
      return sortedListaBolli;
    } catch (BusinessException e) {
      log.error("BolloImpl -- getListaDettagliBolli: errore API veicoli:", e);
      throw new BusinessException(ERRORE_API_VEICOLI);
    } catch (WebApplicationException e) {
      log.error("BolloImpl -- getListaDettagliBolli: errore nella Response:" + e.getMessage());
      throw new BusinessException(e.getResponse() + "  " + e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "BolloImpl -- getListaDettagliBolli: errore RuntimeException nella Response:"
              + e.getMessage());
      throw new BusinessException(ERRORE_API_VEICOLI);
    }
  }
}
