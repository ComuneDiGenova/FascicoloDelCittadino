package it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato;

import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tributi.model.VersamentiAtto;

public class VersamentiAttoScadenzeExt extends VersamentiAtto {

  private static final long serialVersionUID = 1L;

  @Override
  public String getDataVersamento() {
    return PageUtil.convertiAAAAminusMMminusDDFormatoData(super.getDataVersamento());
  }

  public String getImportoVersatoL() {
    return PageUtil.convertiImportoToEuroZeroWantNull(super.getImportoVersato());
  }
}
