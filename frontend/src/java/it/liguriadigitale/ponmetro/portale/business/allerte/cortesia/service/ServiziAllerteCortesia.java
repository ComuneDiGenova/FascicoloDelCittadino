package it.liguriadigitale.ponmetro.portale.business.allerte.cortesia.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertecortesia.model.AggiornamentoRecapitiResponse;
import it.liguriadigitale.ponmetro.allertecortesia.model.DettagliUtente;
import it.liguriadigitale.ponmetro.allertecortesia.model.InserimentoResponse;
import it.liguriadigitale.ponmetro.allertecortesia.model.StradeCivici;
import it.liguriadigitale.ponmetro.allertecortesia.model.VerificaServizi;
import it.liguriadigitale.ponmetro.allertecortesia.model.VerificaServiziResponse;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiCompletiRegistrazioneUtenteAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiVerificaCellulareAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import java.io.IOException;
import java.util.List;

public interface ServiziAllerteCortesia {

  List<StepFdC> getListaStep();

  List<StepFdC> getListaStepPreferenze();

  List<BreadcrumbFdC> getListaBreadcrumbAllerteCortesia();

  List<BreadcrumbFdC> getListaBreadcrumbVerificaEmail();

  List<BreadcrumbFdC> getListaBreadcrumbVerificaCellulare();

  List<BreadcrumbFdC> getListaBreadcrumbCambiaCellulare();

  List<BreadcrumbFdC> getListaBreadcrumbRecuperaRegistrazione();

  List<BreadcrumbFdC> getListaBreadcrumbPrimaRegistrazione();

  List<BreadcrumbFdC> getListaBreadcrumbModificaDati();

  List<BreadcrumbFdC> getListaBreadcrumbModificaServizio();

  List<BreadcrumbFdC> getListaBreadcrumbAggiungiPreferenza();

  List<BreadcrumbFdC> getListaBreadcrumbPrivacy();

  List<StepFdC> getListaStepPrimaRegistrazione();

  List<MessaggiInformativi> popolaListaMessaggiCortesia();

  List<MessaggiInformativi> popolaListaMessaggiVerificaEmail();

  List<MessaggiInformativi> popolaListaMessaggiVerificaCellulare();

  List<MessaggiInformativi> popolaListaMessaggiCambiaCellulare();

  List<MessaggiInformativi> popolaListaMessaggiAggiungiPreferenza();

  List<MessaggiInformativi> popolaListaMessaggiAggiungiStrada();

  DettagliUtente getWsLoginByCf(String codiceFiscale)
      throws BusinessException, ApiException, IOException;

  DettagliUtente getWsLoginEMAIL(String email, String pwd, String cf)
      throws BusinessException, ApiException, IOException;

  VerificaServiziResponse getWsGetListaServizi(String email)
      throws BusinessException, ApiException, IOException;

  VerificaServiziResponse getWsGetServizioById(String email, String idServizio)
      throws BusinessException, ApiException, IOException;

  InserimentoResponse putWsPutUtente(
      DatiCompletiRegistrazioneUtenteAllerteCortesia datiCompletiRegistrazioneUtenteAllerteCortesia)
      throws BusinessException, ApiException, IOException;

  AggiornamentoRecapitiResponse putwsUpdateUtente(
      DatiCompletiRegistrazioneUtenteAllerteCortesia datiCompletiRegistrazioneUtenteAllerteCortesia)
      throws BusinessException, ApiException, IOException;

  VerificaServizi putWsIscrizioneAdUnServizio(String email, String idRelazioneServizioCanale)
      throws BusinessException, ApiException, IOException;

  VerificaServizi putWsCancellazioneAdUnServizio(String email, String idRelazioneServizioCanale)
      throws BusinessException, ApiException, IOException;

  StradeCivici getWsGetStradeCivici(
      String email, String idServizio, String strada, String paginazioneStart)
      throws BusinessException, ApiException, IOException;

  InserimentoResponse putWsAggiuntaPreferenzaServizio(
      String email,
      String idServizio,
      String codiceStrada,
      String codiceDivisione,
      String codiceCircoscrizione)
      throws BusinessException, ApiException, IOException;

  InserimentoResponse putWsCancellazionePreferenzaServizio(
      String email,
      String idServizio,
      String codiceStrada,
      String codiceDivisione,
      String codiceCircoscrizione,
      String codiceUnitaUrbanistica)
      throws BusinessException, ApiException, IOException;

  AggiornamentoRecapitiResponse putWsUpdateUtenteTelefonoCellulare(String email, String telefonoNew)
      throws BusinessException, ApiException, IOException;

  AggiornamentoRecapitiResponse putWsPutCheckTelefonoSMS(
      DatiVerificaCellulareAllerteCortesia datiVerifica)
      throws BusinessException, ApiException, IOException;

  AggiornamentoRecapitiResponse getWsGetCheckTelefonoSMS(
      String email, String codiceVerificaTelefono)
      throws BusinessException, ApiException, IOException;

  AggiornamentoRecapitiResponse putWsUpdateUtenteMail(String emailOld, String emailNew)
      throws BusinessException, ApiException, IOException;

  String pulisciStringaStrada(String strada);

  AggiornamentoRecapitiResponse getWsGetCheckEmail(String codice)
      throws BusinessException, ApiException, IOException;
}
