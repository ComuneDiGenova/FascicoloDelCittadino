package it.liguriadigitale.ponmetro.portale.pojo.richiestaassistenza.mapper;

import it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.form.Help;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsCasePostRequest;
import org.modelmapper.PropertyMap;

public class AssistenzaMapper extends PropertyMap<Help, ServicesDataV590SobjectsCasePostRequest> {

  @Override
  protected void configure() {

    map(source.getNome(), destination.getSuppliedFirstNameC());

    map(source.getCognome(), destination.getSuppliedName());

    map(source.getCf(), destination.getCodiceFiscaleC());

    map(source.getEmail(), destination.getSuppliedEmail());

    map(source.getTelefono(), destination.getSuppliedPhone());

    map(source.getOggetto(), destination.getSubject());

    map(source.getDescrizione(), destination.getDescription());

    map(source.getServizioAssistenza().getSottoFascicolo(), destination.getSottofascicoloC());

    map(source.getServizioAssistenza().getId(), destination.getWorkTypeGroupC());

    map(source.getPrivacy(), destination.getPrivacyC());
  }
}
