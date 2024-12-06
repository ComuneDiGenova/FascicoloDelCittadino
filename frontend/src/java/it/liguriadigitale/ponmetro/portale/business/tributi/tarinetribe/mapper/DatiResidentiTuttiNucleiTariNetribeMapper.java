package it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.mapper;

import it.liguriadigitale.ponmetro.demografico.model.ResidenteTari;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiResidenti;
import org.modelmapper.PropertyMap;

public class DatiResidentiTuttiNucleiTariNetribeMapper
    extends PropertyMap<ResidenteTari, DatiResidenti> {

  @Override
  protected void configure() {
    map().setCodiceFiscale(source.getCpvComponentTaxCode());
    map().setCodiceParentela(source.getCpvParentType());
  }
}
