package it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.mapper;

import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiIseeExtend;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiIsee;
import org.modelmapper.PropertyMap;

public class DatiIseeTariNetribeMapper extends PropertyMap<DatiIseeExtend, DatiIsee> {

  @Override
  protected void configure() {

    map().setCodiceFiscale(source.getCodiceFiscale());
    map().setDataPresentazione(source.getDataPresentazione());
    map().setImporto(source.getImporto());
    map().setProtocolloDSU(source.getProtocolloDSU());
  }
}
