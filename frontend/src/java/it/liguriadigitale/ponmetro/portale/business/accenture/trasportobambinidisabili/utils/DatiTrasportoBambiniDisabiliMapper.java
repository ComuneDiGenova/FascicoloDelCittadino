package it.liguriadigitale.ponmetro.portale.business.accenture.trasportobambinidisabili.utils;

import it.liguriadigitale.ponmetro.accenture.model.ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet200ResponseRecordsInner;
import org.modelmapper.PropertyMap;

public class DatiTrasportoBambiniDisabiliMapper
    extends PropertyMap<
        ServicesDataV580SobjectsAccountIdRichiestePubblicazioneDiMatrimonioRGet200ResponseRecordsInner,
        DatiTrasportoBambiniDisabili> {

  @Override
  protected void configure() {

    map().setName(source.getName());

    map().setCreatedbyId(source.getCreatedById());

    map().setCreatedDate(source.getCreatedDate());

    map().setId(source.getId());

    map().setIsDeleted(source.getIsDeleted());

    map().setLastModifiedDate(source.getLastModifiedDate());

    map().setLastModifiedById(source.getLastModifiedById());

    map().setLastReferencedDate(source.getLastReferencedDate());

    map().setLastViewedDate(source.getLastViewedDate());

    map().setName(source.getName());

    map().setNote__c(source.getNoteC());

    map().setOwnerId(source.getOwnerId());

    map().setSystemModeStamp(source.getSystemModstamp());

    map().setURL_Pubblico_Pdf__c(source.getUrLPubblicoPdfC());
  }
}
