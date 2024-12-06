package it.liguriadigitale.ponmetro.api.business.entitaconfigurazioneutente.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiComparto;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiCompletiComparto;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiCompletiFunzione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiCompletiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiCompletiWidget;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiFunzione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiGestioneNotificaComparto;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiGestioneVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiNotificaComparto;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiWidget;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.EnumEntitaConfigFiltroSospensioniFunzioni;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.EnumFiltroFlag;
import java.util.List;

public interface EntitaConfigurazioneUtenteInterface {
  /* Sezione */
  public List<DatiSezione> getSezioni() throws BusinessException;

  public DatiSezione getSezioneByKey(String sezioneKey) throws BusinessException;

  public DatiCompletiSezione getSezioneCompletiByKey(String sezioneKey) throws BusinessException;

  /* Comparto */
  public List<DatiComparto> getCompartiBySezioneKey(String sezioneKey) throws BusinessException;

  public DatiComparto getCompartoByKey(String compartoKey) throws BusinessException;

  public DatiCompletiComparto getCompartoCompletiByKey(String compartoKey) throws BusinessException;

  /* Funzioni */
  public List<DatiFunzione> getFunzioniByCompartoKey(String compartoKey) throws BusinessException;

  public List<DatiFunzione> getFunzioniByFiltroSospensioniSezioneKeyCompartoKey(
      EnumEntitaConfigFiltroSospensioniFunzioni filtro, String sezioneKey, String compartoKey)
      throws BusinessException;

  public List<DatiFunzione> getFunzioniBySezioneKeyCompartoKey(
      String sezioneKey, String compartoKey) throws BusinessException;

  public DatiFunzione getFunzioneByKey(String funzioneKey) throws BusinessException;

  public DatiCompletiFunzione getFunzioneCompletiByKey(String funzioneKey) throws BusinessException;

  /* Widget */
  public List<DatiWidget> getWidgetsByFunzioneKey(String funzioneKey) throws BusinessException;

  public List<DatiWidget> getWidgetsBySezioneKeyCompartoKeyFunzioneKey(
      String sezioneKey, String compartoKey, String funzioneKey) throws BusinessException;

  public DatiWidget getWidgetByKey(String wicketKey) throws BusinessException;

  List<DatiVisualizzazioneSezioneWidget> getWidgetsCittadinoBySezioneKeyAnagraficaKey(
      String sezioneKey, String anagraficaKey) throws BusinessException;

  List<DatiVisualizzazioneSezioneWidget>
      getWidgetsCittadinoDaVisualizzareOConfigurareBySezioneKeyAnagraficaKey(
          String sezioneKey, String anagraficaKey, boolean getDaVisualizzare, Boolean isResidente)
          throws BusinessException;

  List<DatiVisualizzazioneSezioneWidget> getWidgetsCittadinoConDelega(Boolean isResidente)
      throws BusinessException;

  void setWidgetsCittadinoBySezioneKeyAnagraficaKey(
      String sezioneKey,
      String anagraficaKey,
      List<DatiGestioneVisualizzazioneSezioneWidget> listaDati)
      throws BusinessException;

  List<DatiNotificaComparto> getNoticheCompartiByAnagraficaKey(String anagraficaKey)
      throws BusinessException;

  void setNoticheCompartiByAnagraficaKey(
      String anagraficaKey, List<DatiGestioneNotificaComparto> listaDati) throws BusinessException;

  void setNoticheCompartiBySezioneKeyCompartoKeyAnagraficaKey(
      String sezioneKey,
      String compartoKey,
      String anagraficaKey,
      List<DatiGestioneNotificaComparto> listaDati)
      throws BusinessException;

  public List<DatiCompletiFunzione> getFunzioniCompletiByFilter(EnumFiltroFlag enumFiltroFlag)
      throws BusinessException;

  public List<DatiCompletiComparto> getCompartiCompletiByFilter(EnumFiltroFlag enumFiltroFlag)
      throws BusinessException;

  public List<DatiCompletiWidget> getWidgetsCompletiByFilter(EnumFiltroFlag enumFiltroFlag)
      throws BusinessException;
}
