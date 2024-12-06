package it.liguriadigitale.ponmetro.api.presentation.rest.globo;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.common.builder.EsitoResponseBuilder;
import it.liguriadigitale.ponmetro.api.pojo.globo.VCfgTCatGlobo;
import it.liguriadigitale.ponmetro.api.presentation.delegate.ServiceLocator;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Path("/globo")
public class GloboResource implements GloboBackendInterface {

  private static Log log = LogFactory.getLog(GloboResource.class);

  @Override
  @GET
  @Path("/funzioni")
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public ListaGlobo getListaFunzioni() {

    ListaGlobo risultato = new ListaGlobo();

    try {
      List<VCfgTCatGlobo> lista = ServiceLocator.getInstance().getGlobo().ricercaFunzioniGlobo();
      log.debug("lista VCfgTCatGlobo:" + lista.size());
      risultato.setListaFunzioni(lista);
      risultato.setEsito(new EsitoResponseBuilder().setEsito(true).build());
    } catch (BusinessException e) {
      log.debug("");
      risultato.setEsito(
          new EsitoResponseBuilder().setEsito(false).setEccezione(e.getMessage()).build());
    }
    return risultato;
  }
}
