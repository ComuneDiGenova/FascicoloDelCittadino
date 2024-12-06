package it.liguriadigitale.ponmetro.portale.business.rest.impl.backend;

import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.apiclient.EntitaConfigurazioneUtenteApi;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.AllEnumsEntitaConfig;
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

public class ApiEntitaConfigurazioneUtenteImpl implements EntitaConfigurazioneUtenteApi {

  private EntitaConfigurazioneUtenteApi instance;

  public ApiEntitaConfigurazioneUtenteImpl(EntitaConfigurazioneUtenteApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public List<DatiComparto> catalogoCompartiIdSezioneGet(String arg0, Integer arg1, Integer arg2) {
    return instance.catalogoCompartiIdSezioneGet(arg0, arg1, arg2);
  }

  @Override
  public List<DatiFunzione> catalogoFunzioniFiltroSospensioniIdSezioneIdCompartoGet(
      EnumEntitaConfigFiltroSospensioniFunzioni arg0,
      String arg1,
      String arg2,
      Integer arg3,
      Integer arg4) {
    return instance.catalogoFunzioniFiltroSospensioniIdSezioneIdCompartoGet(
        arg0, arg1, arg2, arg3, arg4);
  }

  @Override
  public List<DatiSezione> catalogoSezioniGet(Integer arg0, Integer arg1) {
    return instance.catalogoSezioniGet(arg0, arg1);
  }

  @Override
  public List<DatiWidget> catalogoWidgetsIdSezioneIdCompartoIdFunzioneGet(
      String arg0, String arg1, String arg2, Integer arg3, Integer arg4) {
    return instance.catalogoWidgetsIdSezioneIdCompartoIdFunzioneGet(arg0, arg1, arg2, arg3, arg4);
  }

  @Override
  public DatiCompletiComparto compartoIdCompartoGet(String arg0) {
    return instance.compartoIdCompartoGet(arg0);
  }

  @Override
  public DatiCompletiFunzione funzioneIdFunzioneGet(String arg0) {
    return instance.funzioneIdFunzioneGet(arg0);
  }

  @Override
  public List<DatiNotificaComparto> notificheCompartiIdSezioneIdCompartoIdAnagraficaGet(
      String arg0, String arg1, String arg2) {
    return instance.notificheCompartiIdSezioneIdCompartoIdAnagraficaGet(arg0, arg1, arg2);
  }

  @Override
  public void notificheCompartiIdSezioneIdCompartoIdAnagraficaPut(
      String arg0, String arg1, String arg2, List<DatiGestioneNotificaComparto> arg3) {
    instance.notificheCompartiIdSezioneIdCompartoIdAnagraficaPut(arg0, arg1, arg2, arg3);
  }

  @Override
  public DatiCompletiSezione sezioneIdSezioneGet(String arg0) {
    return instance.sezioneIdSezioneGet(arg0);
  }

  @Override
  public List<DatiVisualizzazioneSezioneWidget> sezioneWidgetGestisciIdSezioneIdAnagraficaGet(
      String arg0, String arg1) {
    return instance.sezioneWidgetGestisciIdSezioneIdAnagraficaGet(arg0, arg1);
  }

  @Override
  public void sezioneWidgetGestisciIdSezioneIdAnagraficaPut(
      String arg0, String arg1, List<DatiGestioneVisualizzazioneSezioneWidget> arg2) {
    instance.sezioneWidgetGestisciIdSezioneIdAnagraficaPut(arg0, arg1, arg2);
  }

  @Override
  public DatiWidget widgetIdWidgetGet(String arg0) {
    return instance.widgetIdWidgetGet(arg0);
  }

  @Override
  public AllEnumsEntitaConfig allEnumsGet() {
    return instance.allEnumsGet();
  }

  @Override
  public List<DatiCompletiComparto> compartiSezioniFiltroFlagGet(EnumFiltroFlag arg0) {
    return instance.compartiSezioniFiltroFlagGet(arg0);
  }

  @Override
  public List<DatiCompletiFunzione> funzioniCompartiSezioniFiltroFlagGet(EnumFiltroFlag arg0) {
    return instance.funzioniCompartiSezioniFiltroFlagGet(arg0);
  }

  @Override
  public List<DatiCompletiWidget> widgetFunzioniCompartiSezioniFiltroFlagGet(EnumFiltroFlag arg0) {
    return instance.widgetFunzioniCompartiSezioniFiltroFlagGet(arg0);
  }

  @Override
  public List<DatiVisualizzazioneSezioneWidget>
      sezioneWidgetDaConfigurareIdSezioneIdAnagraficaFiltroFlagIsResidenteGet(
          String arg0, String arg1, EnumFiltroFlag arg2, Boolean arg3) {
    return instance.sezioneWidgetDaConfigurareIdSezioneIdAnagraficaFiltroFlagIsResidenteGet(
        arg0, arg1, arg2, arg3);
  }

  @Override
  public List<DatiVisualizzazioneSezioneWidget>
      sezioneWidgetDaVisualizzareIdSezioneIdAnagraficaIsResidenteGet(
          String arg0, String arg1, Boolean arg2) {
    return instance.sezioneWidgetDaVisualizzareIdSezioneIdAnagraficaIsResidenteGet(
        arg0, arg1, arg2);
  }
}
