package it.liguriadigitale.ponmetro.portale.pojo.tributi.quadrotributario;

import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tributi.model.Oggetti;

public class SchedaTributoOggettiExt extends Oggetti {

  private static final long serialVersionUID = 1L;

  public SchedaTributoOggettiExt() {
    super();
  }

  @Override
  public String getConsistenza() {
    return super.getConsistenza() + " Vani/Mq";
  }

  @Override
  public String getDataInizioLegame() {
    return PageUtil.convertiAAAAMMDDFormatoData(super.getDataInizioLegame());
  }

  @Override
  public String getDataFineLegame() {
    return PageUtil.convertiAAAAMMDDFormatoData(super.getDataFineLegame());
  }
}
