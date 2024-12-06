package it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato;

import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tributi.model.Ordinario;

public class OrdinarioScadenzeExt extends Ordinario {
  /** */
  private static final long serialVersionUID = 1L;

  @Override
  public String getDataScadenzaRata() {
    return PageUtil.convertiAAAAminusMMminusDDFormatoData(super.getDataScadenzaRata());
  }

  public Integer getAnnoDataScadenzaRata() {
    return PageUtil.getYearFromAAAAminusMMminusDDOrActualYear(super.getDataScadenzaRata());
  }
}
