package it.liguriadigitale.ponmetro.portale.business.allerte.zonarossa.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Contatto;
import it.liguriadigitale.ponmetro.allertezonarossa.model.DettagliUtente;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Indirizzo;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Lingua;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Problem;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Utente;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.CivicoZonaRossa;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.ComponenteNucleoZonaRossa;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.ContattoTelefonicoZonaRossa;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.DatiCompletiRegistrazioneUtenteAllerteZonaRossa;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.DettagliUtenteZonaRossa;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import java.io.IOException;
import java.util.List;

public interface ServiziAllerteZonaRossa {

  List<BreadcrumbFdC> getListaBreadcrumbAllerte();

  List<BreadcrumbFdC> getListaBreadcrumbAllerteZonaRossa();

  List<BreadcrumbFdC> getListaBreadcrumbAllerteZonaRossaRegistrazione();

  List<BreadcrumbFdC> getListaBreadcrumbCancellaComponente();

  List<BreadcrumbFdC> getListaBreadcrumbDettagliCivico();

  List<BreadcrumbFdC> getListaBreadcrumbAggiungiCivico();

  List<BreadcrumbFdC> getListaBreadcrumbAggiungiContatto();

  List<BreadcrumbFdC> getListaBreadcrumbAggiungiComponente();

  List<MessaggiInformativi> popolaListaMessaggiZonaRossa();

  List<MessaggiInformativi> popolaListaMessaggiCancellaComponente();

  List<StepFdC> getListaStep();

  List<StepFdC> getListaStepAggiungiCivico();

  List<StepFdC> getListaStepAggiungiTelefono();

  List<StepFdC> getListaStepAggiungiComponente();

  DettagliUtente getDettagliUtente(String codiceFiscale)
      throws BusinessException, ApiException, IOException;

  DettagliUtenteZonaRossa getDettagliZonaRossa(DettagliUtente dettagliUtente);

  Utente registraUtente(
      DatiCompletiRegistrazioneUtenteAllerteZonaRossa
          dettagliCompletiRegistrazioneUtenteAllerteZonaRossa)
      throws BusinessException, ApiException, IOException;

  Indirizzo aggiungiCivico(CivicoZonaRossa civicoZonaRossa)
      throws BusinessException, ApiException, IOException;

  Contatto aggiungiTelefono(ContattoTelefonicoZonaRossa contattoTelefonico)
      throws BusinessException, ApiException, IOException;

  Problem deleteComponente(Integer idUtente, Integer idCivico, String motivo)
      throws BusinessException, ApiException, IOException;

  Problem deleteTelefono(Integer idUtente) throws BusinessException, ApiException, IOException;

  Problem aggiungiComponente(ComponenteNucleoZonaRossa componenteZonaRossa)
      throws BusinessException, ApiException, IOException;

  Problem aggiungiComponenteCivico(ComponenteNucleoZonaRossa componenteZonaRossa)
      throws BusinessException, ApiException, IOException;

  List<Lingua> getLingue() throws BusinessException, ApiException, IOException;
}
