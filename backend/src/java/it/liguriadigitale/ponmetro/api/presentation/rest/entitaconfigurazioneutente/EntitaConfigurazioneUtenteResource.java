package it.liguriadigitale.ponmetro.api.presentation.rest.entitaconfigurazioneutente;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.entitaconfigurazioneutente.service.EntitaConfigurazioneUtenteInterface;
import it.liguriadigitale.ponmetro.api.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.api.presentation.rest.application.exception.BadRequestException;
import it.liguriadigitale.ponmetro.api.presentation.rest.application.exception.PartialContentException;
import it.liguriadigitale.ponmetro.api.presentation.rest.application.exception.ResourceNotFoundException;
import it.liguriadigitale.ponmetro.api.util.CommonUtil;
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
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;

public class EntitaConfigurazioneUtenteResource implements EntitaConfigurazioneUtenteApi {
  private static Log log = LogFactory.getLog(EntitaConfigurazioneUtenteResource.class);

  private static String IS_DELEGHE = "-9999";

  @Override
  public List<DatiComparto> catalogoCompartiIdSezioneGet(String arg0, Integer arg1, Integer arg2) {
    log.debug("Inizio -- EntitaConfigurazioneUtenteResource.catalogoCompartiIdSezioneGet");
    log.debug(" arg0 idSezione: " + arg0);
    log.debug(" arg1 limit: " + arg1);
    log.debug(" arg2 offset: " + arg2);
    List<DatiComparto> listDatiComparto = new ArrayList<>();
    try {
      // limit/ offset cercaoggetti da fare (helper tablegen)
      EntitaConfigurazioneUtenteInterface service =
          ServiceLocator.getInstance().getEntitaConfigurazioneUtente();
      listDatiComparto =
          CommonUtil.manageOffsetAndLimit(service.getCompartiBySezioneKey(arg0), arg2, arg1);
    } catch (BusinessException e) {
      log.error(e);
      throw new BadRequestException(e.getMessage());
    } catch (Exception e) {
      String s = "Generic error get lista comparti";
      log.error(s + e);
      throw new BadRequestException(s);
    }
    if (listDatiComparto == null || listDatiComparto.isEmpty()) {
      String s = "Nessun comparto trovato per la sezione: " + arg0;
      log.warn(s);
      throw new NotFoundException(s);
    }
    return listDatiComparto;
  }

  @Override
  public List<DatiFunzione> catalogoFunzioniFiltroSospensioniIdSezioneIdCompartoGet(
      EnumEntitaConfigFiltroSospensioniFunzioni arg0,
      String arg1,
      String arg2,
      Integer arg3,
      Integer arg4) {
    log.debug(
        "Inizio -- EntitaConfigurazioneUtenteResource.catalogoFunzioniFiltroSospensioniIdSezioneIdCompartoGet");
    log.debug("arg0 EnumEntitaConfigFiltroSospensioniFunzioni: " + arg0);
    log.debug("arg1 idSezione: " + arg1);
    log.debug("arg2 idComparto: " + arg2);
    log.debug("arg3 limit: " + arg3);
    log.debug("arg4 offset: " + arg4);
    List<DatiFunzione> listDatiFunzione = new ArrayList<>();
    try {
      // limit/ offset cercaoggetti da fare (helper tablegen)
      EntitaConfigurazioneUtenteInterface service =
          ServiceLocator.getInstance().getEntitaConfigurazioneUtente();
      listDatiFunzione =
          CommonUtil.manageOffsetAndLimit(
              // entitaConfigurazioneUtenteImpl.getFunzioniByCompartoKey(arg2),
              service.getFunzioniByFiltroSospensioniSezioneKeyCompartoKey(arg0, arg1, arg2),
              arg4,
              arg3);
    } catch (BusinessException e) {
      log.error(e);
      throw new BadRequestException(e.getMessage());
    } catch (Exception e) {
      String s = "Generic error get lista funzioni";
      log.error(s + e);
      throw new BadRequestException(s);
    }
    if (listDatiFunzione == null || listDatiFunzione.isEmpty()) {
      String s = "Nessuna funzione trovata per il comparto: " + arg1;
      log.warn(s);
      throw new NotFoundException(s);
    }
    return listDatiFunzione;
  }

  @Override
  public List<DatiSezione> catalogoSezioniGet(Integer arg0, Integer arg1) {
    log.debug("Inizio -- EntitaConfigurazioneUtenteResource.catalogoSezioniGet");
    log.debug("arg0 limit: " + arg0);
    log.debug("arg1 offset: " + arg1);
    List<DatiSezione> listDatiSezione = new ArrayList<>();
    try {
      // limit/ offset cercaoggetti da fare (helper tablegen)
      EntitaConfigurazioneUtenteInterface service =
          ServiceLocator.getInstance().getEntitaConfigurazioneUtente();
      listDatiSezione = CommonUtil.manageOffsetAndLimit(service.getSezioni(), arg1, arg0);
    } catch (BusinessException e) {
      log.error(e);
      throw new BadRequestException(e.getMessage());
    } catch (Exception e) {
      String s = " Generic error get lista sezioni ";
      log.error(s + e);
      throw new BadRequestException(s);
    }
    if (listDatiSezione == null || listDatiSezione.isEmpty()) {
      String s = " Nessuna sezione trovata ";
      log.warn(s);
      throw new NotFoundException(s);
    }
    return listDatiSezione;
  }

  @Override
  public List<DatiWidget> catalogoWidgetsIdSezioneIdCompartoIdFunzioneGet(
      String arg0, String arg1, String arg2, Integer arg3, Integer arg4) {
    log.debug(
        "Inizio -- EntitaConfigurazioneUtenteResource.catalogoWidgetsIdSezioneIdCompartoIdFunzioneGet");
    log.debug("arg0 idSezione: " + arg0);
    log.debug("arg1 idComparto: " + arg1);
    log.debug("arg2 idFunzione: " + arg2);
    log.debug("arg3 limit: " + arg3);
    log.debug("arg4 offset: " + arg4);
    List<DatiWidget> listDatiWidget = new ArrayList<>();
    try {
      // limit/ offset cercaoggetti da fare (helper tablegen)
      EntitaConfigurazioneUtenteInterface service =
          ServiceLocator.getInstance().getEntitaConfigurazioneUtente();
      listDatiWidget =
          CommonUtil.manageOffsetAndLimit(service.getWidgetsByFunzioneKey(arg2), arg4, arg3);
    } catch (BusinessException e) {
      log.error(e);
      throw new BadRequestException(e.getMessage());
    } catch (Exception e) {
      String s = " Generic error get lista widget ";
      log.error(s + e);
      throw new BadRequestException(s);
    }
    if (listDatiWidget == null || listDatiWidget.isEmpty()) {
      String s = " Nessun widget trovato ";
      log.warn(s);
      throw new NotFoundException(s);
    }
    return listDatiWidget;
  }

  @Override
  public DatiCompletiComparto compartoIdCompartoGet(String arg0) {
    log.debug("Inizio -- EntitaConfigurazioneUtenteResource.compartoIdCompartoGet");
    return null;
  }

  @Override
  public DatiCompletiFunzione funzioneIdFunzioneGet(String arg0) {
    log.debug("Inizio -- EntitaConfigurazioneUtenteResource.funzioneIdFunzioneGet");
    return null;
  }

  @Override
  public List<DatiCompletiComparto> compartiSezioniFiltroFlagGet(EnumFiltroFlag arg0) {
    log.debug("Inizio -- EntitaConfigurazioneUtenteResource.compartiSezioniFiltroFlagGet");
    log.debug("arg0 enumFiltroFlag: " + arg0);
    List<DatiCompletiComparto> listDatiCompletiComparto = new ArrayList<>();
    try {
      EntitaConfigurazioneUtenteInterface service =
          ServiceLocator.getInstance().getEntitaConfigurazioneUtente();
      listDatiCompletiComparto = service.getCompartiCompletiByFilter(arg0);
    } catch (BusinessException e) {
      log.error(e);
      throw new BadRequestException(e.getMessage());
    } catch (Exception e) {
      String s = "Generic error get lista comparto";
      log.error(s + e);
      throw new BadRequestException(s);
    }
    if (listDatiCompletiComparto == null || listDatiCompletiComparto.isEmpty()) {
      String s = " Nessun comparto trovato, controllare il filtro ";
      log.warn(s);
      throw new NotFoundException(s);
    }
    return listDatiCompletiComparto;
  }

  @Override
  public List<DatiCompletiFunzione> funzioniCompartiSezioniFiltroFlagGet(EnumFiltroFlag arg0) {
    log.debug("Inizio -- EntitaConfigurazioneUtenteResource.funzioniCompartiSezioniFiltroFlagGet");
    log.debug("arg0 enumFiltroFlag: " + arg0);
    List<DatiCompletiFunzione> listDatiCompletiFunzione = new ArrayList<>();
    try {
      EntitaConfigurazioneUtenteInterface service =
          ServiceLocator.getInstance().getEntitaConfigurazioneUtente();
      listDatiCompletiFunzione = service.getFunzioniCompletiByFilter(arg0);
    } catch (BusinessException e) {
      log.error(e);
      throw new BadRequestException(e.getMessage());
    } catch (Exception e) {
      String s = "Generic error get lista funzioni";
      log.error(s + e);
      throw new BadRequestException(s);
    }
    if (listDatiCompletiFunzione == null || listDatiCompletiFunzione.isEmpty()) {
      String s = " Nessuna funzione trovata, controllare il filtro ";
      log.warn(s);
      throw new NotFoundException(s);
    }
    return listDatiCompletiFunzione;
  }

  @Override
  public List<DatiCompletiWidget> widgetFunzioniCompartiSezioniFiltroFlagGet(EnumFiltroFlag arg0) {
    log.debug(
        "Inizio -- EntitaConfigurazioneUtenteResource.widgetFunzioniCompartiSezioniFiltroFlagGet");
    log.debug("arg0 enumFiltroFlag: " + arg0);
    List<DatiCompletiWidget> listDatiCompletiWidget = new ArrayList<>();
    try {
      EntitaConfigurazioneUtenteInterface service =
          ServiceLocator.getInstance().getEntitaConfigurazioneUtente();
      listDatiCompletiWidget = service.getWidgetsCompletiByFilter(arg0);
    } catch (BusinessException e) {
      log.error(e);
      throw new BadRequestException(e.getMessage());
    } catch (Exception e) {
      String s = "Generic error get lista widget";
      log.error(s + e);
      throw new BadRequestException(s);
    }
    if (listDatiCompletiWidget == null || listDatiCompletiWidget.isEmpty()) {
      String s = " Nessun widget trovato, controllare il filtro ";
      log.warn(s);
      throw new NotFoundException(s);
    }
    return listDatiCompletiWidget;
  }

  @Override
  public List<DatiNotificaComparto> notificheCompartiIdSezioneIdCompartoIdAnagraficaGet(
      String arg0, String arg1, String arg2) {
    log.debug("Inizio -- EntitaConfigurazioneUtenteResource.notificheCompartiIdAnagraficaGet");
    log.debug("arg0 idSezione: " + arg0);
    log.debug("arg1 idComparto: " + arg1);
    log.debug("arg2 idAnagrafica: " + arg2);
    List<DatiNotificaComparto> listDatiNotificaComparto = new ArrayList<>();
    try {
      EntitaConfigurazioneUtenteInterface service =
          ServiceLocator.getInstance().getEntitaConfigurazioneUtente();
      listDatiNotificaComparto = service.getNoticheCompartiByAnagraficaKey(arg2);
    } catch (BusinessException e) {
      log.error(e);
      throw new BadRequestException(e.getMessage());
    } catch (Exception e) {
      String s = "Generic error get lista notifica per comparto";
      log.error(s + e);
      throw new BadRequestException(s);
    }
    if (listDatiNotificaComparto == null || listDatiNotificaComparto.isEmpty()) {
      String s = " Nessun comparto trovato ";
      log.warn(s);
      throw new NotFoundException(s);
    }
    return listDatiNotificaComparto;
  }

  @Override
  public void notificheCompartiIdSezioneIdCompartoIdAnagraficaPut(
      String arg0, String arg1, String arg2, List<DatiGestioneNotificaComparto> arg3) {
    log.debug(
        "Inizio -- EntitaConfigurazioneUtenteResource.notificheCompartiIdSezioneIdCompartoIdAnagraficaPut");
    log.debug(" arg0 idSezione: " + arg0);
    log.debug(" arg1 idComparto: " + arg1);
    log.debug(" arg2 idAnagrafica: " + arg2);
    log.debug(" arg3 List<DatiGestioneNotificaComparto>: " + arg3);
    try {
      EntitaConfigurazioneUtenteInterface service =
          ServiceLocator.getInstance().getEntitaConfigurazioneUtente();
      service.setNoticheCompartiByAnagraficaKey(arg2, arg3);
      log.debug("entitaConfigurazioneUtenteImpl.setNoticheCompartiByAnagraficaKey called right");
    } catch (BusinessException e) {
      utilManageBusinessException(e);
    } catch (Exception e) {
      String s = " Generic error set lista notifiche comparti ";
      log.error(s + e);
      throw new BadRequestException(s);
    }
  }

  @Override
  public DatiCompletiSezione sezioneIdSezioneGet(String arg0) {
    log.debug("Inizio -- EntitaConfigurazioneUtenteResource.sezioneIdSezioneGet");
    log.debug("arg0 idsezione: " + arg0);
    DatiCompletiSezione datiCompletiSezione = null;
    try {
      EntitaConfigurazioneUtenteInterface service =
          ServiceLocator.getInstance().getEntitaConfigurazioneUtente();
      datiCompletiSezione = service.getSezioneCompletiByKey(arg0);
    } catch (BusinessException e) {
      log.error(e);
      throw new BadRequestException(e.getMessage());
    } catch (Exception e) {
      String s = "Generic error get lista sezioni";
      log.error(s + e);
      throw new BadRequestException(s);
    }
    if (datiCompletiSezione == null) {
      String s = "Nessuna sezione trovata";
      log.warn(s);
      throw new NotFoundException(s);
    }
    return datiCompletiSezione;
  }

  @Override
  public List<DatiVisualizzazioneSezioneWidget>
      sezioneWidgetDaConfigurareIdSezioneIdAnagraficaFiltroFlagIsResidenteGet(
          String idSezione, String idAnagrafica, EnumFiltroFlag filtroFlag, Boolean isResidente) {
    log.debug(
        "Inizio -- EntitaConfigurazioneUtenteResource.sezioneWidgetDaVisualizzareIdSezioneIdAnagraficaGet");
    log.debug("arg0 idSezione: " + idSezione);
    log.debug("arg1 idAnagrafica: " + idAnagrafica);
    log.debug("filtroFlag EnumFiltroFlag: " + filtroFlag);
    List<DatiVisualizzazioneSezioneWidget> listDatiVisualizzareSezioneWidget = new ArrayList<>();
    try {
      // booleano per ora a false, cioe' dammi tutti senza filtrare
      EntitaConfigurazioneUtenteInterface service =
          ServiceLocator.getInstance().getEntitaConfigurazioneUtente();
      listDatiVisualizzareSezioneWidget =
          service.getWidgetsCittadinoDaVisualizzareOConfigurareBySezioneKeyAnagraficaKey(
              idSezione, idAnagrafica, false, isResidente);
    } catch (BusinessException e) {
      log.error(e);
      throw new BadRequestException(e.getMessage());
    } catch (Exception e) {
      String s = "Generic error get lista widget";
      log.error(s + e);
      throw new BadRequestException(s);
    }
    if (listDatiVisualizzareSezioneWidget == null || listDatiVisualizzareSezioneWidget.isEmpty()) {
      String s = "Nessun widget per la sezione da visualizzare trovato";
      log.warn(s);
      throw new NotFoundException(s);
    }
    return listDatiVisualizzareSezioneWidget;
  }

  @Override
  public List<DatiVisualizzazioneSezioneWidget>
      sezioneWidgetDaVisualizzareIdSezioneIdAnagraficaIsResidenteGet(
          String sezione, String idFcitt, Boolean isResidente) {
    log.debug(
        "Inizio -- EntitaConfigurazioneUtenteResource.sezioneWidgetDaVisualizzareIdSezioneIdAnagraficaGet");
    log.debug("arg0 idSezione: " + sezione);
    log.debug("arg1 idAnagrafica: " + idFcitt);
    log.debug("arg1 isResidente: " + isResidente);
    List<DatiVisualizzazioneSezioneWidget> listDatiVisualizzareSezioneWidget = new ArrayList<>();

    try {
      EntitaConfigurazioneUtenteInterface service =
          ServiceLocator.getInstance().getEntitaConfigurazioneUtente();
      if (IS_DELEGHE.equals(sezione)) {
        listDatiVisualizzareSezioneWidget = service.getWidgetsCittadinoConDelega(isResidente);
      } else {
        listDatiVisualizzareSezioneWidget =
            service.getWidgetsCittadinoDaVisualizzareOConfigurareBySezioneKeyAnagraficaKey(
                sezione, idFcitt, true, isResidente);
      }
    } catch (BusinessException e) {
      log.error(e);
      throw new BadRequestException(e.getMessage());
    } catch (Exception e) {
      String s = "Generic error get lista widget";
      log.error(s + e);
      throw new BadRequestException(s);
    }
    if (listDatiVisualizzareSezioneWidget == null || listDatiVisualizzareSezioneWidget.isEmpty()) {
      String s = "Nessun widget per la sezione da visualizzare trovato";
      log.warn(s);
      throw new NotFoundException(s);
    }
    return listDatiVisualizzareSezioneWidget;
  }

  /**
   * Ritorna la lista dei settaggi per il cittadino. La lista contiene la storia di tutti i
   * settaggi.
   */
  @Override
  public List<DatiVisualizzazioneSezioneWidget> sezioneWidgetGestisciIdSezioneIdAnagraficaGet(
      String arg0, String arg1) {
    log.debug(
        "Inizio -- EntitaConfigurazioneUtenteResource.sezioneWidgetGestisciIdSezioneIdAnagraficaGet");
    log.debug("arg0 idSezione: " + arg0);
    log.debug("arg1 idAnagrafica: " + arg1);
    List<DatiVisualizzazioneSezioneWidget> listDatiVisualizzareSezioneWidget = new ArrayList<>();
    try {
      EntitaConfigurazioneUtenteInterface service =
          ServiceLocator.getInstance().getEntitaConfigurazioneUtente();
      listDatiVisualizzareSezioneWidget =
          service.getWidgetsCittadinoBySezioneKeyAnagraficaKey(arg0, arg1);
    } catch (BusinessException e) {
      log.error(e);
      throw new BadRequestException(e.getMessage());
    } catch (Exception e) {
      String s = "Generic error get lista widget";
      log.error(s + e);
      throw new BadRequestException(s);
    }
    if (listDatiVisualizzareSezioneWidget == null || listDatiVisualizzareSezioneWidget.isEmpty()) {
      String s = "Nessun widget trovato";
      log.warn(s);
      throw new NotFoundException(s);
    }
    return listDatiVisualizzareSezioneWidget;
  }

  @Override
  public void sezioneWidgetGestisciIdSezioneIdAnagraficaPut(
      String arg0, String arg1, List<DatiGestioneVisualizzazioneSezioneWidget> arg2) {
    log.debug(
        "Inizio -- EntitaConfigurazioneUtenteResource.sezioneWidgetGestisciIdSezioneIdAnagraficaPut");
    log.debug("arg0 idSezione: " + arg0);
    log.debug("arg1 idAnagrafica: " + arg1);
    log.debug("arg2 List<DatiGestioneVisualizzazioneSezioneWidget>: " + arg2);

    try {
      EntitaConfigurazioneUtenteInterface service =
          ServiceLocator.getInstance().getEntitaConfigurazioneUtente();
      service.setWidgetsCittadinoBySezioneKeyAnagraficaKey(arg0, arg1, arg2);
    } catch (BusinessException e) {
      log.error(e);
      throw new BadRequestException(e.getMessage());
    } catch (Exception e) {
      String s = "Generic error get lista widget";
      log.error(s + e);
      throw new BadRequestException(s);
    }
  }

  @Override
  public DatiWidget widgetIdWidgetGet(String arg0) {
    log.debug("Inizio -- EntitaConfigurazioneUtenteResource.widgetIdWidgetGet");
    log.debug("arg0 idWidget: " + arg0);
    DatiWidget datiWidget = new DatiWidget();
    try {
      EntitaConfigurazioneUtenteInterface service =
          ServiceLocator.getInstance().getEntitaConfigurazioneUtente();
      datiWidget = service.getWidgetByKey(arg0);
    } catch (BusinessException e) {
      utilManageBusinessException(e);
    } catch (Exception e) {
      String s = "Generic error get widget";
      log.error(s + e);
      throw new BadRequestException(s);
    }
    if (datiWidget == null) {
      String s = "Widget " + arg0 + "non trovato";
      log.warn(s);
      throw new NotFoundException(s);
    }
    return datiWidget;
  }

  private void utilManageBusinessException(BusinessException e) {
    log.error("EntitaConfigurazioneUtenteResource.utilManageBusinessException BE: " + e);
    if (StringUtils.startsWith(e.getMessage(), String.valueOf(HttpStatus.SC_PARTIAL_CONTENT))) {
      log.error("SC_PARTIAL_CONTENT");
      throw new PartialContentException(e.getMessage());
    } else if (StringUtils.startsWith(e.getMessage(), String.valueOf(HttpStatus.SC_NO_CONTENT))) {
      log.error("ResourceNotFoundException");
      throw new ResourceNotFoundException(e.getMessage());
    } else if (StringUtils.startsWith(e.getMessage(), String.valueOf(HttpStatus.SC_NOT_FOUND))) {
      log.error("NotFoundException");
      throw new NotFoundException(e.getMessage());
    }
    log.error("BadRequestException");
    throw new BadRequestException(e.getMessage());
  }

  @Override
  public AllEnumsEntitaConfig allEnumsGet() {
    // TODO Auto-generated method stub
    return null;
  }
}
