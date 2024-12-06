package it.liguriadigitale.ponmetro.api.presentation.rest.globo;

import it.liguriadigitale.ponmetro.api.pojo.common.EsitoResponse;
import it.liguriadigitale.ponmetro.api.pojo.globo.VCfgTCatGlobo;
import java.io.Serializable;
import java.util.List;

public class ListaGlobo implements Serializable {

  private static final long serialVersionUID = 5887667520545482403L;
  private EsitoResponse esito;
  private List<VCfgTCatGlobo> listaFunzioni;

  public EsitoResponse getEsito() {
    return esito;
  }

  public void setEsito(EsitoResponse esito) {
    this.esito = esito;
  }

  public List<VCfgTCatGlobo> getListaFunzioni() {
    return listaFunzioni;
  }

  public void setListaFunzioni(List<VCfgTCatGlobo> listaFunzioni) {
    this.listaFunzioni = listaFunzioni;
  }
}
