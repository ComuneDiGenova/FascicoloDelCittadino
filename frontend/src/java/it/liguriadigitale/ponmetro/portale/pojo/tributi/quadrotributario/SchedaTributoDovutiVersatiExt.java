package it.liguriadigitale.ponmetro.portale.pojo.tributi.quadrotributario;

import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tributi.model.DovutiVersati;

public class SchedaTributoDovutiVersatiExt extends DovutiVersati {

  private static final long serialVersionUID = 1L;

  public SchedaTributoDovutiVersatiExt() {
    super();
  }

  @Override
  public String getDataScadenza() {
    return PageUtil.convertiAAAAminusMMminusDDFormatoData(super.getDataScadenza());
  }

  public String getVersatoL() {
    return PageUtil.convertiImportoToEuroZeroWantNull(super.getVersato());
  }
}
