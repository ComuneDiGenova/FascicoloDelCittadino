package it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.utils;

import it.liguriadigitale.ponmetro.accenture.model.ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonio2RGet200ResponseRecordsInner;
import org.modelmapper.PropertyMap;

public class DatiConiugeMatrimonioMapper
    extends PropertyMap<
        ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonio2RGet200ResponseRecordsInner,
        DatiMatrimonio> {

  @Override
  protected void configure() {

    map().setName(source.getName());

    map().setStatoPraticaC(source.getStatoPraticaC());

    map().setDichiarante(source.getDichiarante1C());

    map().setConiuge(source.getDichiarante2C());

    map().setCreatedDate(source.getCreatedDate());

    map().setLastModifiedDate(source.getLastModifiedDate());

    map().setComuneC(source.getComuneC());

    map().setProvinciaC(source.getProvinciaC());

    map().setDataAppuntamentoC(source.getDataAppuntamentoC());

    map().setDataPresuntaC(source.getDataPresuntaC());

    map().setNumeroProtocolloC(source.getNumeroProtocolloC());

    map().setRitoC(source.getRitoC());

    map().setuRLPubblicoPdfC(source.getUrLPubblicoPdfC());

    map().setNomeDichiarante(source.getConiuge1NomeC());

    map().setCognomeDichiarante(source.getConiuge1CognomeC());

    map().setNomeConiuge(source.getConiuge2NomeC());

    map().setCognomeConiuge(source.getConiuge2CognomeC());

    map().setCellulareDichiarante(source.getConiuge1CellulareC());

    map().setCellulareConiuge(source.getConiuge2CellulareC());

    map().setEmailDichiarante(source.getConiuge1EmailC());

    map().setEmailConiuge(source.getConiuge2EmailC());

    map().setNominativoDichiarante(source.getDichiarante1formulaC());

    map().setNominativoConiuge(source.getDichiarante2formulaC());

    map().setTipologiaRichiesta(source.getTipologiaPraticaC());
  }
}
