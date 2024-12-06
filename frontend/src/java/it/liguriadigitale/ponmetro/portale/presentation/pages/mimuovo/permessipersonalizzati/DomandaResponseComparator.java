package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati;

import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DomandaResponse;
import java.util.Comparator;

public class DomandaResponseComparator implements Comparator<DomandaResponse> {

  @Override
  public int compare(DomandaResponse o1, DomandaResponse o2) {

    return o2.getIdDomanda().compareTo(o1.getIdDomanda());
  }
}
