package it.liguriadigitale.ponmetro.api.presentation.rest.scadenze;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.audit.FdcAudit;
import it.liguriadigitale.ponmetro.api.pojo.common.EsitoResponse;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.ScadenzePersonalizzateDto;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.response.VScScadenzeResponse;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.response.builder.VScScadenzeResponseBuilder;
import it.liguriadigitale.ponmetro.api.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.api.presentation.rest.application.exception.BadRequestException;
import it.liguriadigitale.ponmetro.api.presentation.rest.scadenze.service.ScadenzeRestInterface;
import java.math.BigDecimal;
import javax.ws.rs.InternalServerErrorException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ScadenzeResource implements ScadenzeRestInterface {

  private static Log log = LogFactory.getLog(ScadenzeResource.class);

  private static final String SCADENZE_PAGE = "ScadenzePage";

  @Override
  public VScScadenzeResponse getListaScadenze() {
    log.debug("getListaScadenze");
    try {
      return ServiceLocator.getInstance().getScadenze().getListaScadenze();
    } catch (BusinessException e) {
      log.error("Errore getListaScadenze: " + e);
      throw new BadRequestException("Impossibile recuperare lista");
    }
  }

  @Override
  public void verificaScadenzePersonalizzate(ScadenzePersonalizzateDto scadenza) {
    log.debug("CP verificaScadenzePersonalizzate BACKEND");
    if (StringUtils.isEmpty(scadenza.getCodiceFiscale())) {
      log.error("codiceFiscale: " + scadenza.getCodiceFiscale());
      throw new BadRequestException("Codifiscale vuoto");
    }
    try {
      ServiceLocator.getInstance()
          .getScadenzePersonalizzate()
          .verificaScadenzePersonalizzate(scadenza);
    } catch (BusinessException e) {
      String errore = "Errore durante il lancio del batch ScadenzePersonalizzate";
      log.error(errore + ":", e);
      throw new InternalServerErrorException(errore);
    }
  }

  @Override
  public VScScadenzeResponse getListaScadenzeCittadino(Long idFcitt) {
    log.debug("getListaScadenze");
    try {
      return ServiceLocator.getInstance().getScadenze().getListaScadenzeByCittadino(idFcitt);
    } catch (BusinessException e) {
      log.error("Errore getListaScadenze: " + e);
      throw new BadRequestException("Impossibile recuperare lista");
    }
  }

  @Override
  public VScScadenzeResponse isUltimoAccessoScadenze24H(Long idFcitt) {
    log.debug("isUltimoAccessoScadenze24H");
    FdcAudit ricerca = new FdcAudit();
    EsitoResponse esito = new EsitoResponse();
    if (idFcitt != null) {
      ricerca.setIdFcitt(BigDecimal.valueOf(idFcitt));
    } else {
      log.error("idFcitt: " + idFcitt);
      throw new BadRequestException("Id cittadino nullo");
    }
    try {
      ricerca.setNomePagina(SCADENZE_PAGE);
      boolean isUltimoAccesso =
          ServiceLocator.getInstance().getAuditBackend().verificaUltimoAccesso(ricerca);
      esito.setEsito(isUltimoAccesso);
    } catch (BusinessException e) {
      log.error("Errore getListaScadenze: " + e);
      esito.setEsito(false);
      esito.setEccezione(e.getMessage());
      esito.setDescrizione("ERRORE");
    }
    VScScadenzeResponse response = new VScScadenzeResponseBuilder().setEsito(esito).build();
    return response;
  }
}
