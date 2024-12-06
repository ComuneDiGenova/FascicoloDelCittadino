package it.liguriadigitale.ponmetro.portale.business.configurazione.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.response.PrivacyResponse;
import it.liguriadigitale.ponmetro.configurazione.model.ImagineCaricata;
import it.liguriadigitale.ponmetro.contattiutente.model.ContattiUtente;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiCompletiFunzione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiNotificaComparto;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.portale.AggiungiFiglio;
import it.liguriadigitale.ponmetro.portale.pojo.portale.MinoreConvivente;
import it.liguriadigitale.ponmetro.portale.pojo.portale.configurazione.WidgetSelezionati;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ConfigurazioneInterface {

  public byte[] getInformativa(Utente utente) throws BusinessException;

  public PrivacyResponse presaVisione(Utente utente) throws BusinessException;

  public MinoreConvivente updateMinoreConviventePerDichiazioneGenitore(
      MinoreConvivente minore, Utente utente) throws BusinessException;

  public AggiungiFiglio updateMinoreConviventePerDichiarazioneGenitoreNonResidente(
      AggiungiFiglio minore, Utente utente) throws BusinessException;

  public AggiungiFiglio insertMinoreConviventePerDichiarazioneGenitoreNonResidente(
      AggiungiFiglio minore, Utente utente) throws BusinessException;

  public MinoreConvivente cancellaMinoreConviventePerDichiazioneGenitore(
      MinoreConvivente minore, Utente utente) throws BusinessException;

  /* Configurazione Widget */

  public List<DatiVisualizzazioneSezioneWidget> getListaWidgetHomePage(Utente utente)
      throws BusinessException;

  public List<DatiVisualizzazioneSezioneWidget> getListaWidgetPerSezione(
      Utente utente, DatiSezione sezione) throws BusinessException;

  public void updateWidget(Utente utente, WidgetSelezionati sezione) throws BusinessException;

  public List<DatiVisualizzazioneSezioneWidget> getListaWidgetPerConfigurazione(Utente utente)
      throws BusinessException;

  /* Configurazione Notifiche */

  public List<DatiNotificaComparto> getListaTutteNotificheComparti(Utente utente)
      throws BusinessException, ApiException;

  public void updateNotifica(DatiNotificaComparto dati, Utente utente, Boolean isSelezionato)
      throws BusinessException, ApiException;

  public void updateNotificheConBtnSalva(
      Utente utente, Map<DatiNotificaComparto, Boolean> hashMapNotifiche)
      throws BusinessException, ApiException;

  /* Abilitazione Funzioni */

  public List<DatiCompletiFunzione> getListaFunzioni(Utente utente) throws BusinessException;

  public List<FunzioniDisponibili> getFunzioniAbilitate() throws BusinessException;

  /* Configurazione Avatar */

  public byte[] getImagineCaricata(Utente utente)
      throws BusinessException, ApiException, IOException;

  public ImagineCaricata uploadAvatar(Utente utente, File avatarFile) throws BusinessException;

  /* Informativa privacy Sebina */

  public byte[] getInformativaSebina(Utente utente) throws BusinessException;

  public PrivacyResponse presaVisioneSebina(Utente utente) throws BusinessException;

  //	public List<ChiaveValore> getListaInEvidenzaOInRealizzazione(Utente utente, String chiave)
  // throws BusinessException;

  public byte[] getInformativaServiziCortesia(Utente utente) throws BusinessException;

  public PrivacyResponse presaVisioneServiziCortesia(Utente utente) throws BusinessException;

  public byte[] getInformativaServiziCedole(Utente utente) throws BusinessException;

  public PrivacyResponse presaVisioneServiziCedole(Utente utente) throws BusinessException;

  public byte[] getInformativaServiziTeleriscaldamento(Utente utente) throws BusinessException;

  /* Configurazione contatti utente */
  public List<ContattiUtente> getContattiUtente(Utente utente)
      throws BusinessException, ApiException, IOException;

  public void upInsertContattiUtente(
      Utente utente, String tipo, ContattiUtente contattiUtenteDaAggiornare)
      throws BusinessException, ApiException, IOException;

  public int getGiorniPerAggiornareContatti();

  public void cancellaContattiUtente(Utente utente, String tipo)
      throws BusinessException, ApiException, IOException;

  /* Informativa privacy Brav */

  byte[] getInformativaServiziBrav(Utente utente) throws BusinessException;

  PrivacyResponse presaVisioneServiziBrav(Utente utente) throws BusinessException;

  /* Informativa privacy Canone Idrico */

  byte[] getInformativaServiziCanoneIdrico(Utente utente) throws BusinessException;

  PrivacyResponse presaVisioneServiziCanoneIdrico(Utente utente) throws BusinessException;
}
