package it.liguriadigitale.ponmetro.api.business.scadenze.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroBusinessHelper;
import it.liguriadigitale.ponmetro.api.business.scadenze.service.ScadenzeInterface;
import it.liguriadigitale.ponmetro.api.integration.dao.VScScadenzeUtDAO;
import it.liguriadigitale.ponmetro.api.pojo.common.builder.EsitoResponseBuilder;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.db.VScScadenzeUt;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.response.VScScadenzeResponse;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.response.builder.VScScadenzeResponseBuilder;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ScadenzeImpl implements ScadenzeInterface {

  private static Log log = LogFactory.getLog(ScadenzeImpl.class);

  private VScScadenzeResponse getListaScadenzeGeneric(Long idCittadino) {
    try {
      VScScadenzeUt vScScadenzeUt = new VScScadenzeUt();
      if (idCittadino != null) {
        BigDecimal idFcitt = new BigDecimal(idCittadino);
        vScScadenzeUt.setIdFcitt(idFcitt);
      }
      VScScadenzeUtDAO vScScadenzeUtDAO = new VScScadenzeUtDAO(vScScadenzeUt);
      PonMetroBusinessHelper helper = new PonMetroBusinessHelper(vScScadenzeUtDAO);
      @SuppressWarnings("unchecked")
      List<VScScadenzeUt> listaScadenze = helper.cercaOggetti();

      return new VScScadenzeResponseBuilder()
          .setEsito(new EsitoResponseBuilder().setEsito(true).build())
          .setListaScadenze(listaScadenze)
          .build();

    } catch (BusinessException e) {
      log.error("[" + this.getClass().getName() + "] Errore", e);
      return new VScScadenzeResponseBuilder()
          .setEsito(new EsitoResponseBuilder().setEsito(false).setEccezione(e.getMessage()).build())
          .build();
    }
  }

  @Override
  public VScScadenzeResponse getListaScadenze() {
    return getListaScadenzeGeneric(null);
  }

  @Override
  public VScScadenzeResponse getListaScadenzeByCittadino(Long idCittadino) {
    return getListaScadenzeGeneric(idCittadino);
  }
}
